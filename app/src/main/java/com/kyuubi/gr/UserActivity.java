package com.kyuubi.gr;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.kyuubi.gr.request.EditClassRequest;
import com.kyuubi.gr.request.EditUserRequest;
import com.kyuubi.gr.request.GetUserRequest;
import com.kyuubi.gr.request.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class UserActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        SharedPreferences share = getSharedPreferences("user", MODE_PRIVATE);
        final String username = share.getString("username","");
        final Integer role = share.getInt("role",0);

        final EditText etName = (EditText) findViewById(R.id.etName);
        final EditText etDate = (EditText) findViewById(R.id.etDate);
        final Button bUpdate = (Button) findViewById(R.id.bUpdate);
        final Button bLogout = (Button) findViewById(R.id.bLogout);
        final Button bChangePassword = (Button) findViewById(R.id.bChangePassword);

        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        String birthday = intent.getStringExtra("birthday");

        etName.setText(name);
        etDate.setText(birthday);

        bLogout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                SharedPreferences share = getSharedPreferences("user", MODE_PRIVATE);
                SharedPreferences.Editor editor = share.edit();
                editor.clear();
                Intent registrIntent = new Intent(UserActivity.this,LoginActivity.class);
                UserActivity.this.startActivity(registrIntent);
            }
        });

        bUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = etName.getText().toString();
                final String date = etDate.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Toast.makeText(UserActivity.this,"Update complete",Toast.LENGTH_SHORT).show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
                                builder.setMessage("Create Class Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                EditUserRequest editUserRequest = new EditUserRequest(username, name, role ,date, responseListener);
                RequestQueue queue = Volley.newRequestQueue(UserActivity.this);
                queue.add(editUserRequest);
                Toast.makeText(UserActivity.this,"Update complete",Toast.LENGTH_SHORT);
            }
        });

        bChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrIntent = new Intent(UserActivity.this,ChangePasswordActivity.class);
                UserActivity.this.startActivity(registrIntent);
            }
        });
    }
}
