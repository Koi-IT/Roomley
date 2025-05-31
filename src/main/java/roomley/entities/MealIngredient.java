package roomley.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Meal ingredient.
 */
@Entity(name = "MealIngredient")
@Table(name = "meal_ingredients")
public class MealIngredient implements Serializable {

    @EmbeddedId
    private MealIngredientId id;

    @ManyToOne()
    @MapsId("mealId")
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @ManyToOne()
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id", nullable = false)
    private Ingredient ingredient;

    /**
     * Instantiates a new Meal ingredient.
     */
    public MealIngredient() {

    }

    /**
     * Instantiates a new Meal ingredient.
     *
     * @param id         the id
     * @param meal       the meal
     * @param ingredient the ingredient
     */
    public MealIngredient(MealIngredientId id, Meal meal, Ingredient ingredient) {
        this.id = id;
        this.meal = meal;
        this.ingredient = ingredient;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public MealIngredientId getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(MealIngredientId id) {
        this.id = id;
    }

    /**
     * Gets meal.
     *
     * @return the meal
     */
    public Meal getMeal() {
        return meal;
    }

    /**
     * Sets meal.
     *
     * @param meal the meal
     */
    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    /**
     * Gets ingredient.
     *
     * @return the ingredient
     */
    public Ingredient getIngredient() {
        return ingredient;
    }

    /**
     * Sets ingredient.
     *
     * @param ingredient the ingredient
     */
    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public String toString() {
        return "MealIngredient{" +
                "id=" + id +
                ", meal=" + meal +
                ", ingredient=" + ingredient +
                '}';
    }

}
