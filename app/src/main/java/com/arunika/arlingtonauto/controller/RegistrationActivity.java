package com.arunika.arlingtonauto.controller;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import com.arunika.arlingtonauto.DAO.UserDAO;
import com.arunika.arlingtonauto.R;
import com.arunika.arlingtonauto.model.User;
import es.dmoral.toasty.Toasty;

public class RegistrationActivity extends AppCompatActivity {

    private EditText firstNameField;
    private EditText lastNameField;
    private EditText utaIdField;
    private EditText usernameField;
    private EditText passwordField;
    private EditText emailField;
    private CheckBox aacMembershipCheckBox;
    private RadioGroup roleRadioGroup;
    private UserDAO UserDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        initViews(); //initialize all text field objects
        this.UserDAO = UserDAO.getInstance(this); //singleton
    }

    private void initViews() {
        this.firstNameField = (EditText) findViewById(R.id.firstName);
        this.lastNameField = (EditText) findViewById(R.id.lastName);
        this.utaIdField = (EditText) findViewById(R.id.utaId);
        this.usernameField = (EditText) findViewById(R.id.username);
        this.passwordField = (EditText) findViewById(R.id.password);
        this.emailField = (EditText) findViewById(R.id.email);
        this.aacMembershipCheckBox = (CheckBox) findViewById(R.id.aacMembership);
        this.roleRadioGroup = (RadioGroup) findViewById(R.id.role);
    }

    public void onRegister(View view) {
        User user = new User();
        //set user attributes with entered text field data
        user.setFirstName(firstNameField.getText().toString());
        user.setLastName(lastNameField.getText().toString());
        user.setUtaId(utaIdField.getText().toString());
        user.setUsername(usernameField.getText().toString());
        user.setPassword(passwordField.getText().toString());
        user.setEmail(emailField.getText().toString());
        if(aacMembershipCheckBox.isChecked())
            user.setAacMembership(1);
        else
            user.setAacMembership(0);
        String role = ((RadioButton)findViewById(roleRadioGroup.getCheckedRadioButtonId())).getText().toString();
        user.setRole(role);
        user.setIsRevoked(0);
        //insert into DB
        long insertId = UserDAO.insertUser(user);

        //display success message and redirect to Login screen (activity_main)
        if(insertId>0) {
            Toasty.success(this, "Successfully registered!", Toast.LENGTH_LONG, true).show();
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}



