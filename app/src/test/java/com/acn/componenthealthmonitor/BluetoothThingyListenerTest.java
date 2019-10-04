package com.acn.componenthealthmonitor;

import android.bluetooth.BluetoothDevice;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import no.nordicsemi.android.thingylib.ThingySdkManager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class BluetoothThingyListenerTest {

    @Test
    public void onServiceDiscoveryCompleted_callsViewModel() {
        ThingySdkManager mockThingySdkManager = mock(ThingySdkManager.class);
        MainActivityViewModel mockViewModel = mock(MainActivityViewModel.class);
        BluetoothThingyListener listener = new BluetoothThingyListener(mockViewModel, mockThingySdkManager, null);

        listener.onServiceDiscoveryCompleted(null);

        verify(mockViewModel).afterInitialDiscoveryCompleted(eq(mockThingySdkManager), (BluetoothDevice) any());
    }

    @Test
    public void onAccelerometerValueChanged_sendsDataToChartManager() {
        LineChartManager mockChartManager = mock(LineChartManager.class);
        BluetoothThingyListener listener = new BluetoothThingyListener(null, null, mockChartManager);

        listener.onAccelerometerValueChangedEvent(null, 1 , 2, 3);

        verify(mockChartManager).addAccelerationVectorEntry(1, 2, 3);
    }
}