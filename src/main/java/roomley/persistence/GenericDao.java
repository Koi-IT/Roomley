package roomley.persistence;

import com.sun.xml.bind.v2.model.core.ID;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import roomley.entities.Household;
import roomley.entities.HouseholdMember;
import org.apache.commons.beanutils.PropertyUtils;


import javax.persistence.criteria.*;
import java.io.Flushable;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Generic Dao for all database connections
 *
 * @param <T>  the type parameter
 * @param <ID> the type parameter
 */
public class GenericDao<T, ID extends Serializable> {

    private final Logger logger = LogManager.getLogger(this.getClass());
    private final Class<T> type;
    /**
     * The Session factory.
     */
    protected final SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * GenericDao constructor
     *
     * @param type the type of the entity class
     */
    public GenericDao(Class<T> type) {
        this.type = type;

    }

    /**
     * Get data by id
     *
     * @param id                id to be checked
     * @param collectionsToInit the collections to init
     * @return results of query
     */
    public T getById(ID id, String... collectionsToInit) {
        try (Session session = sessionFactory.openSession()) {
            T entity = session.get(type, id);
            if (entity != null) {
                for (String collection : collectionsToInit) {
                    Object collectionValue = PropertyUtils.getProperty(entity, collection);
                    Hibernate.initialize(collectionValue);
                }
            }
            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get all data from a table
     *
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
     *
     * @param propertyName the property name
     * @param value        the value to be checked
     * @return results of query
     */
    public List<T> getByPropertyEqual(String propertyName, Object value) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            Root<T> root = query.from(type);

            // Build the nested path (e.g., id.userId)
            Path<?> path = root;
            for (String part : propertyName.split("\\.")) {
                path = path.get(part);
            }

            query.select(root).where(builder.equal(path, value));

            return session.createQuery(query).getResultList();
        }
    }


    /**
     * Get property like
     *
     * @param propertyName the property name
     * @param value        the property value to be checked
     * @return results of query
     */
    public List<T> getByPropertyLike(String propertyName, Object value) {

        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            Root<T> root = query.from(type);
            query.where(builder.like(root.get(propertyName), "%" + value + "%"));
            return session.createQuery(query).getResultList();

        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

    }

    /**
     * Get by properties equal
     *
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
     *
     * @param entity entity to be added to db
     * @return the entity
     */
    public T insert(T entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(entity);
            session.flush();
            session.refresh(entity);
            transaction.commit();
            return entity;
        } catch (Exception e) {
            logger.error("Error inserting entity: " + e.getMessage(), e);
            return null;
        }
    }

    /**
     * Update an entity
     *
     * @param entity entity to be updated
     */
    public void update(T entity) {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(entity);
            transaction.commit();

        }

    }

    /**
     * Delete entity
     *
     * @param entity entity to be deleted
     */
    public void delete(T entity) {

        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();

        }

    }

    /**
     * Gets by id with init.
     *
     * @param id                the id
     * @param collectionsToInit the collections to init
     * @return the by id with init
     */
    public T getByIdWithInit(ID id, String... collectionsToInit) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        T entity = null;

        try {
            tx = session.beginTransaction();

            entity = session.get(type, id);

            for (String collection : collectionsToInit) {
                Object collectionValue = PropertyUtils.getProperty(entity, collection);
                Hibernate.initialize(collectionValue);

                if ("householdMembers".equals(collection)) {
                    List<HouseholdMember> members = (List<HouseholdMember>) collectionValue;
                    for (HouseholdMember m : members) {
                        Hibernate.initialize(m.getHousehold());
                        Hibernate.initialize(m.getHousehold().getHouseholdMembers());  // if you need nested members too
                    }
                }
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return entity;
    }

    /**
     * Gets household with members.
     *
     * @param householdId the household id
     * @return the household with members
     */
    public Household getHouseholdWithMembers(int householdId) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "SELECT h FROM Household h " +
                                    "LEFT JOIN FETCH h.householdMembers m " +
                                    "LEFT JOIN FETCH m.user " +
                                    "WHERE h.householdId = :id", Household.class)
                    .setParameter("id", householdId)
                    .uniqueResult();
        } catch (Exception e) {
            logger.error("Error fetching household with members", e);
            return null;
        }
    }


    /**
     * Gets session.
     *
     * @return the session
     */
    public Session getSession() {
        return sessionFactory.openSession();

    }

}
