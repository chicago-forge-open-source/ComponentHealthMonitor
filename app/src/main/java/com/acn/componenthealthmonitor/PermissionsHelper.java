package com.acn.componenthealthmonitor;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;

import static android.content.Context.BLUETOOTH_SERVICE;

public class PermissionsHelper {

    private static final int ACCESS_COARSE_LOCATION_CODE = 1;
    private static final int BLE_REQUEST_CODE = 2;
    private Activity activity;

    public PermissionsHelper(Activity activity) {

        this.activity = activity;
    }


    public void enableBle() {
        final Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        activity.startActivityForResult(enableIntent, BLE_REQUEST_CODE);
    }
}
