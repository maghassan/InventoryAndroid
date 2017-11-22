package com.thejbix.inventoryandroid;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Vector;

public class ViewPendingOrders extends AppCompatActivity
{
    private Context context;
    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pending_orders);
        context = this;

        table = (TableLayout) findViewById(R.id.tableLayout);
        table.setStretchAllColumns(true);
        //table.setColumnStretchable(0,true);
        //table.setColumnStretchable(1,true);
        //table.setColumnStretchable(2,true);
        //table.setColumnStretchable(3,false);


        int cellHeight = 100;
        TableRow tblRow = new TableRow(context);
        tblRow.setLayoutParams(new TableRow.LayoutParams(0, cellHeight));

        TextView txtOwner = new TextView(this);
        txtOwner.setText("Owner");
        txtOwner.setGravity(Gravity.CENTER);
        txtOwner.setBackgroundResource(R.drawable.border);
        txtOwner.setLayoutParams(new TableRow.LayoutParams(0, cellHeight));
        tblRow.addView(txtOwner);

        TextView txtField = new TextView(this);
        txtField.setText("Field");
        txtField.setGravity(Gravity.CENTER);
        txtField.setBackgroundResource(R.drawable.border);
        txtField.setLayoutParams(new TableRow.LayoutParams(0, cellHeight));
        tblRow.addView(txtField);

        TextView txtDateCreated = new TextView(this);
        txtDateCreated.setText("Date Created");
        txtDateCreated.setGravity(Gravity.CENTER);
        txtDateCreated.setBackgroundResource(R.drawable.border);
        txtDateCreated.setLayoutParams(new TableRow.LayoutParams(0, cellHeight));
        tblRow.addView(txtDateCreated);

/*
        FrameLayout containerFrame = new FrameLayout(this);
        containerFrame.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT));
        containerFrame.setPadding(40,40,40,40);
        containerFrame.setBackgroundResource(R.drawable.border);

        ImageView imgView = new ImageView(this);
        imgView.setBackgroundResource(R.drawable.view);
        imgView.setLayoutParams(new TableRow.LayoutParams(100,100));
        containerFrame.addView(imgView);

        tblRow.addView(containerFrame);

*/

        table.addView(tblRow,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));


        MySqlServerRequest.OnServerResponseListener responseListener = new MySqlServerRequest.OnServerResponseListener()
        {
            @Override
            public void onRequestRecieved(String responseData)
            {
                Log.d("Print", responseData);
                if(responseData.contains(MySqlServerRequest.DataType.Orders.toString()))
                {

                    populateRows();

                }

            }

            @Override
            public void onError()
            {
                Log.d("Print", "Error");
            }
        };


        MySqlServerRequest requester = new MySqlServerRequest("http://thejbix.heliohost.org/getDataFromDatabase.php", responseListener);
        requester.sqlRequestPendingOrders();
        requester.execute();


    }


    public void populateRows()
    {
        int cellHeight = 500; //120

        Vector<OrderEntry> orderEntries = DataBase.getOrderEntries();
        for(int i = 0;i<orderEntries.size();i++)
        {
            TableRow tblRow = new TableRow(context);
            tblRow.setLayoutParams(new TableRow.LayoutParams(0, cellHeight));

            TextView txtOwner = new TextView(this);
            txtOwner.setText(orderEntries.get(i).getOwner());
            txtOwner.setGravity(Gravity.CENTER);
            txtOwner.setBackgroundResource(R.drawable.border);
            txtOwner.setLayoutParams(new TableRow.LayoutParams(0, cellHeight));
            tblRow.addView(txtOwner);

            TextView txtField = new TextView(this);
            txtField.setText(orderEntries.get(i).getField());
            txtField.setGravity(Gravity.CENTER);
            txtField.setBackgroundResource(R.drawable.border);
            txtField.setLayoutParams(new TableRow.LayoutParams(0, cellHeight));
            tblRow.addView(txtField);

            TextView txtDateCreated = new TextView(this);
            txtDateCreated.setText(orderEntries.get(i).getDateCreated().toString());
            txtDateCreated.setGravity(Gravity.CENTER);
            txtDateCreated.setBackgroundResource(R.drawable.border);
            txtDateCreated.setLayoutParams(new TableRow.LayoutParams(0, cellHeight));
            tblRow.addView(txtDateCreated);
    /*

            FrameLayout containerFrame = new FrameLayout(this);
            containerFrame.setLayoutParams(new TableRow.LayoutParams(150,TableRow.LayoutParams.WRAP_CONTENT));
            containerFrame.setPadding(40,40,40,40);
            containerFrame.setBackgroundResource(R.drawable.border);

            ImageView imgView = new ImageView(this);
            imgView.setBackgroundResource(R.drawable.view);
            imgView.setLayoutParams(new TableRow.LayoutParams(100,100));
            containerFrame.addView(imgView);

            tblRow.addView(containerFrame);
*/
            final OrderEntry entry = orderEntries.get(i);
            tblRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context,ViewOrder.class);
                    ViewOrder.setEntry(entry);
                    startActivity(intent);
                }
            });
            table.addView(tblRow,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, 500));

        }
    }


}
