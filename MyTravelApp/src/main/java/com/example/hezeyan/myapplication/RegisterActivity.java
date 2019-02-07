package com.example.hezeyan.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText name;
    private EditText surname;
    private EditText email;
    private EditText phone;
    private ImageView signup;
    private EditText password;
    ProgressDialog progressDialog;
    FirebaseAuth auth;
    DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");

        auth = FirebaseAuth.getInstance();
        signup = (ImageView) findViewById(R.id.signup);
        name = (EditText) findViewById(R.id.name);
        surname = (EditText) findViewById(R.id.surname);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
        progressDialog = new ProgressDialog(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        final String nameuser = name.getText().toString();
        final String surnameuser = surname.getText().toString();
        final String phoneuser = phone.getText().toString();
        final String emailuser = email.getText().toString();
        final String passworduser = password.getText().toString();

        if(emailuser.equals("") || passworduser.equals("")){
            Toast.makeText(getApplicationContext(), "Email or Password is blank", Toast.LENGTH_LONG).show();
        }else {
            progressDialog.setMessage("Registration...");
            progressDialog.show();
            auth.createUserWithEmailAndPassword(emailuser, passworduser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        String id = databaseUsers.push().getKey();
                        User user = new User(id, nameuser, surnameuser, emailuser, phoneuser, "");
                        databaseUsers.child(id).setValue(user);
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), "User registred successfully!", Toast.LENGTH_LONG).show();
                        Intent toMenu = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(toMenu);
                        finish();
                    } else{
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
            }
        });
    }

}
