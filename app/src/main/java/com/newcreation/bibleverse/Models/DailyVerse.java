package com.newcreation.bibleverse.Models;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bengeos on 9/8/16.
 */

public class DailyVerse {
    private int ID;
    private Date time;
    private DateFormat myDateFormat;

    public DailyVerse() {
        this.myDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(String time_str) throws ParseException {

        this.time = myDateFormat.parse(time_str);
    }

    public DateFormat getMyDateFormat() {
        return myDateFormat;
    }

    public void setMyDateFormat(DateFormat myDateFormat) {
        this.myDateFormat = myDateFormat;
    }
}
