package com.kyuubi.gr;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 21/05/2017.
 */

public class CommentAdapter extends ArrayAdapter<Comment> {
    public CommentAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Comment> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Comment comment = this.getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_item,parent,false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView timestamp = (TextView) convertView.findViewById(R.id.timestamp);
        TextView txtStatusMsg = (TextView) convertView.findViewById(R.id.txtStatusMsg);
        name.setText(comment.getUsername());
        timestamp.setText(comment.getTimestamp());
        txtStatusMsg.setText(comment.getContent());
        return convertView;
    }
}
