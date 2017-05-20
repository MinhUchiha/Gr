package com.kyuubi.gr;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.kyuubi.gr.request.GetClassTeacherRequest;
import com.kyuubi.gr.request.GetPointClassRequest;

import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewPointTeacherActivity extends AppCompatActivity {
    List<String> spinnerArray = new ArrayList<String>();
    List<String> pointArray = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_point_teacher);
        final ListView lvPoint = (ListView) findViewById(R.id.lvPoint);
        final Button bFind = (Button) findViewById(R.id.bFind);
        SharedPreferences share = getSharedPreferences("user", MODE_PRIVATE);
        adapter = new ArrayAdapter<String>(ViewPointTeacherActivity.this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter2 = new ArrayAdapter<String>(ViewPointTeacherActivity.this, android.R.layout.simple_list_item_1, pointArray);
        lvPoint.setAdapter(adapter2);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinner = (Spinner) findViewById(R.id.sClassid);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    Toast.makeText(ViewPointTeacherActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        if (spinnerArray.size() == 0) {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        JSONArray listclass = jsonResponse.getJSONArray("classid");
                        for (int i = 0; i < listclass.length(); i++) {
                            JSONObject jsonclass = listclass.getJSONObject(i);
                            spinnerArray.add(jsonclass.getString("classid"));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            String username = share.getString("username", "");
            GetClassTeacherRequest getClassTeacherRequest = new GetClassTeacherRequest(username, responseListener);
            RequestQueue queue = Volley.newRequestQueue(ViewPointTeacherActivity.this);
            queue.add(getClassTeacherRequest);
        } else {
            adapter.notifyDataSetChanged();
        }

        bFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pointArray.clear();
                String classid = spinner.getSelectedItem().toString();
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
                                        pointArray.add(jsonpoint.getString("username")+":"+jsonpoint.getString("point"));
                                    }
                                    adapter2.notifyDataSetChanged();
                                }else{
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    adapter2.notifyDataSetChanged();
                    GetPointClassRequest getPointClassRequest = new GetPointClassRequest(classid,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ViewPointTeacherActivity.this);
                    queue.add(getPointClassRequest);
                }else{
                    adapter2.notifyDataSetChanged();
                }
            }
        });
    }
}
