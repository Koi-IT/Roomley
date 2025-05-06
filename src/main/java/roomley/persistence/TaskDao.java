package roomley.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import roomley.entities.*;

/**
 * Task Dao to run CRUD operations for tasks and search operations using session factory
 */
public class TaskDao {

    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * Gets a task by its id
     * @param id The tasks id
     * @return The task
     */
    public Task getById(int id) {
        Session session = sessionFactory.openSession();
        Task task = session.get(Task.class, id);
        session.close();
        return task;

    }

    /**
     * Updates task
     * @param task Task object
     */
    public void update(Task task) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.merge(task);
        transaction.commit();
        session.close();

    }

    /**
     * Insert task column
     * @param task Task object
     */
    public void insert(Task task) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.persist(task);
        transaction.commit();
        session.close();

    }

    /**
     * Deletes task
     * @param task task to be deleted
     */
    public void delete(Task task) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.delete(task);
        transaction.commit();
        session.close();

    }

    /**
     * Gets all tasks
     * @return all tasks
     */
    public List<Task> getAllTasks() {

        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Task> query = builder.createQuery(Task.class);
        Root<Task> root = query.from(Task.class);
        List<Task> allTasks = session.createQuery(query).getResultList();

        logger.info("Total tasks: " + allTasks.size());
        session.close();

        return allTasks;

    }

    /**
     *
     */
    public List<Task> getTasksByUser(int userId) {

        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Task> query = builder.createQuery(Task.class);
        Root<Task> root = query.from(Task.class);
        query.select(root).where(
                builder.equal(root.get("user").get("id"), userId)
        );

        List<Task> assignedTask = session.createQuery(query).getResultList();

        for (Task task : assignedTask) {
            if (task.getUser() != null) {
                logger.info("Assigned User ID: " + task.getUser().getId());
            } else {
                logger.info("No user assigned to task ID: " + task.getTaskId());
            }
        }

        session.close();
        return assignedTask;

    }

    public List<Task> getTasksByStatus(int status) {

        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Task> query = builder.createQuery(Task.class);
        Root<Task> root = query.from(Task.class);
        query.select(root).where(
                builder.equal(root.get("status"), status)
        );

        List<Task> allTasks = session.createQuery(query).getResultList();

        logger.info("Total tasks: " + allTasks.size());

        session.close();
        return allTasks;

    }

    public List<Task> getCompletedTasksByUser(int userId) {
        Session session = sessionFactory.openSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Task> query = builder.createQuery(Task.class);
        Root<Task> root = query.from(Task.class);

        Predicate byUser = builder.equal(root.get("user").get("id"), userId);
        Predicate isCompleted = builder.equal(root.get("taskStatus"), true);

        query.select(root).where(builder.and(byUser, isCompleted));

        List<Task> completedTasks = session.createQuery(query).getResultList();
        session.close();
        return completedTasks;
    }


}
