package com.thejbix.inventoryandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ViewOrder extends AppCompatActivity {

    private static OrderEntry entry;

    private TextView lblReporter;
    private TextView txtOwner, txtField, txtAcres, txtCreated, txtPilot, txtReport;
    private Button btnReport;
    private TableLayout table;
    private TableRow[] rows = new TableRow[5];


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order);

        txtOwner = (TextView) findViewById(R.id.txtOwner);
        txtField = (TextView) findViewById(R.id.txtField);
        txtAcres = (TextView) findViewById(R.id.txtAcres);
        txtCreated = (TextView) findViewById(R.id.txtCreated);
        txtPilot = (TextView) findViewById(R.id.txtPilot);
        txtReport = (TextView) findViewById(R.id.txtReported);
        lblReporter = (TextView) findViewById(R.id.lbReported);

        btnReport = (Button) findViewById(R.id.btnReport);
        table = (TableLayout) findViewById(R.id.table);
        rows[0] = (TableRow) findViewById(R.id.row1);
        rows[1] = (TableRow) findViewById(R.id.row2);
        rows[2] = (TableRow) findViewById(R.id.row3);
        rows[3] = (TableRow) findViewById(R.id.row4);
        rows[4] = (TableRow) findViewById(R.id.row5);

        txtOwner.setText(entry.getOwner());
        txtField.setText(entry.getField());
        txtAcres.setText(String.valueOf(entry.getAcres()));
        txtCreated.setText(entry.getDateCreated().toString());

        String pilot;
        EmployeeEntry temp = DataBase.getEmployeeFromId(entry.getAssignedPilot());
        if(temp != null)
        {
            pilot = temp.getName();
        }
        else
        {
            pilot = "Unassigned";
        }
        txtPilot.setText(pilot);

        if(entry.isReported())
        {
            String reporter;
            temp = DataBase.getEmployeeFromId(entry.getReporter());
            if(temp != null)
            {
                reporter = temp.getName();
            }
            else
            {
                reporter = "Unassigned";
            }
            txtReport.setText(reporter);
            btnReport.setText("UnReport");
        }
        else
        {
            txtReport.setVisibility(View.INVISIBLE);
            lblReporter.setVisibility(View.INVISIBLE);
        }


        btnReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });


        MySqlServerRequest.OnServerResponseListener responseListener = new MySqlServerRequest.OnServerResponseListener()
        {
            @Override
            public void onRequestRecieved(String responseData)
            {
                Log.d("Print", responseData);
                if(responseData.contains(MySqlServerRequest.DataType.Chemicals.toString()))
                {
                    fillMixTable();

                }

            }

            @Override
            public void onError()
            {
                Log.d("Print", "Error");
            }
        };


        MySqlServerRequest requester = new MySqlServerRequest("http://thejbix.heliohost.org/getDataFromDatabase.php", responseListener);
        requester.sqlRequestChemicals();
        requester.execute();







    }


    private void fillMixTable()
    {
        table.setStretchAllColumns(true);
        int cellHeight = 120;
        for(int i = 0;i<5;i++)
        {
            ChemicalEntry chemicalEntry = DataBase.getChemicalFromId(entry.getChemId(i));

            TextView txtChemName = new TextView(this);
            if(chemicalEntry == null)
            {
                txtChemName.setText("");
            }
            else
            {
                txtChemName.setText(chemicalEntry.getName());
            }
            txtChemName.setGravity(Gravity.LEFT);
            txtChemName.setBackgroundResource(R.drawable.border);
            txtChemName.setLayoutParams(new TableRow.LayoutParams(0, cellHeight));
            rows[i].addView(txtChemName);

            TextView txtChemRate = new TextView(this);
            if(entry.getChemRate(i) == 0)
            {
                txtChemRate.setText("");
            }
            else
            {
                txtChemRate.setText(String.valueOf(entry.getChemRate(i)));
            }
            txtChemRate.setGravity(Gravity.RIGHT);
            txtChemRate.setBackgroundResource(R.drawable.border);
            txtChemRate.setLayoutParams(new TableRow.LayoutParams(0, cellHeight));
            rows[i].addView(txtChemRate);


            TextView txtChemTotal = new TextView(this);
            if(entry.getChemRate(i)*entry.getAcres() == 0)
            {
                txtChemTotal.setText("");
            }
            else
            {
                txtChemTotal.setText(String.valueOf(entry.getChemRate(i)*entry.getAcres()));
            }
            txtChemTotal.setGravity(Gravity.RIGHT);
            txtChemTotal.setBackgroundResource(R.drawable.border);
            txtChemTotal.setLayoutParams(new TableRow.LayoutParams(0, cellHeight));
            rows[i].addView(txtChemTotal);
        }
    }



    static void setEntry(OrderEntry entry)
    {
        ViewOrder.entry = entry;
    }
}
