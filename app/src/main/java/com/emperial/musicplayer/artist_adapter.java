package com.emperial.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.prefs.BackingStoreException;

public class artist_adapter extends BaseAdapter {
    Context cont;
    ArrayList<Artist_model> Artists_;

    public artist_adapter (Context context,
                         ArrayList<Artist_model> Artist){
        this.Artists_=Artist;
        this.cont=context;
    }
    @Override
    public int getCount() {
        return Artists_.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= LayoutInflater.from(cont);
        View itemview=inflater.inflate(R.layout.griditem,null);

        TextView albimname=itemview.findViewById(R.id.album_name);
        albimname.setText(Artists_.get(i).Name);
        return itemview;
    }


}
