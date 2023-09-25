package com.example.dp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class home extends AppCompatActivity {

    ListView listView;
    myadepter adapter;

    Trip trip;
    public static ArrayList<Trip> tripnameArrayList =new ArrayList<>();
    String url="http://192.168.192.106/gettrip/gettrip.php";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        listView=findViewById(R.id.listview);
        adapter= new myadepter(this,tripnameArrayList);
        listView.setAdapter(adapter);
        retriveData();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),Trippage_activity.class));
            }
        });
    }
    public void retriveData(){
        StringRequest request=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tripnameArrayList.clear();
                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            String success =jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if(success.equals("1")){

                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject object=jsonArray.getJSONObject(i);
                                    String trip_id =object.getString("trip_id");
                                    String tripname =object.getString("tripname");

                                    trip  = new Trip(trip_id,tripname);
                                    tripnameArrayList.add(trip);
                                    adapter.notifyDataSetChanged();

                                }
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(home.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    public void btn_add_activity(View view){
        startActivity(new Intent(getApplicationContext(),tripadd_activity.class));
    }
}