package com.example.hezeyan.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.util.CrashUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class UserInformationActivity extends AppCompatActivity {
    private static final String TAG = "UserInformationActivity";
    private ImageView mImageView;
    private Button mUploadbtn, saveButton;
    private EditText name, surname, phone;
     DatabaseReference reference;
    private StorageReference mStorage;
    private Uri uri;
    DatabaseReference databaseUsers;
    User thisUser;
    private static final int CAMERA_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mStorage = FirebaseStorage.getInstance().getReference();
        setContentView(R.layout.user_information);
        mUploadbtn = findViewById(R.id.changePhoto_button);
        mImageView = findViewById(R.id.profilePic);
        name = findViewById(R.id.editName);
        surname = findViewById(R.id.editSurname);
        phone = findViewById(R.id.editPhone);
        mUploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "Clicked");
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);

            }
        });
        FillInfomation();
        saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveInformation();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "Send data");
        super.onActivityResult(requestCode, resultCode, data);
        //mImageView.setImageURI(data.getData());
        //mImageView.setImageBitmap((Bitmap) data.getExtras().get("data"));
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bitmap bitmap = (Bitmap)data.getExtras().get("data");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,baos);
            byte[] dataout = baos.toByteArray();
            StorageReference filePath = mStorage.child("Photos/"+thisUser.id);
            UploadTask uploadTask = filePath.putBytes(dataout);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG,"Fail");
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //taskSnapshot.getMetadata()
                    Log.d(TAG,"Success");
                }
            });
        }

        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        mImageView.setImageBitmap(bitmap);
    }

    public void FillInfomation(){

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            DatabaseReference userReference = FirebaseUtil.getDBInstance().getReference().child("Users");
            userReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        User tempuser = snapshot.getValue(User.class);
                        if(tempuser!=null){
                            Log.d(TAG, "onDataChange: " + tempuser.name);
                            if(tempuser.email.equals(user.getEmail())){
                                thisUser = tempuser;
                                name.setText(tempuser.name);
                                surname.setText(tempuser.surname);
                                phone.setText(tempuser.phone);
                                downloadPicture();
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

    public void saveInformation(){
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //Log.d(TAG, "saveInformation: ", phone);
        User usertemp = new User(thisUser.id, name.getText().toString(), surname.getText().toString(), user.getEmail(), phone.getText().toString(),mStorage.child("Photos").child(thisUser.id).toString());
        databaseUsers.child(thisUser.id).setValue(usertemp);
    }
    public void downloadPicture(){
        if(thisUser.picture != null ){
            mStorage.child("Photos/" + thisUser.picture).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    String link = uri.toString();
                    Picasso.get().load(link).into(mImageView);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors
                }
            });

        }
    }
}

