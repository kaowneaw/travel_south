package com.example.matinee.travel_south.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.matinee.travel_south.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {

    ImageButton btHome_travel, btHome_rec, btHome_checkin;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        btHome_travel = (ImageButton) rootView.findViewById(R.id.btHome_travel);
        btHome_rec = (ImageButton) rootView.findViewById(R.id.btHome_rec);
        btHome_checkin = (ImageButton) rootView.findViewById(R.id.btHome_checkin);


        return rootView;
    }

    @Override
    public void onClick(View v) {

        if (v == btHome_travel) {

        } else if (v == btHome_rec) {

        } else if (v == btHome_checkin) {

        }
    }
}
