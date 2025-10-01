<ng-template [ngIf]="this.inspectionOpenService.saving">
    <div class="card" style="width:100%;">
        <div class="card-body">
            <br><br>
            <mat-progress-spinner mode="indeterminate" color="primary" class="margin-auto small-spinner"
                style="margin:auto;"></mat-progress-spinner>
            <br />
            <h4 class="card-text text-center">Saving New Fault...</h4>
        </div>
    </div>
</ng-template>
<ng-template [ngIf]="!this.inspectionOpenService.saving">
    <div class="card" style="height:100%; padding: 0px !important">
        <div class="card-header" id="newFaultDialogHeader" style="height:60px;">Click And Drag To Create New Fault
            <div class="float-right">
                <button class="btn card-text btn-functional" type="button" *ngIf="createdFault"
                    (click)="showModalNewFaultClassification()" matTooltip="Classify This New Fault">
                    <i class="fa fa-check-double fa-sm"></i> Classify
                </button>
                &nbsp;&nbsp;
                <i class="fa fa-times-circle fa-lg cursor-pointer" (click)="closeModal()"></i>
            </div>
            <div class="float-right" style="margin-right: 5%;"> 
                <button class="nav-button next" (click)="setNextImage()">
                    <i class="fas fa-chevron-right"></i>
                </button>
            </div>

            <div class="float-right" style="margin-right: 5%;"> 
                <button class="nav-button prev" >
                    <i class="fas fa-chevron-left"></i>
                </button>
            </div>
        </div>
        <div class="card-body cursor-pointer" id="cardBodyModal" style="padding:0px;">
            <!--PHOTO-->
            <div id="photoWrapperModal" style="position:relative;width:100%;">
                <div id="canvas" (mousedown)="onMouseDown($event)" (mousemove)="onMouseMove($event)"
                    (mouseup)="onMouseUp($event)" style="z-index:99;width:100%;height:100%;position:absolute;"></div>
                <img src="{{this.inspectionOpenService.selectedImage.imageUrl}}" id="selectedImagePhotoModal"
                    style="-webkit-user-drag: none;" draggable="false" (load)="imageLoaded()" />

                <!--FAULT-->
                <div *ngIf="this.inspectionOpenService.selectedImage?.filename" style="position: absolute;" 
                [ngStyle]="setFaultMarkerPosition(this.inspectionOpenService.selectedImage?.faultPosition)">
                    <div class="other-fault"
                        [ngStyle]="setFaultBoundingBoxPosition(this.inspectionOpenService.selectedImage)">
                    </div>
                </div> 
                <!--OTHER FAULTS-->
                <div *ngFor="let fault of this.inspectionOpenService.otherFaults" style="position: absolute;"
                    [ngStyle]="setFaultMarkerPosition(fault?.faultPosition)" (mousemove)="onMouseMove2($event)">
                    <div class="other-fault" [ngStyle]="setFaultBoundingBoxPosition(fault)">
                    </div>
                </div>
            </div>
        </div>
    </div>
</ng-template>
