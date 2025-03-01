package Roomley.persistence;

import Roomley.entities.Task;
import Roomley.util.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskDaoTest {

    @BeforeEach
    void setUp() {
        Database database = database.getInstance();
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

        assertNull(taskToDelete);

    }

    @Test
    void getAllTasks() {
        TaskDao taskDao = new TaskDao();
        List<Task> taskList = taskDao.getAll();

        assertFalse(taskList.isEmpty());

    }
}