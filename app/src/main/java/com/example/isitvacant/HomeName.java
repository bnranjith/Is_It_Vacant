package com.example.isitvacant;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class HomeName extends AppCompatActivity {
    private EditText meditText,dobText;
    Spinner spin;
    private TextView mobText;
    private Button mButton;
    String mob0;

    String uid;
    FirebaseAuth mAuth;
    String username1;

    String username;
    String dob;
    String nationality;








    String gender1;



    private FirebaseFirestore firebaseFirestore;


    private FirebaseFirestore mFirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_name);
        mFirestore = FirebaseFirestore.getInstance();
        mobText=findViewById(R.id.enter_mob_field);

        dobText=findViewById(R.id.enter_dob_field);


        meditText = (EditText) findViewById(R.id.enter_name_field);

        mButton = (Button) findViewById(R.id.submit_details_button);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore= FirebaseFirestore.getInstance();
        uid = mAuth.getCurrentUser().getUid();

        Spinner spinner = (Spinner) findViewById(R.id.gender_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genders, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender1= (String) adapterView.getItemAtPosition(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spin=findViewById(R.id.nationality_spinner2);
        spin.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,CountryData.countryNames));
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 nationality = adapterView.getItemAtPosition(i).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        dobText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        HomeName.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month = month + 1;
                        String date = dayOfMonth + "/" + month + "/" + year;

                        dobText.setText(date);

                    }
                }, year, month, day);
                datePickerDialog.show();

            }
        });
        mobText.setText(mAuth.getCurrentUser().getPhoneNumber());








        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                username1 = meditText.getText().toString();

                dob=dobText.getText().toString();




                if(username1.isEmpty()||dob.isEmpty()){

                    meditText.setError("Mandatory Field...");

                    dobText.setError("Mandatory Field...");

                    meditText.requestFocus();

                }
                else {
                    mob0=mobText.getText().toString();



                    String uid2 = mAuth.getCurrentUser().getUid();
















                    Map<String, String> userMap = new HashMap<>();

                    userMap.put("name", username1);

                    userMap.put("Date Of Birth", dob);
                    userMap.put("Gender", gender1);
                    userMap.put("Mobile", mob0);
                    userMap.put("Nationality",nationality );
                    userMap.put("uid",uid2);
                    userMap.put("image","https://firebasestorage.googleapis.com/v0/b/is-it-vacant-d1cf7.appspot.com/o/profile%20images%2Fprofile_image.png?alt=media&token=07a82599-e485-4e7f-b937-dac00b1ea41d" );





                    mFirestore.collection("users")
                            .document(uid2)
                            .set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(HomeName.this, "Data Added succesfully to Firebase", Toast.LENGTH_LONG).show();
                        }


                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            String error = e.getMessage();
                            Toast.makeText(HomeName.this, "Error" + error, Toast.LENGTH_LONG).show();
                        }
                    });

                    Intent intent = new Intent(HomeName.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(intent);
                }
            }
        });
    }














    @Override
    protected void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();



        uid = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("users").document(uid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {

                if (documentSnapshot.exists()) {
                    username = documentSnapshot.getString("name");

                    String gender2 = documentSnapshot.getString("Gender");
                    String dob2 = documentSnapshot.getString("Date Of Birth");
                    String nationality2 = documentSnapshot.getString("Nationality");


                    if (username != ""  && gender2!="" && nationality2!=""&& dob2!=""){
                        Intent intent1 = new Intent(HomeName.this, MainActivity.class);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent1);
                    }

                }

            }
        });
    }

}
