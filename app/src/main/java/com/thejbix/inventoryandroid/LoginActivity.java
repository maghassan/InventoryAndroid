package com.thejbix.inventoryandroid;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Allows the user to choose themselves and login so the app recognizes who is using it
 * This is the initial screen
 */
public class LoginActivity extends AppCompatActivity
{

    private Spinner cmbChooseEmployee;
    private Context context;
    private ProgressBar progressBar;
    private Button btnSignIn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        cmbChooseEmployee = (Spinner) findViewById(R.id.cmbEmployee);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);
        btnSignIn = (Button) findViewById(R.id.btnSignIn);


        //Signs in as the selected employee
        btnSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EmployeeEntry selected = (EmployeeEntry)(cmbChooseEmployee.getSelectedItem());
                DataBase.setSignInAs(selected);
                Intent intent = new Intent(context,MainMenu.class);
                startActivity(intent);
                finish();
            }
        });


        //Response Listener for database request
        MySqlServerRequest.OnServerResponseListener responseListener = new MySqlServerRequest.OnServerResponseListener()
        {
            @Override
            public void onRequestRecieved(String responseData)
            {
                //Request successful
                Log.d("Print", responseData);
                if(responseData.contains(MySqlServerRequest.DataType.Employees.toString()))
                {
                    //populate combobox with employee list
                    Vector<EmployeeEntry> employees = DataBase.getEmployees();
                    EmployeeEntry[] e = employees.toArray(new EmployeeEntry[employees.size()]);
                    ArrayAdapter<EmployeeEntry> adapter = new ArrayAdapter<EmployeeEntry>(context, android.R.layout.simple_spinner_dropdown_item, e);
                    cmbChooseEmployee.setAdapter(adapter);

                }
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onError()
            {
                //If there was an error then user signs in as a guest
                EmployeeEntry[] e = {new EmployeeEntry(0,"Guest")};
                ArrayAdapter<EmployeeEntry> adapter = new ArrayAdapter<EmployeeEntry>(context, android.R.layout.simple_spinner_dropdown_item, e);
                cmbChooseEmployee.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                Log.d("Print", "Error");
            }
        };


        if(DataBase.getEmployees() != null)
        {
            Vector<EmployeeEntry> employees = DataBase.getEmployees();
            EmployeeEntry[] e = employees.toArray(new EmployeeEntry[employees.size()]);
            ArrayAdapter<EmployeeEntry> adapter = new ArrayAdapter<EmployeeEntry>(context, android.R.layout.simple_spinner_dropdown_item, e);
            cmbChooseEmployee.setAdapter(adapter);
            progressBar.setVisibility(View.GONE);
        }
        else
        {
            //Request database data.  sets the type of data wanted and executes
            MySqlServerRequest requester = new MySqlServerRequest("http://thejbix.heliohost.org/getDataFromDatabase.php", responseListener);
            requester.sqlRequestEmployees();
            requester.execute();

            //Progress icon displayed
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);
        }





    }

}

