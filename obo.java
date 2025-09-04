@Query(value = "select * from SOLAR.INSPECTION_VIEW where plant_id = :plantId", 
       nativeQuery = true)
List<SolarInspectionView> findByPlantIdWithSort(
    @Param("plantId") short plantId, 
    Sort sort);

Sort sort = Sort.by(Sort.Direction.DESC, "created_ts");
List<SolarInspectionView> results = repository.findByPlantIdWithSort(plantId, sort);
