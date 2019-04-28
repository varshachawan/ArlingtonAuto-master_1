package com.arunika.arlingtonauto.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.arunika.arlingtonauto.DAO.ReservationDAO;
import com.arunika.arlingtonauto.model.ReservationDetails;
import com.arunika.arlingtonauto.R;

import java.time.LocalDateTime;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class ViewAllRRActivity extends BaseMenuActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_rr);
        ListView listView = (ListView)findViewById(R.id.all_rr_list);
        //get list from intent
        ArrayList<ReservationDetails> allResList = (ArrayList<ReservationDetails>)
                getIntent().getSerializableExtra("allResList");
        //populate listview with it
        CustomAdapter customAdapter = new CustomAdapter(this, allResList, this);
        listView.setAdapter(customAdapter);
    }

    public class CustomAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private ArrayList<ReservationDetails> resList;
        private Activity activity;
        private class ViewHolder {
            TextView carNameTextView;
            TextView durationField;
            TextView renterUsernameTextView;
            Button viewBtn;
            Button deleteBtn;
        }
        public CustomAdapter(Context context, ArrayList<ReservationDetails> resList, Activity activity) {
            super();
            inflater = LayoutInflater.from(context);
            this.resList = resList;
            this.activity= activity;
        }

        @Override
        public int getCount() {
            if (resList != null) {
                return resList.size();
            }
            else
                return 0;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            CustomAdapter.ViewHolder holder = new CustomAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.all_rr_list_item, null);

            final ReservationDetails reservationDetails = getItem(position);

            holder.carNameTextView = (TextView) convertView.findViewById(R.id.carName);
            holder.durationField = (TextView) convertView.findViewById(R.id.duration);
            holder.renterUsernameTextView = (TextView) convertView.findViewById(R.id.renterUsername);
            holder.viewBtn = (Button) convertView.findViewById(R.id.viewBtn);
            holder.deleteBtn = (Button) convertView.findViewById(R.id.deleteBtn);

            holder.carNameTextView.setText(resList.get(position).getCarName());
            holder.durationField.setText(resList.get(position).getStartTimeAsString() +" TO "+
                    resList.get(position).getEndTimeAsString());
            holder.renterUsernameTextView.setText("User: " + resList.get(position).getRenterUsername());

            holder.viewBtn.setTag(position);
            holder.viewBtn.setOnClickListener(new View.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    //Go to linked page
                    Intent intent = new Intent();
                    intent.putExtra("selectedReservation",reservationDetails);
                    intent.setClass(getApplicationContext(),ViewRRDetailsActivity.class);
                    startActivity(intent);

                    //TBD message
                    //Toasty.success(getApplicationContext(), "View Reservation Button Clicked", Toast.LENGTH_LONG, true).show();
                }
            });
            holder.deleteBtn.setTag(position);
            holder.deleteBtn.setOnClickListener(new View.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    int deletePos = (int) v.getTag();
                    //check if res in past or not
                    LocalDateTime checkOut = resList.get(deletePos).getStartTime();
                   if(checkOut.isBefore(LocalDateTime.now())) {
                        Toasty.error(getApplicationContext(), "Cannot delete rentals in the past!", Toast.LENGTH_LONG, true).show();
                    }
                    else {
                        //delete res from DB
                        ReservationDAO.getInstance(getApplicationContext())
                                .deleteReservation(resList.get(deletePos).getId());
                        //remove res from the list
                        resList.remove(deletePos);
                        CustomAdapter.this.notifyDataSetChanged();
                        //confirmation message
                        Toasty.success(getApplicationContext(), "Successfully Deleted!", Toast.LENGTH_LONG, true).show();
                    }
                }
            });
            return convertView;
        }
    }
}
