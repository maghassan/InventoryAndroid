package com.thejbix.inventoryandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Vector;

/**
 * Created by jaydonbixenman on 11/17/17.
 */

public class DataBase
{
    private static EmployeeEntry signedInAs = null;
    private static Vector<EmployeeEntry> employeeEntries = null;



    public static void parseEmployeeData(String data)
    {
        employeeEntries = new Vector<EmployeeEntry>();
        try
        {

            JSONObject json = new JSONObject(data);
            JSONArray jsonArray = json.getJSONArray("results");

            String[] array_spinner = new String[jsonArray.length()];

            EmployeeEntry temp;
            for(int i = 0;i<jsonArray.length();i++)
            {
                int id = jsonArray.getJSONObject(i).getInt("id");
                String name = jsonArray.getJSONObject(i).getString("name");
                temp = new EmployeeEntry(id,name);
                employeeEntries.add(temp);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }


    }

    public static Vector<EmployeeEntry> getEmployees()
    {
        return employeeEntries;
    }


    public static void setSignInAs(EmployeeEntry entry)
    {
        signedInAs = entry;
    }

    public static void signOut()
    {
        signedInAs = null;
    }


}
