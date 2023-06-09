package ruby.com.littlespoon.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ruby.com.littlespoon.model.RecipeComments;
import ruby.com.littlespoon.model.User;

@Dao
public interface RecipeCommentDao {
    @Insert
    void insert(RecipeComments recipeComments);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<RecipeComments> recipeCommentsList);

    @Query("DELETE FROM RecipeComments")
    void deleteAll();


    @Query(value = "DELETE FROM RecipeComments WHERE recipeId =:rid AND commentId =:commentId")
    void deleteById(int rid, int commentId);

    @Query("SELECT * from RecipeComments  ")
    LiveData<List<RecipeComments>> getRecipeComments();

    @Query("SELECT * FROM User INNER JOIN recipecomments ON " +
            "user.id = recipecomments.userid WHERE " +
            "recipecomments.commentId =:commentId")
    User getUserFromComment(final int commentId);

    @Query("SELECT * from RecipeComments where commentId=:id  ")
    RecipeComments getRecipeCommentById(int id);

    @Query("UPDATE RecipeComments SET message =:message WHERE commentId =:id")
    void udpdateComment(String message, int id);
}
