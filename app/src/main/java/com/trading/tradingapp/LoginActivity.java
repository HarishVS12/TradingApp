package com.trading.tradingapp;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.trading.tradingapp.Retrofit.CustomersAPI;
import com.trading.tradingapp.Retrofit.LoginJSON;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    //Declarations
    private EditText user,password;
    private Button signIn;
    private Boolean errorBool;
    private CustomersAPI customersAPI;
    private Retrofit retrofit;
    private String baseUrl , message;
    private  String userGotFromJSON;
    private ProgressBar progressBar;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor sharedEditor;

    public String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialisations
        user = findViewById(R.id.user);
        password = findViewById(R.id.password);
        signIn = findViewById(R.id.signIn);
        baseUrl = "https://tradingapprestapi.000webhostapp.com/";
        progressBar = findViewById(R.id.loginprogress);

        //Retrofit Initialisation
        OkHttpClient ok = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60,TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
                        .client(ok)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
        customersAPI = retrofit.create(CustomersAPI.class);

        //SignIn Button OnClick
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = user.getText().toString();
                final String pass = password.getText().toString();
                progressBar.setIndeterminate(true);
                progressBar.setVisibility(View.VISIBLE);
                Login(username , pass);
            }
        });

    }

    //Method for Retrofit to call the REST API
    private void Login(final String username , String password){

        Call<LoginJSON> log = customersAPI.loginPost(username,password);

        log.enqueue(new Callback<LoginJSON>() {
            @Override
            public void onResponse(Call<LoginJSON> call, Response<LoginJSON> response) {

                if(!response.isSuccessful()){
                    Toast.makeText(LoginActivity.this,"Code : "+response.code(),Toast.LENGTH_LONG).show();
                    return;
                }
                LoginJSON lo = response.body();
                errorBool = lo.getError();
                message = lo.getMessage();
                id = lo.getID();
                Log.i("error: ",errorBool+"\t"+message);

                if(errorBool==false){
                    //Session is stored.
                    StoreData();
                    progressBar.setIndeterminate(false);
                    Intent i = new Intent(getApplicationContext(),DashBoardActivity.class);
                    i.putExtra("id",id);
                    startActivity(i);
                    Toast.makeText(LoginActivity.this,"Welocome "+username, Toast.LENGTH_LONG).show();

                }else{
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(LoginActivity.this,""+message, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<LoginJSON> call, Throwable t) {
                Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

    }

    //Storing the boolean "TRUE" to the sharedPref file to store the session.
    private void StoreData(){
        sharedPreferences = getSharedPreferences(getString(R.string.sharedPrefFileName),Context.MODE_PRIVATE);
        sharedEditor = sharedPreferences.edit();
        sharedEditor.putBoolean(getString(R.string.login_status),true);
        sharedEditor.apply();
    }


}
