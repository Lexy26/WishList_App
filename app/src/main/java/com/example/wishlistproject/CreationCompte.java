package com.example.wishlistproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CreationCompte extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor cursor;

    EditText etUsernameCreation;
    EditText etPasswordCreation;
    EditText etConfirmPasswordCreation;
    Button btnSignUpCreation;
    ImageView imgBackArrow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creation_compte);

        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getWritableDatabase();

        etUsernameCreation = findViewById(R.id.etUsernameCreation);
        etPasswordCreation = findViewById(R.id.etPasswordCreation);
        etConfirmPasswordCreation = findViewById(R.id.etConfirmPasswordCreation);
        btnSignUpCreation = findViewById(R.id.btnSignUpCreation);
        imgBackArrow = findViewById(R.id.backArrow);

        btnSignUpCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String password = etPasswordCreation.getText().toString();
                String username = etUsernameCreation.getText().toString().trim();
                String confirmPassword = etConfirmPasswordCreation.getText().toString();
                if (password.isEmpty() && username.isEmpty() && confirmPassword.isEmpty()){
                    Toast.makeText(CreationCompte.this, "Insert Username & Password", Toast.LENGTH_SHORT).show();
                }
                else if(confirmPassword.isEmpty()){
                    Toast.makeText(CreationCompte.this, "Insert Confirm Password", Toast.LENGTH_SHORT).show();
                }
                else if(password.isEmpty()){
                    Toast.makeText(CreationCompte.this, "Insert Password", Toast.LENGTH_SHORT).show();
                }
                else if (username.isEmpty()){
                    Toast.makeText(CreationCompte.this, "Insert Username", Toast.LENGTH_SHORT).show();
                }
                // si username se trouve déjà dans la base de données
                else if (matchUser(username)){
                    Toast.makeText(CreationCompte.this, "Username already exist !", Toast.LENGTH_SHORT).show();
                }
                // si les deux password ne coincide pas
                else if (!password.equals(confirmPassword)) {
                    Toast.makeText(CreationCompte.this, "Incorrect Confirm Password", Toast.LENGTH_SHORT).show();
                }
                // Si le username est nouveau et le password coincide avec le confirm password
                else if (!matchUser(username) && password.equals(etConfirmPasswordCreation.getText().toString())){
                    insert(username, password, db);
                    db.close();
                    Intent profileUser = new Intent(getApplicationContext(), UserProfile.class);
                    profileUser.putExtra("Username",username);
                    startActivity(profileUser);
                    finish();
                }
            }
        });
        imgBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBack = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(goBack);
                finish();
            }
        });
    }
    public void insert(String user, String pass, SQLiteDatabase db){
        String str = "insert into UTILISATEUR " +
                "(User, Password,Image, Taille, Nom_Prenom, Adresse, Couleur_App, Anniversaire, Couleur_Favorite)"
                + "values('" +user+  "','" +pass+ "',null, null," +"' '"+", null, " +"' '"+", null, null)";
        db.execSQL(str);
    }
    Boolean matchUser(String insertUser) {
        try {
            cursor = db.rawQuery("select User from UTILISATEUR", null);
            cursor.moveToFirst();
            do {
                String user = cursor.getString(0);
                if (user.equals(insertUser)) {
                    return true;
                }
            } while (cursor.moveToNext());
        } catch (Exception ex) {
            Toast.makeText(CreationCompte.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}


// Intent intent = new Intent(ProfileActivity.this, com.example.wishlistproject.CreationCompte.class);
// startActivity(intent);
/* continue in other page : PAGE DU PROFILE */