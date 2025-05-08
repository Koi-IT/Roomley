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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import roomley.entities.*;

/**
 * Task Dao to run CRUD operations for tasks and search operations using session factory
 */
public class TaskDao extends GenericDao<Task> {

    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
    UserDao userDao = new UserDao();

    /**
     * Constructor
     * Setup generic dao with Task class
     */
    public TaskDao() {
        super(Task.class);

    }

    /**
     * Gets a task by its id
     * @param id The tasks id
     * @return The task
     */
    public Task getById(int id) {
        return super.getById(id);

    }

    /**
     * Updates task
     * @param task Task object
     */
    public void update(Task task) {
        super.update(task);

    }

    /**
     * Insert task column
     *
     * @param task Task object
     * @return
     */
    public Task insert(Task task) {
        super.insert(task);
        return task;

    }

    /**
     * Deletes task
     * @param task task to be deleted
     */
    public void delete(Task task) {
        super.delete(task);

    }

    /**
     * Gets all tasks
     * @return all tasks
     */
    public List<Task> getAllTasks() {
        return super.getAll();

    }

    /**
     * Get tasks by user
     * @param userId user Id
     * @return all tasks made by a user
     */
    public List<Task> getTasksByUser(int userId) {
        UserDao userDao = new UserDao();
        User user = userDao.getById(userId);
        return super.getByPropertyEqual("user", user);

    }

    /**
     * Get tasks by status
     * @param status the status of the task
     * @return List of tasks
     */
    public List<Task> getTasksByStatus(boolean status) {
        return super.getByPropertyEqual("taskStatus", status);

    }

    /**
     * Get completed tasks by user
     * @param userId user id
     * @return tasks completed by user
     */
    public List<Task> getCompletedTasksByUser(int userId) {
        UserDao userDao = new UserDao();

        Map<String, Object> filters = new HashMap<>();
        filters.put("user", userDao.getById(userId));
        filters.put("taskStatus", true);

        return super.getByPropertiesEqual(filters);

    }

    public List<Task> getTasks(String userSub) {
        User user = userDao.getBySub(userSub);
        Map<String, Object> filters = new HashMap<>();
        filters.put("user", user);
        filters.put("taskStatus", true);
        return super.getByPropertiesEqual(filters);

    }

}
