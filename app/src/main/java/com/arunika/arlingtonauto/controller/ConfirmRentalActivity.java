package com.arunika.arlingtonauto.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.arunika.arlingtonauto.DAO.CarDAO;
import com.arunika.arlingtonauto.DAO.ReservationDAO;
import com.arunika.arlingtonauto.model.Car;
import com.arunika.arlingtonauto.model.Reservation;
import com.arunika.arlingtonauto.model.ReservationDetails;
import com.arunika.arlingtonauto.model.User;
import com.arunika.arlingtonauto.R;
import com.google.gson.Gson;

public class ConfirmRentalActivity extends  BaseMenuActivity {

    private TextView carNameField;
    private TextView capacityField;
    private TextView checkOutField;
    private TextView returnField;
    private TextView gpsField;
    private TextView onstarField;
    private TextView siriusField;
    private CheckBox gpsBox;
    private CheckBox onstarBox;
    private CheckBox siriusBox;
    private TextView priceField;

    private ReservationDetails selectedReservation;
    private Car selectedCar;
    private User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_rental);
        //get selected reservation from intent
        selectedReservation = (ReservationDetails) getIntent().getSerializableExtra("selectedReservation");
        selectedCar = CarDAO.getInstance(this).getCar(selectedReservation.getCarId());
        SharedPreferences preferences = getSharedPreferences("PREFS",0);
        if (preferences.contains("currentUser")) {
            final Gson gson = new Gson();
            currentUser = gson.fromJson(preferences.getString("currentUser", ""), User.class);
        }
        //no add-ons initially
        selectedReservation.setHasGps(0);
        selectedReservation.setHasOnstar(0);
        selectedReservation.setHasSirius(0);

        initViews();
        updateTotalPrice();
        //populate form with data
        fillViews();
    }

    private void initViews() {
        this.carNameField = (TextView) findViewById(R.id.carName);
        this.capacityField = (TextView) findViewById(R.id.capacity);
        this.checkOutField = (TextView) findViewById(R.id.checkOut);
        this.returnField = (TextView) findViewById(R.id.returnBy);
        this.gpsField = (TextView) findViewById(R.id.gpsRate);
        this.onstarField = (TextView) findViewById(R.id.onstarRate);
        this.siriusField = (TextView) findViewById(R.id.siriusRate);
        this.gpsBox = (CheckBox) findViewById(R.id.gpsBox);
        this.onstarBox = (CheckBox) findViewById(R.id.onstarBox);
        this.siriusBox = (CheckBox) findViewById(R.id.siriusBox);
        this.priceField = (TextView) findViewById(R.id.totalPrice);
    }

    private void fillViews() {
        this.carNameField.setText(selectedCar.getName());
        this.capacityField.setText(Integer.toString(selectedCar.getCapacity()));
        this.checkOutField.setText(selectedReservation.getStartTimeAsString());
        this.returnField.setText(selectedReservation.getEndTimeAsString());
        this.gpsField.setText("$"+selectedCar.getDailyGps()+"/day");
        this.onstarField.setText("$"+selectedCar.getDailyOnstar()+"/day");
        this.siriusField.setText("$"+selectedCar.getDailySirius()+"/day");
        this.priceField.setText("$" + selectedReservation.getTotalPrice());
    }

    public void onCheckBoxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.gpsBox:
                if(checked)
                  selectedReservation.setHasGps(1);
                else
                  selectedReservation.setHasGps(0);
                break;
            case R.id.onstarBox:
                if(checked)
                    selectedReservation.setHasOnstar(1);
                else
                    selectedReservation.setHasOnstar(0);
                break;
            case R.id.siriusBox:
                if(checked)
                    selectedReservation.setHasSirius(1);
                else
                    selectedReservation.setHasSirius(0);
                break;
        }
        updateTotalPrice();
    }

    public void onConfirm(View view) {
        Reservation finalReservation = new Reservation();
        //set reservation object
        finalReservation.setUserId(currentUser.getId());
        finalReservation.setCarId(selectedCar.getId());
        finalReservation.setStartAndEndTimes(
                selectedReservation.getStartTimeAsString(),
                selectedReservation.getEndTimeAsString());
        finalReservation.setHasGps(selectedReservation.getHasGps());
        finalReservation.setHasOnstar(selectedReservation.getHasOnstar());
        finalReservation.setHasSirius(selectedReservation.getHasSirius());
        finalReservation.setTotalPrice(selectedReservation.getTotalPrice());
        long id = ReservationDAO.getInstance(this).insertReservation(finalReservation);
        if(id>0) {
            Intent intent = new Intent();
            intent.putExtra("Reservation Number",id);
            intent.setClass(this,SummaryRentalActivity.class);
            startActivity(intent);
        }
    }

    private void updateTotalPrice() {
        int applyDiscount = currentUser.getAacMembership();
        //re-calculate total price to include tax, discount, add-ons
        double finalTotalPrice = selectedReservation.calculateFinalPrice(selectedCar,applyDiscount);
        selectedReservation.setTotalPrice(finalTotalPrice);
        this.priceField.setText("$" + selectedReservation.getTotalPrice());
    }
}
