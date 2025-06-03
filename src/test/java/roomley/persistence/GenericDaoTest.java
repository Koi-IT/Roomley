package roomley.persistence;

import org.hibernate.Hibernate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomley.entities.Household;
import roomley.entities.HouseholdMember;
import roomley.entities.User;
import roomley.util.Database;
import roomley.services.UserService;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GenericDaoTest {

    Timestamp nowTimestamp = new Timestamp(System.currentTimeMillis());
    GenericDao<User, Integer> userDao;
    User user;

    @BeforeEach
    void setUp() {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        userDao = new GenericDao<>(User.class);

    }

    @Test
    void insert() {
        user = new User();
        user.setUsername("test");
        user.setRole("USER");
        user.setEmail("test@test.com");
        user.setCognitoSub("testCognitoSub");
        user.setLastLogin(nowTimestamp);

        userDao.insert(user);

        User userCreated = userDao.getByPropertyEqual("displayName", "test").get(0);

        assertNotNull(userCreated, "User should be inserted and retrievable.");
        assertEquals("test", userCreated.getUsername());
        assertEquals("USER", userCreated.getRole());
        assertEquals("testCognitoSub", userCreated.getCognitoSub());
        assertEquals("test@test.com", userCreated.getEmail());

    }

    @Test
    void getAllUsers() {
        userDao.getAll();

        assertFalse(userDao.getAll().isEmpty());
        assertEquals(10, userDao.getAll().size());

    }

    @Test
    void update() {
        User userToUpdate = userDao.getByPropertyEqual("displayName", "User One").get(0);

        userToUpdate.setUsername("Updated Username");
        userToUpdate.setLastLogin(nowTimestamp);
        userToUpdate.setEmail("Updated Email");
        userToUpdate.setRole("USER");
        userToUpdate.setCognitoSub("Updated CognitoSub");

        userDao.update(userToUpdate);

        assertEquals("Updated Username", userToUpdate.getUsername());
        assertEquals(nowTimestamp, userToUpdate.getLastLogin());
        assertEquals("Updated Email", userToUpdate.getEmail());
        assertEquals("USER", userToUpdate.getRole());
        assertEquals("Updated CognitoSub", userToUpdate.getCognitoSub());

    }

    @Test
    void delete() {
        GenericDao<Household, Integer> householdDao = new GenericDao<>(Household.class);

        UserService userService = new UserService();
        int userId = userDao.getByPropertyEqual("displayName", "User Two").get(0).getId();
        User userToDelete = userDao.getByIdWithInit(userId, "householdMembers");
        int householdId = householdDao.getByPropertyEqual("createdByUserId", userId).get(0).getHouseholdId();

        userService.removeUserFromHouseholdAndDeleteUser(householdId, userId);

        assertTrue(userDao.getByPropertyEqual("displayName", "User Two").isEmpty());

    }

    @Test
    void getById() {
        User userById = userDao.getById(2);
        assertEquals("User Two", userById.getUsername());
        assertEquals("USER", userById.getRole());
        assertEquals("sub2", userById.getCognitoSub());
        assertEquals("user2@example.com", userById.getEmail());
        assertEquals("USER", userById.getRole());

    }

    @Test
    void getByPropertyEqual() {
        User userByPropertyEqual = userDao.getByPropertyEqual("displayName", "User Two").get(0);

        assertEquals("User Two", userByPropertyEqual.getUsername());
        assertEquals("USER", userByPropertyEqual.getRole());
        assertEquals("sub2", userByPropertyEqual.getCognitoSub());
        assertEquals("user2@example.com", userByPropertyEqual.getEmail());
        assertEquals("USER", userByPropertyEqual.getRole());

    }

    @Test
    void getByPropertyLike() {
        List<User> userByPropertyLike = userDao.getByPropertyLike("displayName", "User T");

        assertEquals(3, userByPropertyLike.size());

        assertEquals("User Two", userByPropertyLike.get(0).getUsername());
        assertEquals("USER", userByPropertyLike.get(0).getRole());
        assertEquals("sub2", userByPropertyLike.get(0).getCognitoSub());
        assertEquals("user2@example.com", userByPropertyLike.get(0).getEmail());
        assertEquals("USER", userByPropertyLike.get(0).getRole());

        assertEquals("User Three", userByPropertyLike.get(1).getUsername());
        assertEquals("USER", userByPropertyLike.get(1).getRole());
        assertEquals("sub3", userByPropertyLike.get(1).getCognitoSub());
        assertEquals("user3@example.com", userByPropertyLike.get(1).getEmail());
        assertEquals("USER", userByPropertyLike.get(1).getRole());

        assertEquals("User Ten", userByPropertyLike.get(2).getUsername());
        assertEquals("USER", userByPropertyLike.get(2).getRole());
        assertEquals("sub10", userByPropertyLike.get(2).getCognitoSub());
        assertEquals("user10@example.com", userByPropertyLike.get(2).getEmail());
        assertEquals("USER", userByPropertyLike.get(2).getRole());

    }

    @Test
    void getByPropertiesEqual() {
        // Arrange
        User user = new User();
        user.setUsername("jdoe");
        user.setEmail("jdoe@example.com");
        user.setRole("ADMIN");
        user.setCognitoSub("cognito-test-sub");
        user.setLastLogin(nowTimestamp);

        userDao.insert(user);

        Map<String, Object> properties = new HashMap<>();
        properties.put("displayName", "jdoe");
        properties.put("email", "jdoe@example.com");

        // Act
        List<User> results = userDao.getByPropertiesEqual(properties);

        // Assert
        assertFalse(results.isEmpty(), "Should return at least one user");
        assertEquals("jdoe", results.get(0).getUsername());
        assertEquals("jdoe@example.com", results.get(0).getEmail());
    }

}