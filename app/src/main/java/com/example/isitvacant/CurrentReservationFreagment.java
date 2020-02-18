package com.example.isitvacant;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;




/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentReservationFreagment extends Fragment {


    private View contactsFreagmentView;






    public CurrentReservationFreagment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        contactsFreagmentView = inflater.inflate(R.layout.fragment_current, container, false);














        return contactsFreagmentView;
    }





}
