package com.acn.componenthealthmonitor.thingy;

import android.bluetooth.BluetoothDevice;
import android.widget.ProgressBar;

import com.acn.componenthealthmonitor.LineChartManager;
import com.acn.componenthealthmonitor.MainActivityViewModel;
import com.acn.componenthealthmonitor.helper.AWSHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import no.nordicsemi.android.thingylib.ThingySdkManager;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class BluetoothThingyListenerTest {

    private ProgressBar componentHealthBar = mock(ProgressBar.class);
    private LineChartManager mockChartManager = mock(LineChartManager.class);
    private AWSHelper awsHelper = mock(AWSHelper.class);

    @Test
    public void onServiceDiscoveryCompleted_callsViewModel() {
        ThingySdkManager mockThingySdkManager = mock(ThingySdkManager.class);
        MainActivityViewModel mockViewModel = mock(MainActivityViewModel.class);
        BluetoothThingyListener listener = new BluetoothThingyListener(mockViewModel, mockThingySdkManager, null, componentHealthBar, null);

        listener.onServiceDiscoveryCompleted(null);

        verify(mockViewModel).afterInitialDiscoveryCompleted(eq(mockThingySdkManager), (BluetoothDevice) any());
    }

    @Test
    public void onAccelerometerValueChanged_sendsDataToChartManager() {
        BluetoothThingyListener listener = new BluetoothThingyListener(null, null, mockChartManager, componentHealthBar, awsHelper);

        listener.onAccelerometerValueChangedEvent(null, 1, 2, 3);

        verify(mockChartManager).addAccelerationVectorEntry(1, 2, 3);
    }

    @Test
    public void onGravityVectorChanged_sendsDataToChartManager() {
        BluetoothThingyListener listener = new BluetoothThingyListener(null, null, mockChartManager, componentHealthBar, null);

        listener.onGravityVectorChangedEvent(null, 1, 2, 3);

        verify(mockChartManager).addGravityVectorEntry(1, 2, 3);
    }

    @Test
    public void onAccelerometerValueChanged_zValueGreaterThanOrEqualToTwoDecreasesProgressBar() {
        BluetoothThingyListener listener = new BluetoothThingyListener(null, null, mockChartManager, componentHealthBar, awsHelper);

        listener.onAccelerometerValueChangedEvent(null, 1, 2, 3);

        verify(componentHealthBar).incrementProgressBy(-1);
    }

    @Test
    public void onAccelerometerValueChanged_zValueLessThanTwoDoesNotDecreaseHealthBar() {
        BluetoothThingyListener listener = new BluetoothThingyListener(null, null, mockChartManager, componentHealthBar, awsHelper);

        listener.onAccelerometerValueChangedEvent(null, 1, 2, 1);

        verify(componentHealthBar, never()).incrementProgressBy(-1);
    }

    @Test
    public void onAccelerometerValueChanged_zValueLessThanOrEqualToNegativeTwoDoesNotDecreaseHealthBar() {
        BluetoothThingyListener listener = new BluetoothThingyListener(null, null, mockChartManager, componentHealthBar, awsHelper);

        listener.onAccelerometerValueChangedEvent(null, 1, 2, -3);

        verify(componentHealthBar).incrementProgressBy(-1);
    }

    @Test
    public void onAccelerometerValueChanged_ifHealthBarIsZeroTurnOnAWSLight() {
        BluetoothThingyListener listener = new BluetoothThingyListener(null, null, mockChartManager, componentHealthBar, awsHelper);
        when(componentHealthBar.getProgress()).thenReturn(0);

        listener.onAccelerometerValueChangedEvent(null, 1, 2, -3);

        verify(awsHelper).turnLightOn();
    }
}