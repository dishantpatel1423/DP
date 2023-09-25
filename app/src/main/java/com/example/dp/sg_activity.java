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

public class sg_activity extends AppCompatActivity {
    TextInputEditText textInputEditTextemail, textInputEditTextpassword, textInputEditTextname;

    Button submit;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sg2);

        textInputEditTextname     = findViewById(R.id.name);
        textInputEditTextemail   = findViewById(R.id.email);
        textInputEditTextpassword  = findViewById(R.id.password);

        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertData();
            }
        });
    }

    private void insertData() {

        final String name = textInputEditTextname.getText().toString().trim();
        final String email = textInputEditTextemail.getText().toString().trim();
        final String password = textInputEditTextpassword.getText().toString().trim();


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        if(name.isEmpty()){
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(email.isEmpty()){
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(password.isEmpty()){
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        else{
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.192.106/login-registration/register.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.equalsIgnoreCase("Data Inserted")){
                                Toast.makeText(sg_activity.this, "Data Inserted", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                startActivity(new Intent(getApplicationContext(),Lg.class));
                            }
                            else{
                                Toast.makeText(sg_activity.this, response, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(sg_activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("name",name);
                    params.put("email",email);
                    params.put("password",password);

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(sg_activity.this);
            requestQueue.add(request);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}