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
import com.kyuubi.gr.request.CreateClassRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateClassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_class);
        final EditText etClassid = (EditText) findViewById(R.id.etClassid);
        final EditText etClassName = (EditText) findViewById(R.id.etClassName);
        final EditText etSetember = (EditText) findViewById(R.id.etSetember);
        final EditText etTime = (EditText) findViewById(R.id.etTime);
        final EditText etTeacher = (EditText) findViewById(R.id.etTeacher);
        final Button bCreateClass = (Button) findViewById(R.id.bCreateClass);

        bCreateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String classid = etClassid.getText().toString();
                final String classname = etClassName.getText().toString();
                final String setember = etSetember.getText().toString();
                final String teacher = etTeacher.getText().toString();
                final String time = etTime.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateClassActivity.this);
                                builder.setMessage("Create Class Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                                Intent intent = new Intent(CreateClassActivity.this, ClassManagerActivity.class);
                                CreateClassActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(CreateClassActivity.this);
                                builder.setMessage("Create Class Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                CreateClassRequest createClassRequest = new CreateClassRequest(classid, classname, setember, time ,teacher, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CreateClassActivity.this);
                queue.add(createClassRequest);
            }
        });
    }
}
