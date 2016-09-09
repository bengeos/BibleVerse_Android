package com.newcreation.bibleverse;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.newcreation.bibleverse.Database.Database;
import com.newcreation.bibleverse.Models.Category;

import java.util.List;

/**
 * Created by bengeos on 9/5/16.
 */

public class CategoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private Context context;
    private Database myDatabase;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.category_fragment,null);
        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        context = getActivity();
        myDatabase = new Database(context);





        List<Category> items = myDatabase.GetAllCategories();
        adapter = new CategoryFragmentAdapter(context,items);
        recyclerView.setAdapter(adapter);
        return view;
    }
}
