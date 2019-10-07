package com.acn.componenthealthmonitor;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.acn.componenthealthmonitor.databinding.ActivityMainBinding;
import com.acn.componenthealthmonitor.deviceScan.DeviceScanActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import no.nordicsemi.android.thingylib.ThingyListenerHelper;
import no.nordicsemi.android.thingylib.ThingySdkManager;

import static com.acn.componenthealthmonitor.BleRecyclerAdapter.EXTRA_BLUETOOTH;
import static com.acn.componenthealthmonitor.deviceScan.DeviceScanActivity.INITIAL_CONFIGURATION_RESULT;

public class MainActivity extends AppCompatActivity implements ThingySdkManager.ServiceConnectionListener {

    private MainActivityViewModel viewModel;
    private ActivityMainBinding binding;
    private ThingySdkManager thingySdkManager;
    private BluetoothThingyListener thingyListener;
    private ProgressBar componentHealthBar;
    private BleItem connectedDevice;
    private LineChartManager chartManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);

        configureCharts();
        binding.setViewModel(viewModel);
        binding.setLifecycleOwner(this);

        componentHealthBar = findViewById(R.id.component_health_bar);
        thingySdkManager = ThingySdkManager.getInstance();
        thingyListener = new BluetoothThingyListener(viewModel, thingySdkManager, chartManager);
        setConnectOnClickListener();
    }

    @Override
    protected void onStart() {
        super.onStart();

        thingySdkManager.bindService(this, ThingyService.class);
        ThingyListenerHelper.registerThingyListener(this, thingyListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        thingySdkManager.unbindService(this);
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
        if (connectedDevice != null) {
            BluetoothDevice device = connectedDevice.getDevice();
            if (thingySdkManager.hasInitialServiceDiscoverCompleted(device)) {
                viewModel.afterInitialDiscoveryCompleted(thingySdkManager, device);
            }
        }
    }

    private void configureCharts() {
        LineChart gravityChart = findViewById(R.id.line_chart_gravity_vector);
        LineChart accelerationChart = findViewById(R.id.line_chart_acceleration_vector);

        chartManager = new LineChartManager(gravityChart, accelerationChart);
        chartManager.prepareVectorChart(gravityChart, -10f, 10f, "Gravity Chart");
        chartManager.prepareVectorChart(accelerationChart, -5f, 5f, "Acceleration Chart");
    }
}
