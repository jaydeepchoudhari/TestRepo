<div class="table-sm table-responsible table-light">
                <table class="table" cellspacing="0">
                    <thead style="background-color: transparent !important; border-bottom: 2px solid #D7D9DA !important">
                        <tr>
                            <th class="no-padding inspection-id-header" style="padding-left:4.8px !important">Inspection Id</th>
                            <th></th>
                            <th class="no-padding created-header">Created</th>
                            <th colspan="2" class="text-center no-padding table-column-splitter images-header">Images</th>
                            <th class="text-center no-padding table-column-splitter faults-header">Faults</th>
                            <th colspan="2" class="text-center no-padding table-column-splitter faults-created-by-header">Faults - Created By</th>
                            <th colspan="4" class="text-center no-padding table-column-splitter faults-classification-header">Faults - Classification</th>
                        </tr>
                        <tr>
                            <th colspan="3"></th>
                            <th class="text-center table-column-splitter"><h6><small>Total</small></h6></th>
                            <th class="text-center"><h6><small>No Fault</small></h6></th>
                            <th class="text-center table-column-splitter"><h6><small>Total</small></h6></th>
                            <th class="text-center table-column-splitter"><h6><small>AI</small></h6></th>
                            <th class="text-center"><h6><small>User</small></h6></th>
                            <th class="text-center table-column-splitter"><h6><small>String</small></h6></th>
                            <th class="text-center"><h6><small>Other</small></h6></th>
                            <th class="text-center"><h6><small>Diode</small></h6></th>
                            <th class="text-center"><h6><small>Cell</small></h6></th>
                            <th class="text-center"><h6><small>String Loss (KW)</small></h6></th>
                            <th class="text-center"><h6><small>Diode Loss (KW)</small></h6></th>
                            <th class="text-center"><h6><small>Cell Loss (KW)</small></h6></th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr *ngFor="let inspection of this.inspectionOpenService.openInspections">
                            <td><b><span class="cursor-pointer link" (click)="selectInspection(inspection)">{{inspection.inspectionId}}</span></b></td>
                            <td class="cursor-pointer">
                                <span class="smaller" style="float: left; margin-right: 7px">
                                    <i
                                    class="fas fa-plus fa-lg"
                                    matTooltip="Add file(s) to current inspection"
                                    style="vertical-align: bottom"
                                    (click)="addFileToInspection(inspection.inspectionId)"></i>
                                </span>
                                <div class="potato" style="display: flex; flex-wrap: wrap; align-items: center;">
                                    <i class="fas fa-file-pdf" matTooltip="Generate Images Report" style="margin-right: 7px" (click)="getDataforReportandRedirect(solarPlantService.selectedSolarPlant, inspection)">
                                    </i>
                                    <i class="fas fa-file-excel" matTooltip="Generate Fault List XLS" style="margin-right: 7px" (click)="getWorkOrderNeededReport(inspection)">
                                    </i>
                                    <div *ngIf="userApi.userInfo$.value.role === 'admin' || userApi.userInfo$.value.role === 'Admin'; else rerunModel">                                        
                                        <i class="fas fa-refresh" matTooltip="Rerun Inspection Model"(click)="rerunMadlabModel(inspection)" mat-button [disabled]="!upLoading">
                                    </i>
                                    </div>
                                    <ng-template #rerunModel>                                        
                                    </ng-template>
 
                                </div>
                            </td>
                            <td>{{inspection.createdTs | date:'MM/dd/yyyy'}}</td>
                            <td class="text-center table-column-splitter">{{inspection.imagesTotal}}</td>
                            <td class="text-center">{{inspection.imagesNoFaultTotal}}</td>
                            <td class="text-center table-column-splitter">{{inspection.faultsTotal}}</td>
                            <td class="text-center table-column-splitter">{{inspection.faultsCreatedAi}}</td>
                            <td class="text-center">{{inspection.faultsCreatedUser}}</td>
                            <td class="text-center table-column-splitter">{{inspection.faultsString}}</td>
                            <td class="text-center">{{inspection.faultsOther}}</td>
                            <td class="text-center">{{inspection.faultsDiode}}</td>
                            <td class="text-center">{{inspection.faultsCell}}</td>
                            <td class="text-center">{{inspection.stringLoss}}</td>
                            <td class="text-center">{{inspection.diodeLoss}}</td>
                            <td class="text-center">{{inspection.cellLoss}}</td>
                        </tr>
                    </tbody>
                </table>
            </div>
