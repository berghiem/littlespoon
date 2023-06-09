package ruby.com.littlespoon.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ruby.com.littlespoon.model.ProcessTypeCategory;

@Dao
public interface ProcessTypeCategoryDao {
    @Insert
    void insert(ProcessTypeCategory p);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<ProcessTypeCategory> pList);

    @Query("DELETE FROM ProcessTypeCategory")
    void deleteAll();


    @Query(value = "DELETE FROM ProcessTypeCategory WHERE id =:id")
    void deleteById(int id);

    @Query("SELECT * from ProcessTypeCategory  ")
    LiveData<List<ProcessTypeCategory>> getProcessTypeCategory();

    @Query("UPDATE ProcessTypeCategory SET name =:name WHERE id IN (:id)")
    void updateUser(String name,  int id);
}
