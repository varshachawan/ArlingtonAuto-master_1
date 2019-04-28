package com.arunika.arlingtonauto.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.arunika.arlingtonauto.DAO.CarDAO;
import com.arunika.arlingtonauto.model.Car;
import com.arunika.arlingtonauto.R;

import java.io.Serializable;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class FindAllAvailableCarsActivity extends BaseMenuActivity {
    private EditText startDateField;
    private EditText startTimeField;
    private EditText endDateField;
    private EditText endTimeField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_available_cars_form);
        initViews();
    }

    private void initViews() {
        this.startDateField = (EditText) findViewById(R.id.startDate);
        this.startTimeField = (EditText) findViewById(R.id.startTime);
        this.endDateField = (EditText) findViewById(R.id.endDate);
        this.endTimeField = (EditText) findViewById(R.id.endTime);
    }

    public void onSearchAllAvailableCars(View view) {
        //get all form field data
        String startTimeAsString = startDateField.getText().toString() + " "
                + startTimeField.getText().toString();
        String endTimeAsString = endDateField.getText().toString() + " "
                + endTimeField.getText().toString();

        //get list of Available cars in given time range
        ArrayList<Car> allCarList = CarDAO.getInstance(this)
                .getAvailableCarsForManager(startTimeAsString, endTimeAsString);

        if (allCarList.size() > 0) {
            //send list to next activity
            Intent intent = new Intent();
            intent.putExtra("allCarList", (Serializable) allCarList);
            intent.setClass(this, ViewAllAvailableCarsActivity.class);
            startActivity(intent);
        } else {
            Toasty.info(this, "No available cars found", Toast.LENGTH_LONG, true).show();
        }
    }
}