package com.kyuubi.gr;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.kyuubi.gr.request.AddHomeworkRequest;
import com.kyuubi.gr.request.GetClassTeacherRequest;
import com.kyuubi.gr.request.GetHomeworkRequest;
import com.kyuubi.gr.request.GetUserRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddHomeworkActivity extends AppCompatActivity {
    List<String> spinnerArray = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_homework);
        final Spinner sClassid = (Spinner) findViewById(R.id.sClassid);
        final EditText etContent = (EditText) findViewById(R.id.etContent);
        final Button bAddHomework = (Button) findViewById(R.id.bAddHomework);

        adapter = new ArrayAdapter<String>(AddHomeworkActivity.this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sClassid.setAdapter(adapter);
        sClassid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    final String classid = sClassid.getSelectedItem().toString();

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    etContent.setText(jsonResponse.getString("content"));
                                } else {
                                    etContent.setText("");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    };

                    GetHomeworkRequest getHomeworkRequest = new GetHomeworkRequest(classid,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(AddHomeworkActivity.this);
                    queue.add(getHomeworkRequest);
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
            SharedPreferences share = getSharedPreferences("user", MODE_PRIVATE);
            String username = share.getString("username", "");
            GetClassTeacherRequest getClassTeacherRequest = new GetClassTeacherRequest(username, responseListener);
            RequestQueue queue = Volley.newRequestQueue(AddHomeworkActivity.this);
            queue.add(getClassTeacherRequest);
        } else {
            adapter.notifyDataSetChanged();
        }

        bAddHomework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String classid = sClassid.getSelectedItem().toString();
                final String content = etContent.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(AddHomeworkActivity.this, HomeworkActivity.class);
                                AddHomeworkActivity.this.startActivity(intent);
                            } else {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                AddHomeworkRequest addHomeworkRequest = new AddHomeworkRequest(classid,content,responseListener);
                RequestQueue queue = Volley.newRequestQueue(AddHomeworkActivity.this);
                queue.add(addHomeworkRequest);
            }
        });
    }
}
