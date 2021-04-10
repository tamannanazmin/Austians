package com.example.registration;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PostListAdapter extends ArrayAdapter<PostList> {

    private Activity context;
    private List <PostList> postList;

    public PostListAdapter(Activity context,List<PostList>postList)
    {
        super(context,R.layout.list_view,postList);
        this.context = context;
        this.postList = postList;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listView = inflater.inflate(R.layout.list_view,null,true);


        TextView name = (TextView)listView.findViewById(R.id.post_full_name);
        TextView description  = (TextView)listView.findViewById(R.id.post_description);
        TextView date= (TextView)listView.findViewById(R.id.post_date);
        ImageView postimage=(ImageView)listView.findViewById(R.id.post_image);
        TextView time  = (TextView)listView.findViewById(R.id.post_time);

        PostList post = postList.get(position);
        date.setText(post.getDate());
        description.setText(post.getDescription());
        name.setText(post.getFullname());
        Picasso.get().load(post.getPostimage()).resize(60,100).centerCrop().placeholder(R.drawable.profile).into(postimage);
        time.setText(post.getTime());
        return listView;
    }
}
