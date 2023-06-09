package ruby.com.littlespoon.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ruby.com.littlespoon.model.AgeCategory;

@Dao
public interface AgeCategoryDao {
    @Insert
    void insert(AgeCategory ageCategory);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<AgeCategory> ageCategorys);

    @Query("DELETE FROM AgeCategory")
    void deleteAll();


    @Query(value = "DELETE FROM AgeCategory WHERE id =:id")
    void deleteById(int id);

    @Query("SELECT * from AgeCategory  ")
    LiveData<List<AgeCategory>> getAgeCategory();

    @Query("UPDATE AgeCategory SET name =:name WHERE id IN (:id)")
    void updateAgeCategory(String name,  int id);
}
