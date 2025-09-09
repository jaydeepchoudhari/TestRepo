// inside your component.ts
columns = [
  { field: 'inspectionId', label: 'Inspection Id' },
  { field: 'createdTs', label: 'Created' },
  { field: 'imagesTotal', label: 'Images Total' },
  { field: 'imagesNoFaultTotal', label: 'Images No Fault' },
  { field: 'faultsTotal', label: 'Faults Total' },
  { field: 'faultsCreatedAi', label: 'Faults AI' },
  { field: 'faultsCreatedUser', label: 'Faults User' },
  { field: 'faultsString', label: 'Faults String' },
  { field: 'faultsOther', label: 'Faults Other' },
  { field: 'faultsDiode', label: 'Faults Diode' },
  { field: 'faultsCell', label: 'Faults Cell' },
  { field: 'stringLoss', label: 'String Loss (KW)' },
  { field: 'diodeLoss', label: 'Diode Loss (KW)' },
  { field: 'cellLoss', label: 'Cell Loss (KW)' }
];

sortColumn: string = 'createdTs';
sortDirection: 'asc' | 'desc' = 'desc';

constructor(public inspectionOpenService: InspectionOpenService) {}

ngOnInit() {
  this.inspectionOpenService.loadOpenInspections(this.sortColumn, this.sortDirection);
}

sortData(column: string) {
  if (this.sortColumn === column) {
    this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
  } else {
    this.sortColumn = column;
    this.sortDirection = 'asc';
  }
  this.inspectionOpenService.loadOpenInspections(this.sortColumn, this.sortDirection);
}



openInspections: any[] = [];

loadOpenInspections(sortBy: string = 'createdTs', sortDir: 'asc' | 'desc' = 'desc') {
  // If backend API supports sorting:
  // return this.http.get<any[]>(`/api/inspections?sortBy=${sortBy}&sortDir=${sortDir}`)
  //   .subscribe(data => this.openInspections = data);

  // Local sorting (if no API support):
  this.openInspections.sort((a, b) => {
    const valA = a[sortBy];
    const valB = b[sortBy];

    if (valA < valB) return sortDir === 'asc' ? -1 : 1;
    if (valA > valB) return sortDir === 'asc' ? 1 : -1;
    return 0;
  });
}




<thead style="background-color: transparent !important; border-bottom: 2px solid #D7D9DA !important">
  <tr>
    <th *ngFor="let col of columns"
        class="cursor-pointer"
        (click)="sortData(col.field)">
      {{ col.label }}
      <span *ngIf="sortColumn === col.field">
        {{ sortDirection === 'asc' ? '▲' : '▼' }}
      </span>
    </th>
  </tr>
</thead>


<tbody>
  <tr *ngFor="let inspection of inspectionOpenService.openInspections">
    <td *ngFor="let col of columns">
      {{ col.field === 'createdTs'
         ? (inspection[col.field] | date:'MM/dd/yyyy')
         : inspection[col.field] }}
    </td>
  </tr>
</tbody>


        
