package com.example.isitvacant;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    TextView RESTO_NAME,Resto_Type,Resto_location;
    ImageView Restaurant_image;
    RatingBar ratingBar;
    String profileRestoName,proUid,proRestoImage,proMobile,proRestoGstin,proRestoAddr,proRestoDesc,proRestoType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurants_details);
        mstore=FirebaseFirestore.getInstance();
        mAuth=FirebaseAuth.getInstance();
        RESTO_NAME = findViewById(R.id.restoName);
        Resto_Type = findViewById(R.id.resto_type);
        Restaurant_image = findViewById(R.id.res_background_image);
        Resto_location = findViewById(R.id.resto_location);
        RESTO_NAME.setText(profileRestoName);
        Resto_Type.setText(proRestoType);
        Resto_location.setText(proRestoAddr);
        Glide.with(getApplicationContext()).load(proRestoImage).into(Restaurant_image);
        ratingBar = findViewById(R.id.rating_bar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                startActivity(new Intent(getApplicationContext(),pop_up_rating.class));
            }
        });

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
                Resto_Type.setText(proRestoType);
                Resto_location.setText(proRestoAddr);
                Picasso.get().load(proRestoImage).into(Restaurant_image);







            }
        });
    }
}
