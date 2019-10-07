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

    @Test
    public void configureChartSettings_setPinchZoomEnabled() {
        LineChart lineChart  = new LineChart(context);
        lineChartManager.configureChartSettings(lineChart);

        assertTrue(lineChart.isPinchZoomEnabled());
    }

    @Test
    public void configureChartSettings_setScaleEnabled() {
        LineChart lineChart  = new LineChart(context);
        lineChartManager.configureChartSettings(lineChart);

        assertTrue(lineChart.isScaleXEnabled());
        assertTrue(lineChart.isScaleYEnabled());
    }
}