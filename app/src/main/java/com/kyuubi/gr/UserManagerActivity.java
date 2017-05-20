package com.kyuubi.gr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class UserManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manager);
        final ImageButton ibCreateUser = (ImageButton) findViewById(R.id.ibCreateUser);
        final ImageButton ibResetPassword = (ImageButton) findViewById(R.id.ibResetPassword);
        final ImageButton ibDeleteUser = (ImageButton) findViewById(R.id.ibDeleteUser);

        ibCreateUser.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(UserManagerActivity.this, RegisterActivity.class);
                UserManagerActivity.this.startActivity(intent);
            }
        });

        ibResetPassword.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(UserManagerActivity.this, ResetPasswordActivity.class);
                UserManagerActivity.this.startActivity(intent);
            }
        });

        ibDeleteUser.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(UserManagerActivity.this, DeleteUserActivity.class);
                UserManagerActivity.this.startActivity(intent);
            }
        });
    }
}
