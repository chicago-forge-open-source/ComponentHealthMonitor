package com.acn.componenthealthmonitor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import no.nordicsemi.android.thingylib.ThingyListenerHelper;
import no.nordicsemi.android.thingylib.ThingySdkManager;

public class MainActivity extends AppCompatActivity {

    private ThingySdkManager thingySdkManager;
    private BluetoothThingyListener thingyListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        thingySdkManager.bindService(this, ThingyService.class);
        ThingyListenerHelper.registerThingyListener(this, thingyListener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            //TODO
        }
    }

    private void setConnectOnClickListener() {
        final FloatingActionButton connect = findViewById(R.id.connectFab);
        connect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent initialConfiguration = new Intent(MainActivity.this, DeviceScanActivity.class);
                startActivityForResult(initialConfiguration, Utils.INITIAL_CONFIGURATION_RESULT);
            }
        });
    }
}
