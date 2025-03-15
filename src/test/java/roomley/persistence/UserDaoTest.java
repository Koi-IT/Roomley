package roomley.persistence;

import roomley.entities.Task;
import roomley.entities.User;
import roomley.util.Database;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UserDaoTest {

    UserDao userDao;

    @BeforeEach
    public void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        this.userDao = new UserDao();
    }

    @Test
    void getById() {
        User retrievedUser = this.userDao.getById(5);
        Assertions.assertNotNull(retrievedUser);
        Assertions.assertEquals("garry", retrievedUser.getUserFirstName());
    }

    @Test
    void update() {
        User userUpdated = this.userDao.getById(5);
        userUpdated.setUserFirstName("jake");
        this.userDao.update(userUpdated);
        User retrieveUserUpdate = this.userDao.getById(5);
        Assertions.assertEquals(userUpdated.getUserFirstName(), "jake");
    }

    @Test
    void insert() {
        User userToInsert = new User("Student", "Password", "First","Last", "2001-01-01", "Student@user.com", 1, "user", "household", 1);
        int insertedUserId = this.userDao.insert(userToInsert);
        Assertions.assertNotEquals(0, insertedUserId);
        User inserteduser = this.userDao.getById(insertedUserId);
        Assertions.assertEquals("First", inserteduser.getUserFirstName());
        Assertions.assertEquals("Last", inserteduser.getUserLastName());
        Assertions.assertEquals("Student", inserteduser.getUsername());
        Assertions.assertEquals("24", inserteduser.getAge());
        Assertions.assertEquals("Student@user.com", inserteduser.getUserEmail());
        Assertions.assertEquals("user", inserteduser.getUserType());
    }

    @Test
    void delete() {
        User userToDelete = this.userDao.getById(4);
        this.userDao.delete(userToDelete);
        Assertions.assertNull(this.userDao.getById(4));
    }

    @Test
    void deleteWithTasks() {
        UserDao userDao = new UserDao();
        TaskDao taskDao = new TaskDao();
        User userToDelete = userDao.getById(5);
        List<Task> tasks = userToDelete.getTasks();
        int TaskNumber1 = (tasks.get(0)).getTaskId();
        userDao.delete(userToDelete);
        Assertions.assertNull(userDao.getById(5));
        Assertions.assertNull(taskDao.getById(TaskNumber1));
    }

    @Test
    void getAll() {
        List<User> allUsers = this.userDao.getAllUsers();
        Assertions.assertEquals(4, allUsers.size());
    }

    @Test
    void getByPropertyEqual() {
        List<User> firstNameUser = this.userDao.getByPropertyEqual("userFirstName", "perry");
        Assertions.assertEquals("perry", ((User)firstNameUser.get(0)).getUserFirstName());
    }

    @Test
    void getByPropertyLike() {
        List<User> propertyLikeName = this.userDao.getByPropertyLike("userLastName", "green");
        Assertions.assertEquals(2, propertyLikeName.size());
    }
}
