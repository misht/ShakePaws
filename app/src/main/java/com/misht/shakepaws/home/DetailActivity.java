package com.misht.shakepaws.home;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.misht.shakepaws.R;
import com.misht.shakepaws.home.models.Pet;

import static android.view.View.GONE;

public class DetailActivity extends AppCompatActivity {

    private ImageView petImage;
    private TextView petName, phoneNumber, petStatus, petBreed, petAge, location, petGender, petDesc, email;
    private RelativeLayout phoneNumberLayout, petStatusLayout, petBreedLayout,
            petAgeLayout, petLocationLayout, petGenderLayout, petDescLayout, petEmailLayout;

    private Pet pet;

    int PERMISSION_ALL = 10;
    String[] PERMISSIONS = {Manifest.permission.CALL_PHONE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!hasPermissions(getApplicationContext(), PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS,PERMISSION_ALL);
        }

        setContentView(R.layout.activity_detail);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setContentInsetStartWithNavigation(0);
        setSupportActionBar(toolbar);

        initializeVariables();
        initializeActionBar();
        initCollapsingToolbar();
    }

    private void initializeVariables() {
//        petName = findViewById(R.id.pet_display_name);
        petImage = findViewById(R.id.pet_display_image);
        petStatus = findViewById(R.id.con_status);
        petBreed = findViewById(R.id.con_breed);
        petAge = findViewById(R.id.con_age);
        petGender = findViewById(R.id.con_gender);
        phoneNumber = findViewById(R.id.con_phone);
        location = findViewById(R.id.con_location);
        petDesc = findViewById(R.id.con_desc);
        email = findViewById(R.id.con_email);


        phoneNumberLayout = findViewById(R.id.con_phone_layout);
        petStatusLayout = findViewById(R.id.con_status_layout);
        petBreedLayout = findViewById(R.id.con_breed_layout);
        petAgeLayout = findViewById(R.id.con_age_layout);
        petGenderLayout = findViewById(R.id.con_gender_layout);
        petLocationLayout = findViewById(R.id.con_location_layout);
        petDescLayout = findViewById(R.id.con_desc_layout);
        petEmailLayout = findViewById(R.id.con_email_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
        assert getIntent() != null;
        pet = (Pet) getIntent().getSerializableExtra("pet_object");

        assert pet != null;

        Glide.with(this).load(pet.getImageUrl())
                .apply(RequestOptions.centerCropTransform()).into(petImage);

        if(pet.getName() != null && !pet.getName().equals(""))
            petName.setText(pet.getName());
        else
            petName.setVisibility(GONE);
        if(pet.getAge() != null && !pet.getAge().equals("")) {
            String tempAge = pet.getAge() + " " + getString(R.string.years);
            petAge.setText(tempAge);
        } else {
            petAgeLayout.setVisibility(GONE);
        }

        if(pet.getGender() != null && !pet.getGender().equals(""))
            petGender.setText(pet.getGender());
        else
            petGenderLayout.setVisibility(GONE);

        if(pet.getAvailability() != null && !pet.getAvailability().equals(""))
            petStatus.setText(pet.getAvailability());
        else
            petStatusLayout.setVisibility(GONE);

        if(pet.getBreed() != null && !pet.getBreed().equals(""))
            petBreed.setText(pet.getBreed());
        else
            petBreedLayout.setVisibility(GONE);

        if(pet.getPetOwnerPhoneNumber() != null && !pet.getPetOwnerPhoneNumber().equals(""))
            phoneNumber.setText(pet.getPetOwnerPhoneNumber());
        else
            phoneNumberLayout.setVisibility(GONE);

        if(pet.getLocation() != null && !pet.getLocation().equals(""))
            location.setText(pet.getLocation());
        else
            petLocationLayout.setVisibility(GONE);

        if(pet.getDescription() != null && !pet.getDescription().equals(""))
            petDesc.setText(pet.getDescription());
        else
            petDescLayout.setVisibility(GONE);

        if(pet.getPetOwnerEmail() != null && !pet.getPetOwnerEmail().equals(""))
            email.setText(pet.getPetOwnerEmail());
        else
            petEmailLayout.setVisibility(GONE);
        petEmailLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{ pet.getPetOwnerEmail() });
                startActivity(Intent.createChooser(i, getString(R.string.send_email)));
            }
        });


        final Activity activity = this;

        phoneNumberLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(activity,
                        Manifest.permission.CALL_PHONE)
                        != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.CALL_PHONE},
                            189);
                }
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + pet.getPetOwnerPhoneNumber()));
                startActivity(intent);
            }
        });
    }

    private void initializeActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingtoolbar =
                findViewById(R.id.collapsing_toolbar);
        collapsingtoolbar.setTitle(" ");
        AppBarLayout appBarLayout = findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        //hiding & showing title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if(scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if(scrollRange + verticalOffset == 0) {
                    collapsingtoolbar.setTitle(pet.getName());
                    isShow = true;
                } else if (isShow) {
                    collapsingtoolbar.setTitle(pet.getName());
                    isShow = false;
                }
            }
        });
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null) {
            for(String permission: permissions) {
                if(ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
