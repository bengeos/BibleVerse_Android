package com.newcreation.bibleverse;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.newcreation.bibleverse.Models.Category;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bengeos on 9/5/16.
 */

public class CategoryFragmentAdapter extends RecyclerView.Adapter<CategoryFragmentAdapter.DataObjectHolder> {

    private static List<Category> MyCategories = new ArrayList<Category>();
    private static VerseFragmentAdapter.MyClickListener myClickListener;

    private static Context myContext;

    public CategoryFragmentAdapter(Context myContext,List<Category> items) {
        this.myContext = myContext;
        MyCategories = items;

    }

    @Override
    public CategoryFragmentAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        CategoryFragmentAdapter.DataObjectHolder dataObjectHolder = new CategoryFragmentAdapter.DataObjectHolder(view);

        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.Title.setText(MyCategories.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return MyCategories.size();
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        private TextView Title,SubTitle;
        public DataObjectHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.txt_category_name);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(myContext,VersesFragment.class);
            Bundle b = new Bundle();
            b.putString("CategoryID",String.valueOf(MyCategories.get(getAdapterPosition()).getSerID()));
            intent.putExtras(b);
            myContext.startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

}
