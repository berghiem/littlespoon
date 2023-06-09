package ruby.com.littlespoon.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.sql.Timestamp;
import java.util.List;

@Entity(indices = {@Index("id")})
public class  Recipe {
    @PrimaryKey
    @NonNull
    private int id;

    private String name;
    private int userid;

    @Ignore
    private User user;

    @Ignore
    private List<User> userlikes;

    private boolean isLiked;

    private String caption;

    private String imageUri;
    private Timestamp created_at;
    private Timestamp updated_at;

    @SerializedName("age_category_id")
    private int ageCategoryId;

    @Ignore
    private AgeCategory ageCategory;

    @SerializedName("dish_category_id")
    private int dishCategoryId;

    @Ignore
    private DishCategory dishCategory;

    @SerializedName("process_type_category_id")
    private int processtypeId;

    @Ignore
    private ProcessTypeCategory processTypeCategory;

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


    public AgeCategory getAgeCategory() {
        return ageCategory;
    }

    public void setAgeCategory(AgeCategory ageCategory) {
        this.ageCategory = ageCategory;
    }

    public ProcessTypeCategory getProcessTypeCategory() {
        return processTypeCategory;
    }

    public void setProcessTypeCategory(ProcessTypeCategory processTypeCategory) {
        this.processTypeCategory = processTypeCategory;
    }

    public List<User> getUserlikes() {
        return userlikes;
    }

    public void setUserlikes(List<User> userlikes) {
        this.userlikes = userlikes;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        isLiked = liked;
    }

    public int getDishCategoryId() {
        return dishCategoryId;
    }

    public void setDishCategoryId(int dishCategoryId) {
        this.dishCategoryId = dishCategoryId;
    }

    public DishCategory getDishCategory() {
        return dishCategory;
    }

    public void setDishCategory(DishCategory dishCategory) {
        this.dishCategory = dishCategory;
    }
}
