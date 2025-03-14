package roomley.persistence;

import roomley.entities.Task;
import roomley.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;


/**
 * Test for the TaskDao class
 */
class TaskDaoTest {

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        Database database = new Database();
        Database dbconnection = database.getInstance();
        database.runSQL("roomley/cleandb.sql");
        TaskDao taskDao = new TaskDao();

    }

    /**
     * Update.
     */
    @Test
    void update() {
        TaskDao taskDao = new TaskDao();
        Task updatedTask = taskDao.getById(1);
        updatedTask.setTaskStatus(true);
        taskDao.update(updatedTask);

        assert(updatedTask.getTaskStatus());

    }

    /**
     * Insert.
     */
    @Test
    void insert() {
        TaskDao taskDao = new TaskDao();
        Task insertedTask = new Task();
        insertedTask.setTaskName("test2");
        insertedTask.setTaskStatus(true);
        insertedTask.setTaskDescription("This is a test");

        taskDao.insert(insertedTask);

        assertTrue(insertedTask.getTaskStatus());

    }

    /**
     * Delete.
     */
    @Test
    void delete() {
        TaskDao taskDao = new TaskDao();
        Task taskToDelete = taskDao.getById(2);
        taskDao.delete(taskToDelete);

        assertNull(taskDao.getById(2));

    }

    /**
     * Gets all tasks.
     */
    @Test
    void getAllTasks() {
        TaskDao taskDao = new TaskDao();
        List<Task> taskList = taskDao.getAllTasks();

        assertFalse(taskList.isEmpty());

    }
}