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

    @BeforeEach
    public void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        this.userDao = new UserDao();
    }

    @Test
    void getById() {
//        User retrievedUser = this.userDao.getById(5);
//        assertNotNull(retrievedUser);
//        assertEquals("garry", retrievedUser.getUserFirstName());
    }

    @Test
    void update() {
//        User userUpdated = this.userDao.getById(5);
//        userUpdated.setUserFirstName("jake");
//        this.userDao.update(userUpdated);
//        User retrieveUserUpdate = this.userDao.getById(5);
//        assertEquals("jake", userUpdated.getUserFirstName());
    }

    @Test
    void insert() {
//        User userToInsert = new User("cognito_sub", "username", "email", timestamp, "role");
//        int insertedUserId = this.userDao.insert(userToInsert);
//
//        assertEquals(userToInsert, this.userDao.getById(insertedUserId));
    }

    @Test
    void delete() {
        User userToDelete = this.userDao.getById(4);
        this.userDao.delete(userToDelete);
        assertNull(this.userDao.getById(4));
    }

    @Test
    void deleteWithTasks() {
//        UserDao userDao = new UserDao();
//        TaskDao taskDao = new TaskDao();
//        User userToDelete = userDao.getById(5);
//        List<Task> tasks = userToDelete.getTasks();
//        int TaskNumber1 = (tasks.get(0)).getTaskId();
//        userDao.delete(userToDelete);
//        assertNull(userDao.getById(5));
//        assertNull(taskDao.getById(TaskNumber1));
    }

    @Test
    void getAll() {
        List<User> allUsers = this.userDao.getAllUsers();
        assertEquals(4, allUsers.size());
    }

    @Test
    void getByPropertyEqual() {
//        List<User> firstNameUser = this.userDao.getByPropertyEqual("userFirstName", "perry");
//        assertEquals("perry", ((User)firstNameUser.get(0)).getUserFirstName());
    }

    @Test
    void getByPropertyLike() {
        List<User> propertyLikeName = this.userDao.getByPropertyLike("userLastName", "green");
        assertEquals(2, propertyLikeName.size());
    }

}
