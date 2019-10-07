package com.acn.componenthealthmonitor;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;

import com.github.mikephil.charting.charts.LineChart;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.internal.Instrument;

import static junit.framework.TestCase.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class LineChartManagerTest {

    private Context context;
    private LineChartManager lineChartManager;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        lineChartManager = new LineChartManager(null, null);
    }

    @Test
    public void configureChartSettings_setsDragEnabled() {
        LineChart lineChart  = new LineChart(context);
        lineChartManager.configureChartSettings(lineChart);

        assertTrue(lineChart.isDragEnabled());
    }


}