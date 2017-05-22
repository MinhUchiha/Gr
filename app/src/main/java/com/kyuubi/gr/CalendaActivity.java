package com.kyuubi.gr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.kyuubi.gr.request.GetClassLearnerRequest;
import com.kyuubi.gr.request.GetClassTeacherRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CalendaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calenda_teacher);
        ListView lvCalenda = (ListView) findViewById(R.id.lvcalenda);

        SharedPreferences share = getSharedPreferences("user", MODE_PRIVATE);
        final String username = share.getString("username","");
        final Integer role = share.getInt("role",0);

        Intent intent = getIntent();
        final String learner= intent.getStringExtra("learner");
        final List<Classtime> classtimeList = new ArrayList<Classtime>();
        final ArrayAdapter<Classtime> arrayAdapter
                = new ArrayAdapter<Classtime>(this, android.R.layout.simple_list_item_activated_1 , classtimeList);
        lvCalenda.setAdapter(arrayAdapter);
        if(classtimeList.size()==0){
            if(role == 2) {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            JSONArray listclass = jsonResponse.getJSONArray("classid");
                            for (int i = 0; i < listclass.length(); i++) {
                                JSONObject jsonclass = listclass.getJSONObject(i);
                                Classtime classtimes = new Classtime(jsonclass.getString("classname"), jsonclass.getString("time"));
                                classtimeList.add(classtimes);
                            }
                            arrayAdapter.notifyDataSetChanged();
                            Toast.makeText(CalendaActivity.this, classtimeList.size() + "", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                GetClassTeacherRequest getClassTeacherRequest = new GetClassTeacherRequest(username, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CalendaActivity.this);
                queue.add(getClassTeacherRequest);
            }else{
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            JSONArray listclass = jsonResponse.getJSONArray("classid");
                            for (int i = 0; i < listclass.length(); i++) {
                                JSONObject jsonclass = listclass.getJSONObject(i);
                                Classtime classtimes = new Classtime(jsonclass.getString("classname"), jsonclass.getString("time"));
                                classtimeList.add(classtimes);
                            }
                            arrayAdapter.notifyDataSetChanged();
                            Toast.makeText(CalendaActivity.this, classtimeList.size() + "", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                GetClassLearnerRequest getClassLearnerRequest = new GetClassLearnerRequest(learner, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CalendaActivity.this);
                queue.add(getClassLearnerRequest);
            }
        }else{
            arrayAdapter.notifyDataSetChanged();
        }
    }
}
