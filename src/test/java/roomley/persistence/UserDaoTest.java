package roomley.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomley.entities.Task;
import roomley.entities.User;
import roomley.util.Database;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(3, user.getTasks().size());

    }

    /**
     * Test getting all users
     */
    @Test
    void getAll() {
        assertEquals(3, userDao.getAll().size());

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
        assertEquals(0, user.getTasks().size());

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
        assertEquals(3, user.getTasks().size());

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