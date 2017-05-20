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
import com.kyuubi.gr.request.AddPointRequest;
import com.kyuubi.gr.request.GetClassTeacherRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AddPointActivity extends AppCompatActivity {
    List<String> spinnerArray = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_point);
        final EditText etLearner = (EditText) findViewById(R.id.etLearner);
        final EditText etPoint = (EditText) findViewById(R.id.etPoint);
        final Button bAddPoint = (Button) findViewById(R.id.bAddPoint);

        SharedPreferences share = getSharedPreferences("user", MODE_PRIVATE);
        adapter = new ArrayAdapter<String>(AddPointActivity.this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinner = (Spinner) findViewById(R.id.sClassid);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    Toast.makeText(AddPointActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
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
            RequestQueue queue = Volley.newRequestQueue(AddPointActivity.this);
            queue.add(getClassTeacherRequest);
        } else {
            adapter.notifyDataSetChanged();
        }

        bAddPoint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String classid = spinner.getSelectedItem().toString();
                final String learner = etLearner.getText().toString();
                final double point = Double.parseDouble(etPoint.getText().toString());
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(AddPointActivity.this, PointManagerActivity.class);
                                AddPointActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddPointActivity.this);
                                builder.setMessage("Add point Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                AddPointRequest  addPointRequest = new AddPointRequest(classid, learner,point,responseListener);
                RequestQueue queue = Volley.newRequestQueue(AddPointActivity.this);
                queue.add(addPointRequest);
            }
        });
    }
}
