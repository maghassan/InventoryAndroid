package com.thejbix.inventoryandroid;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

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
        //table.setStretchAllColumns(true);
        table.setColumnStretchable(0,true);
        table.setColumnStretchable(1,true);
        table.setColumnStretchable(2,true);
        table.setColumnStretchable(3,false);



        TableRow tblRow = new TableRow(context);
        tblRow.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT));

        TextView txtOwner = new TextView(this);
        txtOwner.setText("Owner");
        txtOwner.setGravity(Gravity.CENTER);
        txtOwner.setBackgroundResource(R.drawable.border);
        txtOwner.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT));
        tblRow.addView(txtOwner);

        TextView txtField = new TextView(this);
        txtField.setText("Field");
        txtField.setGravity(Gravity.CENTER);
        txtField.setBackgroundResource(R.drawable.border);
        txtField.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT));
        tblRow.addView(txtField);

        TextView txtDateCreated = new TextView(this);
        txtDateCreated.setText("Date Created");
        txtDateCreated.setGravity(Gravity.CENTER);
        txtDateCreated.setBackgroundResource(R.drawable.border);
        txtDateCreated.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT));
        tblRow.addView(txtDateCreated);


        FrameLayout containerFrame = new FrameLayout(this);
        containerFrame.setLayoutParams(new TableRow.LayoutParams(150,TableRow.LayoutParams.WRAP_CONTENT));
        containerFrame.setPadding(40,40,40,40);
        containerFrame.setBackgroundResource(R.drawable.border);

        ImageView imgView = new ImageView(this);
        imgView.setBackgroundResource(R.drawable.view);
        imgView.setLayoutParams(new TableRow.LayoutParams(100,100));
        containerFrame.addView(imgView);

        tblRow.addView(containerFrame);



        table.addView(tblRow,new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

    }

    
}
