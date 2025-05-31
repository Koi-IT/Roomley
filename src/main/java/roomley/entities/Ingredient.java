package roomley.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "ingredient_name")
    private String ingredientName;

    @OneToMany(mappedBy = "ingredient", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealIngredient> mealIngredients = new ArrayList<>();

    /**
     * Instantiates a new Ingredient.
     */
    public Ingredient() {

    }

    /**
     * Instantiates a new Ingredient.
     *
     * @param ingredientId    the ingredient id
     * @param ingredientName  the ingredient name
     * @param mealIngredients the meal ingredients
     */
    public Ingredient(int ingredientId, String ingredientName, List<MealIngredient> mealIngredients) {
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.mealIngredients = mealIngredients;
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
        return "Ingredient{" +
                "ingredientId=" + ingredientId +
                ", ingredientName='" + ingredientName + '\'' +
                ", mealIngredients=" + mealIngredients +
                '}';
    }

}
