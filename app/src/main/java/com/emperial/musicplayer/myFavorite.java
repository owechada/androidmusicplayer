package com.emperial.musicplayer;

import android.content.Context;

import java.time.Duration;
import java.util.ArrayList;


public class myFavorite {

    ArrayList<String> names;
    ArrayList<String> paths;
    ArrayList<String> artists;
    ArrayList<String> durations;

    TinyDB tinyDB;
    String path;
    String name;
    String artist;
    String duration;
    String indexname = "Myfav";
    int curr = 0;
    Context context;

    //empty constructor
    public myFavorite() {
    }

    public myFavorite(Context context, String path, String name, String artist, String duration) {
        this.path = path;
        this.name = name;
        this.artist = artist;
        this.duration = duration;
        this.context=context;

        tinyDB = new TinyDB(context);
        names = tinyDB.getListString("favnames");
        artists = tinyDB.getListString("favarts");
        durations = tinyDB.getListString("favdur");
        paths = tinyDB.getListString("favpath");

        if (names == null) {

            names = new ArrayList<>();
            artists = new ArrayList<>();
            durations = new ArrayList<>();
            paths = new ArrayList<>();

        }


        //   curr=tinyDB.getInt("favnum");


    }




    public void Commit() {
        names.add(name);
        artists.add(artist);
        durations.add(duration);
        paths.add(path);

        tinyDB.putListString("favnames", names);
        tinyDB.putListString("favarts", artists);
        tinyDB.putListString("favpath", paths);
        tinyDB.putListString("favdur", durations);


    }

    public int getfavcount() {

        return names.size();
    }


    public music_file collatefavs(Context cont) {



        tinyDB = new TinyDB(cont);
        names = tinyDB.getListString("favnames");
        artists = tinyDB.getListString("favarts");
        durations = tinyDB.getListString("favdur");
        paths = tinyDB.getListString("favpath");

        if (names == null) {

            names = new ArrayList<>();
            artists = new ArrayList<>();
            durations = new ArrayList<>();
            paths = new ArrayList<>();

        }

        music_file favmsc = new music_file();
        if (names != null) {
            for (int i = 0; names.size() > i; i++) {
                favmsc.add_song_name(names.get(i));
            }
        }

        if (artists != null) {
            for (int i = 0; artists.size() > i; i++) {
                favmsc.add_artist_name(artists.get(i));
            }
        }

        if (durations != null) {
            for (int i = 0; durations.size() > i; i++) {
                favmsc.add_duration(durations.get(i));
            }
        }


        if (paths != null) {
            for (int i = 0; paths.size() > i; i++) {
                favmsc.add_songpath(paths.get(i));
            }
        }


        return favmsc;

    }


}
