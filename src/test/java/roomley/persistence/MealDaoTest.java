package roomley.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomley.entities.Meal;
import roomley.util.Database;

import static org.junit.jupiter.api.Assertions.*;

class MealDaoTest {

    GenericDao<Meal> mealDao;
    Meal meal;

    /**
     * Before each test reset db and create a new mealDao instance
     * @throws Exception If runSQL fails throw exception
     */
    @BeforeEach
    void setUp() throws Exception {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        mealDao = new GenericDao<>(Meal.class);

    }

    @Test
    void getById() {
        meal = mealDao.getById(1);

        assertNotNull(meal);
        assertEquals("Carrot Soup", meal.getMealName());
        assertEquals(7, meal.getUser().getId());
//        assertEquals(1, meal.getIngredient().getIngredientId());

    }

    @Test
    void getAll() {
        assertNotNull(mealDao.getAll());
        assertEquals(2, mealDao.getAll().size());

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