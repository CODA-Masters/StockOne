package com.codamasters.stockone.ui;

import android.app.ActivityOptions;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.codamasters.stockone.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class StockActivity extends AppCompatActivity {

    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ArrayList<Float> testData = new ArrayList<>();
        testData.add(5f);
        testData.add(1231f);
        testData.add(312f);

        createLineChart();
        setLineChartData(testData);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Bundle bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.transition.animation_in_1, R.transition.animation_in_2).toBundle();

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.transition.animation_out_1, R.transition.animation_out_2);
    }


    private void createLineChart() {

        lineChart = (LineChart) findViewById(R.id.lineChart);
        lineChart.setDrawGridBackground(true);

        Description description  = new Description();
        description.setText("Hola");

        lineChart.setDescription(description);

        lineChart.setTouchEnabled(true);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.setScaleXEnabled(true);

        lineChart.getAxisRight().setEnabled(false);

    }


    private void setLineChartData(ArrayList<Float> data) {

        // Because they are data with a timestamp, we have to reversse the order
        // so the last one is always the first in time order in the graph

        ArrayList<Entry> values = new ArrayList<Entry>();
        int j = data.size() - 1;
        for (int i = 0; i < data.size(); i++) {
            values.add(new Entry(i, data.get(j)));
            j--;
        }

        //Collections.reverse(values);

        LineDataSet set1;

        if (lineChart.getData() != null &&
                lineChart.getData().getDataSetCount() > 0) {


            set1 = (LineDataSet) lineChart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            lineChart.getData().notifyDataChanged();
            lineChart.notifyDataSetChanged();

        } else {
            // NEW SET

            // create a dataset and give it a type
            set1 = new LineDataSet(values, "DataSet 1");

            // set the line to be drawn like this "- - - - - -"
            set1.enableDashedLine(10f, 5f, 0f);
            set1.enableDashedHighlightLine(10f, 5f, 0f);
            set1.setColor(Color.BLACK);
            set1.setCircleColor(Color.BLACK);
            set1.setLineWidth(1f);
            set1.setCircleRadius(3f);
            set1.setDrawCircleHole(false);
            set1.setValueTextSize(9f);
            set1.setDrawFilled(true);

            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(set1); // add the datasets

            // create a data object with the datasets
            LineData lineData = new LineData(dataSets);

            // set data
            lineChart.setData(lineData);
        }
    }

}
