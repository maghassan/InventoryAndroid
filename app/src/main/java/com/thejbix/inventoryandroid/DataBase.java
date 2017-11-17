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
    private static Vector<ChemicalEntry> chemicalEntries = null;



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

    public static void parseChemicalData(String data)
    {
        chemicalEntries = new Vector<ChemicalEntry>();
        try
        {

            JSONObject json = new JSONObject(data);
            JSONArray jsonArray = json.getJSONArray("results");

            String[] array_spinner = new String[jsonArray.length()];

            ChemicalEntry temp;
            for(int i = 0;i<jsonArray.length();i++)
            {
                int id = jsonArray.getJSONObject(i).getInt("id");
                String name = jsonArray.getJSONObject(i).getString("name");
                double amount = jsonArray.getJSONObject(i).getDouble("amount");
                temp = new ChemicalEntry(id,name,amount);
                chemicalEntries.add(temp);
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
    public static Vector<ChemicalEntry> getChemicalEntries()
    {
        return chemicalEntries;
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
