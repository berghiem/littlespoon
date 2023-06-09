package ruby.com.littlespoon.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ruby.com.littlespoon.model.Recipe;
import ruby.com.littlespoon.model.RecipeIngredients;

@Dao
public interface RecipeIngredientsDao {
    @Insert
    void insert(RecipeIngredients recipeIngredients);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<RecipeIngredients> recipeIngredients);

    @Query("DELETE FROM RecipeIngredients")
    void deleteAll();


    @Query(value = "DELETE FROM RecipeIngredients WHERE recipeId =:rid AND ingredientId =:inId")
    void deleteById(int rid, int inId);

    @Query("SELECT * from RecipeIngredients  ")
    LiveData<List<RecipeIngredients>> getRecipeIngredients();

    @Query("SELECT * from RecipeIngredients WHERE recipeId =:rId ")
    List<RecipeIngredients> getRecipeIngredientsbyRecipe(int rId);


    @Query("UPDATE recipeingredients SET ingredient =:ingred WHERE ingredientId  =:inId AND recipeId =:rId")
    void updateIngredient(String ingred, int inId, int rId);
}
