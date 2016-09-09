package com.newcreation.bibleverse;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;


import com.newcreation.bibleverse.Database.Database;
import com.newcreation.bibleverse.Models.Verse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bengeos on 9/5/16.
 */

public class VersesFragment extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private Database myDatabase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verse_fragment);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view2);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myDatabase = new Database(getApplicationContext());
        int CategoryID = Integer.parseInt(getIntent().getExtras().getString("CategoryID"));
        List<Verse> items = myDatabase.GetAllVersesByCategory(CategoryID);
        adapter = new VerseFragmentAdapter(this,items);
        recyclerView.setAdapter(adapter);
    }
}
