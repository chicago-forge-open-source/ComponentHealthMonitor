package com.acn.componenthealthmonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DeviceScanActivity extends AppCompatActivity {

    private BleRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_scan);

        adapter = new BleRecyclerAdapter();
    }
}
