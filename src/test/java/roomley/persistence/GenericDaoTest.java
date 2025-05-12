package roomley.persistence;

import com.amazonaws.internal.SdkInternalMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomley.util.Database;

import roomley.entities.Task;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

class GenericDaoTest {

    @BeforeEach
    void setUp() throws Exception {
        Database database = Database.getInstance();
        database.runSQL("cleandb.sql");
        GenericDao<Task> taskDao = new GenericDao<>(Task.class);

    }

    @Test
    void getByIdTest() {

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