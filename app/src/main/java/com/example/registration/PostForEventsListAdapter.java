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

public class PostForEventsListAdapter extends ArrayAdapter<PostListForEvents> {
    private Activity context;
    private List<PostListForEvents> list;

    public PostForEventsListAdapter(Activity context, List<PostListForEvents> list) {
        super(context,R.layout.list_view_for_events,list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listView = inflater.inflate(R.layout.list_view_for_events, null, true);
        TextView eventscategory = (TextView) listView.findViewById(R.id.event_category);
        TextView eventsdate = (TextView) listView.findViewById(R.id.event_date);
        TextView eventstime = (TextView) listView.findViewById(R.id.event_time);
        TextView eventslocation = (TextView) listView.findViewById(R.id.event_location);

        TextView name = (TextView) listView.findViewById(R.id.event_full_name);
        TextView description = (TextView) listView.findViewById(R.id.event_description);
        TextView date = (TextView) listView.findViewById(R.id.e_date);
        ImageView postimage = (ImageView) listView.findViewById(R.id.event_image);
        TextView time = (TextView) listView.findViewById(R.id.e_time);

        PostListForEvents post = list.get(position);
        eventscategory.setText("Category: "+post.getCategory());
        eventsdate.setText("Event Date: "+post.getEventsDate());
        eventstime.setText("Event Time: "+post.getEventsTime());
        eventslocation.setText("Event Location: "+post.getEventsLocation());

        date.setText(post.getDate());
        description.setText(post.getDescription());
        name.setText(post.getFullname());
        Picasso.get().load(post.getPostimage()).resize(60, 100).centerCrop().placeholder(R.drawable.profile).into(postimage);
        time.setText(post.getTime());
        return listView;

    }
}
