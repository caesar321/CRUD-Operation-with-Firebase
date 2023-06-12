package com.example.crudoperationwithfirebase3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText REmail,RPassword,ConfirmPassword;
    private Button btnRegister;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        REmail= findViewById(R.id.REmail);
        RPassword= findViewById(R.id.Rpassword);
        btnRegister= findViewById(R.id.btnRegister);
        auth= FirebaseAuth.getInstance();
        ConfirmPassword= findViewById(R.id.conPassword);
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= REmail.getText().toString();
                String Password= RPassword.getText().toString();
                String Confirm= ConfirmPassword.getText().toString();
                if(email.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Enter Your gmail", Toast.LENGTH_SHORT).show();
                }else if (Password.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Enter Your Password", Toast.LENGTH_SHORT).show();
                }else if(Password.length()<6){
                    Toast.makeText(RegisterActivity.this, "Password is too short", Toast.LENGTH_SHORT).show();
                }else if(Confirm.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "please confirm your password", Toast.LENGTH_SHORT).show();
                }else if (!Password.equals(Confirm)){
                    Toast.makeText(RegisterActivity.this, "password does not match", Toast.LENGTH_SHORT).show();
                }else
                {
                    ValidateUser(email,Password);
                }
            }
        });


    }

    private void ValidateUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    VerificationOfEmail();
                    Intent intent= new Intent(RegisterActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void VerificationOfEmail() {
        if (auth.getCurrentUser()!=null){
            auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "A verification has been sent to your mail", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(RegisterActivity.this, "oop!!!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else{
            Toast.makeText(this, "Registration failed", Toast.LENGTH_SHORT).show();
        }

    }
}