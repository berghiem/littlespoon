package ruby.com.littlespoon.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ruby.com.littlespoon.Navigate;
import ruby.com.littlespoon.R;

public class MainActivity extends AppCompatActivity implements Navigate{

    private TextView mTextMessage;
    private HomeView homeFragment;
    private MyPageView myPageFragment;
    private AddRecipeView addRecipeFragment;
    private NotificationView notificationFragment;
    private int selectedAge;
    private int selectedFoodType;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    ft.replace(R.id.fragment_container, homeFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    return true;

                case R.id.navigation_my_page:
                    ft.replace(R.id.fragment_container, myPageFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    return true;

                case R.id.navigation_add_recipe:
                    ft.replace(R.id.fragment_container, addRecipeFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    return true;

                case R.id.navigation_notifications:
                    ft.replace(R.id.fragment_container, notificationFragment);
                    ft.addToBackStack(null);
                    ft.commit();
                    return true;
            }

            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        if (savedInstanceState != null) {
            // cleanup any existing fragments in case we are in detailed mode
            getFragmentManager().executePendingTransactions();
            android.support.v4.app.Fragment fragmentById = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
            if (fragmentById!=null) {
               ft.remove(fragmentById).commit();
            }
        }

        homeFragment = new HomeView();
        myPageFragment = new MyPageView();
        addRecipeFragment = new AddRecipeView();
        notificationFragment = new NotificationView();
        ft.replace(R.id.fragment_container, homeFragment);
        ft.addToBackStack(null);
        ft.commit();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }


    @Override
    public void onItemClick() {

    }



    @Override
    public void showRecipe(String keyword) {
        ResultView resultView = new ResultView();

        resultView.showResult(selectedAge, selectedFoodType,keyword);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, resultView);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void collect(boolean isAgeCat, int id) {
        if(isAgeCat){
            selectedAge = id;
        }else {
            selectedFoodType = id;
        }
    }

    @Override
    public void editorPickshowRecipe() {

    }

    @Override
    public void streamShowRecipe(int id) {
        RecipeView recipeFragment = new RecipeView();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, recipeFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void navigateToLoginPage() {

    }

    @Override
    public void navigateToRegisterPage() {

    }



    @Override
    public void navigateToHome(String token, int userId) {

    }
}
