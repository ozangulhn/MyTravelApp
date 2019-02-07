package com.example.hezeyan.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class HomeActivity extends AppCompatActivity {

    private ImageView login;
    private ImageView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        login = (ImageView) findViewById(R.id.login);
        register = (ImageView) findViewById(R.id.register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Activity1 = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(Activity1);
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Activity1 = new Intent(HomeActivity.this, RegisterActivity.class);
                startActivity(Activity1);
                finish();
            }
        });
    }
}
