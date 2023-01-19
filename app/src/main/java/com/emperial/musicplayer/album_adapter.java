package com.emperial.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class album_adapter extends BaseAdapter {
    Context cont;
    ArrayList<Album_Model> Albums_;

    public album_adapter(Context context,
                         ArrayList<Album_Model> Albums){

this.Albums_=Albums;
        this.cont=context;
    }
    @Override
    public int getCount() {
        return Albums_.size();
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

        ConstraintLayout layout=itemview.findViewById(R.id.gridlay);
        TextView albimname=itemview.findViewById(R.id.album_name);
        ImageView albumimage=itemview.findViewById(R.id.album_image);
        albimname.setText(Albums_.get(i).Name);

        return itemview;
    }


}
