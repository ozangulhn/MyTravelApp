package com.example.hezeyan.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageButton;
import android.support.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


ImageButton toLoginButton;
ImageButton toSearchButton;
ImageButton toMyListsButton;
ImageButton toMapButton;
ImageButton toRequestButton;
ImageButton toCheckListButton;
ImageButton toWishListButton;
TextView welcome;
    @Override
    public void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showWelcome();
        toLoginButton = findViewById(R.id.toAdminLoginButton);
        toSearchButton = findViewById(R.id.search_imageButton);
        toMyListsButton = findViewById(R.id.popular_imageButton);
        toMapButton = findViewById(R.id.map_imageButton);
        toRequestButton = findViewById(R.id.sendrequest_imageButton);
        toCheckListButton = findViewById(R.id.checklist_imageButton);
        toWishListButton = findViewById(R.id.wishlist_imageButton);
        toLoginButton.setOnClickListener(this);
        toSearchButton.setOnClickListener(this);
        toMyListsButton.setOnClickListener(this);
        toMapButton.setOnClickListener(this);
        toRequestButton.setOnClickListener(this);
        toCheckListButton.setOnClickListener(this);
        toWishListButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == toLoginButton.getId()) {
            startActivity(new Intent(MainActivity.this, UserInformationActivity.class));
        }else if (view.getId() == toSearchButton.getId()){
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
        }else if (view.getId() == toMyListsButton.getId()){
            startActivity(new Intent(MainActivity.this, PopularActivity.class));
        }else if (view.getId() == toMapButton.getId()){
            startActivity(new Intent(MainActivity.this, MapsActivity.class));
        }else if (view.getId() == toRequestButton.getId()){
            startActivity(new Intent(MainActivity.this, SendRequestActivity.class));
        }else if (view.getId() == toCheckListButton.getId()){
            startActivity(new Intent(MainActivity.this, ChecklistActivity.class));

        }else if (view.getId() == toWishListButton.getId()){
            startActivity(new Intent(MainActivity.this,ListActivity.class));
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }
    public void showWelcome(){
        welcome = findViewById(R.id.welcome);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            DatabaseReference userReference = FirebaseUtil.getDBInstance().getReference().child("Users");
            userReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User tempuser = snapshot.getValue(User.class);
                        if(tempuser!=null){
                            if(tempuser.email.equals(user.getEmail())){
                                welcome.setText( " welcome "+tempuser.name+ "!" );
                            }

                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }


}
