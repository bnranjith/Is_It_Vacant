package com.example.isitvacant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class profile_fragment extends Fragment {
    EditText ET_NAME;
    EditText ET_STATUS;
    FirebaseAuth mAuth;

    FirebaseFirestore mstore;
    ProgressBar progressBar;
    String finalResult;
    String uid;
    String mobb,status;
    TextView mob,nation_tv,gender_tv,dob_tv1;
    private static final int GalleryPick=1;
    CircleImageView circleImageView;
    Button update_bt,logout_bt;
    String name1;

    private StorageReference userProfileImageRef;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;
    public profile_fragment() {

    }


    public static profile_fragment newInstance(String param1, String param2, String param3,String param4) {
        profile_fragment fragment = new profile_fragment();
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

        mAuth = FirebaseAuth.getInstance();
        mstore = FirebaseFirestore.getInstance();




                }





    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode==GalleryPick && resultCode== RESULT_OK && data != null)
        {


            Uri ImageUri=data.getData();
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(getContext(), this);

        }
        else{
            progressBar.setVisibility(View.INVISIBLE);


        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            progressBar.setVisibility(View.VISIBLE);
            CropImage.ActivityResult result = CropImage.getActivityResult(data);


            if (resultCode == RESULT_OK){
                Uri resultUri = result.getUri();




                UploadTask uploadTask;
                userProfileImageRef = FirebaseStorage.getInstance().getReference().child("profile images");


                final StorageReference ref = userProfileImageRef.child(uid + ".jpg");
                uploadTask = ref.putFile(resultUri);


                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return ref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            progressBar.setVisibility(View.INVISIBLE);



                            Toast.makeText(getContext(),"Profile Image Uploaded Successfully",Toast.LENGTH_LONG).show();
                            final Uri downloadUri = task.getResult();

                            Map<String, Object> userMap = new HashMap<>();

                            userMap.put("image", downloadUri.toString());



                            mstore.collection("users")
                                    .document(uid)
                                    .update(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                }


                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    String error = e.getMessage();
                                    Toast.makeText(getContext(), "Error" + error, Toast.LENGTH_LONG).show();
                                }
                            });










                        } else {
                            progressBar.setVisibility(View.INVISIBLE);
                            String error =task.getException().toString();
                            Toast.makeText(getContext(),"Error"+error,Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }

        }
        else{
            progressBar.setVisibility(View.INVISIBLE);

        }
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile_fragment, container, false);
        ET_NAME = view.findViewById(R.id.update_user_name);

        circleImageView= view.findViewById(R.id.profile_image);
        mob = view.findViewById(R.id.update_mob);
        nation_tv=view.findViewById(R.id.nation_tv);
        gender_tv=view.findViewById(R.id.gender_tv);
        dob_tv1=view.findViewById(R.id.dob_tv);

        update_bt=view.findViewById(R.id.update_bt);
        logout_bt=view.findViewById(R.id.logout);
        logout_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent intent = new Intent(getContext(),MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });





        progressBar = (ProgressBar)view.findViewById(R.id.spin_kit);

        Sprite doubleBounce = new DoubleBounce();
        progressBar.setIndeterminateDrawable(doubleBounce);



        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GalleryPick);
            }
        });


        update_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name1 = ET_NAME.getText().toString();
                if (name1.isEmpty()) {

                    ET_NAME.setError("Mandatory Field...");



                } else {
                    mobb=mob.getText().toString();







                    Map<String, Object> userMap = new HashMap<>();

                    userMap.put("name", name1);



                    mstore.collection("users")
                            .document(uid)
                            .update(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(getContext(), "User Profile Updated", Toast.LENGTH_LONG).show();
                        }


                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String error = e.getMessage();
                            Toast.makeText(getContext(), "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    });










                }
            }
        });





        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        uid = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = mstore.collection("users").document(uid);
        documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                ET_NAME.setText(documentSnapshot.getString("name"));

                Picasso.get().load(documentSnapshot.getString("image")).into(circleImageView);

                mob.setText(documentSnapshot.getString("Mobile"));

                gender_tv.setText(documentSnapshot.getString("Gender"));
                dob_tv1.setText(documentSnapshot.getString("Date Of Birth"));
                nation_tv.setText(documentSnapshot.getString("Nationality"));

            }
        });
    }
}