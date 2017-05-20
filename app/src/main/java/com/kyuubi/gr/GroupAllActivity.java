package com.kyuubi.gr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

public class GroupAllActivity extends AppCompatActivity {
    List<String> spinnerArray = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_all);

        final Spinner sClassid = (Spinner) findViewById(R.id.sClassid);
        final Button bGroupSelect = (Button) findViewById(R.id.bGroupSelect);

        adapter = new ArrayAdapter<String>(GroupAllActivity.this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sClassid.setAdapter(adapter);
        sClassid.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    Toast.makeText(GroupAllActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
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
            GetClassLearnerRequest getClassLearnerRequest = new GetClassLearnerRequest(username, responseListener);
            RequestQueue queue = Volley.newRequestQueue(GroupAllActivity.this);
            queue.add(getClassLearnerRequest);
        } else {
            adapter.notifyDataSetChanged();
        }

        bGroupSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupAllActivity.this, GroupActivity.class);
                intent.putExtra("classid",sClassid.getSelectedItem().toString());
                GroupAllActivity.this.startActivity(intent);
            }
        });
    }
}
