package com.anjass.raihan.monica20.Authentication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anjass.raihan.monica20.Home.HomeScreen;
import com.anjass.raihan.monica20.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    private EditText name_text,
        username_text,
        email_text,
        password_text;
    private TextView register_btn;
    private ProgressBar loading_bar;

    FirebaseAuth mAuth;

    private String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Firebase
        mAuth = FirebaseAuth.getInstance();
        if(mAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), HomeScreen.class));
        }

        // Context code
        name_text = (EditText) findViewById(R.id.name_text);
        username_text = (EditText) findViewById(R.id.username_text);
        email_text = (EditText) findViewById(R.id.email_text);
        password_text = (EditText) findViewById(R.id.password_text);
        loading_bar = (ProgressBar) findViewById(R.id.loading_bar);

        register_btn = (TextView) findViewById(R.id.register_btn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = email_text.getText().toString().trim();
                password = password_text.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()){
                    createAccount();
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void createAccount (){
        // Start loading
        loading_bar.setVisibility(View.VISIBLE);
        register_btn.setClickable(false);

        // Fetching data
        email = email_text.getText().toString().trim();
        password = password_text.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(getApplicationContext(), "Sign up succes!",
                                    Toast.LENGTH_SHORT).show();
                            FirebaseUser user = mAuth.getCurrentUser();

                            // Move to Homescreen
                            try {
                                Intent i = new Intent(getApplicationContext(), HomeScreen.class);
                                startActivity(i);
                                finish();
                            }
                            catch (Exception e){
                                e.printStackTrace();
                                Toast.makeText(getApplication(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(getApplicationContext(), task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();

                            loading_bar.setVisibility(View.GONE);
                            register_btn.setClickable(true);
                        }


                    }
                });


    }
}
