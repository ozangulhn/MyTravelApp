package com.example.hezeyan.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class SearchActivity extends AppCompatActivity {
    private EditText search;
    private RecyclerView searchList;
    private DatabaseReference placeReference,listReference;
    private SearchAdapter searchAdapter;
    private AlertDialog.Builder newAlertDialog;
    private ArrayList<ListObj> listOfLists = null;
    private String TAG = "SearchActivity";
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setLists();
        setContentView(R.layout.search_screen);
        search = findViewById(R.id.search_edit_text);
        searchList = findViewById(R.id.search_list);
        searchAdapter = new SearchAdapter(this, new ArrayList<Place>());
        searchAdapter.setListener(new SearchAdapter.SearchListener() {
            @Override
            public void onItemClick(Place place) {
                startActivity(PlaceDetailsActivity.newIntent(SearchActivity.this, place));
            }
        });
        user = FirebaseAuth.getInstance().getCurrentUser();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        searchList.setLayoutManager(layoutManager);
        searchList.setItemAnimator(new DefaultItemAnimator());
        searchList.setAdapter(searchAdapter);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
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
                            System.out.println(place.NameTurkish);
                            if (place != null && place.NameTurkish.toLowerCase().contains(s.toString().toLowerCase()) ) {
                                places.add(place);
                                Log.d(TAG, "onDataChange: place name:" + place.NameTurkish);
                            }
                        }

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
        placeReference = FirebaseUtil.getDBInstance().getReference("Places");
    }

    private void clearList() {
        if (searchAdapter != null) {
            searchAdapter.setPlaces(new ArrayList<Place>());
            searchAdapter.notifyDataSetChanged();
        }
    }

    private void showPlaces(final List<Place> places) {
        if (searchAdapter == null)
            searchAdapter = new SearchAdapter(SearchActivity.this, places);
        else
            searchAdapter.setPlaces(places);
        searchAdapter.notifyDataSetChanged();
    }
    public void setLists (){
        listReference = FirebaseUtil.getDBInstance().getReference("Lists");
        Log.d(TAG, "setLists: " + listReference.toString());
        listOfLists = new ArrayList<>();
        listReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listOfLists = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ListObj list = snapshot.getValue(ListObj.class);
                    Log.d(TAG, "onDataChange: Lists children " + snapshot.getValue(ListObj.class).getName() );
                    if(list!=null && list.getUserid().equals(user.getEmail()))

                    listOfLists.add(list);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    private void addPlaceToList(ListObj list, Place place){
        DatabaseReference listToAddPlace = listReference.child(list.getId()).child("placeList").child(place.Key);
        listToAddPlace.setValue(place.NameTurkish);
    }
    public void onPlaceCalled(final Place place){
        Log.d(TAG, "onPlaceCalled: ");
        Intent newIntent = PlaceDetailsActivity.newIntent(this,place);
        startActivity(newIntent);
    }
    public void onClickCalled (final Place place){
        //setLists();
        newAlertDialog= new AlertDialog.Builder(this);
        Log.d(TAG, "onClickCalled: For Place: "  + place.Key);

            Log.d(TAG, "onClickCalled: After setlists");
                if (listOfLists.size() > 0) {
                    String[] items = new String[listOfLists.size()];
                    for (ListObj x : listOfLists) {
                        items[listOfLists.indexOf(x)] = x.getName();
                        Log.d(TAG, "onClickCalled: items" + items[listOfLists.indexOf(x)] );
                    }
                    newAlertDialog.setTitle("Choose a list to add").setItems(items, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //add this place to list i
                            Log.d(TAG, "onClick: List " +listOfLists.get(i).getId()  +" is chosen to be added \n Place: "+ place.Key);

                            // liste seçildi
                            addPlaceToList(listOfLists.get(i),place);
                        }
                    }).setPositiveButton("Create New List", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            AlertDialog.Builder newListDialog = new AlertDialog.Builder(newAlertDialog.getContext());
                            newListDialog.setTitle("Enter new list name");
                            final EditText input = new EditText(newAlertDialog.getContext());
                            newListDialog.setView(input);
                            newListDialog.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    DatabaseReference newListObj = listReference.push();
                                    HashMap<String,Object> newPlaceList = new HashMap<>();
                                    newPlaceList.put(place.Key,place.NameTurkish);
                                    ListObj newList = new ListObj(newListObj.getKey(), input.getText().toString(), user.getEmail(), newPlaceList);
                                    newListObj.setValue(newList);
                                    listOfLists = new ArrayList<>();
                                    listOfLists.add(newList);
                                }
                            }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                                newListDialog.show();
                        }
                    });
                    newAlertDialog.show();

                }
                else{
                Log.d(TAG, "onClickCalled: Null Lists");
                    newAlertDialog.setTitle("Enter new list name");
                    final EditText input = new EditText(this);
                    newAlertDialog.setView(input);
                    newAlertDialog.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            DatabaseReference newListObj = listReference.push();
                            HashMap<String,Object> newPlaceList = new HashMap<>();
                            newPlaceList.put(place.Key,place.NameTurkish);
                            ListObj newList = new ListObj(newListObj.getKey(), input.getText().toString(), user.getEmail(), newPlaceList);
                            newListObj.setValue(newList);
                            listOfLists = new ArrayList<>();
                            listOfLists.add(newList);
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    newAlertDialog.show();
            }
    }
}
