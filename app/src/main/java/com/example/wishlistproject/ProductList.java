package com.example.wishlistproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.wishlistproject.databinding.ActivityWhishlistPageBinding;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;

public class ProductList extends AppCompatActivity {

    private ActivityWhishlistPageBinding binding;
    private ArrayList<String> items = new ArrayList<>();
    private Adapter adapter;
    private String currentQuery;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    String username;
    Button RetourBut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setTitle("Search for a whishlist");

        this.RetourBut=findViewById(R.id.RetourBut);

        this.username = getIntent().getStringExtra("UserFromLogin");
        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getReadableDatabase();

        setUpList();

        setUpSearch();

        RetourBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Menus=new Intent(getApplicationContext(),MenuPrincipal.class);
                Menus.putExtra("Username", username);
                startActivity(Menus);
                finish();
            }
        });
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
                adapter.setItems(items);
            }
        });
    }

    private void processQuery(String query) {
        currentQuery = query;
        ArrayList<String> result = new ArrayList<>();

        //case sensitive search
        for (String item : items) {
            if (item.toLowerCase().contains(query.toLowerCase())) {
                result.add(item);
            }
        }

        adapter.setItems(result);
    }

    private void setUpList() {
        populateItems();
        adapter = new Adapter(this, items);
        binding.include.list.setLayoutManager(new LinearLayoutManager(this));
        binding.include.list.setAdapter(adapter);
    }

    private void populateItems() {
        // Add Listname from database to items List
        try {
            Cursor cursor;
            items = new ArrayList<>();
            cursor = db.rawQuery("select P.NomProd from UTILISATEUR U, QUANTITE_PRODUIT Q, PRODUIT P, SHARE S, WHISHLIST W\n" +
                    "WHERE S.User = U.User  and S.IDList=Q.IDList and Q.NrProduit=P.NrProduit and W.IDList=S.IDList\n" +
                    "and S.FriendUser = 'Yolo' and W.ListName = 'Vetement' ORDER BY P.NomProd ASC", null); // '"+username+"'
            cursor.moveToFirst();
            do {
                String listUser = cursor.getString(cursor.getColumnIndex("ListName"));
                items.add(listUser);
            } while (cursor.moveToNext());
            db.close();
            cursor.close();
        } catch (Exception e){
            Toast.makeText(ProductList.this, "You haven't wish lists", Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        binding.searchView.setMenuItem(menu.findItem(R.id.action_search));
        return true;
    }
}
