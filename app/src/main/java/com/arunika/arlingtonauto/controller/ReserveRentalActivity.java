package com.arunika.arlingtonauto.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.arunika.arlingtonauto.model.ReservationDetails;
import com.arunika.arlingtonauto.R;

import java.util.ArrayList;

public class ReserveRentalActivity extends BaseMenuActivity {

    ArrayList<ReservationDetails> availableResDetailsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_rental_search_results);
        ListView listView = (ListView)findViewById(R.id.requestList);
        //get list from intent
        availableResDetailsList = (ArrayList<ReservationDetails>)
                getIntent().getSerializableExtra("availableResDetailsList");
        //populate listview with it
        CustomAdapter customAdapter = new CustomAdapter(this, availableResDetailsList, this);
        listView.setAdapter(customAdapter);
    }

    public class CustomAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<ReservationDetails> resList;
        private Activity activity;
        private class ViewHolder {
            TextView carNameTextView;
            TextView totalPriceTextView;
            TextView capacityTextView;
            Button reserveBtn;
        }
        public CustomAdapter(Context context, ArrayList<ReservationDetails> resList, Activity activity) {
            super();
            inflater = LayoutInflater.from(context);
            this.resList = resList;
            this.activity= activity;
        }
        @Override
        public int getCount() {
            return resList.size();
        }
        @Override
        public ReservationDetails getItem(int position) {
            return resList.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CustomAdapter.ViewHolder holder = new CustomAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.request_list_item, null);

            final ReservationDetails reservationDetails = getItem(position);

            holder.carNameTextView = (TextView) convertView.findViewById(R.id.carName);
            holder.totalPriceTextView = (TextView) convertView.findViewById(R.id.totalPrice);
            holder.capacityTextView = (TextView) convertView.findViewById(R.id.capacity);
            holder.reserveBtn = (Button) convertView.findViewById(R.id.reserveBtn);

            holder.carNameTextView.setText(resList.get(position).getCarName());
            holder.totalPriceTextView.setText("$"+resList.get(position).getTotalPrice());
            holder.capacityTextView.setText("Parking Access Type: Access");

            holder.reserveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("selectedReservation",reservationDetails);
                    intent.setClass(getApplicationContext(),ConfirmRentalActivity.class);
                    startActivity(intent);
                }
            });
            return convertView;
        }
    }
}