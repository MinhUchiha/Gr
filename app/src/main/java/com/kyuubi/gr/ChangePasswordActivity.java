package com.kyuubi.gr;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.kyuubi.gr.request.ChangePasswordRequest;
import com.kyuubi.gr.request.ResetPasswordRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final EditText etNewPass = (EditText) findViewById(R.id.etNewpass);
        final EditText etCofirm = (EditText) findViewById(R.id.etCofirm);
        final Button bChangePass = (Button) findViewById(R.id.bChangePass);

        bChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SharedPreferences share = getSharedPreferences("user", MODE_PRIVATE);
                final String username = share.getString("username","");
                final String password = etPassword.getText().toString();
                final String newpass = etNewPass.getText().toString();
                final String cofirm = etCofirm.getText().toString();
                if(newpass.equals(cofirm)){
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    SharedPreferences.Editor editor = share.edit();
                                    editor.clear();
                                    Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                                    ChangePasswordActivity.this.startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
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

                    ChangePasswordRequest resetPasswordRequest = new ChangePasswordRequest(username, password, newpass,responseListener);
                    RequestQueue queue = Volley.newRequestQueue(ChangePasswordActivity.this);
                    queue.add(resetPasswordRequest);
                }
                else{
                    Toast.makeText(ChangePasswordActivity.this,"Cofirm unlike newpass",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
