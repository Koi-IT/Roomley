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
    UserDao userDao;


    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        this.taskDao = new TaskDao();
        this.userDao = new UserDao();

    }

    /**
     * Update.
     */
    @Test
    void update() {
        Task task = userDao.getBySub("cognito_sub").getTasks().get(0);
        Task updatedTask = taskDao.getById(task.getTaskId());
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

        // Get User
        User user = userDao.getBySub("cognito_sub");

        // Set task columns
        insertedTask.setUser(user);
        insertedTask.setTaskStatus(true);
        insertedTask.setTaskDifficulty(1);
        insertedTask.setTaskDescription("This is a test");
        insertedTask.setTaskName("This is a test");

        taskDao.insert(insertedTask);
        assertTrue(insertedTask.getTaskStatus());
        assertTrue(insertedTask.getTaskDifficulty() == 1);
        assertTrue(insertedTask.getTaskDescription().equals("This is a test"));
        assertTrue(insertedTask.getTaskName().equals("This is a test"));
        assertEquals(1, insertedTask.getTaskDifficulty());

    }

    /**
     * Delete.
     */
    @Test
    void delete() {
        Task taskToDelete = taskDao.getByPropertyEqual("taskName", "clean livingroom").get(0);
        taskDao.delete(taskToDelete);

        assertTrue(taskDao.getByPropertyEqual("taskName", "clean livingroom").isEmpty());

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

    @Test
    void getTaskByUser() {
        int userId = userDao.getBySub("cognito_sub").getId();
        List<Task> taskList = taskDao.getTasksByUser(userId);
        assertFalse(taskList.isEmpty());
        assertEquals(2, taskList.size());

    }

    @Test
    void getTasks() {
        String userCognitoSub = userDao.getBySub("cognito_sub").getCognitoSub();
        List<Task> taskList = taskDao.getTasks(userCognitoSub);
        assertFalse(taskList.isEmpty());
        assertEquals(1, taskList.size());

    }

    @Test
    void getTasksByStatus() {
        List<Task> taskList = taskDao.getTasksByStatus(true);
        assertFalse(taskList.isEmpty());
        assertEquals(1, taskList.size());

    }

    @Test
    void getCompletedTasksByUser() {
        int userId = userDao.getBySub("cognito_sub").getId();
        List<Task> taskList = taskDao.getCompletedTasksByUser(userId);
        assertFalse(taskList.isEmpty());
        assertEquals(1, taskList.size());

    }

}