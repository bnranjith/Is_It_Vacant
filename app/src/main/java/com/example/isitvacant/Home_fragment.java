package com.example.isitvacant;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

public class Home_fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";


    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;

    CardView Family,Couples,Party;

    public Home_fragment() {

    }


    public static Home_fragment newInstance(String param1, String param2, String param3, String param4) {
        Home_fragment fragment = new Home_fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment, container, false);
        Family = view.findViewById(R.id.family);
        Couples = view.findViewById(R.id.couples);
        Party = view.findViewById(R.id.party);

        Family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(getContext(),family.class));
                Toast.makeText(getContext(), "Family", Toast.LENGTH_SHORT).show();
            }
        });
        Couples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),couples.class));
                Toast.makeText(getContext(), "Couples", Toast.LENGTH_SHORT).show();
            }
        });
        Party.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),party.class));
                Toast.makeText(getContext(), "Party", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}