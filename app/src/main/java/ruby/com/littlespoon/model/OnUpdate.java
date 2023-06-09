package ruby.com.littlespoon.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class OnUpdate {
    @PrimaryKey
    @NonNull
    private int id;

    private boolean isAge;
    private boolean isAnnounce;
    private boolean isEditorPick;
    private boolean isProcess;
    private boolean isRecipe;
    private boolean isUser;
    private  boolean isUserWithRecipe;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public boolean isAge() {
        return isAge;
    }

    public void setAge(boolean age) {
        isAge = age;
    }

    public boolean isAnnounce() {
        return isAnnounce;
    }

    public void setAnnounce(boolean announce) {
        isAnnounce = announce;
    }

    public boolean isEditorPick() {
        return isEditorPick;
    }

    public void setEditorPick(boolean editorPick) {
        isEditorPick = editorPick;
    }

    public boolean isProcess() {
        return isProcess;
    }

    public void setProcess(boolean process) {
        isProcess = process;
    }

    public boolean isRecipe() {
        return isRecipe;
    }

    public void setRecipe(boolean recipe) {
        isRecipe = recipe;
    }

    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    public boolean isUserWithRecipe() {
        return isUserWithRecipe;
    }

    public void setUserWithRecipe(boolean userWithRecipe) {
        isUserWithRecipe = userWithRecipe;
    }
}
