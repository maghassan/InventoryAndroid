package com.thejbix.inventoryandroid;

import android.util.Log;

import java.sql.Date;

/**
 * DataType that stores an order entry
 */
public class OrderEntry
{
    private int id;
    private String owner, field;
    private Date dateCreated, dateCompleted;
    private int status;
    private int assignedPilot,reporter;
    private boolean reported,archived;
    private int[] chemId = new int[5];
    private double[] chemRate = new double[5];
    private int acres;

    public OrderEntry()
    {
        id = 0;
        owner = field = "";
        dateCreated = dateCompleted = new Date(0);
        status = 0;
        assignedPilot = reporter = 0;
        reported = archived = false;
        for(int i = 0;i<5;i++)
        {
            chemId[i] = 0;
            chemRate[i] = 0;
        }
        acres = 0;
    }

    OrderEntry(int id)
    {
        this.id = id;
        owner = field = "";
        dateCreated = dateCompleted = new Date(0);
        status = 0;
        assignedPilot = reporter = 0;
        reported = archived = false;
        for(int i = 0;i<5;i++)
        {
            chemId[i] = 0;
            chemRate[i] = 0;
        }
        acres = 0;
    }

    public int getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateCompleted() {
        return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
        this.dateCompleted = dateCompleted;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getAssignedPilot() {
        return assignedPilot;
    }

    public void setAssignedPilot(int assignedPilot) {
        this.assignedPilot = assignedPilot;
    }

    public int getReporter() {
        return reporter;
    }

    public void setReporter(int reporter) {
        this.reporter = reporter;
    }

    public boolean isReported() {
        return reported;
    }

    public void setReported(boolean reported) {
        this.reported = reported;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public int getChemId(int i) {
        return chemId[i];
    }

    public void setChemId(int i, int chemId) {
        this.chemId[i] = chemId;
    }

    public double getChemRate(int i) {
        return chemRate[i];
    }

    public void setChemRate(int i, double chemRate) {
        this.chemRate[i] = chemRate;
    }

    public int getAcres() {
        return acres;
    }

    public void setAcres(int acres) {
        this.acres = acres;
    }

    public void print()
    {
        Log.d("Print", "Order: " + id + ": " + owner + " " + field);
    }
}
