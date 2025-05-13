package roomley.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;

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

    @ManyToOne
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;

    /**
     * No arg constructor
     */
    public Meal() {

    }

    /**
     * Constructor to build Meal object
     *
     * @param mealId     meal id
     * @param mealName   meal name
     * @param user       user
     * @param ingredient ingredient
     */
    public Meal(int mealId, String mealName, User user, Ingredient ingredient) {
        this.mealId = mealId;
        this.mealName = mealName;
        this.user = user;
        this.ingredient = ingredient;

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
     * Get ingredient
     *
     * @return ingredient ingredient
     */
    public Ingredient getIngredient() {
        return ingredient;

    }

    /**
     * Set ingredient
     *
     * @param ingredient ingredient
     */
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;

    }

    @Override
    public String toString() {
        return "Meal{" +
                "mealId=" + mealId +
                ", mealName='" + mealName + '\'' +
                ", user=" + user +
                ", ingredient=" + ingredient +
                '}';
    }
}
