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
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor cursor;

    TextView tvSignUpLogin;
    EditText etUsernameLogin;
    EditText etPasswordLogin;
    TextView btnSignUpLogin;
    Button btnSignInLogin;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);
        databaseHelper.StartWork();
        db = databaseHelper.getReadableDatabase();

        tvSignUpLogin = findViewById(R.id.tvSignUpLogin);
        etUsernameLogin = findViewById(R.id.etUsernameLogin);
        etPasswordLogin = findViewById(R.id.etPasswordLogin);
        btnSignUpLogin = findViewById(R.id.btnSignUpLogin);
        btnSignInLogin = findViewById(R.id.btnSignInLogin);

        btnSignInLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String password = etPasswordLogin.getText().toString();
                username = etUsernameLogin.getText().toString().trim();
                if (password.isEmpty() && username.isEmpty()){
                    Toast.makeText(MainActivity.this, "Insert Username & Password", Toast.LENGTH_SHORT).show();
                }
                else if (password.isEmpty()){
                    Toast.makeText(MainActivity.this, "Insert Password", Toast.LENGTH_SHORT).show();
                }
                else if (username.isEmpty()){
                    Toast.makeText(MainActivity.this, "Insert Username", Toast.LENGTH_SHORT).show();
                }
                else if (matchUserPass(username, password)){
                    //Go in menus principal
                    Intent menuActivity = new Intent(getApplicationContext(), MenuPrincipal.class);
                    menuActivity.putExtra("Username",username);
                    startActivity(menuActivity);
                    finish();
                }
                else if(!matchUserPass(username, password)){
                    Toast.makeText(MainActivity.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        // Go in CREATION ACCOUNT screen
        btnSignUpLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CreationCompte.class);
                startActivity(intent);
                db.close();
                finish();
            }
        });
    }

    Boolean matchUserPass(String insertUser,  String insertPass) {
        try {
            cursor = db.rawQuery("select User, Password from UTILISATEUR", null);
            cursor.moveToFirst();
            do {
                String user = cursor.getString(0);
                String password = cursor.getString(1);
                if (user.equals(insertUser) && password.equals(insertPass)) {
                    return true;
                }
            } while (cursor.moveToNext());
        } catch (Exception ex) {
            Toast.makeText(MainActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}
