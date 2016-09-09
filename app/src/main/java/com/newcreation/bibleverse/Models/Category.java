package com.newcreation.bibleverse.Models;

/**
 * Created by bengeos on 9/6/16.
 */

public class Category {
    private int ID,SerID;
    private String Name;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getSerID() {
        return SerID;
    }

    public void setSerID(int serID) {
        SerID = serID;
    }
}
