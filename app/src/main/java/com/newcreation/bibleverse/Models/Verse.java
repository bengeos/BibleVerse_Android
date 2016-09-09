package com.newcreation.bibleverse.Models;

/**
 * Created by bengeos on 9/6/16.
 */

public class Verse {
    private int ID,SerID,CategoryID;
    private String Body,Verse;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSerID() {
        return SerID;
    }

    public void setSerID(int serID) {
        SerID = serID;
    }

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public String getVerse() {
        return Verse;
    }

    public void setVerse(String verse) {
        Verse = verse;
    }
}
