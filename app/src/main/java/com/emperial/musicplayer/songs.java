package com.emperial.musicplayer;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link songs#newInstance} factory method to
 * create an instance of this fragment.
 */
public class songs extends Fragment {


  static int mode=-10;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    RecyclerView recyclerView;
    Context contextt;


    public songs() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment songs.
     */
    // TODO: Rename and change types and number of parameters
    public songs newInstance(String param1, String param2) {
        songs fragment = new songs();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);

        fragment.setArguments(args);
        return fragment;
    }


    public void setmode(int pos ){
        mode=pos;

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_songs, container, false);
        recyclerView = layout.findViewById(R.id.recyclerview);

        music_file musicfile=new music_file();
if (mode==-10) {
    musicfile  = ((MainActivity) getActivity()).mscfile;

}


else if  (MainActivity.page ==2 ){// or artists page i.e page =3
    musicfile =((MainActivity) getActivity()).allalbummsclist.get(mode);
    MainActivity.changemscfile(musicfile);

    Log.d("TAG", "onCreateView: "+musicfile.getfilescount()+"");
}




        itemclickinterface itemclickinterface=((MainActivity)getActivity()).itci;
        Myadapter adapter = new Myadapter(getContext(), musicfile, itemclickinterface);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        View layout = getLayoutInflater().inflate(R.layout.fragment_songs, null, false);

        recyclerView = layout.findViewById(R.id.recyclerview);

        music_file musicfile=((MainActivity)getActivity()).mscfile;
        itemclickinterface itemclickinterface=((MainActivity)getActivity()).itci;
        Myadapter adapter = new Myadapter(getContext(), musicfile, itemclickinterface);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        recyclerView.setAdapter(adapter);


    }
}