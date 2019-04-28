package com.arunika.arlingtonauto.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.arunika.arlingtonauto.DAO.CarDAO;
import com.arunika.arlingtonauto.model.Car;
import com.arunika.arlingtonauto.model.ReservationDetails;
import com.arunika.arlingtonauto.R;

import java.io.Serializable;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class RequestRentalActivity extends BaseMenuActivity {

    private EditText checkOutDateField;
    private EditText checkOutTimeField;
    private EditText returnDateField;
    private EditText returnTimeField;
    private EditText capacityField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_rental_form);
        initViews();
    }
    private void initViews() {
        this.checkOutDateField = (EditText) findViewById(R.id.username);
        this.checkOutTimeField = (EditText) findViewById(R.id.checkOutTime);
        this.returnDateField = (EditText) findViewById(R.id.returnDate);
        this.returnTimeField = (EditText) findViewById(R.id.returnTime);
        this.capacityField = (EditText) findViewById(R.id.capacity);
    }
    public void onRequestSearch(View view) {
        //get all form field data
        String startTimeAsString = checkOutDateField.getText().toString() + " "
                + checkOutTimeField.getText().toString();
        String endTimeAsString = returnDateField.getText().toString() + " "
                + returnTimeField.getText().toString();
        int capacity = Integer.parseInt(capacityField.getText().toString());


        //get list of available cars in given time range
        ArrayList<Car> availableCars = CarDAO.getInstance(this).getAvailableCarsForCustomer(
                startTimeAsString, endTimeAsString, capacity);

        ArrayList<ReservationDetails> availableRentalList = new ArrayList<>();
        //for each available car found, calculate price and make preliminary reservation objects
        for(Car car : availableCars) {
            ReservationDetails reservationDetails = new ReservationDetails();
            //calculate price (without add-ons,tax,discount)
            reservationDetails.setCarId(car.getId());
            reservationDetails.setCarName(car.getName());
            reservationDetails.setCapacity(car.getCapacity());
            reservationDetails.setStartAndEndTimes(startTimeAsString,endTimeAsString);
            //calculate initial price (without add-ons,tax,discount)
            double initialTotalPrice = reservationDetails.calculateTotalPrice(car);
            reservationDetails.setTotalPrice(initialTotalPrice);
            //add object to list
            availableRentalList.add(reservationDetails);
        }
        if(availableRentalList.size()>0) {
            //send list to next activity
            Intent intent = new Intent();
            intent.putExtra("availableResDetailsList",(Serializable) availableRentalList);
            intent.setClass(this,ReserveRentalActivity.class);
            startActivity(intent);
        }
        else {
            Toasty.info(this, "No available cars found", Toast.LENGTH_LONG, true).show();
        }

    }
}
