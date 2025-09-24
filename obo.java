<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>Draw Faults</title>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.8.2/angular.min.js"></script>
    <style>
      body {
        font-family: "Segoe UI", Tahoma, Geneva, Verdana, sans-serif;
        margin: 0;
        padding: 20px;
        background-color: #f5f5f5;
      }

      .container {
        max-width: 1200px;
        margin: 0 auto;
        background-color: white;
        border-radius: 8px;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        padding: 20px;
      }

      h1 {
        color: #333;
        text-align: center;
        margin-bottom: 30px;
      }

      .image-grid {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
        gap: 20px;
        margin-bottom: 30px;
      }

      .image-item {
        border-radius: 6px;
        overflow: hidden;
        cursor: pointer;
        transition: transform 0.3s, box-shadow 0.3s;
        box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
      }

      .image-item:hover {
        transform: translateY(-5px);
        box-shadow: 0 5px 15px rgba(0, 0, 0, 0.2);
      }

      .image-item img {
        width: 100%;
        height: 150px;
        object-fit: cover;
        display: block;
      }

      .image-item p {
        margin: 0;
        padding: 10px;
        background-color: #f0f0f0;
        text-align: center;
        font-weight: 500;
      }

      .modal-overlay {
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.7);
        display: flex;
        justify-content: center;
        align-items: center;
        z-index: 1000;
      }

      .modal-content {
        background-color: white;
        border-radius: 8px;
        max-width: 90%;
        max-height: 90%;
        overflow: auto;
        position: relative;
        padding: 20px;
      }

      .image-container {
        position: relative;
        display: inline-block;
        max-width: 100%;
      }

      .image-container img {
        max-width: 100%;
        height: auto;
        display: block;
      }

      .annotation-canvas {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        cursor: crosshair;
      }

      .modal-controls {
        margin-top: 20px;
        display: flex;
        justify-content: space-between;
        align-items: center;
      }

      .btn {
        padding: 10px 20px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        font-weight: 500;
        transition: background-color 0.3s;
      }

      .btn-primary {
        background-color: #4caf50;
        color: white;
      }

      .btn-primary:hover {
        background-color: #45a049;
      }

      .btn-secondary {
        background-color: #f44336;
        color: white;
      }

      .btn-secondary:hover {
        background-color: #da190b;
      }

      .btn-clear {
        background-color: #ff9800;
        color: white;
      }

      .btn-clear:hover {
        background-color: #e68900;
      }

      .coordinates-display {
        background-color: #f9f9f9;
        padding: 10px;
        border-radius: 4px;
        margin-top: 10px;
        font-family: monospace;
      }

      .saved-rectangles {
        margin-top: 20px;
        max-height: 150px;
        overflow-y: auto;
        border: 1px solid #ddd;
        border-radius: 4px;
        padding: 10px;
      }

      .rectangle-item {
        padding: 5px;
        margin-bottom: 5px;
        background-color: #f0f0f0;
        border-radius: 3px;
        display: flex;
        justify-content: space-between;
      }

      .rectangle-item button {
        background-color: #ff5252;
        color: white;
        border: none;
        border-radius: 3px;
        cursor: pointer;
        padding: 2px 8px;
      }

      .instructions {
        background-color: #e7f3ff;
        padding: 10px;
        border-radius: 4px;
        margin-bottom: 20px;
        border-left: 4px solid #2196f3;
      }
    </style>
  </head>
  <body ng-app="imageAnnotationApp" ng-controller="MainController as main">
    <div class="container">
      <h1>Draw Faults</h1>

      <div class="image-grid">
        <div
          class="image-item"
          ng-repeat="image in main.images"
          ng-click="main.openImage(image)"
        >
          <img ng-src="{{image.url}}" alt="{{image.name}}" />
          <p>{{image.name}}</p>
        </div>
      </div>

      <div ng-if="main.selectedImage" class="modal-overlay">
        <div class="modal-content">
          <h2>Draw: {{main.selectedImage.name}}</h2>

          <div class="image-container">
            <img
              ng-src="{{main.selectedImage.url}}"
              alt="{{main.selectedImage.name}}"
              id="annotationImage"
            />
            <canvas class="annotation-canvas" id="annotationCanvas"></canvas>
          </div>

          <div class="coordinates-display" ng-if="main.currentRect">
            Current Rectangle: X: {{main.currentRect.x}}, Y:
            {{main.currentRect.y}}, Width: {{main.currentRect.width}}, Height:
            {{main.currentRect.height}}
          </div>

          <div class="saved-rectangles" ng-if="main.savedRectangles.length > 0">
            <h3>Saved Rectangles</h3>
            <div
              class="rectangle-item"
              ng-repeat="rect in main.savedRectangles"
            >
              <span
                >Rectangle {{$index + 1}}: X: {{rect.x}}, Y: {{rect.y}}, W:
                {{rect.width}}, H: {{rect.height}}</span
              >
              <button ng-click="main.removeRectangle($index)">Remove</button>
            </div>
          </div>

          <div class="modal-controls">
            <div>
              <button class="btn btn-clear" ng-click="main.clearCurrentRect()">
                Clear Current
              </button>
              <button class="btn btn-primary" ng-click="main.saveRectangle()">
                Save Rectangle
              </button>
            </div>
            <button class="btn btn-secondary" ng-click="main.closeImage()">
              Close
            </button>
          </div>
        </div>
      </div>
    </div>

    <script>
      angular
        .module("imageAnnotationApp", [])
        .controller("MainController", function () {
          const vm = this;

          // Sample images
          vm.images = [
            {
              id: 1,
              name: "Sample Image 1",
              url: "https://raw.githubusercontent.com/jaydeepchoudhari/TestRepo/refs/heads/master/fault1.jpg",
            },
            {
              id: 2,
              name: "Sample Image 2",
              url: "https://picsum.photos/600/400?random=2",
            },
            {
              id: 3,
              name: "Sample Image 3",
              url: "https://picsum.photos/600/400?random=3",
            },
          ];

          vm.selectedImage = null;
          vm.currentRect = null;
          vm.savedRectangles = [];
          vm.isDrawing = false;
          vm.startX = 0;
          vm.startY = 0;

          vm.openImage = function (image) {
            vm.selectedImage = image;
            vm.savedRectangles =
              JSON.parse(localStorage.getItem(`rectangles_${image.id}`)) || [];

            setTimeout(function () {
              vm.initCanvas();
            }, 100);
          };

          vm.closeImage = function () {
            vm.selectedImage = null;
            vm.currentRect = null;
            vm.isDrawing = false;
          };

          vm.initCanvas = function () {
            const canvas = document.getElementById("annotationCanvas");
            const img = document.getElementById("annotationImage");

            if (!canvas || !img) return;

            canvas.width = img.clientWidth;
            canvas.height = img.clientHeight;

            const ctx = canvas.getContext("2d");

            vm.drawSavedRectangles(ctx);

            canvas.addEventListener("mousedown", vm.startDrawing);
            canvas.addEventListener("mousemove", vm.drawRectangle);
            canvas.addEventListener("mouseup", vm.stopDrawing);
          };

          vm.startDrawing = function (e) {
            vm.isDrawing = true;
            const canvas = document.getElementById("annotationCanvas");
            const rect = canvas.getBoundingClientRect();

            vm.startX = e.clientX - rect.left;
            vm.startY = e.clientY - rect.top;

            vm.currentRect = {
              x: vm.startX,
              y: vm.startY,
              width: 0,
              height: 0,
            };
          };

          vm.drawRectangle = function (e) {
            if (!vm.isDrawing) return;

            const canvas = document.getElementById("annotationCanvas");
            const rect = canvas.getBoundingClientRect();
            const currentX = e.clientX - rect.left;
            const currentY = e.clientY - rect.top;

            vm.currentRect.width = currentX - vm.startX;
            vm.currentRect.height = currentY - vm.startY;

            vm.redrawCanvas();
          };

          vm.stopDrawing = function () {
            vm.isDrawing = false;
          };

          vm.redrawCanvas = function () {
            const canvas = document.getElementById("annotationCanvas");
            const ctx = canvas.getContext("2d");

            ctx.clearRect(0, 0, canvas.width, canvas.height);

            vm.drawSavedRectangles(ctx);

            if (
              vm.currentRect &&
              (vm.currentRect.width !== 0 || vm.currentRect.height !== 0)
            ) {
              ctx.strokeStyle = "red";
              ctx.lineWidth = 2;
              ctx.strokeRect(
                vm.currentRect.x,
                vm.currentRect.y,
                vm.currentRect.width,
                vm.currentRect.height
              );
            }
          };

          vm.drawSavedRectangles = function (ctx) {
            ctx.strokeStyle = "blue";
            ctx.lineWidth = 2;

            vm.savedRectangles.forEach(function (rect) {
              ctx.strokeRect(rect.x, rect.y, rect.width, rect.height);
            });
          };

          vm.saveRectangle = function () {
            //if (!vm.currentRect) return;
            vm.savedRectangles.push({
              x: vm.currentRect.x,
              y: vm.currentRect.y,
              width: vm.currentRect.width,
              height: vm.currentRect.height,
            });

            localStorage.setItem(
              `rectangles_${vm.selectedImage.id}`,
              JSON.stringify(vm.savedRectangles)
            );

            vm.currentRect = null;

            vm.redrawCanvas();
          };

          vm.removeRectangle = function (index) {
            vm.savedRectangles.splice(index, 1);

            localStorage.setItem(
              `rectangles_${vm.selectedImage.id}`,
              JSON.stringify(vm.savedRectangles)
            );

            vm.redrawCanvas();
          };

          vm.clearCurrentRect = function () {
            vm.currentRect = null;
            vm.redrawCanvas();
          };

          vm.startDrawing = vm.startDrawing.bind(vm);
          vm.drawRectangle = vm.drawRectangle.bind(vm);
          vm.stopDrawing = vm.stopDrawing.bind(vm);
        });
    </script>
  </body>
</html>
