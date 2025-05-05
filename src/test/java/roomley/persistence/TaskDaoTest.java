package roomley.persistence;

import roomley.entities.Task;
import roomley.entities.User;
import roomley.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


/**
 * Test for the TaskDao class
 */
class TaskDaoTest {

    TaskDao taskDao;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        Database database = new Database();
        Database dbconnection = database.getInstance();
        database.runSQL("cleandb.sql");
        this.taskDao = new TaskDao();

    }

    /**
     * Update.
     */
    @Test
    void update() {
        Task updatedTask = taskDao.getById(4);
        updatedTask.setTaskStatus(true);
        taskDao.update(updatedTask);

        assertTrue(updatedTask.getTaskStatus());

    }

    /**
     * Insert.
     */
    @Test
    void insert() {
        // Create Task to be inserted
        Task insertedTask = new Task();

        // Create User Dao
        UserDao userDao = new UserDao();

        // Get User
        User user = userDao.getById(5);

        // Set task columns
        insertedTask.setUser(user);
        insertedTask.setTaskName("test2");
        insertedTask.setTaskStatus(true);
        insertedTask.setTaskDescription("This is a test");
        insertedTask.setTaskDifficulty(0);

        //Insert task
        taskDao.insert(insertedTask);

        assertEquals(user, insertedTask.getUser());
        assertEquals("test2", insertedTask.getTaskName());
        assertTrue(insertedTask.getTaskStatus());
        assertEquals("This is a test", insertedTask.getTaskDescription());
        assertEquals(0, insertedTask.getTaskDifficulty());

    }

    /**
     * Delete.
     */
    @Test
    void delete() {
        Task taskToDelete = taskDao.getById(5);
        taskDao.delete(taskToDelete);

        UserDao userDao = new UserDao();

        assertNull(taskDao.getById(5));
        assertNotNull(userDao.getById(5));

    }

    /**
     * Gets all tasks.
     */
    @Test
    void getAllTasks() {
        List<Task> taskList = taskDao.getAllTasks();

        assertFalse(taskList.isEmpty());
        assertEquals(2, taskList.size());

    }
}