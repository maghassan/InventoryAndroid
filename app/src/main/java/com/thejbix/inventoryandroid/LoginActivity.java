package com.thejbix.inventoryandroid;



import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
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

//import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity
{

    private Spinner cmbChooseEmployee;
    private Context context;
    private ProgressBar progressBar;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = this;

        cmbChooseEmployee = (Spinner) findViewById(R.id.cmbEmployee);
        progressBar = (ProgressBar) findViewById(R.id.login_progress);



        MySqlServerRequest.OnServerResponseListener responseListener = new MySqlServerRequest.OnServerResponseListener()
        {
            @Override
            public void onRequestRecieved(String responseData)
            {
                Log.d("Print", responseData);
                if(responseData.contains("DONE"))
                {

                    Vector<EmployeeEntry> employees = DataBase.getEmployees();
                    EmployeeEntry[] e = employees.toArray(new EmployeeEntry[employees.size()]);
                    ArrayAdapter<EmployeeEntry> adapter = new ArrayAdapter<EmployeeEntry>(context, android.R.layout.simple_spinner_dropdown_item, e);
                    cmbChooseEmployee.setAdapter(adapter);
                    progressBar.setVisibility(View.GONE);

                }




                /*try
                {

                    JSONObject json = new JSONObject(responseData);
                    JSONArray jsonArray = json.getJSONArray("results");

                    String[] array_spinner = new String[jsonArray.length()];

                    for(int i = 0;i<jsonArray.length();i++)
                    {
                        int id = jsonArray.getJSONObject(i).getInt("id");
                        String name = jsonArray.getJSONObject(i).getString("name");
                        double amount = jsonArray.getJSONObject(i).getDouble("amount");
                        Log.d("Print", "" + id + ": " + name + " " + amount);

                    }

                    //ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, array_spinner);


                }
                catch (JSONException e)
                {
                    e.printStackTrace();
                }*/
            }

            @Override
            public void onError()
            {
                Log.d("Print", "Error");
            }
        };

        MySqlServerRequest requester = new MySqlServerRequest("http://thejbix.heliohost.org/getDataFromDatabase.php", responseListener);
        requester.sqlRequestEmployees();
        requester.execute();

        progressBar.setIndeterminate(true);
        progressBar.setVisibility(View.VISIBLE);




    }

}

