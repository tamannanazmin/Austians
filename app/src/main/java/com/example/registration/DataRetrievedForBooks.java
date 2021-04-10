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

public class DataRetrievedForBooks extends AppCompatActivity {
    private ListView InslistView;
    DatabaseReference databaseReference;
    List<PostListForBooks> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_retrieved_for_books);

        InslistView = findViewById(R.id.list_view_for_books);
        databaseReference = FirebaseDatabase.getInstance().getReference("PostsForBooks");

        list = new ArrayList<>();
    }
    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    PostListForBooks post = postSnapshot.getValue(PostListForBooks.class);
                    list.add(post);
                }
                PostForBooksListAdapter postListAdapter = new PostForBooksListAdapter(DataRetrievedForBooks.this,list);
                InslistView.setAdapter(postListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
