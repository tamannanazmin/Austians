package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DataRetrieved extends AppCompatActivity {

    private ListView listView;
    DatabaseReference databaseReference;
    List<PostList>postList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_retrieved);

        listView = findViewById(R.id.list_view);
        databaseReference = FirebaseDatabase.getInstance().getReference("Posts");

        postList = new ArrayList<>();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
            {
                PostList post = postSnapshot.getValue(PostList.class);
                postList.add(post);
            }
            PostListAdapter postListAdapter = new PostListAdapter(DataRetrieved.this,postList);
            listView.setAdapter(postListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
