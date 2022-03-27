package com.example.softec;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>
{
    Context context;
    ArrayList<Events> eventslist;

    public MyAdapter(Context context, ArrayList<Events> eventslist) {
        this.context = context;
        this.eventslist = eventslist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.event, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Events events = eventslist.get(position);
        holder.name.setText(events.getName());
        holder.sdate.setText(events.getSdate());
        holder.edate.setText(events.getEdate());
        holder.venue.setText(events.getVenue());
    }

    @Override
    public int getItemCount() {
        return eventslist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, venue, sdate, edate;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.event_name);
            venue = itemView.findViewById(R.id.event_venue);
            sdate = itemView.findViewById(R.id.sdate);
            edate = itemView.findViewById(R.id.edate);


        }
    }

}
