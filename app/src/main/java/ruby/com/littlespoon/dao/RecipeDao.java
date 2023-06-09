package ruby.com.littlespoon.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ruby.com.littlespoon.model.AgeCategory;
import ruby.com.littlespoon.model.ProcessTypeCategory;
import ruby.com.littlespoon.model.Recipe;
import ruby.com.littlespoon.model.User;

@Dao
public interface RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipe(Recipe Recipe);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRecipes(List<Recipe> Recipes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUserLikes(List<User> users);


    @Query("DELETE FROM Recipe")
    void deleteAll();

    @Query(value = "DELETE FROM Recipe WHERE id =:id")
    void deleteById(int id);

    @Query("SELECT * from Recipe  order by created_at desc")
    LiveData<List<Recipe>> getLatestRecipe();


    @Query("SELECT * from Recipe  WHERE userid =:userId")
    LiveData<List<Recipe>> getRecipeFromUser(int userId);

    @Query("SELECT * from User  WHERE id =:userId")
    User getUser(int userId);



    @Query("SELECT * from AgeCategory  WHERE id =:ageId")
    AgeCategory getAgeCategory(int ageId);

    @Query("SELECT * from ProcessTypeCategory  WHERE id =:processId")
    ProcessTypeCategory getProcessTypeCategory(int processId);

    @Query("SELECT * from Recipe  WHERE ageCategoryId =:ageId")
    LiveData<List<Recipe>> getRecipeByAge(int ageId);

    @Query("SELECT * from Recipe  WHERE processtypeId =:proccesId")
    LiveData<List<Recipe>> getRecipeByFoodCategory(int proccesId);

    @Query("SELECT * from Recipe  WHERE name LIKE :keyword")
    LiveData<List<Recipe>> getRecipeByKeyword(String keyword);


    @Query("SELECT * from Recipe  WHERE name LIKE :keyword AND processtypeId =:proccesId AND ageCategoryId =:ageId ")
    LiveData<List<Recipe>> getRecipeByAll(String keyword , int proccesId, int ageId);

    @Query("SELECT * from Recipe  WHERE name LIKE :keyword AND processtypeId =:proccesId  ")
    LiveData<List<Recipe>> getRecipeByFoodAndKeyword(String keyword , int proccesId);

    @Query("SELECT * from Recipe  WHERE name LIKE :keyword AND ageCategoryId =:ageId  ")
    LiveData<List<Recipe>> getRecipeByAgeAndKeyword(String keyword ,  int ageId);


    @Query("SELECT * from Recipe  WHERE id =:id")
    LiveData<Recipe> getRecipeById(int id);

    @Query("SELECT * from Recipe  order by created_at desc")
    List<Recipe> getLatestRecipeunLive();

    @Query("SELECT * from Recipe  WHERE userid =:userId")
    List<Recipe> getRecipeFromUserunLive(int userId);


    @Query("UPDATE recipe SET name =:name WHERE id IN (:id)")
    void updateRecipe(String name, int id);
}
