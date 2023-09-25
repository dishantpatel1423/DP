package com.example.dp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class myadepter extends ArrayAdapter<Trip> {
    Context context;
    List<Trip> arrayListTrip;

    public myadepter(@NonNull Context context, List<Trip>arrayListTrip) {
        super(context, R.layout.custom_list,arrayListTrip);
        this.context=context;
        this.arrayListTrip=arrayListTrip;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list,null,true);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView trip_id= view.findViewById(R.id.txt_id);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tripname= view.findViewById(R.id.txt_name);

        trip_id.setText(arrayListTrip.get(position).getTrip_id());
        tripname.setText(arrayListTrip.get(position).getTripname());
        return view;
    }
}

