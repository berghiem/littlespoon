package ruby.com.littlespoon.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ruby.com.littlespoon.model.EditorsPick;
import ruby.com.littlespoon.model.Recipe;
import ruby.com.littlespoon.model.User;

@Dao
public interface EditorPickDao {
    @Insert
    void insert(EditorsPick e);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<EditorsPick> eList);

    @Query("DELETE FROM EditorsPick")
    void deleteAll();


    @Query(value = "DELETE FROM EditorsPick WHERE id =:id")
    void deleteById(int id);

    @Query("SELECT * from EditorsPick  ")
    LiveData<List<EditorsPick>> getEditorPick();

    @Query("SELECT * from EditorsPick WHERE recipeId =:rId ")
    LiveData<List<EditorsPick>> getEditorPickForRecipe(int rId);

    @Query("SELECT * from Recipe WHERE id =:rId ")
    List<Recipe> getRecipe (int rId);


    @Query("UPDATE EditorsPick SET recipeId =:rId, tag =:tag WHERE id IN (:id)")
    void updateEditorPick(int rId ,String tag, int id);
}
