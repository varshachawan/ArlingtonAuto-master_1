package com.arunika.arlingtonauto.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.arunika.arlingtonauto.DAO.ReservationDAO;
import com.arunika.arlingtonauto.model.ReservationDetails;
import com.arunika.arlingtonauto.model.User;
import com.arunika.arlingtonauto.R;
import com.google.gson.Gson;

import java.io.Serializable;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class FindMyRRActivity extends BaseMenuActivity {

    private EditText startDateField;
    private EditText startTimeField;
    private EditText endDateField;
    private EditText endTimeField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_rr_form);
        initViews();
    }

    private void initViews() {
        this.startDateField = (EditText) findViewById(R.id.startDate);
        this.startTimeField = (EditText) findViewById(R.id.startTime);
        this.endDateField = (EditText) findViewById(R.id.endDate);
        this.endTimeField = (EditText) findViewById(R.id.endTime);
    }

    public void onSearchRR(View view) {
        //get all form field data
        String startTimeAsString = startDateField.getText().toString() + " "
                + startTimeField.getText().toString();
        String endTimeAsString = endDateField.getText().toString() + " "
                + endTimeField.getText().toString();
        //get current user
        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        User currentUser = new Gson().fromJson(preferences.getString("currentUser", ""), User.class);

        //get list of customer's RRs in given time range
        ArrayList<ReservationDetails> myResList = ReservationDAO.getInstance(this)
                .getCustomerReservations(startTimeAsString, endTimeAsString, currentUser.getId());
        if(myResList.size()>0) {
            //send list to next activity
            Intent intent = new Intent();
            intent.putExtra("myResList",(Serializable) myResList);
            intent.setClass(this,ViewMyRRActivity.class);
            startActivity(intent);
        }
        else {
            Toasty.info(this, "No reservations found", Toast.LENGTH_LONG, true).show();
        }

    }

}
