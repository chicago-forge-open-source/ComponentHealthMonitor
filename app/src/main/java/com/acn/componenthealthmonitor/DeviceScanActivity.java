package com.acn.componenthealthmonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

public class DeviceScanActivity extends AppCompatActivity {

    private DeviceScanViewModel viewModel;

    private BleRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_device_scan);

        viewModel = ViewModelProviders.of(this).get(DeviceScanViewModel.class);

        adapter = new BleRecyclerAdapter();
    }
}
