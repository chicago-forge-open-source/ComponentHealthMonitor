package com.acn.componenthealthmonitor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class DeviceScanViewModelTest {

    @Test
    public void prepareForScanning_doesNotHaveCoarseLocation() {
        PermissionsHelper mockHelper = mock(PermissionsHelper.class);
        DeviceScanViewModel viewModel = new DeviceScanViewModel();

        viewModel.prepareForScanning(mockHelper);

        verify(mockHelper).requestCoarseLocationPermissions();
    }

}