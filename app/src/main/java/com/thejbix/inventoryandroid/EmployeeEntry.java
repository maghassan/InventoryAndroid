package com.thejbix.inventoryandroid;

import android.util.Log;

/**
 * DataType that holds an employee entry
 */
public class EmployeeEntry
{
    private int id;
    private String name;

    public EmployeeEntry()
    {
        id = 0;
        name = "";
    }

    public EmployeeEntry(int id, String name)
    {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void print()
    {
        Log.d("Print","Employee: " + this.id + " " + this.name);
    }



    @Override
    public String toString()
    {
        return name;
    }
}
