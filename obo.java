@GetMapping("/api/solar/{plant_id}/inspections/all")
@ApiOperation(value="Gets all inspections for a given solar plant for a specified status")
public List<SolarInspectionView> getAllInspectionsForSolarPlant(
    @ApiParam(value="The Solar Plant Plant Id", required=true) @PathVariable short plant_id,
    @ApiParam(value="Sort column name", defaultValue="created_ts") @RequestParam(required = false, defaultValue = "created_ts") String sortColumn,
    @ApiParam(value="Sort direction (ASC/DESC)", defaultValue="DESC") @RequestParam(required = false, defaultValue = "DESC") String sortDirection)
{
    return solarInspectionViewService.getAllInspectionsForSolarPlantV3(plant_id, sortColumn, sortDirection);
}
