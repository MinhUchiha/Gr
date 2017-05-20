package com.kyuubi.gr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class PointManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_manager);
        final ImageButton ibAddPoint = (ImageButton) findViewById(R.id.ibAddPoint);
        final ImageButton ibViewPoint = (ImageButton) findViewById(R.id.ibViewPoint);

        ibAddPoint.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(PointManagerActivity.this,AddPointActivity.class);
                PointManagerActivity.this.startActivity(intent);
            }
        });
        ibViewPoint.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(PointManagerActivity.this,ViewPointTeacherActivity.class);
                PointManagerActivity.this.startActivity(intent);
            }
        });
    }
}
