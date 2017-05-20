package com.kyuubi.gr;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.LayoutInflaterFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.kyuubi.gr.Post;

import java.util.List;

/**
 * Created by Administrator on 20/05/2017.
 */

public class PostAdapter extends ArrayAdapter<Post>{
    public PostAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Post> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Post post = this.getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView timestamp = (TextView) convertView.findViewById(R.id.timestamp);
        TextView txtStatusMsg = (TextView) convertView.findViewById(R.id.txtStatusMsg);
        name.setText(post.getUsername());
        timestamp.setText(post.getTimestamp());
        txtStatusMsg.setText(post.getContent());
        return convertView;
    }
}
