package com.acn.componenthealthmonitor;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import androidx.lifecycle.ViewModel;

import no.nordicsemi.android.thingylib.ThingySdkManager;

public class MainActivityViewModel extends ViewModel {

    private String deviceName = "No Device Connected";

    void connectToDevice(Context context, ThingySdkManager sdkManager, BleItem device) {
        deviceName = device.getName();

        if (sdkManager != null) {
            BluetoothDevice bluetoothDevice = device.getDevice();
            sdkManager.connectToThingy(context, bluetoothDevice, ThingyService.class);
            sdkManager.setSelectedDevice(bluetoothDevice);
        }
    }

    public String getDeviceName() {
        return deviceName;
    }

}
