package roomley.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomley.entities.Task;
import roomley.util.Database;

import static org.junit.jupiter.api.Assertions.*;
class InvitationDaoTest {

    GenericDao<Task, Integer> taskDao;
    Task task;

    /**
     * Before each test reset db and create a new taskDao instance
     * @throws Exception If runSQL fails throw exception
     */
    @BeforeEach
    void setUp() throws Exception {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        taskDao = new GenericDao<>(Task.class);

    }

    @Test
    void getById() {
        assertNotNull(taskDao.getById(1));

    }

    @Test
    void getAll() {
        assertNotNull(taskDao.getAll());

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