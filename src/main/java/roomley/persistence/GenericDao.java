package roomley.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenericDao<T> {

    private final Class<T> type;
    protected final SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    public GenericDao(Class<T> type) {
        this.type = type;
    }

    public T getById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(type, id);
        }
    }

    public List<T> getAll() {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            query.from(type);
            return session.createQuery(query).getResultList();
        }
    }

    public List<T> getByPropertyEqual(String propertyName, Object value) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            Root<T> root = query.from(type);
            query.select(root).where(builder.equal(root.get(propertyName), value));
            return session.createQuery(query).getResultList();
        }
    }

    public List<T> getByPropertyLike(String propertyName, Object value) {
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<T> query = builder.createQuery(type);
            Root<T> root = query.from(type);
            query.where(builder.like(root.get(propertyName), "%" + value + "%"));
            return session.createQuery(query).getResultList();
        }
    }

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
                Path<Object> path = root.get(keys[0]);
                for (int i = 1; i < keys.length; i++) {
                    path = path.get(keys[i]);
                }
                predicates.add(builder.equal(path, entry.getValue()));
            }

            query.select(root).where(builder.and(predicates.toArray(new Predicate[0])));
            return session.createQuery(query).getResultList();
        }
    }


    public T insert(T entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
            return entity;
        }
    }

    public void update(T entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(entity);
            transaction.commit();
        }
    }

    public void delete(T entity) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        }
    }
}
