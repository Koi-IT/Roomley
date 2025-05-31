package roomley.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Meal.
 */
@Entity(name = "Meal")
@Table(name = "meals")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "meal_id")
    private int mealId;

    @Column(name = "meal_name")
    private String mealName;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealIngredient> mealIngredients = new ArrayList<>();


    /**
     * No arg constructor
     */
    public Meal() {

    }

    /**
     * Instantiates a new Meal.
     *
     * @param mealId          the meal id
     * @param mealName        the meal name
     * @param user            the user
     * @param mealIngredients the meal ingredients
     */
    public Meal(int mealId, String mealName, User user, List<MealIngredient> mealIngredients) {
        this.mealId = mealId;
        this.mealName = mealName;
        this.user = user;
        this.mealIngredients = mealIngredients;
    }

    /**
     * Get meal id
     *
     * @return meal id
     */
    public int getMealId() {
        return mealId;

    }

    /**
     * Set meal id
     *
     * @param mealId meal id
     */
    public void setMealId(int mealId) {
        this.mealId = mealId;

    }

    /**
     * Get meal name
     *
     * @return meal name
     */
    public String getMealName() {
        return mealName;

    }

    /**
     * Set meal name
     *
     * @param mealName meal name
     */
    public void setMealName(String mealName) {
        this.mealName = mealName;

    }

    /**
     * Get user
     *
     * @return user user
     */
    public User getUser() {
        return user;

    }

    /**
     * Set user
     *
     * @param user user
     */
    public void setUser(User user) {
        this.user = user;

    }

    /**
     * Gets meal ingredients.
     *
     * @return the meal ingredients
     */
    public List<MealIngredient> getMealIngredients() {
        return mealIngredients;
    }

    /**
     * Sets meal ingredients.
     *
     * @param mealIngredients the meal ingredients
     */
    public void setMealIngredients(List<MealIngredient> mealIngredients) {
        this.mealIngredients = mealIngredients;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "mealId=" + mealId +
                ", mealName='" + mealName + '\'' +
                ", user=" + user +
                ", mealIngredients=" + mealIngredients +
                '}';
    }

}
