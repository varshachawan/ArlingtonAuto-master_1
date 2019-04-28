package com.arunika.arlingtonauto.controller;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.arunika.arlingtonauto.model.User;
import com.arunika.arlingtonauto.R;

import es.dmoral.toasty.Toasty;

public class EditAnotherUserActivity extends BaseMenuActivity {

    private EditText firstNameField;
    private EditText lastNameField;
    private EditText utaIdField;
    private EditText usernameField;
    private EditText passwordField;
    private EditText emailField;
    private CheckBox aacMembershipCheckBox;
    private EditText role;
    private com.arunika.arlingtonauto.DAO.UserDAO UserDAO;
    private User otherUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_another_profile);
        initViews();
        UserDAO = UserDAO.getInstance(this);
        //get user to be edited from previous activity
        Intent intent = getIntent();
        otherUser = (User) intent.getSerializableExtra("otherUser");
        displayProfile(otherUser);
    }

    private void initViews() {
        this.firstNameField = (EditText) findViewById(R.id.firstName);
        this.lastNameField = (EditText) findViewById(R.id.lastName);
        this.utaIdField = (EditText) findViewById(R.id.utaId);
        this.usernameField = (EditText) findViewById(R.id.username);
        this.usernameField.setEnabled(false); //cant edit username
        this.passwordField = (EditText) findViewById(R.id.password);
        this.emailField = (EditText) findViewById(R.id.email);
        this.aacMembershipCheckBox = (CheckBox) findViewById(R.id.aacMembership);
        this.role = (EditText) findViewById(R.id.role);
        this.role.setEnabled(true); //cant edit role
    }

    //populates form with user data
    private void displayProfile(User user) {
        this.firstNameField.setText(user.getFirstName());
        this.lastNameField.setText(user.getLastName());
        this.utaIdField.setText(user.getUtaId());
        this.usernameField.setText(user.getUsername());
        this.passwordField.setText(user.getPassword());
        this.emailField.setText(user.getEmail());
        this.role.setText(user.getRole());
        if(user.getAacMembership()==1)
            this.aacMembershipCheckBox.setChecked(true);
        else
            this.aacMembershipCheckBox.setChecked(false);
    }

    //when user presses update button
    public void onUpdate(View view) {
        User updatedUser = new User();
        updatedUser.setFirstName(firstNameField.getText().toString());
        updatedUser.setLastName(lastNameField.getText().toString());
        updatedUser.setUtaId(utaIdField.getText().toString());
        updatedUser.setUsername(usernameField.getText().toString());
        updatedUser.setPassword(passwordField.getText().toString());
        updatedUser.setEmail(emailField.getText().toString());
        if(aacMembershipCheckBox.isChecked())
            updatedUser.setAacMembership(1);
        else
            updatedUser.setAacMembership(0);
        updatedUser.setRole(role.getText().toString());

        //saved updates to DB
        UserDAO.updateUser(updatedUser);

        //display confirmation
        Toasty.success(this, "Profile Updated Successfully!", Toast.LENGTH_LONG, true).show();
    }
}