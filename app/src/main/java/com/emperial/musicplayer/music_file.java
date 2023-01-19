package com.emperial.musicplayer;

import java.util.ArrayList;

public class music_file {

    ArrayList<String> Album_name, song_name, artists, song_path, duration,albumids;

    music_file() {
        this.Album_name=new ArrayList<>();
        this.song_name=new ArrayList<>();
        this.artists=new ArrayList<>();
        this.song_path=new ArrayList<>();
       this.duration =new ArrayList<>();
       this.albumids =new ArrayList<>();


    }


    public void addmusic(String tit, String data,String durr,String artist,String albumname,String ALbumID){
        add_song_name(tit);
        add_songpath(data);
        add_duration(durr);
        add_artist_name(artist);
        add_album_name(albumname);
        add_album_ID(ALbumID);

    }
//adders

    public void add_album_name(String albn) {

Album_name.add(albn);

    }

    public void add_album_ID(String albnid) {

albumids.add(albnid);

    }

    public void add_song_name(String sngn) {

song_name.add(sngn);
    }

    public void add_artist_name(String artn) {

artists.add(artn);
    }


    public void add_duration(String dura) {

duration.add(dura);
    }
    public void add_songpath(String d) {

        song_path.add(d);
    }


    //getters
    public String get_songpath(int pos) {

        return song_path.get(pos);
    }


    public boolean remove_song(int pos){

song_name.remove(pos);
song_path.remove(pos);
artists.remove(pos);
duration.remove(pos);
albumids.remove(pos);
return true;
    }
    public String get_album_name(int pos) {

return Album_name.get(pos);
    }

    public String get_album_ID(int pos) {

return albumids.get(pos);
    }

    public String get_song_name(int pos) {

return song_name.get(pos);
    }

    public String get_artist_name(int pos) {

return artists.get(pos);
    }

    public String get_duration(int pos) {

return duration.get(pos);
    }


    public int getfilescount(){

      return song_name.size();
    }

    public ArrayList<String> getnamelist(){
       return song_name;
    }


    public ArrayList<String> getartlist(){
        return artists;
    }


}

