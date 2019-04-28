package com.arunika.arlingtonauto.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.arunika.arlingtonauto.DAO.UserDAO;
import com.arunika.arlingtonauto.R;
import com.arunika.arlingtonauto.model.User;
import es.dmoral.toasty.Toasty;

public class FindUserProfileActivity extends BaseMenuActivity {

    private EditText usernameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user_profile);
        initViews();
    }

    private void initViews() {
        this.usernameField = (EditText) findViewById(R.id.username);
    }

    public void onSearch(View view) {
        String username = usernameField.getText().toString().trim();
        User otherUser = UserDAO.getInstance(this).getUser(username);
        if(otherUser == null) {
            Toasty.error(this, "This username does not exist!", Toast.LENGTH_LONG, true).show();
        }
        else {
            Intent intent = new Intent();
            intent.putExtra("otherUser",otherUser);
            intent.setClass(this,EditAnotherUserActivity.class);
            startActivity(intent);
        }
    }
}
