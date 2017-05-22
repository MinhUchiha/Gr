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
import com.kyuubi.gr.request.GetLearnerRelationshipRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectLearnerActivity extends AppCompatActivity {
    List<String> spinnerArray = new ArrayList<String>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_learner);

        final Spinner sLearner = (Spinner) findViewById(R.id.sLearner);
        final Button bSelect = (Button) findViewById(R.id.bSelect);
        SharedPreferences share = getSharedPreferences("user", MODE_PRIVATE);
        String username = share.getString("username", "");
        int role = share.getInt("role",0);
        Intent intent = getIntent();
        final int id= intent.getIntExtra("id",0);
        adapter = new ArrayAdapter<String>(SelectLearnerActivity.this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sLearner.setAdapter(adapter);
        sLearner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view,
                                       int position, long id) {
                Object item = adapterView.getItemAtPosition(position);
                if (item != null) {
                    Toast.makeText(SelectLearnerActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    JSONArray listclass = jsonResponse.getJSONArray("listrelationship");
                    for (int i = 0; i < listclass.length(); i++) {
                        JSONObject jsonlearner = listclass.getJSONObject(i);
                        spinnerArray.add(jsonlearner.getString("learner"));
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        GetLearnerRelationshipRequest getLearnerRelationshipRequest = new GetLearnerRelationshipRequest(username, responseListener);
        RequestQueue queue = Volley.newRequestQueue(SelectLearnerActivity.this);
        queue.add(getLearnerRelationshipRequest);
        adapter.notifyDataSetChanged();

        bSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(id==1) {
                    Intent intent = new Intent(SelectLearnerActivity.this, CalendaActivity.class);
                    intent.putExtra("learner", sLearner.getSelectedItem().toString());
                    SelectLearnerActivity.this.startActivity(intent);
                }
                if(id==2){
                    Intent intent = new Intent(SelectLearnerActivity.this, ViewHomework.class);
                    intent.putExtra("learner", sLearner.getSelectedItem().toString());
                    SelectLearnerActivity.this.startActivity(intent);
                }
                if(id==3){
                    Intent intent = new Intent(SelectLearnerActivity.this, ViewPointLearnerActivity.class);
                    intent.putExtra("learner", sLearner.getSelectedItem().toString());
                    SelectLearnerActivity.this.startActivity(intent);
                }
            }
        });
    }
}
