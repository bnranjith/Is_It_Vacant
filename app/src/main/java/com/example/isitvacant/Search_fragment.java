package com.example.isitvacant;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Search_fragment extends Fragment {
    private View view;
    private RecyclerView findFriendRecyclerList;
    EditText searchText;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference contactsRef = db.collection("restaurants");
    private RestaurantsAdapter adapter;
    Query query;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    public Search_fragment() {

    }


    public static Search_fragment newInstance(String param1, String param2, String param3, String param4) {
        Search_fragment fragment = new Search_fragment();
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
        view = inflater.inflate(R.layout.activity_search_fragment, container, false);
        findFriendRecyclerList = (RecyclerView) view.findViewById(R.id.find_frieds_recycler_list);
        findFriendRecyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
        searchText= view.findViewById(R.id.search);
        query = contactsRef.orderBy("name",Query.Direction.DESCENDING);
        setUpRecyclerView(query);


        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                query = contactsRef.orderBy("name",Query.Direction.DESCENDING);
                setUpRecyclerView(query);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==0){
                    query = contactsRef.orderBy("name",Query.Direction.DESCENDING);
                    setUpRecyclerView(query);
                    adapter.startListening();

                }
                else {
                    query = contactsRef.orderBy("name").startAt(s.toString()).endAt(s.toString() + "\uf8ff");
                    setUpRecyclerView(query);
                    adapter.startListening();
                }





            }

            @Override
            public void afterTextChanged(Editable s) {








            }
        });


        return view;
    }


    private void setUpRecyclerView(Query query) {







        FirestoreRecyclerOptions<RestaurantsModel> options = new FirestoreRecyclerOptions.Builder<RestaurantsModel>()
                .setQuery(query, RestaurantsModel.class)
                .build();

        adapter = new RestaurantsAdapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.find_frieds_recycler_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }
    @Override
    public void onStart() {
        super.onStart();



        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            adapter.startListening();

        }

    }
}