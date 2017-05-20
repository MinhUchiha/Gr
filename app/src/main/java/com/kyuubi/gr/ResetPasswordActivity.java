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
import com.kyuubi.gr.request.ResetPasswordRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ResetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        final EditText etUserName = (EditText) findViewById(R.id.etUsername);
        final EditText etNewPassword = (EditText) findViewById(R.id.etNewPassword);
        final Button bResetPassword = (Button) findViewById(R.id.bResetPassword);

        bResetPassword.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                final String username = etUserName.getText().toString();
                final String password = etNewPassword.getText().toString();
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
                                Intent intent = new Intent(ResetPasswordActivity.this, AdminMainActivity.class);
                                ResetPasswordActivity.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(ResetPasswordActivity.this);
                                builder.setMessage("Reset Password Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest(username, password,responseListener);
                RequestQueue queue = Volley.newRequestQueue(ResetPasswordActivity.this);
                queue.add(resetPasswordRequest);
            }
        });
    }
}
