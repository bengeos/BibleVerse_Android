
package com.newcreation.bibleverse.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.newcreation.bibleverse.Models.Category;
import com.newcreation.bibleverse.Models.DailyVerse;
import com.newcreation.bibleverse.Models.Verse;

import java.util.ArrayList;
import java.util.List;

public class Database {
    public static final String CategoryTable = "VerseCategory";
    public static final String[] CategoryColumn = { "id", "SerID","Name"};

    public static final String VersesTable = "BibleVerses";
    public static final String[] VersesColumn = { "id", "SerID","CategoryID","Body","Verse"};

    public static final String FavouriteTable = "FavouriteVerse";
    public static final String[] FavouriteColumn = { "id", "VerseID"};

    public static final String DailyTable = "DailyVerse";
    public static final String[] DailyColumn = { "id", "Date_Time"};

	private SQLiteDatabase myDatabase;
	private SQL_Helper mySQL;
	private Context myContext;
    public static final String TAG = "Database";

    public Database(Context context){
        myContext = context;
        mySQL = new SQL_Helper(myContext);
        myDatabase = mySQL.getWritableDatabase();
        mySQL.createTables(CategoryTable, CategoryColumn);
        mySQL.createTables(VersesTable, VersesColumn);
        mySQL.createTables(FavouriteTable, FavouriteColumn);
        mySQL.createTables(DailyTable, DailyColumn);
    }

    public long insert(String DB_Table,ContentValues cv){
        long state = myDatabase.insert(DB_Table, null, cv);
        Log.i(TAG, "Inserting->: " + cv.toString());
        return state;
    }
    public long Delete_All(String DB_Table){
        long state = myDatabase.delete(DB_Table, null, null);
        return state;
    }
    public long Delete(String DB_Table,String where,String[] arg){
        long state = myDatabase.delete(DB_Table, where, arg);
        return state;
    }
    public long remove(String DB_Table,int id){
        String[] args = {""+id};
        long val = myDatabase.delete(DB_Table, "id = ?", args);
        return val;
    }

    public long update(String DB_Table,ContentValues cv,int id){
        Log.i(TAG, "Updating Table: "+DB_Table);
        String[] args = {""+id};
        long state = myDatabase.update(DB_Table, cv, "id = ?", args);
        Log.i(TAG, "Updating Data: "+cv.toString());
        Log.i(TAG, "Updating Completed: "+state+"\n");
        return state;
    }

    public int count(String DB_Table){
        Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
        if(c != null){
            return c.getCount();
        }else{
            return 0;
        }
    }
    public Cursor getAll(String DB_Table){
        Cursor c = myDatabase.query(DB_Table, getColumns(DB_Table), null, null, null, null, null);
        return c;
    }

    public DailyVerse GetDailyVerse(){
        DailyVerse found = null;
        try{
            Cursor c = myDatabase.query(DailyTable, getColumns(DailyTable), null, null, null, null, null);
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                c.moveToPosition(i);
                DailyVerse dis = new DailyVerse();
                dis.setID(Integer.parseInt(c.getString(c.getColumnIndex(DailyColumn[0]))));
                dis.setTime(c.getString(c.getColumnIndex(DailyColumn[1])));
                found = dis;
            }
        }catch (Exception e){

        }
        return found;
    }
    public List<Category> GetAllCategories(){
        ArrayList<Category> found = new ArrayList<Category>();
        try{
            Cursor c = myDatabase.query(CategoryTable, getColumns(CategoryTable), null, null, null, null, null);
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                c.moveToPosition(i);
                Category dis = new Category();
                dis.setID(Integer.parseInt(c.getString(c.getColumnIndex(CategoryColumn[0]))));
                dis.setSerID(Integer.parseInt(c.getString(c.getColumnIndex(CategoryColumn[1]))));
                dis.setName(c.getString(c.getColumnIndex(CategoryColumn[2])));
                found.add(dis);
            }
        }catch (Exception e){

        }
        return found;
    }
    public Category GetCategoryByID(int catID){
        try{
            Cursor c = myDatabase.query(CategoryTable, getColumns(CategoryTable), null, null, null, null, null);
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                c.moveToPosition(i);
                Category dis = new Category();
                dis.setID(Integer.parseInt(c.getString(c.getColumnIndex(CategoryColumn[0]))));
                dis.setSerID(Integer.parseInt(c.getString(c.getColumnIndex(CategoryColumn[1]))));
                dis.setName(c.getString(c.getColumnIndex(CategoryColumn[2])));
                if(dis.getSerID() == catID){
                    return dis;
                }
            }
        }catch (Exception e){

        }
        return null;
    }
    public List<Verse> GetAllVersesByCategory(int CategoryID){
        ArrayList<Verse> found = new ArrayList<Verse>();
        try{
            Cursor c = myDatabase.query(VersesTable, getColumns(VersesTable), null, null, null, null, null);
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                c.moveToPosition(i);
                Verse dis = new Verse();
                dis.setID(Integer.parseInt(c.getString(c.getColumnIndex(VersesColumn[0]))));
                dis.setSerID(Integer.parseInt(c.getString(c.getColumnIndex(VersesColumn[1]))));
                dis.setCategoryID(Integer.parseInt(c.getString(c.getColumnIndex(VersesColumn[2]))));
                dis.setBody(c.getString(c.getColumnIndex(VersesColumn[3])));
                dis.setVerse(c.getString(c.getColumnIndex(VersesColumn[4])));
                if(dis.getCategoryID() == CategoryID){
                    found.add(dis);
                }

            }
        }catch (Exception e){

        }
        return found;
    }
    public List<Verse> GetAllVerses(){
        ArrayList<Verse> found = new ArrayList<Verse>();
        try{
            Cursor c = myDatabase.query(VersesTable, getColumns(VersesTable), null, null, null, null, null);
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                c.moveToPosition(i);
                Verse dis = new Verse();
                dis.setID(Integer.parseInt(c.getString(c.getColumnIndex(VersesColumn[0]))));
                dis.setSerID(Integer.parseInt(c.getString(c.getColumnIndex(VersesColumn[1]))));
                dis.setCategoryID(Integer.parseInt(c.getString(c.getColumnIndex(VersesColumn[2]))));
                dis.setBody(c.getString(c.getColumnIndex(VersesColumn[3])));
                dis.setVerse(c.getString(c.getColumnIndex(VersesColumn[4])));
                found.add(dis);
            }
        }catch (Exception e){

        }
        return found;
    }
    public Verse GetVerseByID(int verseID){
        try{
            Cursor c = myDatabase.query(VersesTable, getColumns(VersesTable), null, null, null, null, null);
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                c.moveToPosition(i);
                Verse dis = new Verse();
                dis.setID(Integer.parseInt(c.getString(c.getColumnIndex(VersesColumn[0]))));
                dis.setSerID(Integer.parseInt(c.getString(c.getColumnIndex(VersesColumn[1]))));
                dis.setCategoryID(Integer.parseInt(c.getString(c.getColumnIndex(VersesColumn[2]))));
                dis.setBody(c.getString(c.getColumnIndex(VersesColumn[3])));
                dis.setVerse(c.getString(c.getColumnIndex(VersesColumn[4])));
                if(dis.getSerID() == verseID){
                    return dis;
                }
            }
        }catch (Exception e){

        }
        return null;
    }
    public Verse GetFavouriteByVerseID(int verseID){
        try{
            Cursor c = myDatabase.query(FavouriteTable, getColumns(FavouriteTable), null, null, null, null, null);
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                c.moveToPosition(i);
                Verse dis = new Verse();
                int id = Integer.parseInt(c.getString(c.getColumnIndex(FavouriteColumn[1])));
                if(id == verseID){
                    dis = GetVerseByID(id);
                    return dis;
                }
            }
        }catch (Exception e){

        }
        return null;
    }
    public List<Verse> GetAllFavourite(){
        ArrayList<Verse> found = new ArrayList<Verse>();
        try{
            Cursor c = myDatabase.query(FavouriteTable, getColumns(FavouriteTable), null, null, null, null, null);
            c.moveToFirst();
            for(int i=0;i<c.getCount();i++){
                c.moveToPosition(i);
                int id = Integer.parseInt(c.getString(c.getColumnIndex(FavouriteColumn[1])));
                Verse dis = GetVerseByID(id);
                if(dis != null){
                    found.add(dis);
                }
            }
        }catch (Exception e){

        }
        return found;
    }

    private String[] getColumns(String DB_Table){
        String[] strs = null;
        if(DB_Table == VersesTable){
            strs = VersesColumn;
        }else if(DB_Table == CategoryTable){
            strs = CategoryColumn;
        }else if(DB_Table == FavouriteTable){
            strs = FavouriteColumn;
        }else if(DB_Table == DailyTable){
            strs = DailyColumn;
        }
        return strs;
    }
}
