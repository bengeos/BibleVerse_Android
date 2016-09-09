package com.newcreation.bibleverse;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.newcreation.bibleverse.Database.Database;
import com.newcreation.bibleverse.Models.Verse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bengeos on 9/6/16.
 */

public class Favourites extends AppCompatActivity {
    private static RecyclerView recyclerView;
    private static RecyclerView.Adapter adapter;
    private static RecyclerView.LayoutManager layoutManager;
    private static Context myContext;
    private static Database myDatabase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verse_fragment);
        myContext = getApplicationContext();
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view2);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myDatabase = new Database(getApplicationContext());
        List<Verse> items = myDatabase.GetAllFavourite();
        adapter = new FavouritesAdapter(this,items);
        recyclerView.setAdapter(adapter);
    }
    public static void UpdateList(){
        List<Verse> items = myDatabase.GetAllFavourite();
        adapter = new FavouritesAdapter(myContext,items);
        recyclerView.setAdapter(adapter);
    }
}
