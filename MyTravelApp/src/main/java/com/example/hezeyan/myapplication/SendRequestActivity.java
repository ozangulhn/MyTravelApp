package com.example.hezeyan.myapplication;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SendRequestActivity extends AppCompatActivity implements View.OnClickListener{
    private Button sendRequestButton;
    private EditText headerTextView;
    private EditText opinionTextView;
    private FirebaseDatabase database;
    private DatabaseReference dataRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sendrequest_screen);
        sendRequestButton = findViewById(R.id.sendRequestButton);
        sendRequestButton.setOnClickListener(this);
        headerTextView =  findViewById(R.id.requestNameTextView);
        opinionTextView = findViewById(R.id.RequestOpinionTextView);
        database = FirebaseDatabase.getInstance("https://touristurcoapp.firebaseio.com/");
        dataRef = database.getReference("Request");
    }
    @Override
    public void onClick(View view) {
        Request newRequest = new Request(headerTextView.getText().toString(),opinionTextView.getText().toString());
        DatabaseReference NewRequestRef = dataRef.push();
        NewRequestRef.setValue(newRequest);
    }
}
