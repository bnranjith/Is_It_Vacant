package com.example.isitvacant;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

public class RestaurantsDetails extends AppCompatActivity {
    FirebaseFirestore mstore;
    FirebaseAuth mAuth;
    TextView RESTO_NAME;
    String profileRestoName,proUid,proRestoImage,proMobile,proRestoGstin,proRestoAddr,proRestoDesc,proRestoType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_details);
        mstore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        RESTO_NAME = findViewById(R.id.restoName);
        RESTO_NAME.setText(profileRestoName);
    }

    @Override
    protected void onStart() {
        super.onStart();

        proUid=getIntent().getStringExtra("uid");

        DocumentReference documentReference = mstore.collection("restaurants").document(proUid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {


                //Picasso.get().load(documentSnapshot.getString("image")).into(circleImageView);


                proMobile=documentSnapshot.getString("Mobile");
                profileRestoName = documentSnapshot.getString("name");
                proRestoImage=documentSnapshot.getString("image");
                proRestoGstin=documentSnapshot.getString("GSTIN_NUMBER");
                proRestoAddr=documentSnapshot.getString("Address");
                proRestoDesc=documentSnapshot.getString("discription");
                proRestoType=documentSnapshot.getString("Type");
                RESTO_NAME.setText(profileRestoName);






            }
        });
    }
}
