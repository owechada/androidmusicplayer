package com.emperial.musicplayer;

import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.Objects;

public class loadinbackground {
    Context context;
    ArrayList<String> album_idslist = new ArrayList<>();
    ArrayList<String> artist_idslist = new ArrayList<>();
    ArrayList<Album_Model> album_model_list = new ArrayList<>();
    ArrayList<Artist_model> artist_model_list = new ArrayList<>();
    Handler handlercon;

    music_file mscfile = new music_file();
    static music_file favorite_msc = new music_file();


    public loadinbackground(Context context, Handler handler) {
        this.context = context;
        this.handlercon = handler;


    }


    public void start() {
        Runnable Loadallsongsrunnable = new Runnable() {
            @Override
            public void run() {
                String[] projection = {
                        MediaStore.Audio.Media.TITLE,
                        MediaStore.Audio.Media.DATA,
                        MediaStore.Audio.Media.DURATION,
                        MediaStore.Audio.Media.ARTIST,
                        MediaStore.Audio.Media.ALBUM_ID,
                        MediaStore.Audio.Media.ALBUM,
                        MediaStore.Audio.Media.ARTIST_ID
                };
                String selection = MediaStore.Audio.Media.IS_MUSIC + "!=0";
                Cursor c = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection, null, "TITLE ASC");


                while (c.moveToNext()) {
                    String name = c.getString(0);
                    String urii = c.getString(1);
                    String artist = c.getString(3);
                    String albumid = c.getString(4);
                    String albumname = c.getString(5);
                    String artist_id = c.getString(6);

                    mscfile.addmusic(c.getString(0), c.getString(1), c.getString(2), artist, albumname, albumid);
                    if (artist_idslist.size() == 0) {

                        //add to list and create new artist
                        artist_idslist.add(artist_id);
                        Artist_model artistmodel = new Artist_model(artist, artist_id);
                        artist_model_list.add(artistmodel);

                        Log.d("ARTIST", "ID:" + artist_id + " NAME: " + artist);


                    } else {

                        if (artist_idslist.contains(artist_id)) {
                            //don't create new artist

                        } else {
                            //add to list and create new artist object
                            artist_idslist.add(artist_id);
                            Artist_model artist_model = new Artist_model(artist, artist_id);
                            artist_model_list.add(artist_model);

                            Log.d("Artist", "ID:" + artist_id + " NAME: " + artist);

                        }
                    }


                    if (album_idslist.size() == 0) {

                        //add to list and create new album
                        album_idslist.add(albumid);
                        Album_Model album = new Album_Model(albumid, albumname);
                        album_model_list.add(album);

                        Log.d("ALBUMS", "ID:" + albumid + " NAME: " + albumname);

                    } else {

                        if (album_idslist.contains(albumid)) {
                            //don't create new album

                        } else {
                            //add to list and create new album object
                            album_idslist.add(albumid);
                            Album_Model album = new Album_Model(albumid, albumname);
                            album_model_list.add(album);

                            Log.d("ALBUMS", "ID:" + albumid + " NAME: " + albumname);

                        }

                    }

                }


                myFavorite myfavs = new myFavorite();
                favorite_msc = myfavs.collatefavs(context);


                handlercon.sendEmptyMessage(0);


            }
        };
        Thread loadsongsthread = new Thread(Loadallsongsrunnable);
        loadsongsthread.start();

    }


    public ArrayList<music_file> getallalbum() {
        Log.d("TAG", "NUMBER OF MUSIC FILES " + mscfile.getfilescount() + "");

        ArrayList<music_file> allalbumlist = new ArrayList<>();

        int num = album_idslist.size();

        for (int i = 0; i < num; i++) {

            allalbumlist.add(new music_file());
            Log.d("Song", "" + album_model_list.get(i).Name);
            int numofsongs = mscfile.getfilescount();

            for (int c = 0; c < numofsongs; c++) {
                Log.d("count", ""+c+"");

                if (Objects.equals(mscfile.get_album_ID(c), album_idslist.get(i))) {

                     allalbumlist.get(i).addmusic(mscfile.get_song_name(c), mscfile.get_songpath(c), mscfile.get_duration(c), mscfile.get_artist_name(c), mscfile.get_album_name(c), mscfile.get_album_ID(c));
                    Log.d("Song", "" + mscfile.get_song_name(c));

                }


            }
        }

        return allalbumlist;
    }

    public music_file getFavorite_msc() {


        return favorite_msc;
    }

    public music_file getmusicfile() {

        return mscfile;
    }

    public ArrayList<Album_Model> getAlbum_models() {


        return album_model_list;
    }

    public ArrayList<Artist_model> getArtist_models() {

        return artist_model_list;
    }


}
