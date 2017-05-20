package com.kyuubi.gr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.kyuubi.gr.request.GetHomeworkRequest;

public class HomeworkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        final ImageButton ibHomework = (ImageButton) findViewById(R.id.ibHomework);
        final ImageButton ibViewHomework = (ImageButton) findViewById(R.id.ibViewHomework);

        ibHomework.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeworkActivity.this,AddHomeworkActivity.class);
                HomeworkActivity.this.startActivity(intent);
            }
        });

        ibViewHomework.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeworkActivity.this,ViewHomework.class);
                HomeworkActivity.this.startActivity(intent);
            }
        });
    }
}
