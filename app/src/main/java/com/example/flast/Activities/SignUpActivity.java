package com.example.flast.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.flast.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText repeatPasswordEditText;
    private Button submitButton;
    private EditText usernameEditText;
    private EditText fullNameEditText;

    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        emailEditText = findViewById(R.id.email_edit_text_sign_up);
        passwordEditText = findViewById(R.id.password_edit_text_sign_up);
        repeatPasswordEditText = findViewById(R.id.repeat_password_edit_text_sign_up);
        submitButton = findViewById(R.id.submit_button_sign_up);
        usernameEditText = findViewById(R.id.username_edit_text);
        fullNameEditText = findViewById(R.id.full_name_edit_text);

        mRootRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String fullName = fullNameEditText.getText().toString();
                String emailText = emailEditText.getText().toString();
                String passwordText = passwordEditText.getText().toString();
                String repeatPasswordText = repeatPasswordEditText.getText().toString();

                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(fullName) || TextUtils.isEmpty(emailText) || TextUtils.isEmpty(passwordText) || TextUtils.isEmpty(repeatPasswordText)){
                    Toast.makeText(SignUpActivity.this, "Fill all fields!", Toast.LENGTH_SHORT).show();
                }else if (passwordText.length() < 6){
                    Toast.makeText(SignUpActivity.this, "Password is too short!", Toast.LENGTH_SHORT).show();
                }else if (!passwordText.equals(repeatPasswordText)) {
                    Toast.makeText(SignUpActivity.this, "Invalid repeated password!", Toast.LENGTH_SHORT).show();
                }else {
                    registerUser(username, fullName, emailText, passwordText);
                }

            }
        });
    }

    private void registerUser(String username, String fullName, String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("username", username);
                map.put("fullName", fullName);
                map.put("email", email);
                /*map.put("status", "");*/
                map.put("aboutYourself", "");
                map.put("id", mAuth.getCurrentUser().getUid());
                map.put("imageUrl", "default");
                Toast.makeText(SignUpActivity.this, "Here1!", Toast.LENGTH_SHORT).show();
                mRootRef.child("Users").child(mAuth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(SignUpActivity.this, "inside!", Toast.LENGTH_SHORT).show();
                        if (task.isSuccessful()){
//                    progressDialog.dismiss();

                            Toast.makeText(SignUpActivity.this, "Update the profile for better experience.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, AddUserDataSignUpActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                            finish();
                        }else{
                            Toast.makeText(SignUpActivity.this, "Fail!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                progressDialog.dismiss();
                        Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                /*startActivity(new Intent(SignUpActivity.this, AddUserDataSignUpActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();*/
            }
        });
    }
}