package com.acn.componenthealthmonitor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.Collections;
import java.util.List;

public class DeviceScanViewModel extends ViewModel {

    private MutableLiveData<List<BleItem>> _items = new MutableLiveData<>();
    public LiveData<List<BleItem>> items = _items;

    public DeviceScanViewModel() {
        _items.setValue(Collections.<BleItem>emptyList());
    }
}
