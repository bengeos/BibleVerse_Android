package com.newcreation.bibleverse;

import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.newcreation.bibleverse.Database.Database;
import com.newcreation.bibleverse.Models.DailyVerse;
import com.newcreation.bibleverse.Models.Verse;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Toolbar toolbar;
    private ViewPager viewPager;
    private Database myDatabase;
    private Calendar myCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDatabase = new Database(this);
        myCalendar = Calendar.getInstance();

        // fetch new updates from the network
        BibleVerse.Update();


        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.nav_open,R.string.nav_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new CategoryFragment(),"Top Rated");
        viewPager.setAdapter(adapter);

        DailyVerse dailyVerse = myDatabase.GetDailyVerse();
        List<Verse> verses = myDatabase.GetAllVerses();
        if(dailyVerse != null){
            DailyVerse newdaily = new DailyVerse();
            try {
                newdaily.setTime(myCalendar.getTime().toString());
                if(newdaily.getTime() != dailyVerse.getTime()){
                    Collections.shuffle(verses);
                    ContentValues cv = new ContentValues();
                    cv.put(Database.DailyColumn[1],newdaily.getTime().toString());
                    myDatabase.Delete_All(Database.DailyTable);
                    myDatabase.insert(Database.DailyTable,cv);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            DailyVerse newdaily = new DailyVerse();
            try {
                newdaily.setTime(myCalendar.getTime().toString());
                Collections.shuffle(verses);
                ContentValues cv = new ContentValues();
                cv.put(Database.DailyColumn[1],newdaily.getTime().toString());
                myDatabase.Delete_All(Database.DailyTable);
                myDatabase.insert(Database.DailyTable,cv);
            }catch (ParseException e){

            }

        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.nav_liked){
            Intent intent = new Intent(this,Favourites.class);
            Bundle b = new Bundle();
            // b.putString("CategoryID",String.valueOf(MyCategories.get(getAdapterPosition()).getSerID()));
            intent.putExtras(b);
            startActivity(intent);
        }else if(item.getItemId() == R.id.nav_exit){
            finish();
        }else if(item.getItemId() == R.id.nav_facebook){
            Uri uri = Uri.parse("http://www.facebook.com"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }else if(item.getItemId() == R.id.nav_help){
            Dialog mydialog  = new Dialog(this);
            mydialog.setContentView(R.layout.about);
            TextView txt = (TextView) mydialog.findViewById(R.id.txt_about);



            mydialog.show();
        }else if(item.getItemId() == R.id.nav_about){
            Dialog mydialog  = new Dialog(this);
            mydialog.setContentView(R.layout.about);
            TextView txt = (TextView) mydialog.findViewById(R.id.txt_about);



            mydialog.show();
        }else if(item.getItemId() == R.id.nav_more){
            Uri uri = Uri.parse("http://www.facebook.com"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }else if(item.getItemId() == R.id.nav_share){
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plain");
            String message = "";
            share.putExtra(Intent.EXTRA_TEXT, message);
            startActivity(Intent.createChooser(share, "አካፍ"));
        }else if(item.getItemId() == R.id.nav_rating){
            Uri uri = Uri.parse("http://www.google.com"); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
