package com.thejbix.inventoryandroid;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by jaydonbixenman on 11/14/17.
 */

public class MySqlServerRequest extends AsyncTask<String, Void, String>
{
    private enum DataType
    {
        Employees, Chemicals, Orders, Errors
    }

    private OnServerResponseListener listener;
    private String url;
    private String sqlString;
    private DataType dataType;
    private Vector<EmployeeEntry> employeeEntries;


    public MySqlServerRequest(String url, OnServerResponseListener listener)
    {
        this.url = url;
        this.listener = listener;
        sqlString = "";
        dataType = DataType.Errors;
    }

    @Override
    protected String doInBackground(String... strings)
    {

        int timeout = 60000;
        HttpURLConnection c = null;
        try
        {
            url = addParametersToURL(url,"sql",sqlString);
            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            URL temp = c.getURL();

            c.connect();
            int status = c.getResponseCode();
            String result = "";
            switch (status)
            {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    br.close();
                    result = sb.toString();
                    break;
                default:
                    return "ERROR";
            }
            if(result.contains("ERROR"))
            {
                return "ERROR";
            }
            if(dataType == DataType.Employees)
            {
                DataBase.parseEmployeeData(result);
            }
            return "DONE";

        }
        catch (MalformedURLException ex)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            if (c != null)
            {
                try
                {
                    c.disconnect();
                }
                catch (Exception ex)
                {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s)
    {
        super.onPostExecute(s);

        if(s != null && listener != null)
        {
            listener.onRequestRecieved(s);
        }
        else
        {
            listener.onError();
        }
    }

    public interface OnServerResponseListener
    {
        void onRequestRecieved(String responseData);
        void onError();
    }

    public void sqlRequestEmployees()
    {
        sqlString = "SELECT * FROM Employees";
        dataType = DataType.Employees;
    }

    public void sqlRequestChemicals()
    {
        sqlString = "SELECT * FROM Chemicals";
        dataType = DataType.Chemicals;
    }

    private String addParametersToURL(String urlStr, String name, String value)
    {
        urlStr += "?"+name +"="+value;
        return urlStr;
    }


}