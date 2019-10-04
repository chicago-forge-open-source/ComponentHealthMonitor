package com.acn.componenthealthmonitor;

import android.bluetooth.BluetoothDevice;
import android.content.Context;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import no.nordicsemi.android.thingylib.ThingySdkManager;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class MainActivityViewModelTest {

    private MainActivityViewModel viewModel = new MainActivityViewModel();
    private BleItem bleItem;

    @Before
    public void setUp() throws Exception {
        bleItem = new BleItem();
        bleItem.setName("Test");
    }

    @Test
    public void connectToDevice_setsDeviceName() {
        BleItem bleItem = new BleItem();
        bleItem.setName("Test");
        viewModel.connectToDevice(null, null, bleItem);

        assertEquals(bleItem.getName(), viewModel.getDeviceName());
    }

    @Test
    public void connectToDevice_connectsToThingy() {
        Context mockContext = mock(Context.class);
        ThingySdkManager mockThingySdkManager = mock(ThingySdkManager.class);

        viewModel.connectToDevice(mockContext, mockThingySdkManager, bleItem);

        verify(mockThingySdkManager).connectToThingy(eq(mockContext), (BluetoothDevice) any(), eq(ThingyService.class));
    }

    @Test
    public void connectToDevice_setsSelectedDevice() {
        Context mockContext = mock(Context.class);
        ThingySdkManager mockThingySdkManager = mock(ThingySdkManager.class);

        viewModel.connectToDevice(mockContext, mockThingySdkManager, bleItem);

        verify(mockThingySdkManager).setSelectedDevice((BluetoothDevice) any());
    }

    @Test
    public void afterInitialDiscoveryCompleted_enablesMotionNotifications() {
        ThingySdkManager mockThingySdkManager = mock(ThingySdkManager.class);

        viewModel.afterInitialDiscoveryCompleted(mockThingySdkManager, bleItem);

        verify(mockThingySdkManager).enableMotionNotifications((BluetoothDevice) any(), eq(true));
    }

    @Test
    public void afterInitialDiscoveryCompleted_enablesButtonStateNotification() {
        ThingySdkManager mockThingySdkManager = mock(ThingySdkManager.class);

        viewModel.afterInitialDiscoveryCompleted(mockThingySdkManager, bleItem);

        verify(mockThingySdkManager).enableButtonStateNotification((BluetoothDevice) any(), eq(true));
    }
}
