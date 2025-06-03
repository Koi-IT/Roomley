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

        // Test Failcase
        Map<String, Object> failedProperties = new HashMap<>();
        List<User> failedResults = userDao.getByPropertiesEqual(failedProperties);
        assertTrue(failedResults.isEmpty());

    }

    @Test
    void userHouseholdMembersAreInitialised() {
        // Arrange – grab a known user that already has members
        int userId = userDao.getByPropertyEqual("displayName", "User One")
                .get(0).getId();

        // Act
        User reloaded = userDao.getById(userId, "householdMembers");

        // Assert – collection itself
        assertTrue(Hibernate.isInitialized(reloaded.getHouseholdMembers()));
        assertFalse(reloaded.getHouseholdMembers().isEmpty());

        // Assert – nested collection (only matters if you called init inside the loop)
        HouseholdMember member = reloaded.getHouseholdMembers().get(0);
        assertTrue(Hibernate.isInitialized(member.getHousehold()));
        assertTrue(Hibernate.isInitialized(member.getHousehold()
                .getHouseholdMembers())); // second-level
    }

    @Test
    void getByIdWithInit_initialisesNestedCollections() {
        int userId = userDao.getByPropertyEqual("displayName", "User One")
                .get(0).getId();

        User reloaded = userDao.getByIdWithInit(userId, "householdMembers");

        // Same asserts as above, but you’re exercising the other code path
        assertTrue(Hibernate.isInitialized(reloaded.getHouseholdMembers()));
        HouseholdMember member = reloaded.getHouseholdMembers().get(0);
        assertTrue(Hibernate.isInitialized(member.getHousehold()));
        assertTrue(Hibernate.isInitialized(member.getHousehold().getHouseholdMembers()));
    }

    @Test
    void getByPropertiesEqual_invalidPath_returnsEmptyList() {
        Map<String,Object> badFilter = Map.of("foo.bar.baz", 123);
        List<User> results = userDao.getByPropertiesEqual(badFilter);
        assertTrue(results.isEmpty(), "DAO should swallow reflection error and return []");
    }

    @Test
    void getSession_returnsCurrentSession() {
        assertNotNull(userDao.getSession());
    }

    @Test
    void getByPropertiesEqual_emptyMap_returnsEmptyList() {
        Map<String, Object> emptyMap = new HashMap<>();
        List<User> results = userDao.getByPropertiesEqual(emptyMap);
        assertTrue(results.isEmpty());
    }

    @Test
    void getById_invalidId_returnsNull() {
        User result = userDao.getById(-1);
        assertNull(result, "Should return null for a non-existent user ID");
    }

    @Test
    void getByIdWithInit_invalidProperty_throwsExceptionHandled() {
        User user = userDao.getByPropertyEqual("displayName", "User One").get(0);
        User result = userDao.getByIdWithInit(user.getId(), "nonExistentProperty");
        assertNotNull(result); // DAO should handle and return the user still
    }

    @Test
    void getByPropertyLike_invalidProperty_throwsExceptionHandled() {
        List<User> result = userDao.getByPropertyLike("nonExistentField", "test");
        assertTrue(result.isEmpty(), "Should handle exception and return empty list");
    }

    @Test
    void insert_invalidEntity_returnsNull() {
        User badUser = new User();
        User result = userDao.insert(badUser);
        assertNull(result, "Insert should fail and return null");
    }

    @Test
    void delete_transientEntity_handlesGracefully() {
        User transientUser = new User();
        try {
            userDao.delete(transientUser);
        } catch (Exception e) {
            fail("Should not throw error for transient entities, but got: " + e.getMessage());
        }
    }

    @Test
    void update_transientEntity_throwsExceptionHandled() {
        User newUser = new User();
        newUser.setUsername("willfail");

        try {
            userDao.update(newUser);
            fail("Expected an exception when updating a transient entity, but none was thrown.");
        } catch (Exception e) {
            assertTrue(e.getMessage().contains("Batch update returned unexpected row count"));
        }
    }



}