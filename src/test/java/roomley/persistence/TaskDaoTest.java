package roomley.persistence;



import roomley.entities.Task;
import roomley.util.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;



class TaskDaoTest {

    @BeforeEach
    void setUp() {
        Database database = new Database();
        Database dbconnection = database.getInstance();
        database.runSQL("cleandb.sql");
        TaskDao taskDao = new TaskDao();

    }

    @Test
    void update() {
        TaskDao taskDao = new TaskDao();
        Task updatedTask = taskDao.getById(1);
        updatedTask.setTaskStatus(true);

        assert(updatedTask.getTaskStatus());

    }

    @Test
    void insert() {
        TaskDao taskDao = new TaskDao();
        Task insertedTask = new Task();
        insertedTask.setTaskName("test2");
        insertedTask.setTaskStatus(false);
        insertedTask.setTaskDescription("This is a test");

        taskDao.insert(insertedTask);

        assertFalse(insertedTask.getTaskStatus());

    }

    @Test
    void delete() {
        TaskDao taskDao = new TaskDao();
        Task taskToDelete = taskDao.getById(2);
        taskDao.delete(taskToDelete);

        assertNull(taskDao.getById(2));

    }

    @Test
    void getAllTasks() {
        TaskDao taskDao = new TaskDao();
        List<Task> taskList = taskDao.getAll();

        assertFalse(taskList.isEmpty());

    }
}