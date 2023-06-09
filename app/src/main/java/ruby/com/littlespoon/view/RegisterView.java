package ruby.com.littlespoon.view;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ruby.com.littlespoon.Navigate;
import ruby.com.littlespoon.R;
import ruby.com.littlespoon.api.call.APIInterface;
import ruby.com.littlespoon.api.call.RetrofitClient;
import ruby.com.littlespoon.api.call.request.Register;
import ruby.com.littlespoon.api.call.response.Sign;
import ruby.com.littlespoon.model.User;
import ruby.com.littlespoon.view.util.EditTextValidator;

public class RegisterView extends Fragment implements View.OnClickListener {

    private Button register;
    private EditText email;
    private EditText username;
    private  EditText password;
    private EditText name;
    private  EditText uniqcode;
    private Context context;
    private Navigate navigate;

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        this.context = context;
         this.navigate = (Navigate) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register,
                container, false);

        email = (TextInputEditText)view.findViewById(R.id.email);
        username =(TextInputEditText) view.findViewById(R.id.username);
        password = (TextInputEditText)view.findViewById(R.id.password);
        name = (TextInputEditText)view.findViewById(R.id.name);
        uniqcode =(TextInputEditText)view.findViewById(R.id.uniqcode);
        register = (Button) view.findViewById(R.id.register);
        register.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.register :
                Log.i("register","clicked");
                Toast.makeText(getActivity(),"halo",Toast.LENGTH_SHORT);

                int errorCount = 0;
                   if(!EditTextValidator.isValidEmail(email.getText())){
                       email.setError("Please enter valid mail address");
                       errorCount ++;
                   }

                   if(!EditTextValidator.isValidPassword(password.getText())){
                       password.setError("Password must be at least 7 characters long");
                       errorCount ++;
                   }

                   if(!EditTextValidator.isValid(username.getText())){
                       username.setError("Please enter username");
                       errorCount ++;
                   }

                   if(errorCount > 0){
                       break;
                   }


                    APIInterface service = RetrofitClient.getClient().create(APIInterface.class);
                    final Register register = new Register(email.getText().toString(), username.getText().toString(),
                                        password.getText().toString(), name.getText().toString());
                    Call<Sign> call = service.register(register);
                    call.enqueue(new Callback<Sign>() {
                        @Override
                        public void onResponse(Call<Sign> call, Response<Sign> response) {
                            if(response.isSuccessful() && response.body().isIssuccess()){
                                navigate.navigateToLoginPage();

                            }else{
                                Log.i("register_failed",response.message());
                            }
                        }

                        @Override
                        public void onFailure(Call<Sign> call, Throwable t) {
                           Log.i("server_register_failed","fail response call "+t.getLocalizedMessage());
                        }
                    });

                break;

        }
    }


}
