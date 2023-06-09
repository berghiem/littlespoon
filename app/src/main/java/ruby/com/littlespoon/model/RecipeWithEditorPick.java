package ruby.com.littlespoon.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import java.util.List;

public class RecipeWithEditorPick {
    @Embedded public Recipe recipe;
    @Relation(parentColumn = "id",
              entityColumn = "recipeId")
    public List<EditorsPick> editorsPickList;
}
