package com.newcreation.bibleverse;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.newcreation.bibleverse.Database.Database;
import com.newcreation.bibleverse.Models.Verse;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bengeos on 9/5/16.
 */

public class VerseFragmentAdapter extends RecyclerView.Adapter<VerseFragmentAdapter.DataObjectHolder> {
    private static List<Verse> MyVerses = new ArrayList<Verse>();
    private static VerseFragmentAdapter.MyClickListener myClickListener;
    private static Database myDatabase;

    private static Context myContext;

    public VerseFragmentAdapter(Context myContext,List<Verse> items) {
        this.myContext = myContext;
        MyVerses = items;
        myDatabase = new Database(myContext);

    }

    @Override
    public VerseFragmentAdapter.DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.verse_item, parent, false);
        VerseFragmentAdapter.DataObjectHolder dataObjectHolder = new VerseFragmentAdapter.DataObjectHolder(view);

        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(VerseFragmentAdapter.DataObjectHolder holder, int position) {
           holder.Title.setText(MyVerses.get(position).getBody());
           holder.SubTitle.setText(MyVerses.get(position).getVerse());
    }

    @Override
    public int getItemCount() {
        return MyVerses.size();
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{
        private TextView Title,SubTitle;
        public DataObjectHolder(View itemView) {
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.txt_verse_title);
            SubTitle = (TextView) itemView.findViewById(R.id.txt_verse_subtitle);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
//            Intent intent = new Intent(myContext,VersesFragment.class);
//            Bundle b = new Bundle();
//            // b.putString("CategoryID",String.valueOf(MyCategories.get(getAdapterPosition()).getSerID()));
//            intent.putExtras(b);
//            myContext.startActivity(intent);
            final Dialog myDialog = new Dialog(myContext);
            myDialog.setContentView(R.layout.show_dialog);
            TextView verse = (TextView) myDialog.findViewById(R.id.txt_dialog_verse);
            verse.setText(MyVerses.get(getAdapterPosition()).getBody());
            TextView verse_num = (TextView) myDialog.findViewById(R.id.txt_dialog_verse_num);
            verse_num.setText(MyVerses.get(getAdapterPosition()).getVerse());
            Button btn_share = (Button) myDialog.findViewById(R.id.btn_dialog_share);
            btn_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myDialog.cancel();
                }
            });
            Button btn_like = (Button) myDialog.findViewById(R.id.btn_dialog_like);
            btn_like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int VerseID = MyVerses.get(getAdapterPosition()).getSerID();
                    ContentValues cv = new ContentValues();
                    cv.put(Database.FavouriteColumn[1],VerseID);
                    Verse verse = myDatabase.GetFavouriteByVerseID(VerseID);
                    if(verse == null){
                        long x = myDatabase.insert(Database.FavouriteTable,cv);
                        if(x>0){
                            Toast.makeText(myContext,"ወደውታ",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(myContext,"ማስቅመጥ አልተቻለም",Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(myContext,"ከዚህ በፊትም ወደውታል",Toast.LENGTH_SHORT).show();
                    }
                    myDialog.cancel();
                }
            });
            btn_share = (Button) myDialog.findViewById(R.id.btn_dialog_share);
            btn_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent share = new Intent(Intent.ACTION_SEND);
                    share.setType("text/plain");
                    String message = MyVerses.get(getAdapterPosition()).getBody()+"\n"+MyVerses.get(getAdapterPosition()).getVerse();
                    share.putExtra(Intent.EXTRA_TEXT, message);
                    myContext.startActivity(Intent.createChooser(share, "አካፍ"));
                }
            });
            myDialog.show();
        }

        @Override
        public boolean onLongClick(View v) {
            SaveVerse(MyVerses.get(getAdapterPosition()).getSerID());
            return false;
        }
    }
    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
    public static void ShowDialog(final String message) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:

                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        builder.setMessage(message )
                .setPositiveButton("እሺ", dialogClickListener)
                .show();
    }
    public static void SaveVerse(final int VerseID) {

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        ContentValues cv = new ContentValues();
                        cv.put(Database.FavouriteColumn[1],VerseID);
                        Verse verse = myDatabase.GetFavouriteByVerseID(VerseID);
                        if(verse == null){
                            long x = myDatabase.insert(Database.FavouriteTable,cv);
                            if(x>0){
                                Toast.makeText(myContext,"ወደውታ",Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(myContext,"ለማስቅመጥ አልተቻል",Toast.LENGTH_SHORT).show();
                            }

                        }else {
                            Toast.makeText(myContext,"ከዚህ በፊትም ተቀምጣ",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        //No button clicked
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
        builder.setMessage("ይህ ጥቅስ እንዲቀመጥ ይፈልጋሉ?" )
                .setPositiveButton("አዎ", dialogClickListener)
                .setNegativeButton("አይ",dialogClickListener)
                .show();
    }
}
