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

public class Trippage_activity extends AppCompatActivity {

    ListView listView1;
    Myadp adapter1;

    User user;
    public static ArrayList<User> userArrayList =new ArrayList<>();
    String url="http://192.168.192.106/gettrip/getuser.php";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trippage);
        listView1=findViewById(R.id.lst);
        adapter1= new Myadp(this,userArrayList);
        listView1.setAdapter(adapter1);
        retriveData();

    }
    public void retriveData(){
        StringRequest request=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        userArrayList.clear();
                        try {
                            JSONObject jsonObject =new JSONObject(response);
                            String success =jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");
                            if(success.equals("1")){

                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject object=jsonArray.getJSONObject(i);

                                    String username =object.getString("username");

                                    user  = new User(username);
                                    userArrayList.add(user);
                                    adapter1.notifyDataSetChanged();

                                }
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Trippage_activity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
    public void btn_adduser_activity(View view){
        startActivity(new Intent(getApplicationContext(),adduser.class));
    }
    public void btn_addexpense_activity(View view){
        startActivity(new Intent(getApplicationContext(),adexp.class));
    }

    public void btn_gen_activity(View view){
        startActivity(new Intent(getApplicationContext(),report.class));
    }


}