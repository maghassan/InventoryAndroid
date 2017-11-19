package com.thejbix.inventoryandroid;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ViewChemicals extends AppCompatActivity {

    HorizontalBarChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_chemicals);

        chart = (HorizontalBarChart) findViewById(R.id.barChart);

        BarData data = new BarData(getDataSet());

        XAxis yaxis = chart.getXAxis();
        IAxisValueFormatter formatter = new IndexAxisValueFormatter(getXAxisValues());
        yaxis.setValueFormatter(formatter);

        chart.setData(data);



        Description d = new Description();
        d.setText("My Chart");
        chart.setDescription(d);
        chart.animateXY(2000, 2000);
        chart.invalidate();
    }

    private ArrayList<IBarDataSet> getDataSet()
    {
        ArrayList<IBarDataSet> dataSets = null;

        ArrayList<BarEntry> valueSet1 = new ArrayList<>();
        BarEntry v1e1 = new BarEntry(0, 10); // Jan
        valueSet1.add(v1e1);
        BarEntry v1e2 = new BarEntry(1, 20); // Feb
        valueSet1.add(v1e2);
        BarEntry v1e3 = new BarEntry(2, 50); // Mar
        valueSet1.add(v1e3);
        BarEntry v1e4 = new BarEntry(3, 100); // Apr
        valueSet1.add(v1e4);
        BarEntry v1e5 = new BarEntry(4, 5); // May
        valueSet1.add(v1e5);
        BarEntry v1e6 = new BarEntry(5, 100); // Jun
        valueSet1.add(v1e6);



        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Brand 1");
        barDataSet1.setColor(Color.rgb(0, 155, 0));

        dataSets = new ArrayList<>();
        dataSets.add(barDataSet1);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("JAN");
        xAxis.add("FEB");
        xAxis.add("MAR");
        xAxis.add("APR");
        xAxis.add("MAY");
        xAxis.add("JUN");
        return xAxis;
    }

}
