package com.newcreation.bibleverse;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.newcreation.bibleverse.Models.Verse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bengeos on 9/5/16.
 */

public class VerseFragmentActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.verse_fragment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        List<Verse> items = new ArrayList<Verse>();
        items.add(new Verse());
        items.add(new Verse());
        items.add(new Verse());
        VerseFragmentAdapter adapter = new VerseFragmentAdapter(this,items);
        recyclerView.setAdapter(adapter);
    }
}
