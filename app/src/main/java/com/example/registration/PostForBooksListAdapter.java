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

public class PostForBooksListAdapter extends ArrayAdapter<PostListForBooks> {
    private Activity context;
    private List<PostListForBooks> list;
    public PostForBooksListAdapter(Activity context, List<PostListForBooks> list) {
        super(context, R.layout.list_view_for_books,list);
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View listView = inflater.inflate(R.layout.list_view_for_books, null, true);

        TextView department = (TextView) listView.findViewById(R.id.books_department);
        TextView semester = (TextView) listView.findViewById(R.id.books_semester);
        TextView name = (TextView) listView.findViewById(R.id.books_full_name);
        TextView description = (TextView) listView.findViewById(R.id.books_description);
        TextView date = (TextView) listView.findViewById(R.id.books_date);
        ImageView postimage = (ImageView) listView.findViewById(R.id.books_image);
        TextView time = (TextView) listView.findViewById(R.id.books_time);

        PostListForBooks post = list.get(position);
        department.setText("Department: "+post.getDepartment());
        semester.setText("Semester: "+post.getSemester());
        date.setText(post.getDate());
        description.setText(post.getDescription());
        name.setText(post.getFullname());
        Picasso.get().load(post.getPostimage()).resize(60, 60).centerCrop().placeholder(R.drawable.profile).into(postimage);
        time.setText(post.getTime());
        return listView;

    }

}
