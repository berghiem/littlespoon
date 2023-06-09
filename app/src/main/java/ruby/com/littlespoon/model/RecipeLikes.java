package ruby.com.littlespoon.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

@Entity(primaryKeys = {"recipeId", "userId"},
        foreignKeys = {
                @ForeignKey(entity = Recipe.class,
                        parentColumns = "id",
                        childColumns = "recipeId"),
                @ForeignKey(entity = User.class,
                        parentColumns = "id",
                        childColumns = "userId")
        },
        indices = {@Index("recipeId"),@Index("userId")})
public class RecipeLikes {
    private int recipeId;

    private int userId;

    public int getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(int recipeId) {
        this.recipeId = recipeId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userid) {
        this.userId = userid;
    }
}
