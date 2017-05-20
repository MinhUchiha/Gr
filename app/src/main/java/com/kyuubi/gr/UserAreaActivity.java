package com.kyuubi.gr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class UserAreaActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_area);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String username = intent.getStringExtra("username");
        String role = intent.getStringExtra("role");
        SharedPreferences share = getSharedPreferences("user", MODE_PRIVATE);
        share.getString("username","");
        TextView tvWelcomeMsg = (TextView) findViewById(R.id.tvWellcomemsg);
        EditText etUsername = (EditText) findViewById(R.id.etUsername);
        EditText etRole = (EditText) findViewById(R.id.etRole);

        String message = name + " welcome";
        tvWelcomeMsg.setText(message);
        etUsername.setText(username);
        etRole.setText(role);
    }
}

