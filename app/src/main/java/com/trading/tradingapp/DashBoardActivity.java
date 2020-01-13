package com.trading.tradingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class DashBoardActivity extends AppCompatActivity {

    //Declarations
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Button signOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

       //Initialisations
       signOutButton = findViewById(R.id.signOutButt);
       signOutButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               StoreData();
               startActivity(new Intent(DashBoardActivity.this,LoginActivity.class));
           }
       });
    }

    //Storing the session as false as the user logs out.
    private void StoreData(){
        sharedPreferences = getSharedPreferences(getString(R.string.sharedPrefFileName),Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.login_status),false);
        editor.apply();
    }

    //Cannot go back from this Activity
    @Override
    public void onBackPressed() {
    }


}
