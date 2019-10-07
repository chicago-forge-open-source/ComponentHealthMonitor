package com.acn.componenthealthmonitor;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.ViewModel;

import no.nordicsemi.android.thingylib.ThingySdkManager;

public class MainActivityViewModel extends ViewModel implements Observable {

    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();
    @Bindable
    private String deviceName = "No Device Connected";

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.remove(callback);
    }

    public String getDeviceName() {
        return deviceName;
    }

    void connectToDevice(Context context, ThingySdkManager sdkManager, BleItem device) {
        if (sdkManager != null) {
            BluetoothDevice bluetoothDevice = device.getDevice();
            deviceName = bluetoothDevice.getName();
            notifyPropertyChanged(BR.deviceName);
            sdkManager.connectToThingy(context, bluetoothDevice, ThingyService.class);
            sdkManager.setSelectedDevice(bluetoothDevice);
        }
    }

    void afterInitialDiscoveryCompleted(ThingySdkManager sdkManager, BluetoothDevice device) {
        sdkManager.enableButtonStateNotification(device, true);
        sdkManager.enableMotionNotifications(device, true);
    }

    private void notifyPropertyChanged(int fieldId) {
        callbacks.notifyCallbacks(this, fieldId, null);
    }
}
