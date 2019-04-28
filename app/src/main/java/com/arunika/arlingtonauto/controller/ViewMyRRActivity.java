package com.arunika.arlingtonauto.controller;

import android.app.Activity;
import android.content.Context;
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
import com.arunika.arlingtonauto.R;
import com.arunika.arlingtonauto.model.ReservationDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

/**
 * View My RRs
 * Cancel My RR
 */
public class ViewMyRRActivity extends BaseMenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_rr);
        ListView listView = (ListView)findViewById(R.id.myRRlist);
        //get list from intent
        ArrayList<ReservationDetails> myResList = (ArrayList<ReservationDetails>)
                getIntent().getSerializableExtra("myResList");
        //populate listview with it
        CustomAdapter customAdapter = new CustomAdapter(this, myResList, this);
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
            TextView durationField;
            TextView extrasField;
            TextView confirmationField;
            Button cancelBtn;
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            CustomAdapter.ViewHolder holder = new CustomAdapter.ViewHolder();
            convertView = inflater.inflate(R.layout.my_rr_list_item, null);

            final ReservationDetails reservationDetails = getItem(position);

            holder.carNameTextView = (TextView) convertView.findViewById(R.id.carName);
            holder.totalPriceTextView = (TextView) convertView.findViewById(R.id.totalPrice);
            holder.capacityTextView = (TextView) convertView.findViewById(R.id.capacity);
            holder.durationField = (TextView) convertView.findViewById(R.id.duration);
            holder.extrasField = (TextView) convertView.findViewById(R.id.extras);
            holder.confirmationField = (TextView) convertView.findViewById(R.id.confirmationNumber);
            holder.cancelBtn = (Button) convertView.findViewById(R.id.cancelBtn);

            holder.carNameTextView.setText(resList.get(position).getCarName());
            holder.totalPriceTextView.setText("$"+resList.get(position).getTotalPrice());
            holder.capacityTextView.setText("Parking Access Type: Access ");
            holder.durationField.setText(resList.get(position).getStartTimeAsString() +" TO "+
                                         resList.get(position).getEndTimeAsString());
            holder.extrasField.setText("Options: "+resList.get(position).getAdditionalFeatures());
            holder.confirmationField.setText("Reservation #: " + resList.get(position).getId());

            holder.cancelBtn.setTag(position);
            holder.cancelBtn.setOnClickListener(new View.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View v) {
                    int cancelPos = (int) v.getTag();
                    //check if res in past or not
                    LocalDateTime checkOut = resList.get(cancelPos).getStartTime();
                    if(checkOut.isBefore(LocalDateTime.now())) {
                        Toasty.error(getApplicationContext(), "Cannot cancel reservations in the past!", Toast.LENGTH_LONG, true).show();
                    }
                    else {
                        //delete res from DB
                        ReservationDAO.getInstance(getApplicationContext())
                                .deleteReservation(resList.get(cancelPos).getId());
                        //remove res from the list
                        resList.remove(cancelPos);
                        CustomAdapter.this.notifyDataSetChanged();
                        //confirmation message
                        Toasty.success(getApplicationContext(), "Successfully Cancelled!", Toast.LENGTH_LONG, true).show();
                    }
                }
            });
            return convertView;
        }
    }

}
