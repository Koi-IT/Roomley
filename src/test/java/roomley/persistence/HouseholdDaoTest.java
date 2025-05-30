package roomley.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomley.entities.Household;
import roomley.entities.HouseholdMember;
import roomley.entities.Task;
import roomley.util.Database;

import static org.junit.jupiter.api.Assertions.*;

class HouseholdDaoTest {

    GenericDao<Household> householdDao;
    Household household;

    /**
     * Before each test reset db and create a new householdDao instance
     * @throws Exception If runSQL fails throw exception
     */
    @BeforeEach
    void setUp() throws Exception {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        householdDao = new GenericDao<>(Household.class);

    }

    @Test
    void getById() {
        assertNotNull(householdDao.getById(1));

    }

    @Test
    void getAll() {
        assertNotNull(householdDao.getAll());

    }

    @Test
    void getByPropertyEqual() {
        assertEquals("The Smiths", householdDao.getByPropertyEqual("groupName", "The Smiths").get(0).getGroupName());

    }

    @Test
    void getByPropertyLike() {
        assertEquals("The Smiths", householdDao.getByPropertyLike("groupName", "Smiths").get(0).getGroupName());

    }

    @Test
    void getByPropertiesEqual() {
//        assertEquals();

    }

    @Test
    void insert() {
        household = new Household();
        household.setGroupName("Test Group");
        household.setHouseholdMembers();

        householdDao.insert(household);

        assertNotNull(householdDao.getById(1));


    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}