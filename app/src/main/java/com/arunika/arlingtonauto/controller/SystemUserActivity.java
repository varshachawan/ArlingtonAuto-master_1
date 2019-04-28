package com.arunika.arlingtonauto.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class SystemUserActivity extends AppCompatActivity {

    public void editOwnProfile(View view) {
        startActivity(new Intent(this,UpdateProfileActivity.class));
    }
    public void logout(View view) {
        //REMOVE all stored session variables
        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        finish();
        //display success msg and redirect to main screen (login)
        Toasty.success(this,"Successfully logged out!", Toast.LENGTH_SHORT, true).show();
        startActivity(new Intent(this, MainActivity.class));
    }

}
