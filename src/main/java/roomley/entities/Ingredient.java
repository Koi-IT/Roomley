package roomley.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * The type Ingredient.
 */
@Entity(name = "Ingredient")
@Table(name = "ingredients")
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native",strategy = "native")
    @Column(name = "ingredient_id")
    private int ingredientId;

    @Column(name = "meal_id")
    private int mealId;

    @Column(name = "ingredient_name")
    private String ingredientName;


    /**
     * Gets ingredient id.
     *
     * @return the ingredient id
     */
    public int getIngredientId() {
        return ingredientId;
    }

    /**
     * Sets ingredient id.
     *
     * @param ingredientId the ingredient id
     */
    public void setIngredientId(int ingredientId) {
        this.ingredientId = ingredientId;
    }

    /**
     * Gets meal id.
     *
     * @return the meal id
     */
    public int getMealId() {
        return mealId;
    }

    /**
     * Sets meal id.
     *
     * @param mealId the meal id
     */
    public void setMealId(int mealId) {
        this.mealId = mealId;
    }

    /**
     * Gets ingredient name.
     *
     * @return the ingredient name
     */
    public String getIngredientName() {
        return ingredientName;
    }

    /**
     * Sets ingredient name.
     *
     * @param ingredientName the ingredient name
     */
    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "ingredientId=" + ingredientId +
                ", mealId='" + mealId + '\'' +
                ", ingredientName='" + ingredientName + '\'' +
                '}';
    }
}
