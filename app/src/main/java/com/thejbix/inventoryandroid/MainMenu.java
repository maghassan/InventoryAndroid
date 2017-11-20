package com.thejbix.inventoryandroid;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.Vector;

public class MainMenu extends AppCompatActivity
{
    private Context context;
    private Button btnLogOut, btnViewChemicals, btnViewPendingOrders, btnViewCompletedOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        context = this;

        btnLogOut = (Button) findViewById(R.id.btnSignOut);
        btnViewChemicals = (Button) findViewById(R.id.btnViewChemical);
        btnViewPendingOrders = (Button) findViewById(R.id.btnViewPendingOrders);
        btnViewCompletedOrders = (Button) findViewById(R.id.btnViewCompletedOrders);


        btnLogOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DataBase.signOut();
                Intent intent = new Intent(context,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });


        final MySqlServerRequest.OnServerResponseListener responseListener = new MySqlServerRequest.OnServerResponseListener()
        {
            @Override
            public void onRequestRecieved(String responseData)
            {
                Log.d("Print", responseData);
                if(responseData.contains("DONE"))
                {

                    Vector<OrderEntry> orders = DataBase.getOrderEntries();
                    for(OrderEntry order: orders)
                    {
                        order.print();
                    }


                }

            }

            @Override
            public void onError()
            {
                Log.d("Print", "Error");
            }
        };
        btnViewPendingOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewPendingOrders.class);
                startActivity(intent);
            }
        });



        btnViewChemicals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewChemicals.class);
                startActivity(intent);
            }
        });



    }

}
