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
import com.kyuubi.gr.request.GetPointClassRequest;
import com.kyuubi.gr.request.GetPointLearnerRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewPointLearnerActivity extends AppCompatActivity {
    List<String> pointArray = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_point_learner);

        final ListView lvPoint = (ListView) findViewById(R.id.lvPoint);

        Intent intent = getIntent();
        final String learner = intent.getStringExtra("learner");

        adapter = new ArrayAdapter<String>(ViewPointLearnerActivity.this, android.R.layout.simple_list_item_1, pointArray);
        lvPoint.setAdapter(adapter);
        pointArray.clear();
        if(pointArray.size()==0){
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            JSONArray array = jsonResponse.getJSONArray("listpoint");
                            for(int i=0;i<array.length();i++){
                                JSONObject jsonpoint  = array.getJSONObject(i);
                                pointArray.add(jsonpoint.getString("classid")+":"+jsonpoint.getString("point"));
                            }
                            adapter.notifyDataSetChanged();
                        }else{
                            Toast.makeText(ViewPointLearnerActivity.this,"False check",Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            adapter.notifyDataSetChanged();
            GetPointLearnerRequest getPointLearnerRequest = new GetPointLearnerRequest(learner,responseListener);
            RequestQueue queue = Volley.newRequestQueue(ViewPointLearnerActivity.this);
            queue.add(getPointLearnerRequest);
        }else{
            adapter.notifyDataSetChanged();
        }
    }
}
