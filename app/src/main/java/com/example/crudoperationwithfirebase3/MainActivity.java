package com.example.crudoperationwithfirebase3;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText LgEmail,LgPassword;
    private Button btnLogin;
    private TextView txtSignUp;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LgEmail= findViewById(R.id.lgEmail);
        LgPassword= findViewById(R.id.lgPassword);
        btnLogin= findViewById(R.id.btnLogin);
        txtSignUp= findViewById(R.id.signUp);
        auth= FirebaseAuth.getInstance();
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Lgemail= LgEmail.getText().toString();
                String Lgpassword= LgPassword.getText().toString();
                if(Lgemail.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter your email", Toast.LENGTH_SHORT).show();
                }else if(Lgpassword.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter your password", Toast.LENGTH_SHORT).show();
                }
                else {
                    LoginUser(Lgemail,Lgpassword);
                }
            }
        });
    }

    private void LoginUser(String lgemail, String lgpassword) {
        auth.signInWithEmailAndPassword(lgemail,lgpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    verifyEmail();
                }
                else {
                    Toast.makeText(MainActivity.this, "oop!!! An error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*if(auth.getCurrentUser()!=null){
            Intent intent= new Intent(getApplicationContext(),DisplayActivity.class);
            startActivity(intent);
        }*/
    }

    private void verifyEmail() {
        if (auth.getCurrentUser().isEmailVerified()){
            Toast.makeText(this, "Email has been verified", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this,DisplayActivity.class);
            startActivity(intent);
            finish();
        }

    }
}