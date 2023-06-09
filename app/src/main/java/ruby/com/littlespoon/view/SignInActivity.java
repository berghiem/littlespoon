package ruby.com.littlespoon.view;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import ruby.com.littlespoon.Navigate;
import ruby.com.littlespoon.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener, Navigate {

    private RegisterView registerFragment;
    private HomeView homeFragment;
    private LoginView loginFragment;
    private Button signButton;
    private TextView signText;
    private boolean isRegister;
    private String token;
    private Context context;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle extras = intent.getExtras();
            if(extras != null){
                String action = extras.getString("ACTION");
                if(action.equalsIgnoreCase("REG")){
                    navigateToRegisterPage();
                }else if(action.equalsIgnoreCase("LOGIN")) {
                    navigateToLoginPage();
                }else if(action.equalsIgnoreCase("HOME")){
                    String token= extras.getString("TOKEN");
                  //  String id
                  //  navigateToHome();
                }
            }
        }
    };

     @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
         supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign);
        this.context = this;
        signButton = findViewById(R.id.signbutton);
        signButton.setOnClickListener(this);
        signText = findViewById(R.id.signtext);
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if (savedInstanceState != null) {
            // cleanup any existing fragments in case we are in detailed mode
            getFragmentManager().executePendingTransactions();
            Fragment fragmentById = getFragmentManager().findFragmentById(R.id.fragment_container);
            if (fragmentById!=null) {
                ft.remove(fragmentById).commit();
            }
        }

        registerFragment = new RegisterView();
        loginFragment = new LoginView();
        homeFragment = new HomeView();
        ft.replace(R.id.fragment_container, registerFragment);
        ft.addToBackStack(null);
        ft.commit();
        isRegister = true;


    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        String token = sharedPref.getString(getString(R.string.token), "");
        Log.e("token",token);
        if(token.length() > 0){

                            Intent intent  = new Intent(context, MainActivity.class);
                            startActivity(intent);

         }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.signbutton:
                if(isRegister){
                   navigateToLoginPage();
                    isRegister = false;
                }else {
                    navigateToRegisterPage();
                    isRegister = true;
                }

                break;

        }
    }


    @Override
    public void onItemClick() {

    }

    @Override
    public void showRecipe(String keyword) {

    }

    @Override
    public void collect(boolean isAgeCat, int id) {

    }

    @Override
    public void editorPickshowRecipe() {

    }

    @Override
    public void streamShowRecipe(int id) {

    }




    public void navigateToHome(String token, int userId) {
        this.token = token;

        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(getString(R.string.token), token);
        editor.putInt(getString(R.string.userid), userId);

        editor.commit();

        signButton.setVisibility(View.GONE);
        signText.setVisibility(View.GONE);

        Intent intent  = new Intent(this, MainActivity.class);
        startActivity(intent);

        /*
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, homeFragment);
        ft.addToBackStack(null);
        ft.commit();
        */

    }

    public void navigateToRegisterPage(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, registerFragment);
        ft.addToBackStack(null);
        ft.commit();
        signText.setText("Already have account?");
        signButton.setText("Login");
    }

    @Override
    public void navigateToLoginPage(){
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, loginFragment);
        ft.addToBackStack(null);
        ft.commit();
        signText.setText("Don't have account?");
        signButton.setText("Register");

    }


    @Override
    public void onBackPressed() {
        if(isRegister){

        }else {
            navigateToRegisterPage();
            isRegister = true;
        }
    }
}
