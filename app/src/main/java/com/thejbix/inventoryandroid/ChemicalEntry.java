package com.thejbix.inventoryandroid;

import android.util.Log;

/**
 * Created by jaydonbixenman on 11/17/17.
 */

public class ChemicalEntry
{
    private int id;
    private String name;
    private double amount;


    public ChemicalEntry()
    {
        id = 0;
        name = "";
        amount = 0;
    }

    public ChemicalEntry(int id, String name, double amount)
    {
        this.id = id;
        this.name = name;
        this.amount = amount;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void print()
    {
        Log.d("Print","Chemical: " + id + ": " + name + " " + amount);
    }

    @Override
    public String toString()
    {
        return name;
    }
}
