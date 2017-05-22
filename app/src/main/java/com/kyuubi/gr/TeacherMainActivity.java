package com.kyuubi.gr;

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

public class TeacherMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_main);
        final ImageButton ibPointManager = (ImageButton) findViewById(R.id.ibPointManager);
        final ImageButton ibGroup = (ImageButton) findViewById(R.id.ibGroup);
        final ImageButton ibUser = (ImageButton) findViewById(R.id.ibUser);
        final ImageButton ibCalenda = (ImageButton) findViewById(R.id.ibCalenda);
        final ImageButton ibHomework = (ImageButton) findViewById(R.id.ibHomework);
        final ImageButton ibMessager = (ImageButton) findViewById(R.id.ibMessager);

        ibPointManager.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(TeacherMainActivity.this,PointManagerActivity.class);
                TeacherMainActivity.this.startActivity(intent);
            }
        });
        ibGroup.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(TeacherMainActivity.this,GroupTeacherActivity.class);
                TeacherMainActivity.this.startActivity(intent);
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
                                Intent intent = new Intent(TeacherMainActivity.this, UserActivity.class);
                                intent.putExtra("name", name);
                                intent.putExtra("birthday", birthday);
                                TeacherMainActivity.this.startActivity(intent);
                            } else {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                GetUserRequest getUserRequest = new GetUserRequest(username,responseListener);
                RequestQueue queue = Volley.newRequestQueue(TeacherMainActivity.this);
                queue.add(getUserRequest);
            }
        });
        ibCalenda.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(TeacherMainActivity.this,CalendaActivity.class);
                TeacherMainActivity.this.startActivity(intent);
            }
        });
        ibHomework.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(TeacherMainActivity.this,HomeworkActivity.class);
                TeacherMainActivity.this.startActivity(intent);
            }
        });
        ibMessager.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(TeacherMainActivity.this,ChatListActivity.class);
                TeacherMainActivity.this.startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed(){

    }
}
