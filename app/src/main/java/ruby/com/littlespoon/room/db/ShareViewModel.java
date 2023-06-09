package ruby.com.littlespoon.room.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import ruby.com.littlespoon.model.Recipe;

public class ShareViewModel extends ViewModel {
    private final MutableLiveData<Recipe> selected = new MutableLiveData<Recipe>();

    public void select(Recipe item) {
        selected.setValue(item);
    }

    public LiveData<Recipe> getSelected() {
        return selected;
    }
}
