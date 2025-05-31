package roomley.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomley.entities.Task;
import roomley.entities.User;
import roomley.util.Database;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for the user entity database connection using the GenericDao
 */
class UserDaoTest {

    User user;
    GenericDao<User> userDao;

    /**
     * Before each test reset db and create a new taskDao instance
     * @throws Exception If runSQL fails throw exception
     */
    @BeforeEach
    void setUp() throws Exception {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        userDao = new GenericDao<>(User.class);

    }

    /**
     * Test getting a user by Id
     */
    @Test
    void getById() {
        user = userDao.getById(7);
        assertNotNull(user);
        assertEquals(7, user.getId());
        assertEquals("cognito_sub", user.getCognitoSub());
        assertEquals("email", user.getUserEmail());
        assertEquals("role", user.getRole());
//        assertEquals(3, user.getTasks().size());

    }

    /**
     * Test getting all users
     */
    @Test
    void getAll() {
        assertEquals(5, userDao.getAll().size());

    }

    /**
     * Test getting by a property value
     */
    @Test
    void getByPropertyEqual() {
        List<User> results = userDao.getByPropertyEqual("cognito_sub", "cognito_sub");
        assertFalse(results.isEmpty(), "Expected at least one result but got none.");
        assertEquals("cognito_sub", results.get(0).getCognitoSub());

    }

    /**
     * Test getting users by a property like query
     */
    @Test
    void getByPropertyLike() {
        List<User> results = userDao.getByPropertyLike("username", "username");
        assertFalse(results.isEmpty(), "Expected at least one result but got none.");
        assertEquals(2, results.size());

    }

    /**
     * Test deleting a user with tasks to make sure they are all deleted with the user
     */
    @Test
    void deleteUserAndTasks() {
        GenericDao<Task> taskDao = new GenericDao<>(Task.class);

        user = userDao.getById(7);
        userDao.delete(user);
        assertNull(userDao.getById(7));

        List<User> results = userDao.getAll();
        assertFalse(results.isEmpty(), "Expected at least one result but got none.");
        assertEquals(2, results.size());

        taskDao.getByPropertyEqual("user", 7);

    }

    @Test
    void getByPropertiesEqual() {
        // Test success
        Map<String, Object> userQuery1 = new HashMap<>();
        userQuery1.put("username", "username");

        Map<String, Object> userQuery2 = new HashMap<>();
        userQuery2.put("username", "username2");

        Map<String, Object> query3 = new HashMap<>();
        query3.put("tasks.taskName", "clean room");

        assertEquals(userDao.getById(7), userDao.getByPropertiesEqual(userQuery1).get(0));
        assertEquals(userDao.getById(8), userDao.getByPropertiesEqual(userQuery2).get(0));
        assertEquals(userDao.getById(7), userDao.getByPropertiesEqual(query3).get(0));

        // Test failure
        Map<String, Object> nonUser = new HashMap<>();

        nonUser.put("username", "non-existent");
        assertTrue(userDao.getByPropertiesEqual(nonUser).isEmpty());

        List<Object> emptyList = new ArrayList<>();
        Map<String, Object> emptyMap = new HashMap<>();
        assertEquals(emptyList, userDao.getByPropertiesEqual(emptyMap));

        Map<String, Object> missingField = new HashMap<>();
        missingField.put("tasks.NonExistentTaskField", "clean room");

        assertEquals(emptyList, userDao.getByPropertiesEqual(missingField));

    }

    /**
     * Test inserting a new user
     */
    @Test
    void insert() {
        User user = new User();

        user.setCognitoSub("cognito_sub");
        user.setEmail("email");
        user.setRole("role");
        user.setUsername("test");
        user.setCreateAt(Timestamp.valueOf(LocalDateTime.now()));
        user.setLastLogin(Timestamp.valueOf(LocalDateTime.now()));
        userDao.insert(user);

        assertNotNull(userDao.getByPropertyEqual("username", "test"));
        assertEquals("cognito_sub", user.getCognitoSub());
        assertEquals("email", user.getUserEmail());
        assertEquals("role", user.getRole());
        assertEquals("test", user.getUsername());
//        assertEquals(0, user.getTasks().size());

    }

    /**
     * Test updating a user
     */
    @Test
    void update() {
        user = userDao.getById(7);

        user.setEmail("email_updated");
        user.setRole("role_updated");
        user.setUsername("test_updated");
        user.setLastLogin(Timestamp.valueOf(LocalDateTime.now()));
        userDao.update(user);

        assertNotNull(userDao.getById(7));
        assertEquals("email_updated", user.getUserEmail());
        assertEquals("role_updated", user.getRole());
        assertEquals("test_updated", user.getUsername());
//        assertEquals(3, user.getTasks().size());

    }

    /**
     * Test deleting a user
     */
    @Test
    void delete() {
        user = userDao.getById(7);

        userDao.delete(user);
        assertNull(userDao.getById(7));

    }

}