package com.arunika.arlingtonauto.controller;

import android.os.Bundle;
import android.view.View;

import com.arunika.arlingtonauto.R;

public class RevokeeActivity extends BaseMenuActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revoked_page);
    }
    public void onBackHome(View view) {
        goBackHome(); //call method in Base Menu Activity
    }

}
