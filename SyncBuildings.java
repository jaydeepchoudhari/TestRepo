import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildingSynchronizer {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void syncBuildings(List<BuildingViewDTO> sourceBuildings) {
        // Step 1: Load all locations by siteId for quick lookup
        Map<String, String> locationIdMap = loadLocationIdsBySiteId();
        
        // Step 2: Load all existing buildings by source building_id for quick lookup
        Map<Integer, BuildingEntity> existingBuildings = loadExistingBuildings();
        
        // Step 3: Process each source building
        for (BuildingViewDTO sourceBuilding : sourceBuildings) {
            String siteId = sourceBuilding.getSiteId();
            Integer buildingId = Integer.parseInt(sourceBuilding.getBuildingId());
            
            // Find matching location UUID
            String locationUuid = locationIdMap.get(siteId);
            if (locationUuid == null) {
                // Location not found, skip this building
                continue;
            }
            
            // Check if building exists
            BuildingEntity building = existingBuildings.get(buildingId);
            
            if (building == null) {
                // Create new building
                building = new BuildingEntity();
                building.setUuid(UUID.randomUUID().toString());
                building.setBuildingId(buildingId);
                building.setLocationId(locationUuid);
                building.setBuildingCode(sourceBuilding.getBuildingCode());
                building.setName(sourceBuilding.getBuildingName());
                entityManager.persist(building);
            } else {
                // Update existing building if needed
                boolean needsUpdate = false;
                
                if (!equals(building.getBuildingCode(), sourceBuilding.getBuildingCode())) {
                    building.setBuildingCode(sourceBuilding.getBuildingCode());
                    needsUpdate = true;
                }
                
                if (!equals(building.getName(), sourceBuilding.getBuildingName())) {
                    building.setName(sourceBuilding.getBuildingName());
                    needsUpdate = true;
                }
                
                if (!equals(building.getLocationId(), locationUuid)) {
                    building.setLocationId(locationUuid);
                    needsUpdate = true;
                }
                
                if (needsUpdate) {
                    entityManager.merge(building);
                }
            }
        }
        
        // Flush changes in batches for better performance
        entityManager.flush();
        entityManager.clear();
    }
    
    private Map<String, String> loadLocationIdsBySiteId() {
        TypedQuery<Object[]> query = entityManager.createQuery(
            "SELECT l.siteId, l.uuid FROM LocationEntity l", Object[].class);
        
        Map<String, String> locationMap = new HashMap<>();
        for (Object[] result : query.getResultList()) {
            locationMap.put((String) result[0], (String) result[1]);
        }
        return locationMap;
    }
    
    private Map<Integer, BuildingEntity> loadExistingBuildings() {
        TypedQuery<BuildingEntity> query = entityManager.createQuery(
            "SELECT b FROM BuildingEntity b", BuildingEntity.class);
        
        Map<Integer, BuildingEntity> buildingMap = new HashMap<>();
        for (BuildingEntity building : query.getResultList()) {
            buildingMap.put(building.getBuildingId(), building);
        }
        return buildingMap;
    }
    
    private boolean equals(String a, String b) {
        return (a == null) ? (b == null) : a.equals(b);
    }





    @Transactional
public void syncBuildings(List<BuildingViewDTO> sourceBuildings) {
    // Step 1: Load all locations by siteId for quick lookup
    Map<String, String> locationIdMap = loadLocationIdsBySiteId();
    
    // Step 2: Load all existing buildings by source building_id for quick lookup
    Map<Integer, BuildingEntity> existingBuildings = loadExistingBuildings();
    
    // Counters for batch processing
    int processedCount = 0;
    int createdCount = 0;
    int updatedCount = 0;
    int errorCount = 0;
    
    // Step 3: Process each source building
    for (BuildingViewDTO sourceBuilding : sourceBuildings) {
        try {
            String siteId = sourceBuilding.getSiteId();
            Integer buildingId = parseBuildingId(sourceBuilding.getBuildingId());
            
            if (buildingId == null) {
                errorCount++;
                continue;
            }
            
            // Find matching location UUID
            String locationUuid = siteId != null ? locationIdMap.get(siteId.trim()) : null;
            if (locationUuid == null) {
                errorCount++;
                continue;
            }
            
            // Check if building exists
            BuildingEntity building = existingBuildings.get(buildingId);
            
            if (building == null) {
                // Create new building
                building = new BuildingEntity();
                building.setUuid(UUID.randomUUID().toString());
                building.setBuildingId(buildingId);
                building.setLocationId(locationUuid);
                building.setBuildingCode(trimToNull(sourceBuilding.getBuildingCode()));
                building.setName(trimToNull(sourceBuilding.getBuildingName()));
                entityManager.persist(building);
                createdCount++;
            } else {
                // Update existing building if needed
                boolean needsUpdate = false;
                
                if (!equalsIgnoreCase(building.getBuildingCode(), sourceBuilding.getBuildingCode())) {
                    building.setBuildingCode(trimToNull(sourceBuilding.getBuildingCode()));
                    needsUpdate = true;
                }
                
                if (!equalsIgnoreCase(building.getName(), sourceBuilding.getBuildingName())) {
                    building.setName(trimToNull(sourceBuilding.getBuildingName()));
                    needsUpdate = true;
                }
                
                if (!equals(building.getLocationId(), locationUuid)) {
                    building.setLocationId(locationUuid);
                    needsUpdate = true;
                }
                
                if (needsUpdate) {
                    entityManager.merge(building);
                    updatedCount++;
                }
            }
            
            processedCount++;
            
            // Batch processing: flush and clear every 1000 entities
            if (processedCount % 1000 == 0) {
                entityManager.flush();
                entityManager.clear();
                
                // Re-attach any entities that might be needed in next batch
                if (building != null && !entityManager.contains(building)) {
                    building = entityManager.merge(building);
                }
            }
            
        } catch (Exception e) {
            errorCount++;
            // Log the error if needed
            // logger.error("Error processing building: " + sourceBuilding, e);
        }
    }
    
    // Final flush
    entityManager.flush();
    entityManager.clear();
    
    // Log statistics if needed
    // logger.info("Processed: {}, Created: {}, Updated: {}, Errors: {}", 
    //     processedCount, createdCount, updatedCount, errorCount);
}

// Helper methods
private Integer parseBuildingId(String buildingIdStr) {
    if (buildingIdStr == null || buildingIdStr.trim().isEmpty()) {
        return null;
    }
    try {
        return Integer.parseInt(buildingIdStr.trim());
    } catch (NumberFormatException e) {
        return null;
    }
}

private String trimToNull(String value) {
    if (value == null) {
        return null;
    }
    String trimmed = value.trim();
    return trimmed.isEmpty() ? null : trimmed;
}

private boolean equalsIgnoreCase(String a, String b) {
    if (a == null && b == null) return true;
    if (a == null || b == null) return false;
    return a.trim().equalsIgnoreCase(b.trim());
}

private boolean equals(String a, String b) {
    return (a == null) ? (b == null) : a.equals(b);
}
}
