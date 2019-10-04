package com.acn.componenthealthmonitor;

import android.os.ParcelUuid;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat;
import no.nordicsemi.android.support.v18.scanner.ScanFilter;
import no.nordicsemi.android.support.v18.scanner.ScanSettings;
import no.nordicsemi.android.thingylib.utils.ThingyUtils;

public class DeviceScanViewModel extends ViewModel {

    private MutableLiveData<List<BleItem>> _items = new MutableLiveData<>();
    public LiveData<List<BleItem>> items = _items;

    public DeviceScanViewModel() {
        _items.setValue(Collections.<BleItem>emptyList());
    }

    boolean prepareForScanning(PermissionsHelper helper) {
        if (helper.hasCoarseLocationPermission()) {
            if (helper.isLocationEnabled()) {
                if (helper.isBleEnabled()) {
                    return true;
                } else {
                    helper.enableBle();
                }
            } else {
                helper.enableLocation();
            }
        } else {
            helper.requestCoarseLocationPermissions();
        }

        return false;
    }
}
