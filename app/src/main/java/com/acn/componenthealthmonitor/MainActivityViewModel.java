package com.acn.componenthealthmonitor;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;

import androidx.databinding.Bindable;
import androidx.databinding.Observable;
import androidx.databinding.PropertyChangeRegistry;
import androidx.lifecycle.ViewModel;

import com.acn.componenthealthmonitor.bleItem.BleItem;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import no.nordicsemi.android.thingylib.ThingySdkManager;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

public class MainActivityViewModel extends ViewModel implements Observable {

    private static final String LOG_TAG = "***";
    private static final String CUSTOMER_SPECIFIC_IOT_ENDPOINT = "a2soq6ydozn6i0-ats.iot.us-west-2.amazonaws.com";

    @Bindable
    private String deviceName = "No Device Connected";
    private PropertyChangeRegistry callbacks = new PropertyChangeRegistry();
    private AWSIotMqttManager mqttManager;
    private String clientId;

    @Override
    public void addOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.add(callback);
    }

    @Override
    public void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback) {
        callbacks.remove(callback);
    }

    public String getDeviceName() {
        return deviceName;
    }

    void connectToDevice(Context context, ThingySdkManager sdkManager, BleItem device) {
        if (sdkManager != null) {
            BluetoothDevice bluetoothDevice = device.getDevice();
            changeDeviceName(bluetoothDevice);
            sdkManager.connectToThingy(context, bluetoothDevice, ThingyService.class);
            sdkManager.setSelectedDevice(bluetoothDevice);
        }
    }

    private void changeDeviceName(BluetoothDevice bluetoothDevice) {
        if (bluetoothDevice != null) {
            deviceName = bluetoothDevice.getName();
            notifyPropertyChanged(com.acn.componenthealthmonitor.BR.deviceName);
        }
    }

    void afterInitialDiscoveryCompleted(ThingySdkManager sdkManager, BluetoothDevice device) {
        sdkManager.enableButtonStateNotification(device, true);
        sdkManager.enableMotionNotifications(device, true);
    }

    private void notifyPropertyChanged(int fieldId) {
        callbacks.notifyCallbacks(this, fieldId, null);
    }


    void setUpAWS(Context context) {
        final CountDownLatch latch = new CountDownLatch(1);
        AWSMobileClient.getInstance().initialize(
                context,
                new Callback<UserStateDetails>() {
                    @Override
                    public void onResult(UserStateDetails result) {
                        latch.countDown();
                    }

                    @Override
                    public void onError(Exception e) {
                        latch.countDown();
                        Log.e(LOG_TAG, "onError: ", e);
                    }
                }
        );

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clientId = UUID.randomUUID().toString();
        mqttManager = new AWSIotMqttManager(clientId, CUSTOMER_SPECIFIC_IOT_ENDPOINT);
    }


    void connectToAWS() {
        Log.d(LOG_TAG, "clientId = " + clientId);

        try {
            mqttManager.connect(AWSMobileClient.getInstance(), new AWSIotMqttClientStatusCallback() {
                @Override
                public void onStatusChanged(final AWSIotMqttClientStatus status,
                                            final Throwable throwable) {
                    Log.d(LOG_TAG, "Status = " + status);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (throwable != null) {
                                Log.e(LOG_TAG, "Connection error.", throwable);
                            }
                        }
                    });
                }
            });
        } catch (final Exception e) {
            Log.e(LOG_TAG, "Connection error.", e);
        }
    }

    void turnLightOn() {
        final String msg = "{\n" +
                "  \"state\": {\n" +
                "    \"desired\": {\n" +
                "      \"state\": \"on\"\n" +
                "    }\n" +
                "  }\n" +
                "}";

        publishToTopic(msg);
    }

    void turnLightOff() {
        final String msg = "{\n" +
                "  \"state\": {\n" +
                "    \"desired\": {\n" +
                "      \"state\": \"off\"\n" +
                "    }\n" +
                "  }\n" +
                "}";

        publishToTopic(msg);
    }

    private void publishToTopic(String msg) {
        final String topic = "$aws/things/IoTLight/shadow/update";
        try {
            mqttManager.publishString(msg, topic, AWSIotMqttQos.QOS0);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Publish error.", e);
        }
    }

    void disconnectFromAWS() {
        try {
            mqttManager.disconnect();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Disconnect error.", e);
        }
    }
}
