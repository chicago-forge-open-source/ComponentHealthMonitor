package com.acn.componenthealthmonitor.thingy;

import android.bluetooth.BluetoothDevice;
import android.widget.ProgressBar;

import com.acn.componenthealthmonitor.LineChartManager;
import com.acn.componenthealthmonitor.MainActivityViewModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import no.nordicsemi.android.thingylib.ThingySdkManager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@RunWith(RobolectricTestRunner.class)
public class BluetoothThingyListenerTest {

    private ProgressBar componentHealthBar = mock(ProgressBar.class);

    @Test
    public void onServiceDiscoveryCompleted_callsViewModel() {
        ThingySdkManager mockThingySdkManager = mock(ThingySdkManager.class);
        MainActivityViewModel mockViewModel = mock(MainActivityViewModel.class);
        BluetoothThingyListener listener = new BluetoothThingyListener(mockViewModel, mockThingySdkManager, null, componentHealthBar);

        listener.onServiceDiscoveryCompleted(null);

        verify(mockViewModel).afterInitialDiscoveryCompleted(eq(mockThingySdkManager), (BluetoothDevice) any());
    }

    @Test
    public void onAccelerometerValueChanged_sendsDataToChartManager() {
        LineChartManager mockChartManager = mock(LineChartManager.class);
        BluetoothThingyListener listener = new BluetoothThingyListener(null, null, mockChartManager, componentHealthBar);

        listener.onAccelerometerValueChangedEvent(null, 1, 2, 3);

        verify(mockChartManager).addAccelerationVectorEntry(1, 2, 3);
    }

    @Test
    public void onGravityVectorChanged_sendsDataToChartManager() {
        LineChartManager mockChartManager = mock(LineChartManager.class);
        BluetoothThingyListener listener = new BluetoothThingyListener(null, null, mockChartManager, componentHealthBar);

        listener.onGravityVectorChangedEvent(null, 1, 2, 3);

        verify(mockChartManager).addGravityVectorEntry(1, 2, 3);
    }

    @Test
    public void onAccelerometerValueChanged_zValueGreaterThanOrEqualToTwoDecreasesProgressBar() {
        LineChartManager mockChartManager = mock(LineChartManager.class);
        BluetoothThingyListener listener = new BluetoothThingyListener(null, null, mockChartManager, componentHealthBar);

        listener.onAccelerometerValueChangedEvent(null, 1, 2, 3);

        verify(componentHealthBar).incrementProgressBy(-1);
    }

    @Test
    public void onAccelerometerValueChanged_zValueLessThanTwoDoesNotDecreaseHealthBar() {
        LineChartManager mockChartManager = mock(LineChartManager.class);
        BluetoothThingyListener listener = new BluetoothThingyListener(null, null, mockChartManager, componentHealthBar);

        listener.onAccelerometerValueChangedEvent(null, 1, 2, 1);

        verify(componentHealthBar, never()).incrementProgressBy(-1);
    }

    @Test
    public void onAccelerometerValueChanged_zValueLessThanOrEqualToNegativeTwoDoesNotDecreaseHealthBar() {
        LineChartManager mockChartManager = mock(LineChartManager.class);
        BluetoothThingyListener listener = new BluetoothThingyListener(null, null, mockChartManager, componentHealthBar);

        listener.onAccelerometerValueChangedEvent(null, 1, 2, -3);

        verify(componentHealthBar).incrementProgressBy(-1);
    }
}