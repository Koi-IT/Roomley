package roomley.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * The type Meal ingredient id.
 */
@Embeddable
public class MealIngredientId implements Serializable {

    @Column(name = "meal_id")
    private int mealId;

    @Column(name = "ingredient_id")
    private int ingredientId;


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MealIngredientId that = (MealIngredientId) o;
        return mealId == that.mealId && ingredientId == that.ingredientId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(mealId, ingredientId);
    }

    /**
     * Instantiates a new Meal ingredient id.
     */
    public MealIngredientId() {

    }

    /**
     * Instantiates a new Meal ingredient id.
     *
     * @param mealId       the meal id
     * @param ingredientId the ingredient id
     */
    public MealIngredientId(int mealId, int ingredientId) {
        this.mealId = mealId;
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

    @Override
    public String toString() {
        return "MealIngredientId{" +
                "mealId=" + mealId +
                ", ingredientId=" + ingredientId +
                '}';
    }

}
