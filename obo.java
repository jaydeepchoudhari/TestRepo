@GetMapping("/api/solar/{plant_id}/inspections/all")
    @ApiOperation(value="Gets all inspections for a given solar plant for a specified status")
    public List<SolarInspectionView> getAllInspectionsForSolarPlant(@ApiParam(value="The Solar Plant Plant Id", required=true) @PathVariable short plant_id)
    {
        return solarInspectionViewService.getAllInspectionsForSolarPlantV3(plant_id);
    }
