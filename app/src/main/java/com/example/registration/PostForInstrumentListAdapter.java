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

public class PostForInstrumentListAdapter extends ArrayAdapter<postListForInstrument> {
    private Activity context;
    private List<postListForInstrument> list;

    public PostForInstrumentListAdapter(Activity context, List<postListForInstrument> list) {
        super(context, R.layout.list_view_for_instrument,list);
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listView = inflater.inflate(R.layout.list_view_for_instrument, null, true);


        TextView name = (TextView) listView.findViewById(R.id.ins_full_name);
        TextView description = (TextView) listView.findViewById(R.id.ins_description);
        TextView date = (TextView) listView.findViewById(R.id.ins_date);
        ImageView postimage = (ImageView) listView.findViewById(R.id.ins_image);
        TextView time = (TextView) listView.findViewById(R.id.ins_time);

        postListForInstrument postinstrument = list.get(position);
        date.setText(postinstrument.getDate());
        description.setText(postinstrument.getDescription());
        name.setText(postinstrument.getFullname());
        Picasso.get().load(postinstrument.getPostimageforInstruments()).resize(60, 100).centerCrop().placeholder(R.drawable.profile).into(postimage);
        time.setText(postinstrument.getTime());
        return listView;

    }
}

