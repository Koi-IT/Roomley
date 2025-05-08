package roomley.entities;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * The type User.
 */
@Entity(name = "User")
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "user_id")
    private int id;

    @Column(name = "cognito_sub")
    private String cognito_sub;

    @Column(name = "display_name")
    private String username;

    @Column(name = "user_created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "email")
    private String email;

    @Column(name = "last_login")
    private java.sql.Timestamp last_login;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Task> tasks = new ArrayList<>();

    /**
     * No argument constructor for User
     */
    public User() {
        this.created_at = Timestamp.valueOf(LocalDateTime.now());

    }

    /**
     * User constructor
     *
     * @param cognito_sub Cognito Sub
     * @param username Username
     * @param email Email
     * @param last_login Last Login
     * @param role User Role
     */
    public User( String cognito_sub, String username, String email, java.sql.Timestamp user_created_at,java.sql.Timestamp last_login, String role) {
        this.cognito_sub = cognito_sub;
        this.username = username;
        this.email = email;
        this.last_login = last_login;
        this.role = role;
        this.created_at = Timestamp.valueOf(LocalDateTime.now());

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
     * Gets Cognito Sub
     *
     * @return the Cognito Sub
     */
    public String getCognitoSub() { return cognito_sub; }

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets user email.
     *
     * @return the user email
     */
    public String getUserEmail() {
        return email;
    }

    /**
     * Gets user level.
     *
     * @return the user level
     */
    public Timestamp getCreatedAt() {
        return created_at;
    }

    /**
     * Gets user type.
     *
     * @return the user type
     */
    public java.sql.Timestamp getLastLogin() {
        return last_login;
    }

    /**
     * Gets user role.
     *
     * @return  the user type
     */
    public String getRole() { return role; }

    /**
     * Returns the list of tasks associated with this user.
     * Modifications to the returned list may affect the internal task list.
     * @return task list for the user
     */
    public List<Task> getTasks() { return tasks; }

    /**
     * Sets cognito_sub.
     *
     * @param cognito_sub the cognito_sub
     */
    public void setCognitoSub(String cognito_sub) {
        this.cognito_sub = cognito_sub;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets user created_at.
     *
     * @param created_at at the user created_at
     */
    public void setCreateAt(java.sql.Timestamp created_at) {
        this.created_at = created_at;
    }

    /**
     * Sets last_login.
     *
     * @param last_login the last_login
     */
    public void setLastLogin(java.sql.Timestamp last_login) {
        this.last_login = last_login;
    }

    /**
     * Sets user role.
     *
     * @param role the user role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Sets username
     *
     * @param username the username
     */
    public void setUsername(String username) { this.username = username; }

    /**
     * To string override for logging
     * @return User log
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", cognito_sub='" + cognito_sub + '\'' +
                ", username='" + username + '\'' +
                ", created_at=" + created_at +
                ", email='" + email + '\'' +
                ", last_login=" + last_login +
                ", role='" + role + '\'' +
                '}';
    }
}
