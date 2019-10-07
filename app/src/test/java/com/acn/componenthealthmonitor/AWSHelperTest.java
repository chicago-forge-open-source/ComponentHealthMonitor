package com.acn.componenthealthmonitor;

import com.amazonaws.mobileconnectors.iot.AWSIotMqttClientStatusCallback;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttManager;
import com.amazonaws.mobileconnectors.iot.AWSIotMqttQos;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.security.KeyStore;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@RunWith(RobolectricTestRunner.class)
public class AWSHelperTest {

    private AWSIotMqttManager mockManager;
    private AWSHelper awsHelper;

    @Before
    public void setUp() {
        mockManager = mock(AWSIotMqttManager.class);
        awsHelper = new AWSHelper(mockManager);
    }

    @Test
    public void disconnectFromAWS_disconnects() {
        awsHelper.disconnectFromAWS();

        verify(mockManager).disconnect();
    }

    @Test
    public void turnLightOn_publishesToTopic() {
        String expectedMessage = "{\n" +
                "  \"state\": {\n" +
                "    \"desired\": {\n" +
                "      \"state\": \"on\"\n" +
                "    }\n" +
                "  }\n" +
                "}";

        awsHelper.turnLightOn();

        verify(mockManager).publishString(expectedMessage, "$aws/things/IoTLight/shadow/update", AWSIotMqttQos.QOS0);
    }
}