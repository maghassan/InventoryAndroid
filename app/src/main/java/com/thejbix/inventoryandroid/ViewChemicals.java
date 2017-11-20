package com.thejbix.inventoryandroid;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import java.util.Vector;

public class ViewChemicals extends AppCompatActivity {

    private HorizontalBarChart chart;
    private Context context;
    private GraphView graph;
    private Map<Integer, Integer> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_chemicals);
        context = this;
        map = new TreeMap<>();

        chart = (HorizontalBarChart) findViewById(R.id.barChart);
        //graph = (GraphView) findViewById(R.id.graph);


        //BarGraphSeries<DataPoint> series;

        MySqlServerRequest.OnServerResponseListener responseListener = null;

        final MySqlServerRequest requester = new MySqlServerRequest("http://thejbix.heliohost.org/getDataFromDatabase.php");


        responseListener = new MySqlServerRequest.OnServerResponseListener()
        {
            @Override
            public void onRequestRecieved(String responseData)
            {
                Log.d("Print", responseData);
                if(responseData.contains(MySqlServerRequest.DataType.Chemicals.toString()))
                {
                    if(DataBase.getChemicalEntries() == null)
                    {
                        Log.d("Print","Null");
                    }
                    else
                    {
                        for(int i = 0;i<DataBase.getChemicalEntries().size();i++)
                        {
                            DataBase.getChemicalEntries().get(i).print();
                        }

                    }

                    MySqlServerRequest requester = new MySqlServerRequest("http://thejbix.heliohost.org/getDataFromDatabase.php",this);
                    requester.sqlRequestOrders();
                    requester.execute();
                }
                else if(responseData.contains(MySqlServerRequest.DataType.Orders.toString()))
                {
                    if(DataBase.getOrderEntries() == null)
                    {
                        Log.d("Print","Null");
                    }
                    else
                    {
                        for(int i = 0;i<DataBase.getOrderEntries().size();i++)
                        {
                            DataBase.getOrderEntries().get(i).print();
                        }

                    }
                    configureGraph();

                }


            }

            @Override
            public void onError()
            {
                Log.d("Print","Error");
                DataBase.setupTestChemicalData();
                DataBase.setupTestOrderData();
                configureGraph();

            }
        };

        requester.setListener(responseListener);
        requester.sqlRequestChemicals();
        requester.execute();
    }

    private ArrayList<IBarDataSet> getDataSet()
    {
        ArrayList<IBarDataSet> dataSets = null;

        ArrayList<BarEntry> inventory = new ArrayList<>();
        ArrayList<BarEntry> pending = new ArrayList<>();

        Vector<ChemicalEntry> chemicalEntries = DataBase.getChemicalEntries();
        for(int i = 0;i<chemicalEntries.size();i++)
        {
            BarEntry v = new BarEntry(i,(float)(chemicalEntries.get(i).getAmount()));
            inventory.add(v);
            pending.add(new BarEntry(i,0));
            map.put(new Integer(chemicalEntries.get(i).getId()),new Integer(i));
        }

        Vector<OrderEntry> orderEntries = DataBase.getOrderEntries();
        for(int i = 0;i<orderEntries.size();i++)
        {
            for(int j = 0;j<5;j++)
            {
                if(orderEntries.get(i).getChemId(j) != 0)
                {
                    int pos = map.get(orderEntries.get(i).getChemId(j));
                    double amount = orderEntries.get(i).getChemRate(j) * orderEntries.get(i).getAcres();
                    BarEntry entry = pending.get(pos);
                    entry.setY(entry.getY() + (float)(amount));

                }
            }

        }

        BarDataSet barDataSet1 = new BarDataSet(inventory, "Inventory");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(pending, "Pending");
        barDataSet2.setColor(Color.rgb(155, 0, 0));


        dataSets = new ArrayList<>();
        dataSets.add(barDataSet2);
        dataSets.add(barDataSet1);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        Vector<ChemicalEntry> chemicalEntries = DataBase.getChemicalEntries();
        for(int i = 0;i<chemicalEntries.size();i++)
        {
            xAxis.add(chemicalEntries.get(i).getName());
        }
        return xAxis;
    }

    private void configureGraph()
    {
        BarData barData = new BarData(getDataSet());



        XAxis xaxis = chart.getXAxis();
        xaxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xaxis.setGranularity(1);
        xaxis.setCenterAxisLabels(true);
        IAxisValueFormatter formatter = new IndexAxisValueFormatter(getXAxisValues());
        xaxis.setValueFormatter(formatter);
        xaxis.setAxisMinimum(0.0f);
        xaxis.setAxisMaximum(DataBase.getChemicalEntries().size());
        xaxis.setDrawGridLines(false);


        YAxis yaxis = chart.getAxisLeft();
        yaxis.setDrawGridLines(false);
        yaxis.setAxisMinimum(0);
        chart.setData(barData);


        float start = 0;
        float groupSpace = .2f;
        float barSpace = .02f;
        float barWidth = (float)((1 - groupSpace)/2.0)-barSpace;

        Description d = new Description();
        d.setText("Inventory List");
        chart.setDrawGridBackground(false);
        chart.setDescription(d);
        chart.setDrawValueAboveBar(true);
        chart.getBarData().setBarWidth(barWidth);

        chart.groupBars(0,groupSpace,barSpace);

        chart.getAxisRight().setEnabled(false);
        chart.animateXY(2000, 2000);
        chart.invalidate();
    }

}
