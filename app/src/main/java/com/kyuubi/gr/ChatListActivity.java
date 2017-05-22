package com.kyuubi.gr;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kyuubi.gr.request.GetUserListRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatListActivity extends AppCompatActivity {
    private ListView listView;
    private ArrayAdapter<User> arrayAdapter;
    private ArrayList<User> list_of_user = new ArrayList<>();
    private DatabaseReference root = FirebaseDatabase.getInstance().getReference().getRoot();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences share = getSharedPreferences("user", MODE_PRIVATE);
        final String username = share.getString("username","");
        final String name = share.getString("name","");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        listView = (ListView) findViewById(R.id.listView);
        arrayAdapter = new ArrayAdapter<User>(this,android.R.layout.simple_list_item_1,list_of_user);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                root.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String room_name;
                        if(dataSnapshot.hasChild(username+list_of_user.get(position).getUsername()))
                        {
                            room_name = username+list_of_user.get(position).getUsername();
                            Intent intent = new Intent(getApplicationContext(),ChatRoomActivity.class);
                            intent.putExtra("room_name",room_name);
                            intent.putExtra("name",list_of_user.get(position).getUsername());
                            intent.putExtra("user_name",name);
                            startActivity(intent);
                        }else if(dataSnapshot.hasChild(list_of_user.get(position).getUsername()+username)){
                            room_name = list_of_user.get(position).getUsername()+username;
                            Intent intent = new Intent(getApplicationContext(),ChatRoomActivity.class);
                            intent.putExtra("room_name",room_name);
                            intent.putExtra("name",list_of_user.get(position).getUsername());
                            intent.putExtra("user_name",name);
                            startActivity(intent);
                        }else{
                            room_name = username+list_of_user.get(position).getUsername();
                            Map<String,Object> map = new HashMap<String, Object>();
                            map.put(room_name,"");
                            root.updateChildren(map);
                            Intent intent = new Intent(getApplicationContext(),ChatRoomActivity.class);
                            intent.putExtra("room_name",room_name);
                            intent.putExtra("name",list_of_user.get(position).getUsername());
                            intent.putExtra("user_name",name);
                            startActivity(intent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        if(list_of_user.size()==0){
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            JSONArray listuser = jsonResponse.getJSONArray("userlist");
                            for (int i = 0; i < listuser.length(); i++) {
                                JSONObject jsonUser = listuser.getJSONObject(i);
                                User user = new User(jsonUser.getString("name"),jsonUser.getString("username"));
                                list_of_user.add(user);
                            }
                            arrayAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                GetUserListRequest getUserListRequest = new GetUserListRequest(username, responseListener);
                RequestQueue queue = Volley.newRequestQueue(ChatListActivity.this);
                queue.add(getUserListRequest);
        }else{
            arrayAdapter.notifyDataSetChanged();
        }
    }
}
