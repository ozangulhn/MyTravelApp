package com.example.hezeyan.myapplication;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import android.content.Intent;
public class ListActivity extends AppCompatActivity {

    private RecyclerView list;
    private ListAdapter listAdapter;
    private static String TAG = "ListActivity";
    private DatabaseReference listReference,listPlacesReference;
    private static final String NAME = "key.name";
    private static final String ID = "key.id";
    private static final String PLACELIST  = "key.placeList";
    private static final String USERID = "key.userID";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Activity created for list: " + getIntent().getExtras().getString(ID) );
        setContentView(R.layout.wishlist_screen);
        listReference = FirebaseUtil.getDBInstance().getReference("Lists");
        listPlacesReference = FirebaseUtil.getDBInstance().getReference("Lists").child(getIntent().getExtras().getString(ID));

        Log.d(TAG, "onCreate: Firebase reference for list: " + listPlacesReference.toString() );
        list = findViewById(R.id.wishlist_list);
        final ArrayList<Place> places = new ArrayList<>();
        listReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    ListObj list = snapshot.getValue(ListObj.class);
                    if(list != null && list.getId().equals(getIntent().getExtras().getString(ID))){

                        for(Map.Entry<String,Object> x : list.getPlaceList().entrySet() ){
                            Place place = new Place();
                            place.NameTurkish =(String) x.getValue();
                            place.Key = x.getKey();
                            places.add(place);
                        }
                    }
                }
                showLists(places);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        listAdapter = new ListAdapter(this,new ArrayList<Place>(),getIntent().getExtras().getString(ID));

        listAdapter.setListener(new ListAdapter.listListener() {
            @Override
            public void onItemClick(Place places) {

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        list.setLayoutManager(layoutManager);
        list.setItemAnimator(new DefaultItemAnimator());
        list.setAdapter(listAdapter);
    }

    private void clearList(){
        if(list != null){
            listAdapter.setLists(new ArrayList<Place>());
            listAdapter.notifyDataSetChanged();
        }
    }

    public void showLists(final ArrayList<Place> places){
        if(listAdapter == null)
            listAdapter = new ListAdapter(ListActivity.this,places,getIntent().getExtras().getString(ID));
        else
            listAdapter.setLists(places);
        listAdapter.notifyDataSetChanged();
    }
    public static Intent newIntent(Context context1, String listID){
        Bundle b = new Bundle();
        Log.d(TAG, "newIntent: " + listID);
        //b.putString(NAME,list.getName());
        //b.putString(ID,listID);
        //b.putString(PLACELIST,null);
        //b.putString(USERID,list.getUserid());
        Intent intent = new Intent(context1,ListActivity.class);
        intent.putExtra(ID,listID);
        //Log.d(TAG, "newIntent: getString " + intent.getString(ID));
        return intent;
    }
}