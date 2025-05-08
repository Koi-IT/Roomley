package roomley.persistence;

import roomley.entities.User;
import roomley.entities.Task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type User dao.
 */
public class UserDao extends GenericDao<User> {

    private final Logger logger = LogManager.getLogger(this.getClass());
    SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();

    /**
     * Constructor
     * Setup generic dao with User class
     */
    public UserDao() {
        super(User.class);

    }

    /**
     * Get user cognito sub
     * @param cognitoSub the cognito sub
     * @return the user found
     */
    public User getBySub(String cognitoSub) {
        List<User> results = getByPropertyEqual("cognito_sub", cognitoSub);
        return results.isEmpty() ? null : results.get(0);

    }

    /**
     * Get user by id
     *
     * @param id the id
     * @return the by id
     */
    public User getById(int id) {
        return super.getById(id);

    }

    /**
     * update user
     *
     * @param user User to be updated
     */
    public void update(User user) {
        super.update(user);

    }

    /**
     * insert a new user
     *
     * @param user User to be inserted
     * @return the int
     */
    public User insert(User user) {
        super.insert(user);
        return user;

    }


    /**
     * Delete a user
     *
     * @param user User to be deleted
     */
    public void delete(User user) {
        super.delete(user);

    }

    /**
     * Return a list of all users
     *
     * @return All users
     */
    public List<User> getAllUsers() {
        List<User> users = super.getAll();
        logger.debug("The list of users {}", users);
        return users;

    }

    /**
     * Get user by property (exact match)
     * sample usage: getByPropertyEqual("lastname", "Curry")
     *
     * @param propertyName the property name
     * @param value        the value
     * @return the by property equal
     */
    public List<User> getByPropertyEqual(String propertyName, String value) {
        return super.getByPropertyEqual(propertyName,value);

    }

    /**
     * Get user by property (like)
     * sample usage: getByPropertyLike("lastname", "C")
     *
     * @param propertyName the property name
     * @param value        the value
     * @return the by property like
     */
    public List<User> getByPropertyLike(String propertyName, String value) {
        return super.getByPropertyLike(propertyName, value);

    }

}
