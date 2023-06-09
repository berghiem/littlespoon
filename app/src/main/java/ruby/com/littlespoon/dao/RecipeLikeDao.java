package ruby.com.littlespoon.dao;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ruby.com.littlespoon.model.Recipe;
import ruby.com.littlespoon.model.RecipeLikes;
import ruby.com.littlespoon.model.User;

@Dao
public interface RecipeLikeDao {
    @Insert
    void insert(RecipeLikes RecipeLikes);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<RecipeLikes> RecipeLikes);

    @Query("SELECT * FROM RecipeLikes WHERE recipeId =:recipeId AND userId =:userId")
    RecipeLikes getRecipeLikes(final int recipeId, final int userId);

    @Query("SELECT * FROM User INNER JOIN recipelikes ON " +
            "user.id = recipelikes.userid WHERE " +
            "recipelikes.recipeId =:recipeId")
    List<User> getUserLikesFromRecipe(final int recipeId);

    @Query("SELECT * FROM Recipe INNER JOIN recipelikes ON " +
            "recipe.id = recipelikes.recipeId WHERE " +
            "recipelikes.userId =:uId")
    List<Recipe> getRecipeLikesFromUser(final int uId);


    @Query("DELETE FROM RecipeLikes")
    void deleteAll();


    @Query(value = "DELETE FROM RecipeLikes WHERE recipeId =:rid AND userid =:inId")
    void deleteById(int rid, int inId);

    @Query("SELECT * from RecipeLikes  ")
    LiveData<List<RecipeLikes>> getRecipeLikes();

}
