columns = [
  { field: 'inspectionId', label: 'Inspection Id', className: 'no-padding inspection-id-header cursor-pointer' },
  { field: 'createdTs', label: 'Created', className: 'no-padding created-header cursor-pointer', width: 150 },
  { field: 'imagesTotal', label: 'Images Total', className: 'text-center cursor-pointer' },
  { field: 'imagesNoFaultTotal', label: 'Images No Fault', className: 'text-center cursor-pointer' },
  { field: 'faultsTotal', label: 'Faults Total', className: 'text-center cursor-pointer' },
  { field: 'faultsCreatedAi', label: 'Faults AI', className: 'text-center cursor-pointer' },
  { field: 'faultsCreatedUser', label: 'Faults User', className: 'text-center cursor-pointer' },
  { field: 'faultsString', label: 'Faults String', className: 'text-center cursor-pointer' },
  { field: 'faultsOther', label: 'Faults Other', className: 'text-center cursor-pointer' },
  { field: 'faultsDiode', label: 'Faults Diode', className: 'text-center cursor-pointer' },
  { field: 'faultsCell', label: 'Faults Cell', className: 'text-center cursor-pointer' },
  { field: 'stringLoss', label: 'String Loss (KW)', className: 'text-center cursor-pointer' },
  { field: 'diodeLoss', label: 'Diode Loss (KW)', className: 'text-center cursor-pointer' },
  { field: 'cellLoss', label: 'Cell Loss (KW)', className: 'text-center cursor-pointer' }
];

<th *ngFor="let col of columns"
    [ngClass]="col.className"
    [style.width.px]="col.width ? col.width : null"
    (click)="sortData(col.field)">
  {{ col.label }}
  <span *ngIf="sortColumn === col.field">
    {{ sortDirection === 'asc' ? '▲' : '▼' }}
  </span>
</th>
