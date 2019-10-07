package com.acn.componenthealthmonitor;

import android.content.Context;
import android.graphics.Color;

import androidx.test.platform.app.InstrumentationRegistry;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.internal.Instrument;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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

    @Test
    public void configureChartSettings_setAutoScaleEnabled() {
        LineChart lineChart  = new LineChart(context);
        lineChartManager.configureChartSettings(lineChart);

        assertTrue(lineChart.isAutoScaleMinMaxEnabled());
    }

    @Test
    public void configureChartSettings_setTouchEnabled() {
        LineChart lineChart = mock(LineChart.class);
        lineChartManager.configureChartSettings(lineChart);

        verify(lineChart).setTouchEnabled(true);
    }

    @Test
    public void configureChartSettings_setDrawGridBackgroundDisabled() {
        LineChart lineChart = mock(LineChart.class);
        lineChartManager.configureChartSettings(lineChart);

        verify(lineChart).setDrawGridBackground(false);
    }

    @Test
    public void configureChartSettings_setBackgroundColorToWhite() {
        LineChart lineChart = mock(LineChart.class);
        lineChartManager.configureChartSettings(lineChart);

        verify(lineChart).setBackgroundColor(Color.WHITE);
    }

    @Test
    public void configureXAxis_setsPositionToBottom() {
        LineChart lineChart = new LineChart(context);

        lineChartManager.configureXAxis(lineChart);

        assertEquals(XAxis.XAxisPosition.BOTTOM, lineChart.getXAxis().getPosition());
    }

    @Test
    public void configureXAxis_setTextColorToBlack() {
        LineChart lineChart = new LineChart(context);

        lineChartManager.configureXAxis(lineChart);

        assertEquals(Color.BLACK, lineChart.getXAxis().getTextColor());
    }

    @Test
    public void configureXAxis_setDrawGridLinesDisabled() {
        LineChart lineChart = new LineChart(context);

        lineChartManager.configureXAxis(lineChart);

        assertFalse(lineChart.getXAxis().isDrawGridLinesEnabled());
    }

    @Test
    public void configureXAxis_setClippingEnabled() {
        LineChart lineChart = new LineChart(context);

        lineChartManager.configureXAxis(lineChart);

        assertTrue(lineChart.getXAxis().isAvoidFirstLastClippingEnabled());
    }

    @Test
    public void configureYAxis_setTextColorToBlack() {
        LineChart lineChart = new LineChart(context);

        lineChartManager.configureYAxis(lineChart, -1, 1);

        assertEquals(Color.BLACK, lineChart.getAxisLeft().getTextColor());
    }

    @Test
    public void configureYAxis_setValueFormatting() {
        LineChart lineChart = new LineChart(context);

        lineChartManager.configureYAxis(lineChart, -1, 1);

        assertNotNull(lineChart.getAxisLeft().getValueFormatter());
    }

    @Test
    public void configureYAxis_setDrawLabelsEnabled() {
        LineChart lineChart = new LineChart(context);

        lineChartManager.configureYAxis(lineChart, -1, 1);

        assertTrue(lineChart.getAxisLeft().isDrawLabelsEnabled());
    }

    @Test
    public void configureYAxis_setMinMaxValues() {
        LineChart lineChart = new LineChart(context);

        int axisYMinValue = -1;
        int axisYMaxValue = 1;
        lineChartManager.configureYAxis(lineChart, axisYMinValue, axisYMaxValue);

        assertEquals(axisYMinValue, lineChart.getAxisLeft().getAxisMinimum(),0.1);
        assertEquals(axisYMaxValue, lineChart.getAxisLeft().getAxisMaximum(), 0.1);
    }

    @Test
    public void configureYAxis_setLabelCount() {
        LineChart lineChart = new LineChart(context);

        lineChartManager.configureYAxis(lineChart, -1, 1);

        assertEquals(3, lineChart.getAxisLeft().getLabelCount());
    }

    @Test
    public void configureYAxis_setDrawZeroLineEnabled() {
        LineChart lineChart = new LineChart(context);

        lineChartManager.configureYAxis(lineChart, -1, 1);

        assertTrue(lineChart.getAxisLeft().isDrawZeroLineEnabled());
    }

    @Test
    public void configureYAxis_setAxisRightDisabled() {
        LineChart lineChart = new LineChart(context);

        lineChartManager.configureYAxis(lineChart, -1, 1);

        assertFalse(lineChart.getAxisRight().isEnabled());
    }
}