package com.misht.shakepaws.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.misht.shakepaws.R;

public class UploadPetActivity extends AppCompatActivity {

    public static final int PICK_IMAGE = 1;

    private EditText inputPetName, inputPetOwnerPhone, inputPetAge, inputPetDesc, inputPetLocation;
    private ImageView inputPetImage;
    private Button chooseImageBtn;
    private Spinner inputPetBreed, inputPetAvailability, inputPetGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_pet);

        initializeActionBar();
        initializeVariables();

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
                Uri uri = data.getData();
                if(uri != null)
                    Glide.with(getApplicationContext()).load(uri).apply(RequestOptions.centerCropTransform()).into(inputPetImage);
            } else {
                Toast.makeText(getApplicationContext(), "could not get image. try again.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
