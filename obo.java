@Transactional
    public void bulkInsert(List<LocationDTO> locations) {
        int count = 0;
        String sql = "INSERT INTO dbo.locations (uuid, site_id, deleted_ind, site_name, address_1, address_2, city, state_code, postal_code, county, created, created_by) " +
                "VALUES (:uuid, :siteId, :deletedInd, :siteName, :address1, :address2, :city, :stateCode, :postalCode, :county, :created, :createdDate)";

        for (LocationDTO location : locations) {
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
                    .setParameter("createdDate", location.getCreatedBy())
                    .setParameter("deletedInd", location.getDeletedInd())
                    .executeUpdate();
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
