package com.anjass.raihan.monica20.Authentication;

import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.anjass.raihan.monica20.Home.HomeScreen;
import com.anjass.raihan.monica20.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextView forgot_password_btn,
            login_btn;
    private EditText email_username_text,
        password_text;
    private ImageButton visible_password;


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


        // Android Context
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        email_username_text = (EditText) findViewById(R.id.email_username_text);
        password_text = (EditText) findViewById(R.id.password_text);

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
                        Intent i = new Intent(getApplicationContext(), HomeScreen.class);
                        startActivity(i);
                        finish();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                        Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplication(),"Please fill all fields.", Toast.LENGTH_SHORT).show();
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

    public void userLogin(){
        email_username = email_username_text.getText().toString().trim();
        password = password_text.getText().toString().trim();

        if (!email_username.isEmpty() && !password.isEmpty()){
            firebaseAuth.signInWithEmailAndPassword(email_username, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    //if the task is successfull
                    if(task.isSuccessful()){
                        //start the profile activity
                        Toast.makeText(getApplication(), "", Toast.LENGTH_SHORT).show();
                        finish();
                        startActivity(new Intent(getApplicationContext(), HomeScreen.class));
                    }
                }
            });
        }
    }
}
