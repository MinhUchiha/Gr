package com.kyuubi.gr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.kyuubi.gr.request.AddComment;
import com.kyuubi.gr.request.AddPostRequest;
import com.kyuubi.gr.request.GetCommentRequest;
import com.kyuubi.gr.request.GetPostRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommentActivity extends AppCompatActivity {
    List<Comment> commentList = new ArrayList<Comment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        Intent intent = getIntent();
        final String postid = intent.getStringExtra("postid");
        Integer.parseInt(postid);

        final EditText etPost = (EditText) findViewById(R.id.etComment);
        final ListView lvPost = (ListView) findViewById(R.id.lvComment);
        final Button bPost = (Button) findViewById(R.id.bComment);
        final CommentAdapter postAdapter = new CommentAdapter(CommentActivity.this,R.layout.post_item,commentList);
        lvPost.setAdapter(postAdapter);

        bPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String content = etPost.getText().toString();
                SharedPreferences share = getSharedPreferences("user", MODE_PRIVATE);
                String username = share.getString("username", "");
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            etPost.setText("");
                            commentList.clear();
                            if(commentList.size()==0) {
                                Response.Listener<String> responseListener = new Response.Listener<String>() {
                                    public void onResponse(String response) {
                                        try {
                                            Toast.makeText(CommentActivity.this,Integer.parseInt(postid)+"",Toast.LENGTH_SHORT).show();
                                            JSONObject jsonResponse = new JSONObject(response);
                                            JSONArray jsonArray = jsonResponse.getJSONArray("comment");
                                            for (int i = 0; i < jsonArray.length(); i++) {
                                                JSONObject jsonclass = jsonArray.getJSONObject(i);
                                                Comment post = new Comment(jsonclass.getString("commentid"),jsonclass.getInt("postid"),jsonclass.getString("username"),jsonclass.getString("content"),jsonclass.getString("timestamp"));
                                                commentList.add(post);
                                            }
                                            postAdapter.notifyDataSetChanged();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                };
                                GetCommentRequest getPostRequest = new GetCommentRequest(Integer.parseInt(postid), responseListener);
                                RequestQueue queue = Volley.newRequestQueue(CommentActivity.this);
                                queue.add(getPostRequest);
                            }else{
                                postAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                AddComment addComment = new AddComment(Integer.parseInt(postid),username,content, responseListener);
                RequestQueue queue = Volley.newRequestQueue(CommentActivity.this);
                queue.add(addComment);
            }
        });


        if(commentList.size()==0) {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        JSONArray jsonArray = jsonResponse.getJSONArray("comment");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonclass = jsonArray.getJSONObject(i);
                            Comment post = new Comment(jsonclass.getString("commentid"),jsonclass.getInt("postid"),jsonclass.getString("username"),jsonclass.getString("content"),jsonclass.getString("timestamp"));
                            commentList.add(post);
                        }
                        postAdapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            GetCommentRequest getPostRequest = new GetCommentRequest(Integer.parseInt(postid), responseListener);
            RequestQueue queue = Volley.newRequestQueue(CommentActivity.this);
            queue.add(getPostRequest);
        }else{
            postAdapter.notifyDataSetChanged();
        }
    }
}
