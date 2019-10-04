package com.acn.componenthealthmonitor;

import android.content.Context;
import android.graphics.Color;

import androidx.core.content.ContextCompat;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.Date;

import no.nordicsemi.android.thingylib.utils.ThingyUtils;

class LineChartManager {

    private Context context;
    private final LineChart lineChartGravity;
    private final LineChart lineChartAcceleration;

    LineChartManager(Context context, LineChart lineChartGravityVector, LineChart lineChartAccelerationVector) {
        this.context = context;
        this.lineChartGravity = lineChartGravityVector;
        this.lineChartAcceleration = lineChartAccelerationVector;
    }

    void prepareVectorChart(LineChart lineChart, float axisYMinValue, float axisYMaxValue, String chartDescription) {
        if (!lineChart.isEmpty()) {
            lineChart.clearValues();
        }
        lineChart.setDescription(chartDescription);
        lineChart.setVisibleXRangeMinimum(5);
        lineChart.setVisibleXRangeMaximum(5);

        configureChartSettings(lineChart);

        LineData data = new LineData();
        data.setValueFormatter(new VectorChartValueFormatter());
        data.setValueTextColor(Color.WHITE);
        lineChart.setData(data);

        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);

        configureXAxis(lineChart);
        configureYAxis(lineChart, axisYMinValue, axisYMaxValue);
    }

    void addGravityVectorEntry(final float x, final float y, final float z) {
        addVectorEntry(x, y, z, lineChartGravity);
    }

    void addAccelerationVectorEntry(float x, float y, float z) {
        addVectorEntry(x, y, z, lineChartAcceleration);
    }

    private void addVectorEntry(final float vectorX, final float vectorY, final float vectorZ, LineChart lineChart) {
        LineData data = lineChart.getData();

        if (data != null) {
            ILineDataSet setX = data.getDataSetByIndex(0);
            ILineDataSet setY = data.getDataSetByIndex(1);
            ILineDataSet setZ = data.getDataSetByIndex(2);

            if (setX == null || setY == null || setZ == null) {
                final LineDataSet[] dataSets = createVectorDataSet();
                setX = dataSets[0];
                setY = dataSets[1];
                setZ = dataSets[2];
                data.addDataSet(setX);
                data.addDataSet(setY);
                data.addDataSet(setZ);
            }

            data.addXValue(ThingyUtils.TIME_FORMAT_PEDOMETER.format(new Date()));
            data.addEntry(new Entry(vectorX, setX.getEntryCount()), 0);
            data.addEntry(new Entry(vectorY, setY.getEntryCount()), 1);
            data.addEntry(new Entry(vectorZ, setZ.getEntryCount()), 2);

            lineChart.notifyDataSetChanged();
            lineChart.setVisibleXRangeMaximum(10);
            lineChart.moveViewToX(data.getXValCount() - 11);
        }
    }

    private void configureChartSettings(LineChart lineChart) {
        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setPinchZoom(true);
        lineChart.setScaleEnabled(true);
        lineChart.setAutoScaleMinMaxEnabled(true);
        lineChart.setDrawGridBackground(false);
        lineChart.setBackgroundColor(Color.WHITE);
    }

    private void configureXAxis(LineChart lineChart) {
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawGridLines(false);
        xAxis.setAvoidFirstLastClipping(true);
    }

    private void configureYAxis(LineChart lineChart, float axisYMinValue, float axisYMaxValue) {
        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setTextColor(Color.BLACK);
        leftAxis.setValueFormatter(new VectorYValueFormatter());
        leftAxis.setDrawLabels(true);
        leftAxis.setAxisMinValue(axisYMinValue);
        leftAxis.setAxisMaxValue(axisYMaxValue);
        leftAxis.setLabelCount(3, false);
        leftAxis.setDrawZeroLine(true);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private LineDataSet[] createVectorDataSet() {
        final LineDataSet[] lineDataSets = new LineDataSet[3];

        configureAxisDataSet(lineDataSets, "X", R.color.red, 0);
        configureAxisDataSet(lineDataSets, "Y", R.color.green, 1);
        configureAxisDataSet(lineDataSets, "Z", R.color.blue, 2);

        return lineDataSets;
    }

    private void configureAxisDataSet(LineDataSet[] lineDataSets, String axis, int color, int index) {
        float CHART_VALUE_TEXT_SIZE = 6.5f;
        float CHART_LINE_WIDTH = 2.0f;

        LineDataSet dataSet = new LineDataSet(null, axis);
        dataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataSet.setColor(ContextCompat.getColor(context, color));
        dataSet.setHighLightColor(ContextCompat.getColor(context, R.color.colorAccent));
        dataSet.setValueFormatter(new VectorChartValueFormatter());
        dataSet.setDrawValues(true);
        dataSet.setDrawCircles(true);
        dataSet.setDrawCircleHole(false);
        dataSet.setValueTextSize(CHART_VALUE_TEXT_SIZE);
        dataSet.setLineWidth(CHART_LINE_WIDTH);
        lineDataSets[index] = dataSet;
    }

    class VectorChartValueFormatter implements ValueFormatter {
        private DecimalFormat mFormat;

        VectorChartValueFormatter() {
            mFormat = new DecimalFormat("#0.00");
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return mFormat.format(value);
        }
    }

    class VectorYValueFormatter implements YAxisValueFormatter {
        private DecimalFormat mFormat;

        VectorYValueFormatter() {
            mFormat = new DecimalFormat("##,##,#0.00");
        }

        @Override
        public String getFormattedValue(float value, YAxis yAxis) {
            return mFormat.format(value);
        }
    }
}