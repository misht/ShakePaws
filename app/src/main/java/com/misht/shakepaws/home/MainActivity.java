package com.misht.shakepaws.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.misht.shakepaws.R;
import com.misht.shakepaws.home.fragments.HomeFragment;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView userName, userEmail;
    private ImageView userProfileImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_start, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setItemIconTintList(null);
        View nView = navigationView.getHeaderView(0);

        userProfileImg = nView.findViewById(R.id.user_profile_img);
        userName = nView.findViewById(R.id.user_name);
        userEmail = nView.findViewById(R.id.user_email);
        setupDrawerContent(navigationView);

        if(savedInstanceState == null) {
            selectDrawerItem(0);
        }

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        updateNavigationDrawer(user);

        HomeFragment homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, homeFragment).commitAllowingStateLoss();
    }

    @Override
    protected void onResume() {
        super.onResume();

//        showSamplePets();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectDrawerItem(item.getItemId());
                return true;
            }
        });
    }

    private void selectDrawerItem(int id) {
        switch (id) {
            case R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.upload:
                startActivity(new Intent(this, UploadPetActivity.class));
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.logout:
                removeLoggedInStatus();
                drawerLayout.closeDrawer(GravityCompat.START);
                mAuth.signOut();
                finish();
            default:
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }

    private void removeLoggedInStatus() {
        final SharedPreferences preferences = getSharedPreferences(getString(R.string.shakepaws), Context.MODE_PRIVATE);
        preferences.edit().putBoolean(getString(R.string.logged_in), false).apply();
    }


    private void updateNavigationDrawer(FirebaseUser user) {
        userName.setText(user.getDisplayName());
        userEmail.setText(user.getEmail());
        Glide.with(getApplicationContext()).load(user.getPhotoUrl())
                .apply(RequestOptions.circleCropTransform()).into(userProfileImg);
    }

}
