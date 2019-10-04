package com.acn.componenthealthmonitor;

import android.bluetooth.BluetoothDevice;

import no.nordicsemi.android.support.v18.scanner.ScanResult;

public class BleItem {

    private BluetoothDevice device;
    private String name;

    BleItem(ScanResult result) {
        this.device = result.getDevice();
        this.name = result.getScanRecord() != null ? result.getScanRecord().getDeviceName() : null;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public String getName() {
        return name;
    }
}
