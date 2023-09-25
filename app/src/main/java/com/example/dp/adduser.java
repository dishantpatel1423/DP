package com.example.dp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class adduser extends AppCompatActivity {

    TextInputEditText textInputEditTextusername;

    Button adduser;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adduser);

         textInputEditTextusername = findViewById(R.id.username);
          adduser=findViewById(R.id.adduser);
          adduser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertData();
            }
        });
    }

    private void insertData() {

        final String username = textInputEditTextusername.getText().toString().trim();




        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        if(username.isEmpty()){
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.192.106/adduser/adduser.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.equalsIgnoreCase("useradd  successfully")){

                                textInputEditTextusername.setText("");
                                startActivity(new Intent(getApplicationContext(),home.class));
                                Toast.makeText(adduser.this, response, Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(adduser.this, response, Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(adduser.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<String,String>();

                    params.put("username",username);


                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(adduser.this);
            requestQueue.add(request);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}