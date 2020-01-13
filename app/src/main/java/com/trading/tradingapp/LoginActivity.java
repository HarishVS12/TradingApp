package com.trading.tradingapp;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.trading.tradingapp.Retrofit.CustomersAPI;
import com.trading.tradingapp.Retrofit.LoginJSON;

public class LoginActivity extends AppCompatActivity {

    //Declarations
    private EditText user,password;
    private Button signIn;
    private Boolean errorBool;
    private CustomersAPI customersAPI;
    private Retrofit retrofit;
    private String baseUrl , message;
    private ProgressBar progressBar;

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
        retrofit = new Retrofit.Builder()
                        .baseUrl(baseUrl)
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
    private void Login(String username , String password){

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
                String user = lo.getUser();
                Log.i("error: ",errorBool+"\t"+message);

                if(errorBool==false){
                    progressBar.setIndeterminate(false);
                    startActivity(new Intent(LoginActivity.this,DashBoardActivity.class));
                    Toast.makeText(LoginActivity.this,"Welocome "+user, Toast.LENGTH_LONG).show();
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


}
