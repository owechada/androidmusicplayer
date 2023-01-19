package com.emperial.musicplayer;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Myadapter extends RecyclerView.Adapter<Myadapter.Myviewholder> {


    Context cont;
    music_file mscfiles;
    private itemclickinterface itemclickinterface;

    public Myadapter(Context context, music_file music_files, itemclickinterface itemclickinterface) {

        cont = context;
        this.mscfiles = music_files;
        this.itemclickinterface = itemclickinterface;

    }


    @NonNull
    @Override
    public Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(cont);
        View view = inflater.inflate(R.layout.rowview, parent, false);
        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {
        holder.textview.setText(mscfiles.get_song_name(position));
holder.artist.setText(mscfiles.get_artist_name(position));

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemclickinterface.onitemclick(position);
            }
        });

        holder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                holder.del.setVisibility(View.VISIBLE);
                holder.del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        deletesong(position);

                        holder.del.setVisibility(View.GONE);
                    }
                });


                return false;
            }
        });

    }


    public void deletesong(int pos)
    {
        Toast.makeText(cont, "Are you sure you want to delete this at "+pos+"?", Toast.LENGTH_SHORT).show();

    }

    @Override
    public int getItemCount() {
        return mscfiles.getfilescount();
    }

    public class Myviewholder extends RecyclerView.ViewHolder {
        LinearLayout container;
        TextView textview,artist;
        ImageView del;

        public Myviewholder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.row_container);
            textview = itemView.findViewById(R.id.rowtext);
       artist = itemView.findViewById(R.id.rowartt);
       del=itemView.findViewById(R.id.row_del);


        }
    }
}
