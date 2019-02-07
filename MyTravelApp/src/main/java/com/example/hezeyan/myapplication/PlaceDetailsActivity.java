package com.example.hezeyan.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.ImageView;
import android.graphics.Bitmap;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class PlaceDetailsActivity extends AppCompatActivity {

    private static final String LOCATION = "key.location";
    private static final String TR_NAME = "key.tr.name";
    private static final String EN_NAME = "key.en.name";
    private static final String EN_INFO = "key.en.info";
    private static final String TR_INFO = "key.tr.info";
    private static final String CITY = "key.city";
    private static final String IMAGE1= "key.image1";
    private static final String IMAGE2= "key.image2";
    private static final String IMAGE3= "key.image3";
    private static final String IMAGE4= "key.image4";
    private TextView name;
    private TextView city;
    private TextView information;
    private ImageView image1,image2,image3,image4;
    private URL url1,url2,url3,url4;
    private Bitmap bmp1,bmp2,bmp3,bmp4;
    public static Intent newIntent(Context context, Place place) {
        Bundle b = new Bundle();
        b.putString(TR_NAME, place.NameTurkish);
        b.putString(LOCATION, place.Location);
        b.putString(EN_NAME,place.NameEnglish);
        b.putString(EN_INFO,place.InformationEnglish);
        b.putString(TR_INFO,place.InformationTurkish);
        b.putString(CITY,place.City);
        if(place.Image1 != null);
        b.putString(IMAGE1,place.Image1);
        if(place.Image2 != null);
        b.putString(IMAGE2,place.Image2);
        if(place.Image3 != null);
        b.putString(IMAGE3,place.Image3);
        if(place.Image4 != null);
        b.putString(IMAGE4,place.Image4);

        Intent intent = new Intent(context, PlaceDetailsActivity.class);
        intent.putExtras(b);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_details_activity);

        try {
            if (savedInstanceState != null) {
                url1= new URL(savedInstanceState.getString("key.image1"));
                url2 = new URL(savedInstanceState.getString("key.image2"));
                url3 = new URL(savedInstanceState.getString("key.image3"));
                url4 = new URL(savedInstanceState.getString("key.image4"));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        name = findViewById(R.id.detail_name);
        information = findViewById(R.id.detail_information);
        city = findViewById(R.id.detail_city);
        image1 = findViewById(R.id.detail_imageview1);
        image2 = findViewById(R.id.detail_imageview2);
        image3 = findViewById(R.id.detail_imageview3);
        image4 = findViewById(R.id.detail_imageview4);
//        try {
//            bmp1 = BitmapFactory.decodeStream(url1.openConnection().getInputStream());
//            bmp2 = BitmapFactory.decodeStream(url2.openConnection().getInputStream());
//            bmp3 = BitmapFactory.decodeStream(url3.openConnection().getInputStream());
//            bmp4 = BitmapFactory.decodeStream(url4.openConnection().getInputStream());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Bundle b = getIntent().getExtras();
        name.setText(b.getString(TR_NAME, ""));
        city.setText(b.getString(CITY));
        information.setText(b.getString(TR_INFO));
/*      image1.setImageBitmap(bmp1);
        image2.setImageBitmap(bmp2);
        image3.setImageBitmap(bmp3);
        image4.setImageBitmap(bmp4);*/
    }
}
