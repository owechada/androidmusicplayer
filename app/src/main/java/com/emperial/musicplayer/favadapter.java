package com.emperial.musicplayer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class favadapter extends  RecyclerView.Adapter<favadapter.Myviewholder>{
Context cont;
itemclickinterface itt;
    public favadapter(Context cont, music_file musicfiles,itemclickinterface itc) {
        this.cont = cont;
        this.itt=itc;
        this.musicfiles = musicfiles;
    }



    music_file  musicfiles;


    @NonNull
    @Override
    public favadapter.Myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(cont);
        View view = inflater.inflate(R.layout.rowview, parent, false);


        return new Myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Myviewholder holder, int position) {

        holder.textview.setText(musicfiles.get_song_name(position));
        holder.artist.setText(musicfiles.get_song_name(position));


        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itt.onitemclick(holder.getAdapterPosition());
            }
        });
        holder.container.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                holder.del.setVisibility(View.VISIBLE);
                holder.del.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                      //  deletesong(position);

                        holder.del.setVisibility(View.GONE);
                    }
                });

return  false;
            }
        });

    }


    @Override
    public int getItemCount() {
        return musicfiles.getfilescount();
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
