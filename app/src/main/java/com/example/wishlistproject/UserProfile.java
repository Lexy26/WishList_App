package com.example.wishlistproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("Registered")
public class UserProfile extends AppCompatActivity {

    Button editProfile;
    Button menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        this.editProfile = findViewById(R.id.editProfile);
        this.menu = findViewById(R.id.menu);
        final String username = getIntent().getStringExtra("UserFromLogin");

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editP = new Intent(getApplicationContext(), MenuPrincipal.class);
                editP.putExtra("Username", username);
                startActivity(editP);
                finish();
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editP = new Intent(getApplicationContext(), MenuPrincipal.class);
                editP.putExtra("Username", username);
                startActivity(editP);
                finish();
            }
        });
    }
}