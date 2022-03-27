package com.example.softec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONObject;

public class Organizerscreen extends AppCompatActivity {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("Event");
    JSONObject globalstate;
    EditText name,sdate,edate,venue;
    EditText degree,age,sem;
    Button proc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizerscreen);

        name = findViewById(R.id.orgName);
        sdate = findViewById(R.id.orgSdate);
        edate = findViewById(R.id.orgEdate);
        venue = findViewById(R.id.orgVenue);
        proc = findViewById(R.id.btnproceed);

        degree = findViewById(R.id.eligDegree);
        age = findViewById(R.id.elegAge);
        sem = findViewById(R.id.elegsem);

        proc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child(name.getText().toString()).child("name").setValue(name.getText().toString());
                myRef.child(name.getText().toString()).child("edate").setValue(edate.getText().toString());
                myRef.child(name.getText().toString()).child("sdate").setValue(sdate.getText().toString());
                myRef.child(name.getText().toString()).child("venue").setValue(venue.getText().toString());


                myRef.child(name.getText().toString()).child("Eligibility").child("degree").setValue(degree.getText().toString());
                myRef.child(name.getText().toString()).child("Eligibility").child("age").setValue(age.getText().toString());
                myRef.child(name.getText().toString()).child("Eligibility").child("sem").setValue(sem.getText().toString());

                Intent i = new Intent(getApplicationContext(),event_view.class);
                startActivity(i);
            }
        });



    }
}