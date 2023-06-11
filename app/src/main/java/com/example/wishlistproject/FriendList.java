package com.example.wishlistproject;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.os.Bundle;

import com.example.wishlistproject.databinding.ActivityFriendListBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;


import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class FriendList extends AppCompatActivity {

    private ActivityFriendListBinding binding;
    private ArrayList<String> friends = new ArrayList<>();
    private Adapter adapter;
    private String currentQuery;

    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    String username;
    Button RetourBut;
    Button Add_whishlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = DataBindingUtil.setContentView(this,R.layout.activity_friend_list);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Search for a friend");

        this.RetourBut=findViewById(R.id.RetourBut);
        Add_whishlist = findViewById(R.id.Add_whishlist);


        this.username = getIntent().getStringExtra("UserFromLogin");
        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getReadableDatabase();

        RetourBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Menus=new Intent(getApplicationContext(),MenuPrincipal.class);
                Menus.putExtra("Username", username);
                startActivity(Menus);
                finish();
            }
        });
        Add_whishlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FriendList.this, "               Add Friend \n    404 Working Progress", Toast.LENGTH_SHORT).show();

            }
        });


        setUpList();

        setUpSearch();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Send a message to your friend", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void friend_list(View view) {
        Toast.makeText(getApplicationContext(), "Clicked on a list", Toast.LENGTH_LONG).show();
    }

    private void setUpSearch() {
        binding.searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processQuery(newText);
                return true;
            }
        });

        binding.searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
                binding.searchView.setQuery(currentQuery, false);
            }

            @Override
            public void onSearchViewClosed() {
                currentQuery = "";
                adapter.setItems(friends);
            }
        });
    }

    private void processQuery(String query) {
        currentQuery = query;
        ArrayList<String> result = new ArrayList<>();

        //case sensitive search
        for (String friend : friends) {
            if (friend.toLowerCase().contains(query.toLowerCase())) {
                result.add(friend);
            }
        }

        adapter.setItems(result);

    }

    private void setUpList() {
        populateFriends();
        adapter = new Adapter(this, friends);
        binding.inClude.list.setLayoutManager(new LinearLayoutManager(this));
        binding.inClude.list.setAdapter(adapter);
    }

    private void populateFriends() {
        friends = new ArrayList<>();
// Add Listname from database to items List
        try {
            Cursor cursor;
            friends = new ArrayList<>();
            cursor = db.rawQuery("select FriendUser from UTILISATEUR U, FRIENDS F WHERE F.User = U.User and F.User = '"+username+"' ORDER BY FriendUser ASC", null);
            cursor.moveToFirst();
            do {
                String listUser = cursor.getString(cursor.getColumnIndex("FriendUser"));
                Log.d("DEBUG Programme", listUser);
                friends.add(listUser);
            } while (cursor.moveToNext());
            db.close();
            cursor.close();
        } catch (Exception e){
            Toast.makeText(FriendList.this, "You haven't friends", Toast.LENGTH_SHORT).show();
        }

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        binding.searchView.setMenuItem(menu.findItem(R.id.action_search));
        return true;
    }

}
