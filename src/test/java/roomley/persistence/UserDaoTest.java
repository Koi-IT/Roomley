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

    UserDaoTest() {
    }

    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        this.userDao = new UserDao();
    }

    @Test
    void getById() {
        User retrievedUser = this.userDao.getById(1);
        Assertions.assertNotNull(retrievedUser);
        Assertions.assertEquals("Joe", retrievedUser.getFirstName());
    }

    @Test
    void update() {
        User userUpdated = this.userDao.getById(1);
        userUpdated.setFirstName("Jake");
        this.userDao.update(userUpdated);
        User retrieveUserUpdate = this.userDao.getById(1);
        Assertions.assertEquals(userUpdated.getFirstName(), "Jake");
    }

    @Test
    void insert() {
        User userToInsert = new User("Student", "Password", "First","Last", "2001-01-01", "Student@user.com", 1, "user");
        int insertedUserId = this.userDao.insert(userToInsert);
        Assertions.assertNotEquals(0, insertedUserId);
        User inserteduser = this.userDao.getById(insertedUserId);
        Assertions.assertEquals("First", inserteduser.getUserFirstName());
        Assertions.assertEquals("Last", inserteduser.getUserLastName());
        Assertions.assertEquals("Student", inserteduser.getUsername());
        Assertions.assertEquals("24", inserteduser.getAge());
    }

    @Test
    void delete() {
        User userToDelete = this.userDao.getById(1);
        this.userDao.delete(userToDelete);
        Assertions.assertNull(this.userDao.getById(1));
    }

    @Test
    void deleteWithTasks() {
        UserDao userDao = new UserDao();
        TaskDao taskDao = new TaskDao();
        User userToDelete = userDao.getById(3);
        List<Task> tasks = userToDelete.getAll();
        int TaskNumber1 = ((Task)tasks.get(0)).getId();
        int TaskNumber2 = ((Task)tasks.get(1)).getId();
        userDao.delete(userToDelete);
        Assertions.assertNull(userDao.getById(3));
        Assertions.assertNull(taskDao.getById(TaskNumber1));
        Assertions.assertNull(taskDao.getById(TaskNumber2));
    }

    @Test
    void getAll() {
        List<User> allUsers = this.userDao.getAll();
        Assertions.assertEquals(6, allUsers.size());
    }

    @Test
    void getByPropertyEqual() {
        List<User> firstNameUser = this.userDao.getByPropertyEqual("firstName", "Joe");
        Assertions.assertEquals("Joe", ((User)firstNameUser.get(0)).getUserFirstName());
    }

    @Test
    void getByPropertyLike() {
        List<User> propertyLikeName = this.userDao.getByPropertyLike("userName", "d");
        Assertions.assertEquals(2, propertyLikeName.size());
    }
}
