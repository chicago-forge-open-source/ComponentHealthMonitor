package com.acn.componenthealthmonitor;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class PermissionsHelperTest {

    private Activity mockActivity = mock(Activity.class);
    @Test
    public void enableBleSendsIntent() {
        PermissionsHelper helper = new PermissionsHelper(mockActivity);

        helper.enableBle();

        ArgumentCaptor<Intent> captor = ArgumentCaptor.forClass(Intent.class);

        verify(mockActivity).startActivityForResult(captor.capture(), eq(2));

        Intent result = captor.getValue();

        assertEquals(result.getAction(), BluetoothAdapter.ACTION_REQUEST_ENABLE);
    }
}