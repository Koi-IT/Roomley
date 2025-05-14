package roomley.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomley.entities.Household;
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

    }

    @Test
    void getAll() {

    }

    @Test
    void getByPropertyEqual() {

    }

    @Test
    void getByPropertyLike() {

    }

    @Test
    void getByPropertiesEqual() {
    }

    @Test
    void insert() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}