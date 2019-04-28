package com.arunika.arlingtonauto.controller;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.arunika.arlingtonauto.R;

public class SummaryRentalActivity extends BaseMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation_summary);
        TextView confirmationField = (TextView) findViewById(R.id.confirmationNumber);
        long confirmationNumber = getIntent().getLongExtra("Reservation Number",1);
        confirmationField.setText(Long.toString(confirmationNumber));
    }

    public void onBackHome(View view) {
        goBackHome(); //call method in Base Menu Activity
    }

}
