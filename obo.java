@Repository
public class SolarInspectionViewRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    public List<SolarInspectionView> findByPlantIdWithSort(
            short plantId, String sortColumn, String sortDirection) {

        // ✅ whitelist columns to prevent SQL injection
        List<String> allowedColumns = List.of("inspection_id", "plant_id", "create_date", "status");
        if (!allowedColumns.contains(sortColumn.toLowerCase())) {
            throw new IllegalArgumentException("Invalid sort column: " + sortColumn);
        }

        String direction = sortDirection.equalsIgnoreCase("DESC") ? "DESC" : "ASC";

        String sql = "SELECT * FROM SOLAR.INSPECTION_VIEW " +
                     "WHERE plant_id = :plantId " +
                     "ORDER BY " + sortColumn + " " + direction;

        Query query = entityManager.createNativeQuery(sql, SolarInspectionView.class);
        query.setParameter("plantId", plantId);

        return query.getResultList();
    }
}
