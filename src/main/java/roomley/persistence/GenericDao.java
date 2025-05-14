package roomley.persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Generic Dao for all database connections
 * @param <T>
 */
public class GenericDao<T> {

    private final Logger logger = LogManager.getLogger(this.getClass());
    private final Class<T> type;
    protected final SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * GenericDao constructor
     * @param type the type of the entity class
     */
    public GenericDao(Class<T> type) {
        this.type = type;

    }

    /**
     * Get data by id
     * @param id id to be checked
     * @return results of query
     */
    public T getById(int id) {

        try (Session session = sessionFactory.openSession()) {
            return session.get(type, id);

        }

    }

    /**
     * Get all data from a table
     * @return results of query
     */
    public List<T> getAll() {

        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            query.from(type);
            return session.createQuery(query).getResultList();

        }

    }

    /**
     * Get by property equal
     * @param propertyName the property name
     * @param value the value to be checked
     * @return results of query
     */
    public List<T> getByPropertyEqual(String propertyName, Object value) {

        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            Root<T> root = query.from(type);
            query.select(root).where(builder.equal(root.get(propertyName), value));
            return session.createQuery(query).getResultList();

        }

    }

    /**
     * Get property like
     * @param propertyName the property name
     * @param value the property value to be checked
     * @return results of query
     */
    public List<T> getByPropertyLike(String propertyName, Object value) {

        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            Root<T> root = query.from(type);
            query.where(builder.like(root.get(propertyName), "%" + value + "%"));
            return session.createQuery(query).getResultList();

        }

    }

    /**
     * Get by properties equal
     * @param properties list of properties to be checked
     * @return results of query
     */
    public List<T> getByPropertiesEqual(Map<String, Object> properties) {

        if (properties.isEmpty()) {
            return new ArrayList<>();  // Return empty list if no properties are passed

        }

        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            Root<T> root = query.from(type);
            List<Predicate> predicates = new ArrayList<>();

            for (Map.Entry<String, Object> entry : properties.entrySet()) {
                String[] keys = entry.getKey().split("\\.");
                Path<?> path = root;

                for (int i = 0; i < keys.length; i++) {

                    if (i == 0) {
                        Field field = type.getDeclaredField(keys[0]);

                        if (Collection.class.isAssignableFrom(field.getType())) {
                            path = root.join(keys[0]);

                        } else {
                            path = root.get(keys[0]);

                        }

                    } else {
                        path = path.get(keys[i]);

                    }

                }
                predicates.add(builder.equal(path, entry.getValue()));

            }

            query.select(root).where(builder.and(predicates.toArray(new Predicate[0])));
            return session.createQuery(query).getResultList();

        } catch (Exception e) {
            logger.error(e + " General exception");
            return new ArrayList<>();
        }

    }

    /**
     * Insert an entity
     * @param entity entity to be added to db
     * @return the entity
     */
    public T insert(T entity) {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            transaction.commit();
            session.save(entity);

            return entity;

        }

    }

    /**
     * Update an entity
     * @param entity entity to be updated
     */
    public void update(T entity) {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();

        }

    }

    /**
     * Delete entity
     * @param entity entity to be deleted
     */
    public void delete(T entity) {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();

        }

    }

}
