@Transactional
public void bulkInsert(List<LocationDTO> locations) {
    int count = 0;
    Set<String> insertedSiteIds = new HashSet<>(); // Track already inserted site IDs

    String sql = "INSERT INTO dbo.locations " +
            "(uuid, site_id, deleted_ind, site_name, address_1, address_2, city, state_code, postal_code, county, created, created_by) " +
            "VALUES (:uuid, :siteId, :deletedInd, :siteName, :address1, :address2, :city, :stateCode, :postalCode, :county, :created, :createdBy)";

    for (LocationDTO location : locations) {
        if (insertedSiteIds.contains(location.getSiteId())) {
            continue; // Skip duplicate site_id in the same batch
        }

        entityManager.createNativeQuery(sql)
                .setParameter("uuid", location.getUuid())
                .setParameter("siteId", location.getSiteId())
                .setParameter("siteName", location.getSiteName())
                .setParameter("address1", location.getAddress1())
                .setParameter("address2", location.getAddress2())
                .setParameter("city", location.getCity())
                .setParameter("stateCode", location.getStateCode())
                .setParameter("postalCode", location.getPostalCode())
                .setParameter("county", location.getCounty())
                .setParameter("created", location.getCreated())
                .setParameter("createdBy", location.getCreatedBy())
                .setParameter("deletedInd", location.getDeletedInd())
                .executeUpdate();

        insertedSiteIds.add(location.getSiteId()); // Remember this site_id
        count++;

        if (count % BATCH_SIZE == 0) {
            entityManager.flush();
            entityManager.clear();
        }
    }

    // Final flush and clear
    entityManager.flush();
    entityManager.clear();
}
