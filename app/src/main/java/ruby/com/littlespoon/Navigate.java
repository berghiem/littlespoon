package ruby.com.littlespoon;

import android.view.View;

public interface Navigate {
    public void onItemClick();
    public void showRecipe(String keyword);
    public void collect(boolean isAgeCat, int id);
    public void editorPickshowRecipe();
    public void streamShowRecipe(int id);
    public void navigateToLoginPage();
    public void navigateToRegisterPage();
    public void navigateToHome(String token, int userId);

}
