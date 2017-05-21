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
import com.kyuubi.gr.request.EditClassRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class EditClassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_class);
        final EditText etClassid = (EditText) findViewById(R.id.etClassid);
        final EditText etClassName = (EditText) findViewById(R.id.etCLassName);
        final EditText etSetember = (EditText) findViewById(R.id.etSetember);
        final EditText etTime = (EditText) findViewById(R.id.etTime);
        final EditText etTeacher = (EditText) findViewById(R.id.etTeacher);
        final Button bCreateClass = (Button) findViewById(R.id.bEditClass);

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
                                Intent intent = new Intent(EditClassActivity.this, ClassManagerActivity.class);
                                EditClassActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditClassActivity.this);
                                builder.setMessage("Edit Class Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                EditClassRequest editClassRequest = new EditClassRequest(classid, classname, setember, time ,teacher, responseListener);
                RequestQueue queue = Volley.newRequestQueue(EditClassActivity.this);
                queue.add(editClassRequest);
            }
        });
    }
}