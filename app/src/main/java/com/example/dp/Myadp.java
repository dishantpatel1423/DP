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

public class Myadp extends ArrayAdapter<User> {
    Context context;
    List<User> arrayListuser;

    public Myadp(@NonNull Context context, List<User>arrayListuser) {
        super(context, R.layout.custom_list2,arrayListuser);
        this.context=context;
        this.arrayListuser=arrayListuser;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_list2,null,true);


        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView username= view.findViewById(R.id.user_name);

        username.setText(arrayListuser.get(position).getUsername());
        return view;
    }
}
