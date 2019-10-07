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
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class BluetoothThingyListenerTest {

    private ProgressBar mockComponentHealthBar = mock(ProgressBar.class);
    private LineChartManager mockChartManager = mock(LineChartManager.class);
    private AWSHelper awsHelper = mock(AWSHelper.class);
    private int BUTTON_STATE_ON = 1;

    @Test
    public void onServiceDiscoveryCompleted_callsViewModel() {
        ThingySdkManager mockThingySdkManager = mock(ThingySdkManager.class);
        MainActivityViewModel mockViewModel = mock(MainActivityViewModel.class);
        BluetoothThingyListener listener = new BluetoothThingyListener(mockViewModel, mockThingySdkManager, null, mockComponentHealthBar, null);

        listener.onServiceDiscoveryCompleted(null);

        verify(mockViewModel).afterInitialDiscoveryCompleted(eq(mockThingySdkManager), (BluetoothDevice) any());
    }

    @Test
    public void onAccelerometerValueChanged_sendsDataToChartManager() {
        BluetoothThingyListener listener = new BluetoothThingyListener(null, null, mockChartManager, mockComponentHealthBar, awsHelper);

        listener.onAccelerometerValueChangedEvent(null, 1, 2, 3);

        verify(mockChartManager).addAccelerationVectorEntry(1, 2, 3);
    }

    @Test
    public void onGravityVectorChanged_sendsDataToChartManager() {
        BluetoothThingyListener listener = new BluetoothThingyListener(null, null, mockChartManager, mockComponentHealthBar, null);

        listener.onGravityVectorChangedEvent(null, 1, 2, 3);

        verify(mockChartManager).addGravityVectorEntry(1, 2, 3);
    }

    @Test
    public void onAccelerometerValueChanged_zValueGreaterThanOrEqualToTwoDecreasesProgressBar() {
        BluetoothThingyListener listener = new BluetoothThingyListener(null, null, mockChartManager, mockComponentHealthBar, awsHelper);

        listener.onAccelerometerValueChangedEvent(null, 1, 2, 3);

        verify(mockComponentHealthBar).incrementProgressBy(-1);
    }

    @Test
    public void onAccelerometerValueChanged_zValueLessThanTwoDoesNotDecreaseHealthBar() {
        BluetoothThingyListener listener = new BluetoothThingyListener(null, null, mockChartManager, mockComponentHealthBar, awsHelper);

        listener.onAccelerometerValueChangedEvent(null, 1, 2, 1);

        verify(mockComponentHealthBar, never()).incrementProgressBy(-1);
    }

    @Test
    public void onAccelerometerValueChanged_zValueLessThanOrEqualToNegativeTwoDoesNotDecreaseHealthBar() {
        BluetoothThingyListener listener = new BluetoothThingyListener(null, null, mockChartManager, mockComponentHealthBar, awsHelper);

        listener.onAccelerometerValueChangedEvent(null, 1, 2, -3);

        verify(mockComponentHealthBar).incrementProgressBy(-1);
    }

    @Test
    public void onAccelerometerValueChanged_ifHealthBarIsZeroTurnOnAWSLight() {
        BluetoothThingyListener listener = new BluetoothThingyListener(null, null, mockChartManager, mockComponentHealthBar, awsHelper);
        when(mockComponentHealthBar.getProgress()).thenReturn(0);

        listener.onAccelerometerValueChangedEvent(null, 1, 2, -3);

        verify(awsHelper).turnLightOn();
    }

    @Test
    public void onButtonStateChanged_resetHealthBarBackToFull() {
        BluetoothThingyListener listener = new BluetoothThingyListener(null, null, mockChartManager, mockComponentHealthBar, awsHelper);

        listener.onButtonStateChangedEvent(null, BUTTON_STATE_ON);

        verify(mockComponentHealthBar).setProgress(100);
    }
}