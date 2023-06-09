package ruby.com.littlespoon.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ruby.com.littlespoon.model.DishCategory;

@Dao
public interface DishCategoryDao {
    @Insert
    void insert(DishCategory dishCategory);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<DishCategory> dishCategories);

    @Query("DELETE FROM DishCategory")
    void deleteAll();


    @Query(value = "DELETE FROM DishCategory WHERE id =:id")
    void deleteById(int id);

    @Query("SELECT * from DishCategory  ")
    LiveData<List<DishCategory>> getDishCategory();

    @Query("SELECT * from DishCategory WHERE id =:id  ")
    DishCategory getDishCategoryById(int id);

    @Query("UPDATE DishCategory SET name =:name WHERE id IN (:id)")
    void updateDishCategory(String name,  int id);
}
