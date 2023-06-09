package ruby.com.littlespoon.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import ruby.com.littlespoon.model.Recipe;
import ruby.com.littlespoon.model.User;

@Dao
public interface UserDao {



    @Insert
    void insert(User User);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<User> Users);

    @Query("DELETE FROM User")
    void deleteAll();


    @Query(value = "DELETE FROM User WHERE id =:id")
    void deleteById(int id);

    @Query("SELECT * from User  ")
    LiveData<List<User>> getAllUser();

    @Query("UPDATE User SET name =:name, username =:username, imageUri=:imageuri, email =:email, password=:password WHERE id IN (:id)")
    void updateUser(String name, String username, String imageuri, String email, String password,  int id);



    @Query("SELECT * from Recipe  WHERE userid =:userId")
    List<Recipe> getRecipeFromUser(int userId);

    @Query("SELECT * from User  WHERE id =:userId")
    LiveData<User> getUser(int userId);
}
