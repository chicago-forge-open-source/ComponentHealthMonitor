package com.acn.componenthealthmonitor;

import android.util.Log;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

public class AWSHelper {

    private static final String LOG_TAG = "***";
    private AWSIotMqttManager mqttManager;

    AWSHelper(AWSIotMqttManager mqttManager) {
        this.mqttManager = mqttManager;
    }

    void connectToAWS() {
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
