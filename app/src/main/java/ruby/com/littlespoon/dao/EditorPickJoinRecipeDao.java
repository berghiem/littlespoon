package ruby.com.littlespoon.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;


import ruby.com.littlespoon.api.call.response.EditorPick;
import ruby.com.littlespoon.model.RecipeWithEditorPick;

@Dao
public interface EditorPickJoinRecipeDao {

    @Query("SELECT * FROM recipe")
    public List<RecipeWithEditorPick> getRecipeWithEditorPick();

    @Query("SELECT * FROM recipe WHERE id =:id")
    public List<RecipeWithEditorPick> getRecipeWithEditorPickById(int id);


}
