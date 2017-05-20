package com.kyuubi.gr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ClassManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_manager);
        final ImageButton ibCreateClass = (ImageButton) findViewById(R.id.ibAddClass);
        final ImageButton ibEditCLass = (ImageButton) findViewById(R.id.ibEditClass);
        final ImageButton ibDeleteCLass = (ImageButton) findViewById(R.id.ibDeleteClass);
        final ImageButton ibAddLearner = (ImageButton) findViewById(R.id.ibAddLearn);
        ibCreateClass.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(ClassManagerActivity.this, CreateClassActivity.class);
                ClassManagerActivity.this.startActivity(intent);
            }
        });
        ibEditCLass.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(ClassManagerActivity.this, EditClassActivity.class);
                ClassManagerActivity.this.startActivity(intent);
            }
        });
        ibDeleteCLass.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(ClassManagerActivity.this, DeleteClassActivity.class);
                ClassManagerActivity.this.startActivity(intent);
            }
        });
        ibAddLearner.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(ClassManagerActivity.this, AddLearnerActivity.class);
                ClassManagerActivity.this.startActivity(intent);
            }
        });
    }
}
