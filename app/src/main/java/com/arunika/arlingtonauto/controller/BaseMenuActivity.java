package com.arunika.arlingtonauto.controller;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.arunika.arlingtonauto.R;
import com.arunika.arlingtonauto.model.User;
import com.google.gson.Gson;

import es.dmoral.toasty.Toasty;
/** PLEASE NOTE:
 *  This class must be extended from all other activities you make
 *  (except for homescreens and mainscreen and registerScreen)
 *  It creates the three-dot nav menu in the action bar for Home and Logout links
 */
public class BaseMenuActivity extends AppCompatActivity {
    private SharedPreferences preferences;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        preferences = this.getSharedPreferences("PREFS", 0);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home: goBackHome();
                            return true;
            case R.id.logout: logout();
                              return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void goBackHome() {
        User currentUser;
        if (preferences.contains("currentUser")) {
            /**
             * Retrieve the active currentUser from preferences
             * which was stored at login time.
             * Must use GSON since it is an object.
             */
            final Gson gson = new Gson();
            currentUser = gson.fromJson(preferences.getString("currentUser", ""), User.class);
            /**
             * get role of currentUser so that
             * we can redirect to correct homescreen
             */
            String role = currentUser.getRole();
            if (role.equalsIgnoreCase("User"))
                startActivity(new Intent(this,CustomerHomeActivity.class));
            else if (role.equalsIgnoreCase("Manager"))
                startActivity(new Intent(this,ManagerHomeActivity.class));
            else
                startActivity(new Intent(this,AdminHomeActivity.class));
        }
    }
    private void logout() {
        //REMOVE all stored session variables
        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
        finish();
        //display success msg and redirect to main screen (login)
        Toasty.success(this,"Successfully logged out!", Toast.LENGTH_SHORT, true).show();
        startActivity(new Intent(this, MainActivity.class));
    }
}
