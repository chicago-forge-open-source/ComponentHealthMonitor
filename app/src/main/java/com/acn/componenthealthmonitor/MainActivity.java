package com.acn.componenthealthmonitor;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.acn.componenthealthmonitor.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import no.nordicsemi.android.thingylib.ThingyListenerHelper;
import no.nordicsemi.android.thingylib.ThingySdkManager;

import static com.acn.componenthealthmonitor.BleRecyclerAdapter.EXTRA_BLUETOOTH;
import static com.acn.componenthealthmonitor.DeviceScanActivity.INITIAL_CONFIGURATION_RESULT;

public class MainActivity extends AppCompatActivity implements ThingySdkManager.ServiceConnectionListener {

    private MainActivityViewModel viewModel;
    private ActivityMainBinding binding;
    private ThingySdkManager thingySdkManager;
    private BluetoothThingyListener thingyListener;
    private ProgressBar componentHealthBar;
    private BleItem connectedDevice;
    private TextView deviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        binding.setViewModel(viewModel);

        componentHealthBar = findViewById(R.id.component_health_bar);
        deviceName = findViewById(R.id.header_device_name);
        thingySdkManager = ThingySdkManager.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        thingySdkManager.bindService(this, ThingyService.class);
        ThingyListenerHelper.registerThingyListener(this, thingyListener);
        setConnectOnClickListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            connectedDevice = data.getParcelableExtra(EXTRA_BLUETOOTH);
            viewModel.connectToDevice(this, thingySdkManager, connectedDevice);
        }
    }

    private void setConnectOnClickListener() {
        final FloatingActionButton connect = findViewById(R.id.connectFab);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent initialConfiguration = new Intent(MainActivity.this, DeviceScanActivity.class);
                startActivityForResult(initialConfiguration, INITIAL_CONFIGURATION_RESULT);
            }
        });
    }

    @Override
    public void onServiceConnected() {
        //TODO
    }
}
