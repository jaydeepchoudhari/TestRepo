MERGE INTO dbo.ENTITY AS target
USING dbo.LOCATIONS AS source
ON target.site_id = source.site_id
WHEN MATCHED THEN
    UPDATE SET 
        entity_name = source.site_name,
        created_date = CASE WHEN target.created_date IS NULL THEN GETDATE() ELSE target.created_date END,
        created_by = CASE WHEN target.created_by IS NULL THEN SYSTEM_USER ELSE target.created_by END
WHEN NOT MATCHED BY TARGET THEN
    INSERT (site_id, entity_name, created_date, created_by)
    VALUES (source.site_id, source.site_name, GETDATE(), SYSTEM_USER);


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

public class EntityRepositoryCustomImpl implements EntityRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void mergeEntitiesFromLocations() {
        String mergeSql = 
            "MERGE INTO ENTITY AS target " +
            "USING LOCATIONS AS source " +
            "ON target.site_id = source.site_id " +
            "WHEN MATCHED THEN " +
            "    UPDATE SET " +
            "        target.entity_name = source.site_name, " +
            "        target.created_date = CASE WHEN target.created_date IS NULL THEN GETDATE() ELSE target.created_date END, " +
            "        target.created_by = CASE WHEN target.created_by IS NULL THEN SYSTEM_USER ELSE target.created_by END " +
            "WHEN NOT MATCHED BY TARGET THEN " +
            "    INSERT (site_id, entity_name, created_date, created_by) " +
            "    VALUES (source.site_id, source.site_name, GETDATE(), SYSTEM_USER)";

        entityManager.createNativeQuery(mergeSql).executeUpdate();
    }
}
