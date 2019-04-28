package com.arunika.arlingtonauto.controller;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.arunika.arlingtonauto.model.User;
import com.arunika.arlingtonauto.R;
import com.google.gson.Gson;

/** All 3 HomeScreen Activities must extend SystemUserActivity.
 *  It contains 2 functions common to ALL 3 types of System Users.
 *  EditOwnProfile and Logout.
 *  This inheritance keeps code centralized and cleaner.
 */
public class CustomerHomeActivity extends SystemUserActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);
    }
    public void requestRental(View view) {
        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        final Gson gson = new Gson();
        User currentUser = gson.fromJson(preferences.getString("currentUser", ""), User.class);
        if(currentUser.getIsRevoked()==1) {
            startActivity(new Intent(this, RevokeeActivity.class));
        }
        else{
            startActivity(new Intent(this, RequestRentalActivity.class));
        }
    }
    public void viewMyRR(View view) {
        startActivity(new Intent(this,FindMyRRActivity.class));
    }
}