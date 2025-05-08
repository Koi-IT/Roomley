package roomley.persistence;

import com.amazonaws.internal.SdkInternalMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomley.util.Database;

import roomley.entities.Task;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
class GenericDaoTest {

    TaskDao taskDao;

    /**
     * Sets up.
     */
    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        this.taskDao = new TaskDao();
    }

    @Test
    void testSinglePropertyReturnsMatch() {
        // Arrange: make sure there is a task with name "clean livingroom"
        Map<String,Object> filter = new HashMap<>();
        filter.put("taskName", "clean livingroom");

        // Act: this will go into the CriteriaBuilder block
        List<Task> results = taskDao.getByPropertiesEqual(filter);

        // Assert: we got exactly the task we inserted earlier
        assertFalse(results.isEmpty());
        assertEquals("clean livingroom", results.get(0).getTaskName());
    }

    @Test
    void testMultiplePropertiesReturnsMatch() {
        Map<String,Object> filter = new HashMap<>();
        filter.put("taskName", "clean livingroom");
        filter.put("taskStatus", 1);

        List<Task> results = taskDao.getByPropertiesEqual(filter);

        assertFalse(results.isEmpty());
        Task t = results.get(0);
        assertEquals("clean livingroom", t.getTaskName());
        assertTrue( t.getTaskStatus());
    }

    @Test
    void testEmptyProperties() {
        Map<String, Object> emptyProperties = new HashMap<>();
        List<Task> result = taskDao.getByPropertiesEqual(emptyProperties);
        assertNotNull(result);  // Check that the result is not null
        assertTrue(result.isEmpty());  // Depends on expected behavior
    }

    @Test
    void testByPropertyReturnsMatch() {
        Map<String, Object> filters = new HashMap<>();
        filters.put("user.cognito_sub", "someSubValue");

        List<Task> result = taskDao.getByPropertiesEqual(filters);
    }

}