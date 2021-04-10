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

public class PostForRentRoomsListAdapter extends ArrayAdapter<PostListForRentRooms> {
    private Activity context;
    private List<PostListForRentRooms> list;

    public PostForRentRoomsListAdapter(Activity context, List<PostListForRentRooms> list) {
        super(context, R.layout.list_view_for_rent_rooms, list);
        this.context = context;
        this.list = list;

    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listView = inflater.inflate(R.layout.list_view_for_rent_rooms, null, true);

        TextView cost = (TextView) listView.findViewById(R.id.rm_cost);
        TextView location = (TextView) listView.findViewById(R.id.rm_location);
        TextView month = (TextView) listView.findViewById(R.id.rm_month);
        TextView name = (TextView) listView.findViewById(R.id.rm_full_name);
        TextView description = (TextView) listView.findViewById(R.id.rm_description);
        TextView date = (TextView) listView.findViewById(R.id.rm_date);
        ImageView postimage = (ImageView) listView.findViewById(R.id.rm_image);
        TextView time = (TextView) listView.findViewById(R.id.rm_time);

        PostListForRentRooms post = list.get(position);
        cost.setText("Cost:"+post.getCost());
        location.setText("Location: "+post.getLocation());
        month.setText("Month: "+post.getMonth());
        date.setText(post.getDate());
        description.setText(post.getDescription());
        name.setText(post.getFullname());
        Picasso.get().load(post.getPostimage()).resize(60, 100).centerCrop().placeholder(R.drawable.profile).into(postimage);
        time.setText(post.getTime());
        return listView;

    }
}
