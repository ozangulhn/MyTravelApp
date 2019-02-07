package com.example.hezeyan.myapplication;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private DatabaseReference placeReference;
    private ArrayList <Place> places;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        System.out.println("Start");
        places = new ArrayList<Place>();
        placeReference = FirebaseUtil.getDBInstance().getReference("Places");
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        placeReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Place place = snapshot.getValue(Place.class);
//                    System.out.println(place.NameTurkish);
//                    System.out.println(place.Location.substring(0,9));
//                    System.out.println(place.Location.substring(10,20));
//                    places.add(place);
                    if(place!=null) {
                        double latitude, longitude;
                        latitude = Double.parseDouble(place.Location.substring(0, 9));
                        longitude = Double.parseDouble(place.Location.substring(10, 20));
                        LatLng latlng = new LatLng(latitude, longitude);
                        mMap.addMarker(new MarkerOptions().position(latlng).title(place.NameTurkish));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
//        System.out.println("BURASI");
//        LatLng [] locationArray = new LatLng[20];
//        int n=0;
//        Iterator<Place> itr = places.iterator();
//        while(itr.hasNext()) {
//            int latitude,longitude;
//            latitude = Integer.parseInt(itr.next().Location.substring(0,9));
//            longitude = Integer.parseInt(itr.next().Location.substring(10,20));
//            locationArray[n] = new LatLng(latitude,longitude);
//            mMap.addMarker(new MarkerOptions().position(locationArray[n]).title(itr.next().NameTurkish));
//            System.out.println(n);
//            n++;
//        }
//        System.out.println(n);
       //mMap.moveCamera(CameraUpdateFactory.newLatLng(locationArray[n-1]));

            //update, check for collisions, etc
    }
}
