package com.kyuubi.gr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.kyuubi.gr.request.GetUserRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class AllMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_main);

        final ImageButton ibViewPoint = (ImageButton) findViewById(R.id.ibViewPoint);
        final ImageButton ibGroup = (ImageButton) findViewById(R.id.ibGroup);
        final ImageButton ibUser = (ImageButton) findViewById(R.id.ibUser);
        final ImageButton ibCalenda = (ImageButton) findViewById(R.id.ibCalenda);
        final ImageButton ibHomework = (ImageButton) findViewById(R.id.ibHomework);
        final ImageButton ibMesseger = (ImageButton) findViewById(R.id.ibMessenger);


        ibViewPoint.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
            }
        });
        ibGroup.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(AllMainActivity.this,GroupAllActivity.class);
                AllMainActivity.this.startActivity(intent);
            }
        });
        ibUser.setOnClickListener(new View.OnClickListener(){
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
                                Intent intent = new Intent(AllMainActivity.this, UserActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("birthday", birthday);
                                AllMainActivity.this.startActivity(intent);
                            } else {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                GetUserRequest getUserRequest = new GetUserRequest(username,responseListener);
                RequestQueue queue = Volley.newRequestQueue(AllMainActivity.this);
                queue.add(getUserRequest);
            }
        });
        ibCalenda.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(AllMainActivity.this,CalendaTeacherActivity.class);
                AllMainActivity.this.startActivity(intent);
            }
        });
        ibHomework.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            }
        });
        ibMesseger.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){

            }
        });
    }
}
