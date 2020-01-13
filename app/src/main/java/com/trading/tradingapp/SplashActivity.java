package com.trading.tradingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

public class SplashActivity extends AppCompatActivity {

    //Declarations
    private SharedPreferences sharedPreferences;
    private Boolean isLoggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //Checking the session.
        getData();
        if(isLoggedIn==false)
            startActivity(new Intent(SplashActivity.this,LoginActivity.class));
        else
            startActivity(new Intent(SplashActivity.this,DashBoardActivity.class));
    }

    //Taking the values from the sharedPref file to check whether the user has logged out or not.
    private void getData(){
        sharedPreferences = getSharedPreferences(getString(R.string.sharedPrefFileName),Context.MODE_PRIVATE);
        isLoggedIn = sharedPreferences.getBoolean(getString(R.string.login_status),false);
        Log.i("isLoggedIn : ",""+isLoggedIn);
    }
}
