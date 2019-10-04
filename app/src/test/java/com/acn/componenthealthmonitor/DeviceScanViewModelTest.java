package com.acn.componenthealthmonitor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class DeviceScanViewModelTest {

    private PermissionsHelper mockHelper = mock(PermissionsHelper.class);
    private DeviceScanViewModel viewModel = new DeviceScanViewModel();

    @Test
    public void prepareForScanning_doesNotHaveCoarseLocation() {
        viewModel.prepareForScanning(mockHelper);

        verify(mockHelper).requestCoarseLocationPermissions();
    }

    @Test
    public void prepareForScanning_doesNotHaveLocationTurnedOn() {
        when(mockHelper.hasCoarseLocationPermission()).thenReturn(true);
        viewModel.prepareForScanning(mockHelper);

        verify(mockHelper).enableLocation();
    }

    @Test
    public void prepareForScanning_doesNotHaveBluetoothTurnedOn() {
        when(mockHelper.hasCoarseLocationPermission()).thenReturn(true);
        when(mockHelper.isLocationEnabled()).thenReturn(true);

        viewModel.prepareForScanning(mockHelper);

        verify(mockHelper).enableBle();
    }
}