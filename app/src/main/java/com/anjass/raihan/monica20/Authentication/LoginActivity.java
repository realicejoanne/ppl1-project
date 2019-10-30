package com.anjass.raihan.monica20.Authentication;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anjass.raihan.monica20.Class.LoginResponse;
import com.anjass.raihan.monica20.Class.UserClass;
import com.anjass.raihan.monica20.Data.ApiInterface;
import com.anjass.raihan.monica20.Data.Network.AuthenticationAPI;
import com.anjass.raihan.monica20.Data.Repositories.AuthRepos;
import com.anjass.raihan.monica20.Home.HomeScreen;
import com.anjass.raihan.monica20.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    TextView titleToolbar;
    ImageView backToolbar;

    private TextView forgot_password_btn,
            login_btn;
    private EditText email_username_text,
        password_text;
    private ImageButton visible_password;
    private ProgressBar loading_bar;


    FirebaseAuth firebaseAuth;


    private String email_username,
        password;
    private boolean dotPasswordMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        // Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), HomeScreen.class));
        }

        // Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        titleToolbar = (TextView) findViewById(R.id.titleToolbar);
        titleToolbar.setText("Login");
        backToolbar = (ImageView) findViewById(R.id.backToolbar);
        backToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Android Context
        email_username_text = (EditText) findViewById(R.id.email_username_text);
        password_text = (EditText) findViewById(R.id.password_text);
        loading_bar = (ProgressBar) findViewById(R.id.loading_bar);

        visible_password = (ImageButton) findViewById(R.id.visible_password);
        visible_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dotPasswordMode){
                    password_text.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    dotPasswordMode = false;
                }
                else{
                    password_text.setInputType(InputType.TYPE_CLASS_TEXT);
                    dotPasswordMode = true;
                }
            }
        });

        login_btn = (TextView) findViewById(R.id.login_btn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email_username = email_username_text.getText().toString().trim();
                password = password_text.getText().toString().trim();

                if (!email_username.isEmpty() && !password.isEmpty()){
                    try {
                        //userLogin();
                        userLoginMonicaAPI();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplication(),"Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

        forgot_password_btn = (TextView) findViewById(R.id.forgot_password_btn);
        forgot_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent i = new Intent(getApplicationContext(), HomeScreen.class);
                    startActivity(i);
                    finish();
                }
                catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    // Login with Firebase
    public void userLogin(){
        // Fetching text
        email_username = email_username_text.getText().toString().trim();
        password = password_text.getText().toString().trim();

        // Logic
        if (!email_username.isEmpty() && !password.isEmpty()){
            // Start loading
            loading_bar.setVisibility(View.VISIBLE);
            login_btn.setClickable(false);

            firebaseAuth.signInWithEmailAndPassword(email_username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //if the task is successfull
                    if(task.isSuccessful()){
                        //start the profile activity
                        Toast.makeText(getApplication(), "Sign in succes!", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                    }
                    else{
                        // If sign in fails, display a message to the user.
                        Toast.makeText(getApplicationContext(), task.getException().toString(),
                                Toast.LENGTH_SHORT).show();

                        loading_bar.setVisibility(View.GONE);
                        login_btn.setClickable(true);
                    }
                }
            });
        }
    }

    // Login with Monica-api API anjay
    public void userLoginMonicaAPI(){
        // Fetching text
        email_username = email_username_text.getText().toString().trim();
        password = password_text.getText().toString().trim();
        Log.d("username",email_username);
        Log.d("password",password);
        // Logic
        UserClass userClass = new UserClass(email_username, password);

        if (!email_username.isEmpty() && !password.isEmpty()){

            // Start loading
            loading_bar.setVisibility(View.VISIBLE);
            login_btn.setClickable(false);

            Retrofit.Builder builder = new Retrofit.Builder()
                    .baseUrl("http://192.168.173.1/monica-api/public/api/")
                    .addConverterFactory(GsonConverterFactory.create());
            Retrofit retrofit = builder.build();

            ApiInterface api = retrofit.create(ApiInterface.class);

            Call<LoginResponse> call = api.userLogin(email_username, password);
            Toast.makeText(getApplicationContext(), call.toString(),
                    Toast.LENGTH_LONG).show();
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {
                    Log.d("onResponse", "Entering onResponse...");

                    try{
                        assert response.body() != null;
                        Toast.makeText(getApplicationContext(), "ass" +response.body().getEmail(),
                                Toast.LENGTH_LONG).show();
                    }
                    catch (Exception e){
                        Log.d("Dead catch", e.getMessage());
                        Toast.makeText(getApplicationContext(), e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.d("onFailure", t.getMessage());
                    Toast.makeText(getApplicationContext(),t.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
