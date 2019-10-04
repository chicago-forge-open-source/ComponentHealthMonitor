package com.acn.componenthealthmonitor;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import no.nordicsemi.android.thingylib.ThingySdkManager;

public class MainActivityViewModel extends ViewModel {

    private MutableLiveData<String> _deviceName = new MutableLiveData<>();
    public LiveData<String> deviceName = _deviceName;

    public MainActivityViewModel() {
        _deviceName.setValue("No Device Connected");
    }

    void connectToDevice(Context context, ThingySdkManager sdkManager, BleItem device) {
        Log.v("****", "name: device.getName: " + device.getName());
        _deviceName.setValue(device.getName());

        if (sdkManager != null) {
            BluetoothDevice bluetoothDevice = device.getDevice();
            sdkManager.connectToThingy(context, bluetoothDevice, ThingyService.class);
            sdkManager.setSelectedDevice(bluetoothDevice);
        }
    }

    void afterInitialDiscoveryCompleted(ThingySdkManager sdkManager, BluetoothDevice device) {
        sdkManager.enableButtonStateNotification(device, true);
        sdkManager.enableMotionNotifications(device, true);
    }

    public String getDeviceName() {
        return deviceName.getValue();
    }
}
