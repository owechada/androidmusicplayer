package com.emperial.musicplayer;

import android.media.MediaPlayer;
import android.widget.Toast;

public class musicPlay {

    static  MediaPlayer playerintsance;



    public static MediaPlayer getPlayerintsance() {

        if (playerintsance==null){
            playerintsance=new MediaPlayer();

        }
        return playerintsance;
    }

public static int currentindex =-1;

}
