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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextfullname,editTextemail,editTextphone,editTextaddress,editTextpassword,editTextcfmpassword;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Objects.requireNonNull(getSupportActionBar()).hide();
        editTextfullname=findViewById(R.id.full_name_input);
        editTextemail=findViewById(R.id.email_input);
        editTextaddress=findViewById(R.id.address_input);
        editTextphone=findViewById(R.id.phone_input);
        editTextpassword=findViewById(R.id.password_input);
        editTextcfmpassword=findViewById(R.id.confirm_password_input);
        progressBar=findViewById(R.id.progress_bar);


        Button buttonRegister=findViewById(R.id.Register_button);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textFullName= editTextfullname.getText().toString();
                String textEmail=editTextemail.getText().toString();
                String textPhone=editTextphone.getText().toString();
                String textAddress= editTextaddress.getText().toString();
                String textPassword=editTextpassword.getText().toString();
                String textCfmPassword=editTextcfmpassword.getText().toString();

                if(TextUtils.isEmpty(textFullName))
                {
                    Toast.makeText(RegisterActivity.this,"Please enter your name",Toast.LENGTH_LONG).show();
                    editTextfullname.setError("Full Name is required");
                    editTextfullname.requestFocus();
                }
                else if(TextUtils.isEmpty(textEmail))
                {
                    Toast.makeText(RegisterActivity.this,"Please enter your email",Toast.LENGTH_LONG).show();
                    editTextemail.setError("Email is required");
                    editTextemail.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches())
                {
                    Toast.makeText(RegisterActivity.this,"Please re-enter your email",Toast.LENGTH_LONG).show();
                    editTextemail.setError("Valid Email is required");
                    editTextemail.requestFocus();
                }
                else if (TextUtils.isEmpty(textPhone))
                {
                    Toast.makeText(RegisterActivity.this,"Please re-enter your phone number",Toast.LENGTH_LONG).show();
                    editTextphone.setError("Phone no is required");
                    editTextphone.requestFocus();
                }
                else if(textPhone.length()!=10)
                {
                    Toast.makeText(RegisterActivity.this,"Please re-enter your phone number",Toast.LENGTH_LONG).show();
                    editTextphone.setError("Valid phone no is required");
                    editTextphone.requestFocus();
                }
                else if(!textCfmPassword.equals(textPassword))
                {
                    Toast.makeText(RegisterActivity.this,"Please enter same password",Toast.LENGTH_LONG).show();
                    editTextcfmpassword.setError("Password Confirmation is required");
                    editTextpassword.requestFocus();
                    editTextpassword.clearComposingText();
                    editTextcfmpassword.clearComposingText();
                }
                else
                {
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textFullName,textEmail,textPhone,textAddress,textPassword);
                }
            }
        });
    }

    private void registerUser(String textFullName, String textEmail, String textPhone, String textAddress, String textPassword) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(textEmail,textPassword).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"User Registration Successfully",Toast.LENGTH_LONG).show();
                    FirebaseUser firebaseUser= mAuth.getCurrentUser();
                    ReadWriteUserDetails writeUserDetails=new ReadWriteUserDetails(textFullName,textPhone,textAddress);
                    FirebaseFirestore db = FirebaseFirestore.getInstance();

                    // Create a new user with a first and last name
                    Map<String, Object> user = new HashMap<>();
                    user.put("Full Name", writeUserDetails.fullname);
                    user.put("Phone Number", writeUserDetails.phone);
                    user.put("Address", writeUserDetails.address);

// Add a new document with a generated ID
                    db.collection("Users")
                            .add(user)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    assert firebaseUser != null;
                                    firebaseUser.sendEmailVerification();
                                    Toast.makeText(RegisterActivity.this, "User Successfully Registered. \nPlease verify your email", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(RegisterActivity.this, AppActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                    finish();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(RegisterActivity.this, "User Registration Failed.", Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            });
                }
                else
                {
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        editTextpassword.setError("Your Password is too weak.Kindly use another");
                        editTextpassword.requestFocus();
                    }
                    catch (FirebaseAuthInvalidCredentialsException e){
                        editTextpassword.setError("Your email is invalid or already in use.Kindly re-enter");
                        editTextpassword.requestFocus();
                    }
                    catch (FirebaseAuthUserCollisionException e){
                        editTextemail.setError("A user is already registered with this email. Use another email");
                        editTextemail.requestFocus();
                    }
                    catch (Exception e){
                        Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}