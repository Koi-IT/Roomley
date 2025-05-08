package roomley.persistence;

import roomley.entities.Task;
import roomley.entities.User;
import roomley.util.Database;

import java.sql.Timestamp;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDaoTest {

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    UserDao userDao;
    TaskDao taskDao;

    @BeforeEach
    public void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        this.userDao = new UserDao();
        this.taskDao = new TaskDao();

    }

    @Test
    void getById() {
        User retrievedUser = this.userDao.getById(1);
        assertEquals("ajohnson@koi-dev.com", retrievedUser.getUserEmail());

    }

    @Test
    void getBySub() {
        User retrievedUser = this.userDao.getBySub("cognito_sub");
        assertEquals("email", retrievedUser.getUserEmail());

    }

    @Test
    void getBySubNotFound() {
        User retrievedUser = this.userDao.getBySub("");
        assertNull(retrievedUser);

    }

    @Test
    void update() {
        User userUpdated = this.userDao.getByPropertyEqual("username", "username").get(0);
        userUpdated.setCognitoSub("cognito_sub");
        this.userDao.update(userUpdated);
        assertEquals("cognito_sub", userUpdated.getCognitoSub());

    }

    @Test
    void insert() {
        User userToInsert = new User("cognito_sub2", "username2", "email2", timestamp, timestamp, "role");
        User insertedUser = this.userDao.insert(userToInsert);
        String insertedUserSub = insertedUser.getCognitoSub();

        assertEquals(userToInsert.getCognitoSub(), insertedUser.getCognitoSub());
        assertEquals(userToInsert.getUsername(), insertedUser.getUsername());
        assertEquals(userToInsert.getUserEmail(), insertedUser.getUserEmail());
        assertEquals(userToInsert.getRole(), insertedUser.getRole());


    }

    @Test
    void delete() {
        List<User> userToDelete = this.userDao.getByPropertyEqual("username", "username");
        this.userDao.delete(userToDelete.get(0));
        assertNull(this.userDao.getById(userToDelete.get(0).getId()));

    }

    @Test
    void deleteWithTasks() {
        User userToDelete = userDao.getByPropertyEqual("username", "username").get(0);
        int userId = userToDelete.getId();

        List<Task> tasks = userToDelete.getTasks();
        int taskOne = (tasks.get(0)).getTaskId();
        int taskTwo = (tasks.get(1)).getTaskId();

        userDao.delete(userToDelete);

        assertNull(userDao.getById(userId));
        assertNull(taskDao.getById(taskOne));
        assertNull(taskDao.getById(taskTwo));
    }

    @Test
    void getAll() {
        List<User> allUsers = this.userDao.getAllUsers();
        assertEquals(2, allUsers.size());

    }

    @Test
    void getByPropertyEqual() {
        List<User> user = this.userDao.getByPropertyEqual("email", "ajohnson@koi-dev.com");
        assertEquals("ajohnson@koi-dev.com", ((User)user.get(0)).getUserEmail());

    }

    @Test
    void getByPropertyLike() {
        List<User> propertyLikeEmail = this.userDao.getByPropertyLike("email", "ajohnson@koi-dev.com");
        assertEquals(1, propertyLikeEmail.size());

    }

}