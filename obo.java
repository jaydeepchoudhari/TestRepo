public interface SolarInspectionViewRepositoryCustom {
    List<SolarInspectionView> findByPlantIdWithSort(short plantId, String sortColumn, String sortDirection);
}

@Repository
public class SolarInspectionViewRepositoryImpl implements SolarInspectionViewRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<SolarInspectionView> findByPlantIdWithSort(short plantId, String sortColumn, String sortDirection) {

        // ✅ whitelist allowed columns (to avoid SQL injection)
        List<String> allowedColumns = List.of("inspection_id", "plant_id", "create_date", "status");
        if (!allowedColumns.contains(sortColumn.toLowerCase())) {
            throw new IllegalArgumentException("Invalid sort column: " + sortColumn);
        }

        // ✅ only allow ASC or DESC
        String direction = sortDirection.equalsIgnoreCase("DESC") ? "DESC" : "ASC";

        String sql = "SELECT * FROM SOLAR.INSPECTION_VIEW " +
                     "WHERE plant_id = :plantId " +
                     "ORDER BY " + sortColumn + " " + direction;

        Query query = entityManager.createNativeQuery(sql, SolarInspectionView.class);
        query.setParameter("plantId", plantId);

        return query.getResultList();
    }
}
