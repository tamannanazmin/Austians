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

public class DataRetrievedForRentRoom extends AppCompatActivity {
    private ListView InslistView;
    DatabaseReference databaseReference;
    List<PostListForRentRooms> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_retrieved_for_rent_room);

        InslistView = findViewById(R.id.list_view_for_rent_rooms);
        databaseReference = FirebaseDatabase.getInstance().getReference("PostsForRoom");

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
                    PostListForRentRooms postInstrument = postSnapshot.getValue(PostListForRentRooms.class);
                    list.add(postInstrument);
                }
                PostForRentRoomsListAdapter postListAdapter = new PostForRentRoomsListAdapter(DataRetrievedForRentRoom.this,list);
                InslistView.setAdapter(postListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
