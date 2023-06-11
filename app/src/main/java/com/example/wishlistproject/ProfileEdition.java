package com.example.wishlistproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnContextClickListener;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

@SuppressLint("Registered")
public class ProfileEdition extends AppCompatActivity {

    private Button saveChanges;
    Button RetourBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edition);

        Button saveChanges = findViewById(R.id.saveChanges);

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent savedProfile = new Intent(getApplicationContext(), UserProfile.class);
                startActivity(savedProfile);
                finish();
            }
        });

        this.RetourBut=findViewById(R.id.RetourBut);

        RetourBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Menus=new Intent(getApplicationContext(),MenuPrincipal.class);
                startActivity(Menus);
                finish();
            }
        });

    }
}
