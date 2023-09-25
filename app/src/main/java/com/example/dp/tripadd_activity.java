package com.example.dp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class tripadd_activity extends AppCompatActivity {

    TextInputEditText textInputEditTripname, textInputEditTripdes;

    Button savetrip;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tripadd);
        textInputEditTripname   = findViewById(R.id.tripname);
        textInputEditTripdes   = findViewById(R.id.tripdescription);
        savetrip = findViewById(R.id.savetrip);
        savetrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertData();
            }
        });
    }

    private void insertData() {

        final String tripname = textInputEditTripname.getText().toString().trim();
        final String tripdes = textInputEditTripdes.getText().toString().trim();



        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        if(tripname.isEmpty()){
            Toast.makeText(this, "Enter tripname", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(tripdes.isEmpty()){
            Toast.makeText(this, "Enter tripdes", Toast.LENGTH_SHORT).show();
            return;
        }


        else{
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.192.106/create-trip/createtrip.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.equalsIgnoreCase("Data Inserted")){
                                Toast.makeText(tripadd_activity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(),home.class));
                            }
                            else{
                                Toast.makeText(tripadd_activity.this, response, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(tripadd_activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("tripname",tripname);
                    params.put("tripdes",tripdes);


                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(tripadd_activity.this);
            requestQueue.add(request);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}