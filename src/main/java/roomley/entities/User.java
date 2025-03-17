package roomley.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;
import roomley.persistence.UserDao;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
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

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "first_name")
    private String userFirstName;

    @Column(name = "last_name")
    private String userLastName;

    @Column(name = "birthdate")
    private String userBirthDate;

    @Column(name = "email")
    private String userEmail;

    @Column(name = "user_level")
    private int userLevel;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "household")
    private String userHousehold;

    @Column(name = "Households_household_id")
    private int householdId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Task> task = new ArrayList<>();

    /**
     * No argument constructor for User
     */
    public User() {

    }

    /**
     * Instantiates a new User.
     *
     * @param username      the username
     * @param password      the password
     * @param userFirstName the user first name
     * @param userLastName  the user last name
     * @param userBirthDate the user birth date
     * @param userEmail     the user email
     * @param userLevel     the user level
     * @param userType      the user type
     * @param userHousehold the user household
     */
    public User(String username,  String password, String userFirstName, String userLastName,String userBirthDate, String userEmail, int userLevel, String userType , String userHousehold, int householdId) {
        this.username = username;
        this.password = password;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userBirthDate = userBirthDate;
        this.userEmail = userEmail;
        this.userLevel = userLevel;
        this.userType = userType;
        this.userHousehold = userHousehold;
        this.householdId = householdId;

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

    /**
     * Gets user first name.
     *
     * @return the user first name
     */
    public String getUserFirstName() {
        return userFirstName;
    }

    /**
     * Sets user first name.
     *
     * @param userFirstName the user first name
     */
    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    /**
     * Gets user last name.
     *
     * @return the user last name
     */
    public String getUserLastName() {
        return userLastName;
    }

    /**
     * Sets user last name.
     *
     * @param userLastName the user last name
     */
    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    /**
     * Gets user birthDate.
     *
     * @return the user birthDate
     */
    public String getUserBirthDate() {
        return userBirthDate;
    }

    /**
     * Sets user birth date.
     *
     * @param userBirthDate the user birth date
     */
    public void setUserBirthDate(String userBirthDate) {
        this.userBirthDate = userBirthDate;
    }

    /**
     * Gets user household.
     *
     * @return the user household
     */
    public String getUserHousehold() { return userHousehold; }

    /**
     * Sets user household.
     *
     * @param userHousehold the user household
     */
    public void setUserHousehold(String userHousehold) {  this.userHousehold = userHousehold; }

    /**
     * Add task.
     */
    public void addTask() {
        Task task = new Task();
        this.task.add(task);
    }

    /**
     * Remove task.
     */
    public void removeTask() { this.task.remove(this.task.get(0)); }

    /**
     * Sets tasks.
     *
     * @param tasks the tasks
     */
    public void setTasks(List<Task> tasks) { this.task = tasks; }

    /**
     * Gets tasks.
     *
     * @return the tasks
     */
    public List<Task> getTasks() {  return this.task; }

    /**
     * Gets Age
     *
     * @return the age
     */
    public String getAge() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDate = LocalDate.parse(userBirthDate, formatter);
        LocalDate currentDate = LocalDate.now();

        int age = Period.between(birthDate, currentDate).getYears();
        return String.valueOf(age);
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", userFirstName='" + userFirstName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", userBirthDate='" + userBirthDate + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", userLevel=" + userLevel +
                ", userType='" + userType + '\'' +
                ", task=" + task +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        User other = (User) o;

        return username.equals(other.username) &&
                password.equals(other.password) &&
                userFirstName.equals(other.userFirstName) &&
                userLastName.equals(other.userLastName) &&
                userBirthDate.equals(other.userBirthDate) &&
                userEmail.equals(other.userEmail) &&
                userLevel == other.userLevel &&
                userType.equals(other.userType) &&
                userHousehold.equals(other.userHousehold) &&
                householdId == other.householdId;
    }

}
