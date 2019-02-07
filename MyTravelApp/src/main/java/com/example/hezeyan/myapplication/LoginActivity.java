package com.example.hezeyan.myapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {


    private EditText email, password;
    private ImageView login;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        login = (ImageView) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startSignin();
            }
        });
    }

    private void startSignin(){
        String emailuser = email.getText().toString();
        String passworduser = password.getText().toString();
        if(TextUtils.isEmpty(emailuser) || TextUtils.isEmpty(passworduser)){
            Toast.makeText(LoginActivity.this, "Fields are empty", Toast.LENGTH_LONG).show();
        }
        auth.signInWithEmailAndPassword(emailuser, passworduser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(LoginActivity.this, "Signin problem", Toast.LENGTH_LONG).show();
                }else {
                    Intent toMenu = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(toMenu);
                    finish();
                }
            }
        });
    }
}
