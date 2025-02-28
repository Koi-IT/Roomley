package Roomley.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;


import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import Roomley.entities.Task;


public class TaskDao {

    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    public void update(Task task) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(task);
        transaction.commit();
        session.close();

    }

    public int insert(Task task) {
        int id = 0;
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(task);
        transaction.commit();
        session.close();

        return id;

    }

    public void delete(Task task) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(task);
        transaction.commit();
        session.close();

    }

    public List<Task> getAllTasks() {

        Session session = sessionFactory.openSession();

        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Task> criteriaQuery = builder.createQuery(Task.class);
        Root<Task> root = criteriaQuery.from(Task.class);
        List<Task> allTasks = session.createQuery(criteriaQuery).getResultList();

        logger.info("Total tasks: " + allTasks.size());
        session.close();

        return allTasks;

    }

}
