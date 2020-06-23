package com.example.processapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.processapp.ui.login.LoginActivity;

public class BaseActivity extends AppCompatActivity {

    SharedPreferences sharedPref;
    private String token;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);

        sharedPref = getSharedPreferences("myPref",Context.MODE_PRIVATE);
        token = sharedPref.getString("token", "no token found");
        Log.d("token",token);

        if(token != "no token found")
        {
            Intent intent = new Intent(this,NavigationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }else
        {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            finish();
            startActivity(intent);
        }
    }


}