package ruby.com.littlespoon.api.call.response;

public class OnUpdate {
    private boolean isAge;
    private boolean isAnnounce;
    private boolean isEditorPick;
    private boolean isProcess;
    private boolean isRecipe;
    private boolean isUser;
    private  boolean isUserWithRecipe;

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
