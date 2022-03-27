package com.example.softec;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class event_view extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter myAdapter;
    ArrayList<Events> eventList;
    Button add_event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_view);

        Bundle extras = getIntent().getExtras();
        String type;


        add_event = findViewById(R.id.eventadd);

        add_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(event_view.this, Organizerscreen.class);
                startActivity(intent);
            }
        });



        recyclerView = findViewById(R.id.eventrecycle);
        database = FirebaseDatabase.getInstance("https://softec-789f6-default-rtdb.firebaseio.com/")
                .getReference("Event");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventList = new ArrayList<>();
        myAdapter = new MyAdapter(this, eventList);
        recyclerView.setAdapter(myAdapter);



        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    String name = dataSnapshot.child("name").getValue().toString();
                    String venue = dataSnapshot.child("venue").getValue().toString();
                    String sdate = dataSnapshot.child("sdate").getValue().toString();
                    String edate = dataSnapshot.child("edate").getValue().toString();

                    eventList.add(new Events(name,venue,sdate,edate));

                }
                myAdapter.notifyDataSetChanged();
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}