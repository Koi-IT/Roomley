package roomley.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomley.entities.User;

import roomley.entities.Task;
import roomley.util.Database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for the task entity database connection using the GenericDao
 */
class TaskDaoTest {

    GenericDao<Task> taskDao;
    Task task;

    /**
     * Before each test reset db and create a new taskDao instance
     * @throws Exception If runSQL fails throw exception
     */
    @BeforeEach
    void setUp() throws Exception {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        taskDao = new GenericDao<>(Task.class);

    }

    /**
     * Test getting data by task id
     */
    @Test
    void getById() {
        assertEquals(5,taskDao.getByPropertyEqual("taskName", "clean room").get(0).getTaskId());

    }

    /**
     * Test getting all data from task table
     */
    @Test
    void getAll() {
        List<Task> tasks = taskDao.getAll();

        assertEquals(3, tasks.size());
        assertEquals("take out trash", tasks.get(0).getTaskName());
        assertEquals("clean livingroom", tasks.get(1).getTaskName());
        assertEquals("clean room", tasks.get(2).getTaskName());

    }

    /**
     * Test getting by property equal
     */
    @Test
    void getByPropertyEqual() {
        assertEquals("clean room", taskDao.getByPropertyEqual("taskName", "clean room").get(0).getTaskName());
        assertEquals("clean livingroom", taskDao.getByPropertyLike("taskName", "clean livingroom").get(0).getTaskName());

    }

    /**
     * Test getting by property like
     */
    @Test
    void getByPropertyLike() {
        assertTrue(taskDao.getByPropertyLike("taskName", "clean").get(0).getTaskStatus());
        assertFalse(taskDao.getByPropertyLike("taskName", "clean").get(1).getTaskStatus());
        assertEquals(2 ,taskDao.getByPropertyLike("taskName", "clean").size());

    }

    /**
     * Test getting by properties equal
     */
    @Test
    void getByPropertiesEqual() {
        // Test success
        Map<String, Object> taskQuery1 = new HashMap<>();
        taskQuery1.put("taskName", "clean room");

        Map<String, Object> taskQuery2 = new HashMap<>();
        taskQuery2.put("taskName", "clean livingroom");

        Map<String, Object> query3 = new HashMap<>();
        query3.put("user.role", "role");

        assertEquals(taskDao.getById(5), taskDao.getByPropertiesEqual(taskQuery1).get(0));
        assertEquals(taskDao.getById(4), taskDao.getByPropertiesEqual(taskQuery2).get(0));
        assertEquals(taskDao.getById(3), taskDao.getByPropertiesEqual(query3).get(0));

        // Test failure
        Map<String, Object> nonTask = new HashMap<>();

        nonTask.put("taskName", "non-existent");
        assertTrue(taskDao.getByPropertiesEqual(nonTask).isEmpty());

        ArrayList<Object> emptyList = new ArrayList<Object>();
        Map<String, Object> emptyMap = new HashMap<>();
        assertEquals(emptyList, taskDao.getByPropertiesEqual(emptyMap));

    }

    /**
     * Test deleting tasks associated with a user and not losing user data
     */
    @Test
    void deleteOnlyTasks() {
        GenericDao<User> userDao = new GenericDao<>(User.class);

        Task taskToDelete = taskDao.getById(3);
        assertNotNull(taskToDelete);

        taskDao.delete(taskToDelete);
        assertNull(taskDao.getById(3));

        userDao.getById(7);
        assertNotNull(userDao.getById(7));

    }

    /**
     * Test inserting a new task
     */
    @Test
    void insert() {
        task = new Task();

        task.setTaskName("test");
        task.setTaskDifficulty(1);
        task.setTaskStatus(false);
        task.setTaskDescription("test description");

        taskDao.insert(task);

        assertNotNull(taskDao.getByPropertyEqual("taskName", "test"));

    }

    /**
     * Test updating a task
     */
    @Test
    void update() {
        task = taskDao.getByPropertyEqual("taskName", "take out trash").get(0);

        task.setTaskName("test updated");
        task.setTaskDifficulty(1);
        task.setTaskStatus(false);
        task.setTaskDescription("test description updated");

        taskDao.update(task);

        Task updatedTask = taskDao.getById(task.getTaskId());

        assertNotNull(updatedTask);

        assertEquals(task.getTaskName(), updatedTask.getTaskName());
        assertEquals(task.getTaskDifficulty(), updatedTask.getTaskDifficulty());
        assertEquals(task.getTaskStatus(), updatedTask.getTaskStatus());
        assertEquals(task.getTaskDescription(), updatedTask.getTaskDescription());

    }

    /**
     * Test deleting a task
     */
    @Test
    void delete() {
        task = new Task();

        task.setTaskName("test");
        task.setTaskDifficulty(1);
        task.setTaskStatus(false);
        task.setTaskDescription("test description");

        taskDao.insert(task);

        int id = task.getTaskId();

        taskDao.delete(task);

        assertNull(taskDao.getById(id));

    }

}