package com.example.efco;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.efco.ui.AppActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextemail,editTextpwd;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).hide();

        editTextemail=findViewById(R.id.email_Login_input);
        editTextpwd=findViewById(R.id.password_Login_input);
        progressBar=findViewById(R.id.Login_progress_bar);

        mAuth=FirebaseAuth.getInstance();

        Button buttonLogin =findViewById(R.id.Login_button);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textEmail=editTextemail.getText().toString();
                String textPwd=editTextpwd.getText().toString();
                if(TextUtils.isEmpty(textEmail))
                {
                    Toast.makeText(LoginActivity.this,"Please enter your email",Toast.LENGTH_LONG).show();
                    editTextemail.setError("Email is required");
                    editTextemail.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches())
                {
                    Toast.makeText(LoginActivity.this,"Please re-enter your email",Toast.LENGTH_LONG).show();
                    editTextemail.setError("Valid Email is required");
                    editTextemail.requestFocus();
                }
                else if(TextUtils.isEmpty(textPwd))
                {
                    Toast.makeText(LoginActivity.this,"Please enter your password",Toast.LENGTH_LONG).show();
                    editTextpwd.setError("Password is required");
                    editTextpwd.requestFocus();
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    loginUser(textEmail,textPwd);
                }
            }
        });
    }

    private void loginUser(String Email, String Pwd) {
        mAuth.signInWithEmailAndPassword(Email,Pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser firebaseUser=mAuth.getCurrentUser();

                    assert firebaseUser != null;
                    if(firebaseUser.isEmailVerified())
                    {
                        Toast.makeText(LoginActivity.this, "You are logged in now", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(LoginActivity.this, AppActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        firebaseUser.sendEmailVerification();
                        Toast.makeText(LoginActivity.this, "Email not verified", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                    }
                }
                else
                {
                    Toast.makeText(LoginActivity.this, "Invalid Email or Password.", Toast.LENGTH_SHORT).show();
                    editTextpwd.requestFocus();
                    editTextpwd.clearComposingText();
                }
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}