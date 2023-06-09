package ruby.com.littlespoon.api.call.response;

import java.sql.Timestamp;

import ruby.com.littlespoon.model.Recipe;

public class EditorPick {
    private int id;
    private Recipe recipe;
    private String tag;
    private Timestamp choosedDate;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Timestamp getChoosedDate() {
        return choosedDate;
    }

    public void setChoosedDate(Timestamp choosedDate) {
        this.choosedDate = choosedDate;
    }
}
