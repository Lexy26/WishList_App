package com.example.wishlistproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuPrincipal extends AppCompatActivity {

    Button WishListBut;
    Button FriendsBut;
    Button ProfileBut;
    Button DecoBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        this.WishListBut = findViewById(R.id.WishListBut);
        this.FriendsBut = findViewById(R.id.FriendsBut);
        this.ProfileBut = findViewById(R.id.ProfileBut);
        this.DecoBut=findViewById(R.id.deconnexion);
        final String username = getIntent().getStringExtra("Username");

        WishListBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent whishlist_activity = new Intent(getApplicationContext(), WhishList_page.class);
                whishlist_activity.putExtra("UserFromLogin", username);
                startActivity(whishlist_activity);
                finish();
            }
        });

        FriendsBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent friendslist_activity = new Intent(getApplicationContext(), FriendList.class);
                friendslist_activity.putExtra("UserFromLogin", username);
                startActivity(friendslist_activity);
                finish();
            }
        });

        ProfileBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pactivity = new Intent(getApplicationContext(), UserProfile.class);
                pactivity.putExtra("UserFromLogin", username);
                startActivity(pactivity);
                finish();
            }
        });

        DecoBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainAct=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainAct);
                finish();
            }
        });

    }

}
