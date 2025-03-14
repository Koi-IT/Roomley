package roomley.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;

/**
 * The type User.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private int id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String userRealName;

    @Column(name = "email")
    private String userEmail;

    @Column(name = "user_level")
    private int userLevel;

    @Column(name = "user_type")
    private String userType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Task> task = new ArrayList<>();

    /**
     * No argument constructor for User
     */
    public User () {

    }

    /**
     * Constructor to build User object
     *
     * @param username        The name of the User
     * @param userDescription The description of the User
     * @param userStatus      The status of the User
     */
    public User (String username,  String userDescription, boolean userStatus) {
        this.username = username;
        this.password = password;
        this.userRealName = userRealName;
        this.userEmail = userEmail;
        this.userLevel = userLevel;
        this.userType = userType;


    }


    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Gets user real name.
     *
     * @return the user real name
     */
    public String getUserRealName() {
        return userRealName;
    }

    /**
     * Gets user email.
     *
     * @return the user email
     */
    public String getUserEmail() {
        return userEmail;
    }

    /**
     * Gets user level.
     *
     * @return the user level
     */
    public int getUserLevel() {
        return userLevel;
    }

    /**
     * Gets user type.
     *
     * @return the user type
     */
    public String getUserType() {
        return userType;
    }

    /**
     * Gets task.
     *
     * @return the task
     */
    public List<Task> getTask() {
        return task;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets username.
     *
     * @param username the username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Sets user real name.
     *
     * @param userRealName the user real name
     */
    public void setUserRealName(String userRealName) {
        this.userRealName = userRealName;
    }

    /**
     * Sets user email.
     *
     * @param userEmail the user email
     */
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    /**
     * Sets user level.
     *
     * @param userLevel the user level
     */
    public void setUserLevel(int userLevel) {
        this.userLevel = userLevel;
    }

    /**
     * Sets user type.
     *
     * @param userType the user type
     */
    public void setUserType(String userType) {
        this.userType = userType;
    }

    /**
     * Sets task.
     *
     * @param task the task
     */
    public void setTask(List<Task> task) {
        this.task = task;
    }
}
