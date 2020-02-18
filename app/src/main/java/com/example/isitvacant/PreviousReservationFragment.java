package com.example.isitvacant;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;



/**
 * A simple {@link Fragment} subclass.
 */
public class PreviousReservationFragment extends Fragment {





    private View groupsFreagmentView;










    public PreviousReservationFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        groupsFreagmentView = inflater.inflate(R.layout.fragment_previous, container, false);










        return groupsFreagmentView;
    }




    }

 

