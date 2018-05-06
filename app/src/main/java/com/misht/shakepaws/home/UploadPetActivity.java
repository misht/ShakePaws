package com.misht.shakepaws.home;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.misht.shakepaws.R;
import com.misht.shakepaws.home.models.Pet;
import com.misht.shakepaws.utils.Constants;

public class UploadPetActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;

    private EditText inputPetName, inputPetOwnerPhone, inputPetAge, inputPetDesc, inputPetLocation;
    private ImageView inputPetImage;
    private Button chooseImageBtn;
    private FloatingActionButton saveBtn;
    private Spinner inputPetBreed, inputPetAvailability, inputPetGender;

    private Uri filePath;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private StorageReference storageReference;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pet);

        initializeActionBar();
        initializeVariables();

        mAuth = FirebaseAuth.getInstance();
        if(mAuth != null) {
            user = mAuth.getCurrentUser();
        }

        storageReference = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_SAVE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        chooseImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });
    }

    private void initializeVariables() {
        inputPetName = findViewById(R.id.input_pet_name);
        inputPetAge = findViewById(R.id.input_pet_age);
        inputPetDesc = findViewById(R.id.input_pet_desc);
        inputPetOwnerPhone = findViewById(R.id.input_pet_phone);
        inputPetGender = findViewById(R.id.input_pet_gender);
        inputPetAvailability = findViewById(R.id.input_pet_availability);
        inputPetLocation = findViewById(R.id.input_pet_location);
        inputPetBreed = findViewById(R.id.input_pet_breed);
        chooseImageBtn = findViewById(R.id.choose_image_btn);
        inputPetImage= findViewById(R.id.input_pet_image);
        saveBtn = findViewById(R.id.save_btn);
    }

    private void initializeActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            if(data != null) {
                filePath = data.getData();
                if(filePath != null)
                    Glide.with(getApplicationContext()).load(filePath).apply(RequestOptions.centerCropTransform()).into(inputPetImage);
            } else {
                Toast.makeText(getApplicationContext(), "could not get image. try again.", Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getFileExtension(Uri uri) {
        ContentResolver resolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(resolver.getType(uri));
    }

//    private void savePetInfo() {
//        Pet pet = new Pet(
//                inputPetName.getText().toString(),
//                inputPetAge.getText().toString(),
//                inputPetOwnerPhone.getText().toString(),
//                user.getDisplayName(),
//                user.getEmail(),
//                inputPetGender.getSelectedItem().toString(),
//                inputPetAvailability.getSelectedItem().toString(),
//                inputPetLocation.getText().toString(),
//                inputPetDesc.getText().toString(),
//                inputPetBreed.getSelectedItem().toString(),
//
//        );
//    }

    private void uploadFile() {
        if(filePath != null) {
            StorageReference sRef = storageReference.child(Constants.STORAGE_PATH_UPLOAD +
                    System.currentTimeMillis() + "." + getFileExtension(filePath));
            sRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            if(taskSnapshot.getDownloadUrl() != null) {
                                Pet pet = new Pet(
                                        inputPetName.getText().toString(),
                                        inputPetAge.getText().toString(),
                                        inputPetOwnerPhone.getText().toString(),
                                        user.getDisplayName(),
                                        user.getEmail(),
                                        inputPetGender.getSelectedItem().toString(),
                                        inputPetAvailability.getSelectedItem().toString(),
                                        inputPetLocation.getText().toString(),
                                        inputPetDesc.getText().toString(),
                                        inputPetBreed.getSelectedItem().toString(),
                                        taskSnapshot.getDownloadUrl().toString()
                                );
                                String petId = mDatabase.push().getKey();
                                mDatabase.child(petId).setValue(pet);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "No image selected.", Toast.LENGTH_SHORT).show();
        }
    }

}
