package com.emperial.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements itemclickinterface {

AutoCompleteTextView spinner;
int loadercount=0;
    RecyclerView recyclerView;
    ArrayList<File> listofitems;
    File[] filearay;
itemclickinterface itci;
FrameLayout frame;

Myadapter adapter;

static ArrayList<Album_Model> album_adapter_data=new ArrayList<>();
static ArrayList<Artist_model> artist_adapter_data=new ArrayList<>();
  static   ArrayList<music_file> allalbummsclist =new ArrayList<>();
int xrot=0;
TextView name,artist,status;
ImageView circle;
ProgressBar pgb;
ConstraintLayout playerr;
    Thread loaderthread;
ImageView playpause;
ImageView dots;
  static music_file mscfile=new music_file();
  static music_file favorite_msc=new music_file();
    BottomNavigationView bottomNavigationItemView;
Context con;
songs song_fragment;
Albumshow Albumfragment;

Artists_fragment artists_fragment;
albums albums_frgament;
    static FragmentManager fm;
  static  MediaPlayer player;
  static String song_path="";
  favfragment favorites;
    loading_fragment loading_frag;

static  int page=0;
EditText search;
    loadinbackground loadinbg;
    private int PERM_CODE=123;

Bundle savedInstanceState1;
  static  music_file searchfiles;
  Animation btnclick;

    @SuppressLint("HandlerLeak")
    Handler Loadhandler =new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if(page==0) {
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame_container, song_fragment);
                ft.commit();
album_adapter_data=loadinbg.getAlbum_models();
artist_adapter_data=loadinbg.getArtist_models();
                mscfile=loadinbg.getmusicfile();

                if(mscfile.getfilescount()==0){

                    favorite_msc=loadinbg.getFavorite_msc();
                    status.setText("You seem not to have any music file on this device. Reboot this Device");}

            }

            else if(page ==1){
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame_container, favorites);
                ft.commit();

                if(mscfile.getfilescount()==0){
                    status.setText("Empty: you have not made any song a favourite");}

            }

            else if(page ==2){
                allalbummsclist=loadinbg.getallalbum();
                Log.d("TAG", "handleMessage: "+allalbummsclist.size()+"");
                Log.d("TAG", "FIRST ON THE LIST:"+allalbummsclist.get(1).getfilescount()+"");

                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame_container, Albumfragment);
                ft.commit();


            }
            else if(page ==3){
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.frame_container,artists_fragment);
                ft.commit();


            }


        }
    };


    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
savedInstanceState1=savedInstanceState;

        if(Checkpermision()==false){
            RequestPErmission();
            return;
        }

        setContentView(R.layout.activity_main);




        bottomNavigationItemView = findViewById(R.id.btmnav);
itci=this;
con=this;
frame=findViewById(R.id.frame_container);
artists_fragment=new Artists_fragment();
song_fragment=new songs();
favorites=new favfragment();
Albumfragment =new Albumshow();
loading_frag =new loading_fragment();
player=musicPlay.getPlayerintsance();
playpause=findViewById(R.id.player_pause);
search=findViewById(R.id.searched);
//searchicon=findViewById(R.id.searchicon);
dots=findViewById(R.id.dots);




ArrayAdapter<CharSequence> spinadapter=ArrayAdapter.createFromResource(this,R.array.sortby,R.layout.spinnerlayout);
spinadapter.setDropDownViewResource(android.R.layout.simple_spinner_item);



        fm=getSupportFragmentManager();
        FragmentTransaction    ft =fm.beginTransaction();
        ft.replace(R.id.frame_container, loading_frag);
ft.commit();




dots.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

    }
});







search.addTextChangedListener(new TextWatcher() {
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
search(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
});

loadinbg=new loadinbackground(this,Loadhandler);

loadinbg.start();

 pgb=findViewById(R.id.progressBar) ;
        playerr=findViewById(R.id.hmplayer);
 name=findViewById(R.id.hmname);
        circle=findViewById(R.id.hmimg);
 artist=findViewById(R.id.hmartist);
 init();
 status=findViewById(R.id.stat);

playpause.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        view.setAnimation(btnclick);
        if(player.isPlaying()){
            player.pause();
            playpause.setImageDrawable(getDrawable(R.drawable.ic_play_play));
        }
        else{
            if(player!=null){
                player.start();
               playpause.setImageDrawable(getDrawable(R.drawable.ic_pause));

            }

        }
    }
});

        playerr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                view.setAnimation(btnclick);
                startplay(null);
            }
        });


        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(player!=null) {
                    if (player.isPlaying()) {
                        circle.setRotation(xrot++);
                        pgb.setProgress(player.getCurrentPosition());

                    }
                }
                new Handler().postDelayed(this, 100);
            }
        });
        bottomNavigationItemView.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);

    }

public static void changemscfile(music_file mscf){

        mscfile=mscf;

}



    @Override
    protected void onResume() {
        super.onResume();

        init();
    }

    public void init(){


    if(player!=null) {
        if
        (player.isPlaying()) {
            playerr.setVisibility(View.VISIBLE);
            name.setSelected(true);
            name.setText(mscfile.get_song_name(musicPlay.currentindex));
            artist.setText(mscfile.get_artist_name(musicPlay.currentindex));
            pgb.setMax(Integer.parseInt(mscfile.get_duration(musicPlay.currentindex)));
            playpause.setImageDrawable(getDrawable(R.drawable.ic_pause));


        } else {
            playerr.setVisibility(View.GONE);

        }
    }


}



public static void getAlbummsc(int pos){

        mscfile=allalbummsclist.get(pos);
}

    public void startplay(String path) {


        Intent i = new Intent(MainActivity.this, music_playactivity.class);
  song_path=path;
        startActivity(i);
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        int itemid = item.getItemId();

        switch (itemid) {
            case R.id.songs:
                page=0;
                Loadhandler.sendEmptyMessage(0);
              break;
            case R.id.favs:

                page=1;
                Loadhandler.sendEmptyMessage(0);
                myFavorite myfavs=new myFavorite();
                favorite_msc=myfavs.collatefavs(getApplicationContext());
              //  Toast.makeText(con, "favs click", Toast.LENGTH_SHORT).show();


break;
            case R.id.albs:
                page=2;
                Loadhandler.sendEmptyMessage(0);
                break;
  case R.id.art:
                page=3;
                Loadhandler.sendEmptyMessage(0);
                break;

        }

        return true;
    }



    @Override
    public void onitemclick(int position) {
    musicPlay.getPlayerintsance().reset();
    musicPlay.currentindex = position;

    startplay(mscfile.get_songpath(position));
}

public void search(String string){
        ArrayList<String> names=    mscfile.getnamelist();
        ArrayList<String> artists= mscfile.getartlist();
    searchfiles   =new music_file();
    for(int i=0; mscfile.getfilescount()>i;i++){

   String name=names.get(i);
   String artist=artists.get(i);
   String charat0=name.charAt(0)+"";
   if (name.contains(string) || artist.contains(string) ){
       searchfiles.add_song_name(name);
       searchfiles.add_songpath(mscfile.get_songpath(i));
       searchfiles.add_duration(mscfile.get_duration(i));
       searchfiles.add_artist_name(mscfile.get_artist_name(i));
   }




     }
    page=10;

    RecyclerView recyclerVieww=findViewById(R.id.recyclerview);
    itemclickinterface itemclickinterface=this;
    favadapter adap = new favadapter(this,searchfiles,itemclickinterface);

    recyclerVieww.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    recyclerVieww.setAdapter(adap);
    mscfile=searchfiles;
   // Toast.makeText(con, "searc", Toast.LENGTH_SHORT).show();



}

public boolean Checkpermision(){
int result = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
if(result== PackageManager.PERMISSION_GRANTED){
return true;


}

else{
    return false;
}


}

public void
    RequestPErmission(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE) ) {

             Toast.makeText(this, "Storage Permission is Required, Allow from Settings", Toast.LENGTH_SHORT).show();


        }
        else{
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERM_CODE);

        }
}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

if ( requestCode== PERM_CODE && grantResults[0]==PackageManager.PERMISSION_GRANTED){

   // Toast.makeText(this, "" + "Granted", Toast.LENGTH_SHORT).show();
     //onCreate(savedInstanceState1);
    finish();
    startActivity(getIntent());

}

    }


}