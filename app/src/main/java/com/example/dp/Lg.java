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

public class Lg extends AppCompatActivity {

    TextInputEditText textInputEditTextemail ,textInputEditTextpassword;
    TextView textViewregister;
    Button submit;
    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lg);
        textViewregister = findViewById(R.id.registernow);
        textInputEditTextemail   = findViewById(R.id.email);
        textInputEditTextpassword = findViewById(R.id.password);
        submit=findViewById(R.id.submit);
        textViewregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), sg_activity.class);
                startActivity(intent);
                finish();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertData();
            }
        });
    }

    private void insertData() {

        final String email = textInputEditTextemail.getText().toString().trim();
        final String password = textInputEditTextpassword.getText().toString().trim();



        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        if(email.isEmpty()){
            Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (password.isEmpty()) {
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
            return;

        } else{
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, "http://192.168.192.106/login-registration/login.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.equalsIgnoreCase("logged in successfully")){
                                textInputEditTextemail.setText("");
                                textInputEditTextpassword.setText("");
                                startActivity(new Intent(getApplicationContext(),home.class));
                                Toast.makeText(Lg.this, response, Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(Lg.this, response, Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Lg.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }

            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String,String> params = new HashMap<String,String>();

                    params.put("email",email);
                    params.put("password",password);

                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(Lg.this);
            requestQueue.add(request);
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}