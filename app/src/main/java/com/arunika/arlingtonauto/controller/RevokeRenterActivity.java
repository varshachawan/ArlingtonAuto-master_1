package com.arunika.arlingtonauto.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.arunika.arlingtonauto.DAO.UserDAO;
import com.arunika.arlingtonauto.R;
import com.arunika.arlingtonauto.model.User;
import java.util.ArrayList;
import es.dmoral.toasty.Toasty;

/**
 *  All activities (except Home,Main,Register) must extend BaseMenuActivity
 *  This creates three-dot nav menu in action bar for Home & Logout links
 */
public class RevokeRenterActivity extends BaseMenuActivity {
    private EditText usernameField;
    private ListView revokeListView;
    private UserDAO UserDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revoke_renter);
        initViews(); //initialize all view objects
        this.UserDAO = UserDAO.getInstance(this);
        displayRevokeList();
    }
    private void initViews() {
        this.usernameField = (EditText) findViewById(R.id.username);
        this.revokeListView = (ListView) findViewById(R.id.revokeList);
    }

    //revoke button pressed
    public void onRevoke(View view) {
        String username = usernameField.getText().toString().trim();
        User targetUser = UserDAO.getUser(username);
        if(targetUser == null) {
            Toasty.error(this, "This username does not exist!", Toast.LENGTH_LONG, true).show();
        }
        else {
            /**
             * Revoke user, save to DB
             * display updated revoke list
             */
            targetUser.setIsRevoked(1);
            UserDAO.updateUser(targetUser);
            // Toasty.success(this, "Successfully revoked!", Toast.LENGTH_LONG, true).show();
            displayRevokeList();
        }
    }
    public void onRevoke2(View view) {
        String username = usernameField.getText().toString().trim();
        User targetUser = UserDAO.getUser(username);
        if(targetUser == null) {
            Toasty.error(this, "This username does not exist!", Toast.LENGTH_LONG, true).show();
        }
        else {
            /**
             * Revoke user, save to DB
             * display updated revoke list
             */
            targetUser.setIsRevoked(1);
            UserDAO.updateUser(targetUser);
            Toasty.success(this, "Successfully revoked!", Toast.LENGTH_LONG, true).show();
            displayRevokeList1();
        }
    }

    private void displayRevokeList() {
        /** DISPLAY LIST OF REVOKEES
         * Get arraylist of all revoked usernames
         * Set list to an adapter
         * Set adapter to listView (revoked list) to display
         */
        ArrayList<String> allRevokedUsernames = UserDAO.getAllRevokedUsernames();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                R.layout.revoked_list_item, /*name of xml file in res/layout*/
                R.id.revokeListItem,
                allRevokedUsernames);
        revokeListView.setAdapter(arrayAdapter);
    }
    private void displayRevokeList1() {
        /** DISPLAY LIST OF REVOKEES
         * Get arraylist of all revoked usernames
         * Set list to an adapter
         * Set adapter to listView (revoked list) to display
         */
        ArrayList<String> allRevokedUsernames = UserDAO.getAllRevokedUsernames();
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this,
                R.layout.revoked_list_item, /*name of xml file in res/layout*/
                R.id.revokeListItem,
                allRevokedUsernames);
        revokeListView.setAdapter(arrayAdapter);
    }
}
