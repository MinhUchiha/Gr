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
import com.kyuubi.gr.request.DeleteClassRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class DeleteClassActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_class);
        final EditText etClassid = (EditText) findViewById(R.id.etClassid);
        final Button bDeleteClass = (Button) findViewById(R.id.bDeleteClass);

        bDeleteClass.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final String classid = etClassid.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(DeleteClassActivity.this, ClassManagerActivity.class);
                                DeleteClassActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteClassActivity.this);
                                builder.setMessage("Delete Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                DeleteClassRequest deleteClassRequest = new DeleteClassRequest(classid,responseListener);
                RequestQueue queue = Volley.newRequestQueue(DeleteClassActivity.this);
                queue.add(deleteClassRequest);
            }
        });
    }
}
