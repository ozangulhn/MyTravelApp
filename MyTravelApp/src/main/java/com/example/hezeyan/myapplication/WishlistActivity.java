package com.example.hezeyan.myapplication;

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

public class WishlistActivity extends AppCompatActivity {
    private EditText search;
    private RecyclerView wishlistList;
    private DatabaseReference placeReference;
    private WishlistAdapter wishlistAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist_screen);
        search = findViewById(R.id.wishlist_edit_text);
        wishlistList = findViewById(R.id.wishlist_list);
        wishlistAdapter = new WishlistAdapter(this, new ArrayList<Place>());
        wishlistAdapter.setListener(new WishlistAdapter.WishlistListener() {
            @Override
            public void onItemClick(Place place) {

            }
        });
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        wishlistList.setLayoutManager(layoutManager);
        wishlistList.setItemAnimator(new DefaultItemAnimator());
        wishlistList.setAdapter(wishlistAdapter);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(final CharSequence s, int start, int before, int count) {
                if (TextUtils.isEmpty(s)) {
                    clearList();
                    return;
                }

                placeReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<Place> places = new ArrayList<>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Place place = snapshot.getValue(Place.class);
                            if (place != null && place.NameTurkish.toLowerCase().contains(s.toString().toLowerCase())) {
                                places.add(place);
                            }
                        }
                        System.out.println();
                        showPlaces(places);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        placeReference = FirebaseUtil.getDBInstance().getReference("Wishlist");
    }

    private void clearList() {
        if (wishlistAdapter != null) {
            wishlistAdapter.setPlaces(new ArrayList<Place>());
            wishlistAdapter.notifyDataSetChanged();
        }
    }

    private void showPlaces(final List<Place> places) {
        if (wishlistAdapter == null)
            wishlistAdapter = new WishlistAdapter(WishlistActivity.this, places);
        else
            wishlistAdapter.setPlaces(places);
        wishlistAdapter.notifyDataSetChanged();
    }

}
