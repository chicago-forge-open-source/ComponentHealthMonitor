package com.acn.componenthealthmonitor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.acn.componenthealthmonitor.databinding.ActivityDeviceScanBinding;

public class DeviceScanActivity extends AppCompatActivity {

    private ActivityDeviceScanBinding binding;
    private DeviceScanViewModel viewModel;
    private BleRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_device_scan);
        viewModel = ViewModelProviders.of(this).get(DeviceScanViewModel.class);

        adapter = new BleRecyclerAdapter();
    }
}
