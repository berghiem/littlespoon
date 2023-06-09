package ruby.com.littlespoon.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ruby.com.littlespoon.model.RecipeStep;

@Dao
public interface RecipeStepDao {
    @Insert
    void insert(RecipeStep RecipeStep);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<RecipeStep> RecipeStep);

    @Query("DELETE FROM RecipeStep")
    void deleteAll();


    @Query(value = "DELETE FROM RecipeStep WHERE recipeId =:rid AND stepId =:stepId")
    void deleteById(int rid, int stepId);

    @Query("SELECT * from RecipeStep  ")
    LiveData<List<RecipeStep>> getRecipeStep();

    @Query("UPDATE RecipeStep SET process =:process WHERE stepId  =:stepId AND recipeId =:rId")
    void updateStep(String process, int stepId, int rId);
}
