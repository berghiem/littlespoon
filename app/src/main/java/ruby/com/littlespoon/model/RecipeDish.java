package ruby.com.littlespoon.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

@Entity(primaryKeys = {"recipeId", "dishId"},
        foreignKeys = {
                @ForeignKey(entity = Recipe.class,
                        parentColumns = "id",
                        childColumns = "recipeId"),
                @ForeignKey(entity = DishCategory.class,
                        parentColumns = "id",
                        childColumns = "dishId")
        }, indices = {@Index("recipeId"), @Index("dishId")})
public class RecipeDish {
    private int recipeId;
    private int dishId;

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }
}
