package ruby.com.littlespoon.api.call.response;


import android.arch.persistence.room.Ignore;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.ArrayList;

import ruby.com.littlespoon.model.User;

public class  Recipe {

    private int id;

    private String name;
    @SerializedName("user_id")
    private int userid;

    @Ignore
    private User user;

    private String caption;

    @SerializedName("image_uri")
    private String imageUri;
    private Timestamp created_at;
    private Timestamp updated_at;

    @SerializedName("age_category_id")
    private int ageCategoryId;

    @SerializedName("process_type_category_id")
    private int processtypeId;

    @SerializedName("count_like")
    private int countlike;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }



    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public int getAgeCategoryId() {
        return ageCategoryId;
    }

    public void setAgeCategoryId(int ageCategoryId) {
        this.ageCategoryId = ageCategoryId;
    }

    public int getProcesstypeId() {
        return processtypeId;
    }

    public void setProcesstypeId(int processtypeId) {
        this.processtypeId = processtypeId;
    }

    public int getCountlike() {
        return countlike;
    }

    public void setCountlike(int countlike) {
        this.countlike = countlike;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
