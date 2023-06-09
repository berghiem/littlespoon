package ruby.com.littlespoon.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ruby.com.littlespoon.model.OnUpdate;

@Dao
public interface OnUpdateDao {
    @Insert
    void insert(OnUpdate OnUpdate);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<OnUpdate> onUpdates);

    @Query("DELETE FROM OnUpdate")
    void deleteAll();


    @Query(value = "DELETE FROM OnUpdate WHERE id =:id")
    void deleteById(int id);

    @Query("SELECT * from OnUpdate  ")
    LiveData<List<OnUpdate>> getOnUpdate();

    @Query("UPDATE OnUpdate SET isAge =:age, " +
            "isAnnounce =:announce , " +
            "isEditorPick =:editorPick ," +
            " isProcess =:process, " +
            "isRecipe =:recipe , " +
            "isUser =:user, " +
            "isUserWithRecipe=:userWithRecipe " +
            "WHERE id IN (:id)")
    void updateOnUpdate(int id, boolean age,  boolean announce , boolean editorPick, boolean process, boolean recipe, boolean user, boolean userWithRecipe);

}
