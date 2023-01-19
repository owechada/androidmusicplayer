package com.emperial.musicplayer;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Albumshow#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Albumshow extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    GridView gridview;

    public Albumshow() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Albumshow.
     */
    // TODO: Rename and change types and number of parameters
    public static Albumshow newInstance(String param1, String param2) {
        Albumshow fragment = new Albumshow();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_albumshow, container, false);
        gridview=layout.findViewById(R.id.albumgrid);

        ArrayList<Album_Model> albumlist= MainActivity.album_adapter_data;
album_adapter album_adapter=new album_adapter(getActivity(),albumlist);

gridview.setAdapter(album_adapter);

gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


songs songs=new songs();
songs.setmode(i);

        FragmentManager fm= ((MainActivity) getActivity()).fm;
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.frame_container,songs);

        ft.commit();


    }
});


        return layout;
    }
}