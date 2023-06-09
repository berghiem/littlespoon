package ruby.com.littlespoon.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Entity
public class User {
    @PrimaryKey
    @NonNull
    private int id;
    private String name;
    private String email;
    private String password;
    private String username;

    @SerializedName("created_at")
    private Timestamp joinedDate;

    @SerializedName("image_uri")
    private String imageUri;

    @SerializedName("remember_token")
    private String rememberToken;
    @Ignore
    private List<Recipe> myRecipes;
    @Ignore
    private List<Recipe> myInterest;


    public User(String email, String password, String username) {
        this.email = email;
        this.password = password;
        this.username = username;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(Timestamp joinedDate) {
        this.joinedDate = joinedDate;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(String rememberToken) {
        this.rememberToken = rememberToken;
    }

    public List<Recipe> getMyRecipes() {
        return myRecipes;
    }

    public void setMyRecipes(List<Recipe> myRecipes) {
        this.myRecipes = myRecipes;
    }

    public List<Recipe> getMyInterest() {
        return myInterest;
    }

    public void setMyInterest(List<Recipe> myInterest) {
        this.myInterest = myInterest;
    }
}
