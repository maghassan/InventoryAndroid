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
 * This class sends a GET request to a php file.
 * That php file then sends the request on to the database and returns whatever data is acquired in
 *   json form.
 * It is an AsyncTask because the web request can not be called on the main UI Thread
 * Hence there is a custom listener has been built in this file that each class can add
 *   implementations to.
 */
public class MySqlServerRequest extends AsyncTask<String, Void, String>
{
    public enum DataType
    {
        Employees, Chemicals, Orders, Errors, Edits, MULTI
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

    public MySqlServerRequest(String url)
    {
        this.url = url;
        this.listener = null;
        sqlString = "";
        dataType = DataType.Errors;
    }

    //Call Procedure
    @Override
    protected String doInBackground(String... strings)
    {
        //Timeout after 20 seconds
        int timeout = 20000;
        HttpURLConnection c = null;
        try
        {
            url = addParametersToURL(url,"sql",sqlString);
            if(dataType == DataType.MULTI)
            {
                url = addAdditionalParamters(url,"multi", "true");
            }
            else
            {
                url = addAdditionalParamters(url, "multi", "false");
            }
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

            //The string is sent to be parsed by the correct function
            if(result.contains("ERROR"))
            {
                return "ERROR";
            }
            if(dataType == DataType.Employees)
            {
                DataBase.parseEmployeeData(result);
            }
            else if(dataType == DataType.Chemicals)
            {
                DataBase.parseChemicalData(result);
            }
            else if(dataType == DataType.Orders)
            {
                DataBase.parseOrderData(result);
            }
            else if(dataType == DataType.Edits)
            {
                Log.d("Print",result);
                return result;
            }
            else if(dataType == DataType.MULTI)
            {
                Log.d("Print",result);
                return result;
            }
            return dataType.toString();

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
        else if(listener != null)
        {
            listener.onError();
        }

    }

    public void setListener(OnServerResponseListener listener)
    {
        this.listener = listener;
    }

    //custom Listener
    public interface OnServerResponseListener
    {
        void onRequestRecieved(String responseData);
        void onError();
    }

    //Set the sql request to be sent to the php file
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

    public void sqlRequestPendingOrders()
    {
        sqlString = "Select * FROM Orders WHERE status != 3 AND archived = 0";
        dataType = DataType.Orders;
    }

    public void sqlRequestNonReportedOrders()
    {
        sqlString = "Select * FROM Orders WHERE reported = 0 AND archived = 0";
        dataType = DataType.Orders;
    }

    public void sqlEditChemical(ChemicalEntry e)
    {
        sqlString = "UPDATE Chemicals SET name = " + e.getName() + ", amount = " + e.getAmount()
                + " WHERE id = " + e.getId();
        dataType = DataType.Edits;
    }

    public void sqlEditOrder(OrderEntry e)
    {
        sqlString = "update Orders set reported = "+ e.isReported() +", reporter = " + e.getReporter()
            + " WHERE id = " + e.getId();
        dataType = DataType.Edits;
    }

    public void sqlReportOrder(OrderEntry e, ChemicalEntry[] c)
    {
        sqlString = "update Orders set reported = "+ e.isReported() +", reporter = " + e.getReporter()
                + " WHERE id = " + e.getId() + ";";

        for(int i = 0;i<5;i++)
        {
            if(c[i] != null)
            {
                sqlString += "UPDATE Chemicals SET amount = " + c[i].getAmount()
                        + " WHERE id = " + c[i].getId() + ";";

            }
        }

        dataType = DataType.MULTI;

    }

    //Adds the first parameter to url str
    private String addParametersToURL(String urlStr, String name, String value)
    {
        urlStr += "?"+name +"="+value;
        return urlStr;
    }

    //adds subsequence paramters to url str
    private String addAdditionalParamters(String urlStr, String name, String value)
    {
        urlStr += "&" +name + "="+value;
        return urlStr;
    }


}
