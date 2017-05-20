package com.kyuubi.gr;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.kyuubi.gr.request.GetUserRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class AdminMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_main);
        final ImageButton ibUserManager = (ImageButton) findViewById(R.id.ibUserManager);
        final ImageButton ibClassManager = (ImageButton) findViewById(R.id.ibClassManager);
        final ImageButton ibUserprofile = (ImageButton) findViewById(R.id.ibUser);

        ibUserManager.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(AdminMainActivity.this, UserManagerActivity.class);
                AdminMainActivity.this.startActivity(intent);
            }
        });

        ibClassManager.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(AdminMainActivity.this, ClassManagerActivity.class);
                AdminMainActivity.this.startActivity(intent);
            }
        });

        ibUserprofile.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                SharedPreferences share = getSharedPreferences("user", MODE_PRIVATE);
                String username = share.getString("username","");
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                String name = jsonResponse.getString("name");
                                String birthday = jsonResponse.getString("birthday");
                                Intent intent = new Intent(AdminMainActivity.this, UserActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("birthday", birthday);
                                AdminMainActivity.this.startActivity(intent);
                            } else {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                GetUserRequest getUserRequest = new GetUserRequest(username,responseListener);
                RequestQueue queue = Volley.newRequestQueue(AdminMainActivity.this);
                queue.add(getUserRequest);
            }
        });
    }
    @Override
    public void onBackPressed(){

    };
}
