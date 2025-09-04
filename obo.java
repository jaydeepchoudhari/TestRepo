 @Query(value = "select * from SOLAR.INSPECTION_VIEW where plant_id = ?1 order by created_ts desc", nativeQuery = true)
    List<SolarInspectionView> findByPlantIdByOrderByCrtDateDesc(short plantId);
