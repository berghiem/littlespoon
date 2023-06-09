package ruby.com.littlespoon.room.db;

import java.util.List;

import ruby.com.littlespoon.model.Recipe;

public interface TaskCompleted {
    public List<Recipe> onComplete(List<Recipe> recipes);
}
