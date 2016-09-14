package com.newcreation.bibleverse;

import android.app.Application;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;
import com.github.kittinunf.fuel.core.Request;
import com.github.kittinunf.fuel.core.Response;
import com.google.gson.Gson;
import com.newcreation.bibleverse.Database.Database;
import com.newcreation.bibleverse.Models.Category;
import com.newcreation.bibleverse.Models.Verse;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.List;

import kotlin.Pair;

import static android.support.v7.widget.StaggeredGridLayoutManager.TAG;

/**
 * Created by bengeos on 9/6/16.
 */

public class BibleVerse extends Application {
    private static String API_URL = "http://192.168.0.87/bibleverse/ben.php";
    private static String TAG = "Sync_Serv";
    private static List<Pair<String, String>> Send_Param;
    private static NotificationManager myNotificationManager;

    private static Database myDatabase;
    private static Context myContext;

    @Override
    public void onCreate() {
        super.onCreate();
        myContext = this;
        myDatabase = new Database(this);
        if(myDatabase.count(Database.VersesTable)== 0){
            myDatabase.Delete_All(Database.VersesTable);
            myDatabase.Delete_All(Database.CategoryTable);

            ContentValues cv = new ContentValues();
            cv.put(Database.CategoryColumn[1],"-1");
            cv.put(Database.CategoryColumn[2],"በመንፈስ ድሆች የሆኑ");
            myDatabase.insert(Database.CategoryTable,cv);
            cv.put(Database.CategoryColumn[1],"-2");
            cv.put(Database.CategoryColumn[2],"የሚምሩ ብፁዓን");
            myDatabase.insert(Database.CategoryTable,cv);
            cv.put(Database.CategoryColumn[1],"3");
            cv.put(Database.CategoryColumn[2],"እግዚአብሔር በደልን");
            myDatabase.insert(Database.CategoryTable,cv);
            cv.put(Database.CategoryColumn[1],"4");
            cv.put(Database.CategoryColumn[2],"ርኅሩህ የተባረከ ይሆናል");
            myDatabase.insert(Database.CategoryTable,cv);


            /// Verses
            ContentValues cv1 = new ContentValues();
            cv1.put(Database.VersesColumn[1],"-1");
            cv1.put(Database.VersesColumn[2],"1");
            cv1.put(Database.VersesColumn[3],"በእግዚአብሔር የታመነ እምነቱም እግዚአብሔር የሆነ ሰው ቡሩክ ነው።");
            cv1.put(Database.VersesColumn[4],"ንቢተ ኤርምያስ 17:7");
            myDatabase.insert(Database.VersesTable,cv1);

            cv1.put(Database.VersesColumn[1],"-2");
            cv1.put(Database.VersesColumn[2],"1");
            cv1.put(Database.VersesColumn[3],"በመንፈስ ድሆች የሆኑ ብፁዓን ናቸው፥ መንግሥተ ሰማያት የእነርሱ ናትና።");
            cv1.put(Database.VersesColumn[4],"የማቴዎስ ወንጌል 5:3");
            myDatabase.insert(Database.VersesTable,cv1);
            cv1.put(Database.VersesColumn[1],"3");
            cv1.put(Database.VersesColumn[2],"1");
            cv1.put(Database.VersesColumn[3],"በእምነትም ያልሆነ ሁሉ ኃጢአት ነው።");
            cv1.put(Database.VersesColumn[4],"ወደ ሮሜ ሰዎች 14:23");
            myDatabase.insert(Database.VersesTable,cv1);
            cv1.put(Database.VersesColumn[2],"1");
            cv1.put(Database.VersesColumn[3],"እንግዲህ ሁልጊዜ ታምነን፥ በእምነት እንጂ በማየት አንመላለስምና በሥጋ ስናድር ከጌታ ተለይተን በስደት እንዳለን የምናውቅ ከሆንን");
            cv1.put(Database.VersesColumn[4]," ወደ ቆሮንቶስ ሰዎች 5:7");
            myDatabase.insert(Database.VersesTable,cv1);
        }



    }

    public static void Update() {
        Send_Param = new ArrayList<Pair<String, String>>();
        Log.i(TAG,"Sync Service Started");
        Fuel.get(API_URL).responseString(new Handler<String>() {
            @Override
            public void success(@NotNull Request request, @NotNull Response response, String s) {
                Gson myGson = new Gson();
                Log.i(TAG,response.toString());

                try {
                    // add your message here
                    sendNotification("Message Title","Your Message Body");
                    JSONObject myObject = (JSONObject) new JSONTokener(s).nextValue();
                    if (!myObject.isNull("Categories")) {
                        JSONArray json_Categories = myObject.getJSONArray("Categories");
                        if(json_Categories.length()>0){
                            for(int i=0;i<json_Categories.length();i++){
                                JSONObject obj = json_Categories.getJSONObject(i);
                                ContentValues cv = new ContentValues();
                                cv.put(Database.CategoryColumn[1], obj.getString("ID"));
                                cv.put(Database.CategoryColumn[2], obj.getString("Name"));
                                int id = Integer.parseInt(obj.getString("ID"));
                                Log.i(TAG,"ID:-> "+id);
                                Category category = myDatabase.GetCategoryByID(id);
                                if(category == null){
                                    myDatabase.insert(Database.CategoryTable,cv);
                                }

                            }
                        }
                    }
                    if (!myObject.isNull("Verses")) {
                        JSONArray json_Categories = myObject.getJSONArray("Verses");
                        if(json_Categories.length()>0){
                            for(int i=0;i<json_Categories.length();i++){
                                JSONObject obj = json_Categories.getJSONObject(i);
                                ContentValues cv = new ContentValues();
                                cv.put(Database.VersesColumn[1], obj.getString("ID"));
                                cv.put(Database.VersesColumn[2], obj.getString("CategoryID"));
                                cv.put(Database.VersesColumn[3], obj.getString("Body"));
                                cv.put(Database.VersesColumn[4], obj.getString("Verse"));
                                int id = Integer.parseInt(obj.getString("ID"));
                                Verse verse = myDatabase.GetVerseByID(id);
                                if(verse == null){
                                    myDatabase.insert(Database.VersesTable,cv);
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            @Override
            public void failure(@NotNull Request request, @NotNull Response response, @NotNull FuelError fuelError) {
                Log.i(TAG,fuelError.toString());
            }
        });
    }
    public static void sendNotification(String title,String msg) {
        myNotificationManager = (NotificationManager)
                myContext.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(myContext, 0, new Intent(myContext, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(myContext)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle(title)
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(msg))
                        .setContentText(msg);
        try{

            Uri uri = Uri.parse("android.resource://"+myContext.getPackageName()+"/"+R.raw.ringtone);
            mBuilder.setSound(uri);
        }catch (Exception e){

        }

        mBuilder.setContentIntent(contentIntent);
        myNotificationManager.notify(1, mBuilder.build());
    }
}
