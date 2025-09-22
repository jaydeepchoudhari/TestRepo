<ng-template [ngIf]="!this.inspectionOpenService.saving">
  <div class="card" style="height: 100%; padding: 0px !important">
    <div
      class="card-body cursor-pointer"
      id="cardBodyModal"
      style="padding: 0px"
    >
      <!--PHOTO WITH NAVIGATION-->
      <div class="image-slider-wrapper">
        <!-- Previous Button -->
        <button 
          class="nav-button prev-button" 
          (click)="previousImage()"
          [disabled]="images.length <= 1">
          &#8249;
        </button>

        <!-- Image Container -->
        <div id="photoWrapperModal" style="position: relative; width: 100%; flex: 1;">
          <div
            id="canvas"
            (mousedown)="onMouseDown($event)"
            (mousemove)="onMouseMove($event)"
            (mouseup)="onMouseUp($event)"
            style="z-index: 99; width: 100%; height: 100%; position: absolute"
          ></div>
          <img
            src="{{this.inspectionOpenService.selectedImage.imageUrl}}"
            id="selectedImagePhotoModal"
            style="-webkit-user-drag: none"
            draggable="false"
            (load)="imageLoaded()"
          />
        </div>

        <!-- Next Button -->
        <button 
          class="nav-button next-button" 
          (click)="nextImage()"
          [disabled]="images.length <= 1">
          &#8250;
        </button>
      </div>

      <!-- Image Counter -->
      <div class="image-counter">
        {{ currentImageNumber }} / {{ totalImages }}
      </div>
    </div>
  </div>
</ng-template>

// Add these methods to your TypeScript component
export class YourComponent {
  currentIndex = 0;
  
  // Your existing images array (replace with your actual property name)
  // images = this.inspectionOpenService.images; // or however you access your images

  previousImage(): void {
    if (this.images && this.images.length > 1) {
      this.currentIndex = this.currentIndex === 0 
        ? this.images.length - 1 
        : this.currentIndex - 1;
      
      // Update the selected image in your service
      this.inspectionOpenService.selectedImage = this.images[this.currentIndex];
    }
  }

  nextImage(): void {
    if (this.images && this.images.length > 1) {
      this.currentIndex = this.currentIndex === this.images.length - 1 
        ? 0 
        : this.currentIndex + 1;
      
      // Update the selected image in your service
      this.inspectionOpenService.selectedImage = this.images[this.currentIndex];
    }
  }

  get currentImageNumber(): number {
    return this.currentIndex + 1;
  }

  get totalImages(): number {
    return this.images ? this.images.length : 0;
  }

  get images() {
    // Replace this with your actual images array property
    return this.inspectionOpenService.images || [];
  }

  // Your existing methods...
  // onMouseDown, onMouseMove, onMouseUp, imageLoaded, etc.
}

// Add this CSS to your component's stylesheet
<style>
.image-slider-wrapper {
  display: flex;
  align-items: center;
  gap: 15px;
  width: 100%;
  height: calc(100% - 40px); /* Reserve space for counter */
}

.nav-button {
  background-color: #333;
  color: white;
  border: none;
  width: 45px;
  height: 45px;
  border-radius: 50%;
  font-size: 20px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background-color 0.3s ease;
  flex-shrink: 0;
  z-index: 100;
}

.nav-button:hover:not(:disabled) {
  background-color: #555;
}

.nav-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
  opacity: 0.5;
}

.image-counter {
  text-align: center;
  font-size: 14px;
  color: #666;
  font-weight: 500;
  padding: 10px;
  background-color: #f8f9fa;
  border-top: 1px solid #dee2e6;
}

/* Responsive adjustments */
@media (max-width: 768px) {
  .image-slider-wrapper {
    gap: 10px;
  }
  
  .nav-button {
    width: 35px;
    height: 35px;
    font-size: 16px;
  }
  
  .image-counter {
    font-size: 12px;
    padding: 8px;
  }
}
</style>
