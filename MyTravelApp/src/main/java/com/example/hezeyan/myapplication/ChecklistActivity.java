package com.example.hezeyan.myapplication;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Button;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import android.widget.Toast;
import android.support.v7.app.AppCompatActivity;
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

public class ChecklistActivity extends AppCompatActivity implements View.OnClickListener {
    private HashSet<String> checkboxHashSet = new HashSet<>();
    private List<Checkbox> checkboxLinkedList = new LinkedList<>();
    private LinkedList<String> selectedCheckbox = new LinkedList<>();
    private CheckboxAdapter adapter;
    private EditText search;
    private RecyclerView checklistList;
    private DatabaseReference placeReference;
    private ChecklistAdapter checklistAdapter;
    private ListView listView;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        listView = (ListView)findViewById(R.id.list_view);
        button = (Button)findViewById(R.id.button);
        for (int i=0; i < 10; i++) {
            checkboxHashSet.add("Checkbox" +" "+ String.valueOf(i + 1));
        }

        getData();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedCheckbox = adapter.getSelectedCountry();
                StringBuilder str = new StringBuilder();
                if(selectedCheckbox.size() != 0){
                    Iterator iterator = selectedCheckbox.iterator();
                    while (iterator.hasNext()){
                        str.append(iterator.next().toString());
                    }
                    Toast.makeText(ChecklistActivity.this, str, Toast.LENGTH_SHORT).show();
                }
            }
        });

        search = findViewById(R.id.checklist_edit_text);
        checklistList = findViewById(R.id.checklist_list);

        checklistAdapter = new ChecklistAdapter(this, new ArrayList<Place>());
        checklistAdapter.setListener(new ChecklistAdapter.ChecklistListener() {
            @Override
            public void onItemClick(Place place) {

            }
        });
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        checklistList.setLayoutManager(layoutManager);
        checklistList.setItemAnimator(new DefaultItemAnimator());
        checklistList.setAdapter(checklistAdapter);
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
                            if (place != null  && place.NameTurkish.toLowerCase().contains(s.toString().toLowerCase()) ) {
                                System.out.println(place.NameTurkish);
                                places.add(place);
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
        placeReference = FirebaseUtil.getDBInstance().getReference("Checklist");
    }
    

    private void clearList() {
        if (checklistAdapter != null) {
            checklistAdapter.setPlaces(new ArrayList<Place>());
            checklistAdapter.notifyDataSetChanged();
        }
    }

    private void showPlaces(final List<Place> places) {
        if (checklistAdapter == null)
            checklistAdapter = new ChecklistAdapter(ChecklistActivity.this, places);
        else
            checklistAdapter.setPlaces(places);
        checklistAdapter.notifyDataSetChanged();
    }

    public void getData(){
        for(String s : checkboxHashSet){
            Checkbox country = new Checkbox();
            country.setName(s);
            checkboxLinkedList.add(country);
        }

        adapter = new CheckboxAdapter(this, checkboxLinkedList);
        listView.setAdapter(adapter);

    }

    @Override
    public void onClick(View view) {

    }
}
