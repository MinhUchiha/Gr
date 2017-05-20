package com.kyuubi.gr;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.kyuubi.gr.request.LoginRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    String username="";
    String password="";
    String role;
    String a ="BB";
    ProgressDialog pd;
    boolean success;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText etUsername = (EditText) findViewById(R.id.etUsername);
        final EditText etPassword = (EditText) findViewById(R.id.etPassword);
        final Button bLogin = (Button) findViewById(R.id.bLogin);
        final TextView registerLink = (TextView) findViewById(R.id.tvRegisterhere);

        registerLink.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent registrIntent = new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(registrIntent);
            }
        });

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = etUsername.getText().toString();
                password = etPassword.getText().toString();
                //new LoginTask().execute();
                pd = ProgressDialog.show(LoginActivity.this, "Loading.Please wait...", "Login....", true);
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            pd.dismiss();
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                String name = jsonResponse.getString("name");
                                String role = jsonResponse.getString("role");
                                SharedPreferences share = getSharedPreferences("user", MODE_PRIVATE);
                                SharedPreferences.Editor editor = share.edit();
                                editor.putString("username",username);
                                editor.putInt("role",Integer.parseInt(role));
                                editor.apply();
                                if(role.equals("1")){
                                    Intent intent = new Intent(LoginActivity.this, AdminMainActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("role", role);
                                    intent.putExtra("username", username);
                                    LoginActivity.this.startActivity(intent);
                                }else if(role.equals("2")) {
                                    Intent intent = new Intent(LoginActivity.this, TeacherMainActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("role", role);
                                    intent.putExtra("username", username);
                                    LoginActivity.this.startActivity(intent);
                                }else{
                                    Intent intent = new Intent(LoginActivity.this, AllMainActivity.class);
                                    intent.putExtra("name", name);
                                    intent.putExtra("role", role);
                                    intent.putExtra("username", username);
                                    LoginActivity.this.startActivity(intent);
                                }
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("Login Failed")
                                        .setNegativeButton("Retry", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                LoginRequest loginRequest = new LoginRequest(username, password,responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
    }

    public class LoginTask extends AsyncTask<Object, Object, Void> {

        @Override
        protected Void doInBackground(Object... params) {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        success = jsonResponse.getBoolean("success");
                        if (success) {
                            role = jsonResponse.getString("role");
                        }else{
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };

            LoginRequest loginRequest = new LoginRequest(username, password,responseListener);
            RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
            queue.add(loginRequest);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void s) {
            super.onPostExecute(s);
            if (success) {
                SharedPreferences share = getSharedPreferences("user", MODE_PRIVATE);
                SharedPreferences.Editor editor = share.edit();
                editor.putString("username",username);
                editor.apply();
                if(role.equals("1")){
                    Intent intent = new Intent(LoginActivity.this, AdminMainActivity.class);
                    LoginActivity.this.startActivity(intent);
                }else if(role.equals("2")) {
                    Intent intent = new Intent(LoginActivity.this, TeacherMainActivity.class);
                    LoginActivity.this.startActivity(intent);
                }else{
                    Intent intent = new Intent(LoginActivity.this, AllMainActivity.class);
                    intent.putExtra("username", username);
                    LoginActivity.this.startActivity(intent);
                }
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setMessage(a)
                        .setNegativeButton("Retry", null)
                        .create()
                        .show();
            }
        }
    }
}
