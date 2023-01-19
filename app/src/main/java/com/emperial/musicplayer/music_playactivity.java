package com.emperial.musicplayer;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.documentfile.provider.DocumentFile;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class music_playactivity extends AppCompatActivity {

    int repeatMode = 0;
    ConstraintLayout consd;
    MediaPlayer player;
    ImageView pause_play, delete, forward_10, backward_10, next, prev, fav, home, share, info, repeat, album_cover;
    Bundle extras;
    TextView song_name, artist_name, startdur, enddur;
    SeekBar seekbar;
    ActivityResultLauncher<Intent> Activityresultlancher;
    int xrotation = 0;
    TinyDB tinyDB;
    private static final int OPENDOCTREECODE = 2;
    music_file mscfile;
    String songn, artn, durr, path;
    Uri urii = null;
    String filename = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);
        seekbar = findViewById(R.id.seekBar);
        delete = findViewById(R.id.deletebtn);
        song_name = findViewById(R.id.song_name);
        artist_name = findViewById(R.id.artist_name_paly);
        repeat = findViewById(R.id.repeatbtn);
        startdur = findViewById(R.id.du_s);
        enddur = findViewById(R.id.du_e);
        album_cover = findViewById(R.id.albumcover);
        fav = findViewById(R.id.love);
        tinyDB = new TinyDB(this);
        if (MainActivity.page == 0) {
            mscfile = MainActivity.mscfile;
        } else if (MainActivity.page == 1) {

            mscfile = MainActivity.favorite_msc;
        } else if (MainActivity.page == 10) {
            mscfile = MainActivity.searchfiles;

        } else if (MainActivity.page == 2) {
            mscfile = MainActivity.allalbummsclist.get(songs.mode);

        }
        songn = mscfile.get_song_name(musicPlay.currentindex);
        artn = mscfile.get_artist_name(musicPlay.currentindex);
        durr = mscfile.get_duration(musicPlay.currentindex);
        path = mscfile.get_songpath(musicPlay.currentindex);


        //init button
        pause_play = findViewById(R.id.pause_play);
        pause_play.setImageDrawable(getDrawable(R.drawable.ic_pause));

        player = musicPlay.getPlayerintsance();
        playMusic((musicPlay.currentindex));
        song_name.setText(songn);
        artist_name.setText(artn);
        song_name.setSelected(true);
        next = findViewById(R.id.nextsong);
        prev = findViewById(R.id.prevsong);
        forward_10 = findViewById(R.id.f10);
        backward_10 = findViewById(R.id.p10);
        home = findViewById(R.id.back);

        repeatMode = 1;

        seekbar.setMax(Integer.parseInt(durr));
        enddur.setText(convertduration(durr));

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ArrayList<String> names = null;
        names = tinyDB.getListString("favnames");
       consd = findViewById(R.id.layoutmainp);
        if (namext(names, songn)) {
            fav.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24));
        } else {


            fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    addnewfav();
                    fav.setImageDrawable(getDrawable(R.drawable.ic_baseline_favorite_24));
                    fav.setOnClickListener(null);
                }
            });

        }


        switch (repeatMode) {
            case 0:
                repeat.setImageDrawable(getDrawable(R.drawable.ic_rep_all));
                break;

            case 1:
                repeat.setImageDrawable(getDrawable(R.drawable.ic_baseline_repeat_one_24));
                break;
            case 2:
                repeat.setImageDrawable(getDrawable(R.drawable.ic_baseline_repeat_24));
                break;

        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(music_playactivity.this)
                        .setMessage("Are you sure you want to delete this file?")
                        .setTitle("Delete music file")
                        .setPositiveButton(
                                "Yes", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                        player.stop();
                                        player.reset();
                                        File file = new File(mscfile.get_songpath(musicPlay.currentindex)).getAbsoluteFile();
                                        filename = file.getName();



                                        if (file.delete()) {

                                            int num = getContentResolver().delete(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "TITLE='" + mscfile.get_song_name(musicPlay.currentindex) + "'", null);
                                            Log.d("TAG", "onClick: " + num + "");
                                            mscfile.remove_song(musicPlay.currentindex);

                                            Snackbar.make(consd, "Deleted ", Snackbar.LENGTH_SHORT)
                                                    .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                                                        @Override
                                                        public void onDismissed(Snackbar transientBottomBar, int event) {
                                                            super.onDismissed(transientBottomBar, event);
                                                            finish();

                                                        }
                                                    })
                                                    .show();


                                        } else {


                                            opendoctree(Uri.parse(file.toURI().toString()));

                                            urii = Uri.parse(file.toURI().toString());






                                        }

                                    }
                                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        });


        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (repeatMode < 2) {
                    repeatMode++;
                } else if (repeatMode == 2) {

                    repeatMode = 0;
                }
                switch (repeatMode) {
                    case 0:
                        repeat.setImageDrawable(getDrawable(R.drawable.ic_rep_all));
                        break;

                    case 1:
                        repeat.setImageDrawable(getDrawable(R.drawable.ic_baseline_repeat_one_24));
                        break;
                    case 2:
                        repeat.setImageDrawable(getDrawable(R.drawable.ic_baseline_repeat_24));
                        break;

                }

            }
        });
        forward_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seeknext10();
            }
        });

        backward_10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekpre10();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextsong();
            }
        });
        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prevsong();
            }
        });


        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                pause_play.setImageDrawable(getDrawable(R.drawable.ic_play_play));

                switch (repeatMode) {
                    case 0:

                        break;
                    case 1:
                        repeat_song();
                        break;
                    case 2:
                        nextsong();
                        break;
                }
            }
        });


        Activityresultlancher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult o) {
                // Log.d("URI", file.toURI().toString());

                if (o.getResultCode() == Activity.RESULT_OK) {

                    Intent intent = o.getData();
                    Uri uri = null;
                    if (intent != null) {
                        uri = intent.getData();


                        DocumentFile DcF = DocumentFile.fromTreeUri(music_playactivity.this, uri);
                        DocumentFile[] dcfs = DcF.listFiles();
                        if (dcfs.length > 0) {
                            listdir(dcfs);



                        }


                    }

                }
            }
        });


//set clicklisteners
        pause_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (player.isPlaying()) {
                    pausemusic();
                    pause_play.setImageDrawable(getDrawable(R.drawable.ic_play_play));

                } else {
                    player.start();
                    pause_play.setImageDrawable(getDrawable(R.drawable.ic_pause));
                }

            }
        });


        music_playactivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (player != null) {
                    seekbar.setProgress(player.getCurrentPosition());
                    startdur.setText(convertduration(player.getCurrentPosition() + ""));

                    if (player.isPlaying()) {
                        album_cover.setRotation(xrotation++);
                        pause_play.setImageDrawable(getDrawable(R.drawable.ic_pause));

                    } else {
                        album_cover.setRotation(0);
                    }
                }

                new Handler().postDelayed(this, 100);

            }
        });
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (player != null && b) {
                    player.seekTo(i);

                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    public void listdir(DocumentFile[] dcfs) {



        for (int i = 0; i < dcfs.length; i++) {
            if (dcfs[i].isFile()) {
                if (dcfs[i].getName().equals(filename)) {
                    DocumentFile file  = dcfs[i];
                    Log.d("TAG", "URI  "+file.getUri().toString());

                    try {
                      if(  DocumentsContract.deleteDocument(getApplicationContext().getContentResolver(),file.getUri())){
                          //delete from mediadtore
                          getContentResolver().delete(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "TITLE='" + mscfile.get_song_name(musicPlay.currentindex) + "'", null);


                      }

                        Snackbar.make(consd,"Deleted ",Snackbar.LENGTH_SHORT)
                                .addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                                    @Override
                                    public void onDismissed(Snackbar transientBottomBar, int event) {
                                        super.onDismissed(transientBottomBar, event);
                                        finish();

                                    }
                                }).show();
                    }

                    catch (Exception e){

                        Log.d("DEL"," Failed to delete: "+e.getMessage());
                        e.printStackTrace();

                    }


                }

            } else if (dcfs[i].isDirectory()) {

                if (dcfs[i].listFiles().length > 0) {

                    listdir(dcfs[i].listFiles());

                }

            }

        }



    }

    public void seeknext10() {
        if (player.getCurrentPosition() == player.getDuration())
            return;
        int newp = player.getCurrentPosition() + 10000;
        player.seekTo(newp);
        seekbar.setProgress(newp);

    }

    public boolean namext(ArrayList<String> Array, String name) {
        boolean result = false;
        for (int i = 0; Array.size() > i; i++) {
            if (Array.get(i).equals(name)) {

                //alredy exists

                result = true;
            } else {
                result = false;
            }

        }
        return result;
    }


    private void opendoctree(Uri pickerInitialUri) {

        // Choose a directory using the system's file picker.


        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT_TREE);

        // Optionally, specify a URI for the directory that should be opened in
        // the system file picker when it loads.
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);
        Activityresultlancher.launch(intent);


    }


    public void addnewfav() {
        myFavorite newmyfav = new myFavorite(this, path, songn, artn, durr);
        newmyfav.Commit();
        // Toast.makeText(this, ""+newmyfav.getfavcount()+"", Toast.LENGTH_SHORT).show();
    }

    public void seekpre10() {
        if (player.getCurrentPosition() == player.getDuration())
            return;
        int newp = player.getCurrentPosition() - 10000;
        player.seekTo(newp);
        seekbar.setProgress(newp);

    }

    public void nextsong() {


        if (musicPlay.currentindex == mscfile.getfilescount() - 1)
            return;

        musicPlay.currentindex++;
        player.reset();
        playMusic(musicPlay.currentindex);
        seekbar.setMax(Integer.parseInt(mscfile.get_duration(musicPlay.currentindex)));
        enddur.setText(convertduration(mscfile.get_duration(musicPlay.currentindex)));
        song_name.setText(mscfile.get_song_name(musicPlay.currentindex));
        artist_name.setText(mscfile.get_artist_name(musicPlay.currentindex));


    }


    public void prevsong() {


        if (musicPlay.currentindex == 0)
            return;

        musicPlay.currentindex--;
        player.reset();
        playMusic(musicPlay.currentindex);
        seekbar.setMax(Integer.parseInt(mscfile.get_duration(musicPlay.currentindex)));
        enddur.setText(convertduration(mscfile.get_duration(musicPlay.currentindex)));
        song_name.setText(mscfile.get_song_name(musicPlay.currentindex));
        artist_name.setText(mscfile.get_artist_name(musicPlay.currentindex));

    }


    public String convertduration(String s) {
        int ss = Integer.parseInt(s);

        int sss = ss / 1000;

        return "" + sss / 60 + ":" + sss % 60 + "";

    }

    public void pausemusic() {

        player.pause();

    }


    public void playMusic(int s) {
        if (!player.isPlaying()) {
            String path = mscfile.get_songpath(s);
            try {

                player.setDataSource(path);//Write your location here
                player.prepare();
                player.start();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    void repeat_song() {
        player.start();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (!player.isPlaying()) {

            pause_play.setImageDrawable(getDrawable(R.drawable.ic_play_play));

        }


    }
}