package com.thejbix.inventoryandroid;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.Vector;

/**
 * Created by jaydonbixenman on 11/17/17.
 */

public class DataBase
{
    private static EmployeeEntry signedInAs = null;
    private static Vector<EmployeeEntry> employeeEntries = null;
    private static Vector<ChemicalEntry> chemicalEntries = null;
    private static Vector<OrderEntry> orderEntries = null;


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

    public static void parseOrderData(String data)
    {
        orderEntries = new Vector<OrderEntry>();
        try
        {

            JSONObject json = new JSONObject(data);
            JSONArray jsonArray = json.getJSONArray("results");

            String[] array_spinner = new String[jsonArray.length()];

            OrderEntry temp;
            for(int i = 0;i<jsonArray.length();i++)
            {
                int id = jsonArray.getJSONObject(i).getInt("id");
                String owner = jsonArray.getJSONObject(i).getString("owner");
                String field = jsonArray.getJSONObject(i).getString("field");
                String dateCreated = jsonArray.getJSONObject(i).getString("date_created");
                String dateCompleted = jsonArray.getJSONObject(i).getString("date_completed");
                int status = jsonArray.getJSONObject(i).getInt("status");
                int pilot = jsonArray.getJSONObject(i).getInt("assigned_pilot");
                int reporter = jsonArray.getJSONObject(i).getInt("reporter");
                boolean reported = (jsonArray.getJSONObject(i).getInt("reported") == 0) ? false : true;
                boolean archived = (jsonArray.getJSONObject(i).getInt("archived") == 0) ? false : true;
                int c1_id = jsonArray.getJSONObject(i).getInt("c1_id");
                int c2_id = jsonArray.getJSONObject(i).getInt("c2_id");
                int c3_id = jsonArray.getJSONObject(i).getInt("c3_id");
                int c4_id = jsonArray.getJSONObject(i).getInt("c4_id");
                int c5_id = jsonArray.getJSONObject(i).getInt("c5_id");
                double c1_rate = jsonArray.getJSONObject(i).getDouble("c1_rate");
                double c2_rate = jsonArray.getJSONObject(i).getDouble("c2_rate");
                double c3_rate = jsonArray.getJSONObject(i).getDouble("c3_rate");
                double c4_rate = jsonArray.getJSONObject(i).getDouble("c4_rate");
                double c5_rate = jsonArray.getJSONObject(i).getDouble("c5_rate");
                int acres = jsonArray.getJSONObject(i).getInt("acres");

                temp = new OrderEntry(id);
                temp.setOwner(owner);
                temp.setField(field);
                temp.setDateCreated(Date.valueOf(dateCreated));
                temp.setDateCompleted(Date.valueOf(dateCompleted));
                temp.setStatus(status);
                temp.setAssignedPilot(pilot);
                temp.setReporter(reporter);
                temp.setReported(reported);
                temp.setArchived(archived);
                temp.setChemId(0,c1_id);
                temp.setChemId(1,c2_id);
                temp.setChemId(2,c3_id);
                temp.setChemId(3,c4_id);
                temp.setChemId(4,c5_id);
                temp.setChemRate(0,c1_rate);
                temp.setChemRate(1,c2_rate);
                temp.setChemRate(2,c3_rate);
                temp.setChemRate(3,c4_rate);
                temp.setChemRate(4,c5_rate);
                temp.setAcres(acres);

                orderEntries.add(temp);
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
    public static Vector<OrderEntry> getOrderEntries()
    {
        return orderEntries;
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
