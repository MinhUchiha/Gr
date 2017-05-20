package com.kyuubi.gr;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.kyuubi.gr.request.AddLearnerRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class AddLearnerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_learner);
        final EditText etClassid = (EditText) findViewById(R.id.etClassid);
        final EditText etLearner = (EditText) findViewById(R.id.etUsername);
        final Button bAddLearner = (Button) findViewById(R.id.bAddLearner);

        bAddLearner.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final String classid = etClassid.getText().toString();
                final String learner = etLearner.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(AddLearnerActivity.this, ClassManagerActivity.class);
                                AddLearnerActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(AddLearnerActivity.this);
                                builder.setMessage("Add Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                AddLearnerRequest addLearnerRequest = new AddLearnerRequest(classid,learner,responseListener);
                RequestQueue queue = Volley.newRequestQueue(AddLearnerActivity.this);
                queue.add(addLearnerRequest);

                Intent intent = new Intent(AddLearnerActivity.this, ClassManagerActivity.class);
                AddLearnerActivity.this.startActivity(intent);
            }
        });
    }
}
