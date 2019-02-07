package com.example.hezeyan.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPlaceActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText placeNameTurkish, placeNameEnglish, informationTurkish, informationEnglish, city, location, image1, image2, image3, image4;
    private FirebaseDatabase database;
    private DatabaseReference placeReference;
    private Button createNewPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addplace_screen);
        placeNameTurkish = findViewById(R.id.nameTurkishPlainText);
        placeNameEnglish = findViewById(R.id.nameEnglishPlainText);
        informationTurkish = findViewById(R.id.informationTurkishPlainText);
        informationEnglish = findViewById(R.id.informationEnglishPlainText);
        city = findViewById(R.id.cityPlainText);
        location = findViewById(R.id.locationPlainText);
        image1 = findViewById(R.id.image1PlainText);
        image2 = findViewById(R.id.image2PlainText);
        image3 = findViewById(R.id.image3PlainText);
        image4 = findViewById(R.id.image4PlainText);
        createNewPlace = findViewById(R.id.createPlaceButton);
        createNewPlace.setOnClickListener(this);
        database = FirebaseUtil.getDBInstance();
        placeReference = database.getReference("Places");
    }

    @Override
    public void onClick(View view) {
        Place newPlace = new Place(
                placeNameTurkish.getText().toString(),
                placeNameEnglish.getText().toString(),
                informationTurkish.getText().toString(),
                informationEnglish.getText().toString(),
                location.getText().toString(),
                city.getText().toString(),
                image1.getText().toString(),
                image2.getText().toString(),
                image3.getText().toString(),
                image4.getText().toString());
        DatabaseReference newPlaceRef = placeReference.push();
        newPlace.Key = newPlaceRef.getKey();
        newPlaceRef.setValue(newPlace, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Toast.makeText(AddPlaceActivity.this, "Success", Toast.LENGTH_LONG).show();
                finish();
            }
        });
    }
}
