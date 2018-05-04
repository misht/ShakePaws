package com.misht.shakepaws.onboarding;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.misht.shakepaws.LoginActivity;
import com.misht.shakepaws.R;
import com.misht.shakepaws.home.MainActivity;

public class OnboardingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences preferences = getSharedPreferences(getString(R.string.shakepaws), Context.MODE_PRIVATE);
        setContentView(R.layout.activity_onboarding);
        ImageView onboardingImage = findViewById(R.id.onboarding_image);
        Glide.with(getApplicationContext()).load(R.mipmap.splash).apply(RequestOptions.circleCropTransform()).into(onboardingImage);

        int TIME_DELAY = 5000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(preferences.getBoolean(getString(R.string.logged_in), false)) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                }
                finish();
            }
        }, TIME_DELAY);
    }
}
