package com.example.hezeyan.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.view.View;
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
import java.util.List;

public class PopularActivity extends AppCompatActivity {
    private RecyclerView popularList;
    private DatabaseReference listReference;
    private PopularAdapter popularAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popular_screen);
        popularList = findViewById(R.id.popular_list);
        popularAdapter = new PopularAdapter(this, new ArrayList<ListObj>());
        popularAdapter.setListener(new PopularAdapter.PopularListener() {
            @Override
            public void onItemClick(ListObj list) {

            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        popularList.setLayoutManager(layoutManager);
        popularList.setItemAnimator(new DefaultItemAnimator());
        popularList.setAdapter(popularAdapter);
        listReference = FirebaseUtil.getDBInstance().getReference().child("Lists");
        listReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<ListObj> lists = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ListObj list = snapshot.getValue(ListObj.class);
                    if (list != null) {
                        lists.add(list);
                    }
                }
                showPlaces(lists);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void goToLists(ListObj list){
        Intent newIntent = ListActivity.newIntent(this,list.getId());
        startActivity(newIntent);
    }
    private void showPlaces(final ArrayList<ListObj> lists) {
        if (popularAdapter == null)
            popularAdapter = new PopularAdapter(PopularActivity.this, lists);
        else
            popularAdapter.setPlaces(lists);
        popularAdapter.notifyDataSetChanged();
    }

}
