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

public class DataRetrievedForInstrument extends AppCompatActivity {
    private ListView InslistView;
    DatabaseReference databaseReference;
    List<postListForInstrument> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_retrieved_for_instrument);

        InslistView = findViewById(R.id.list_view_for_instrument);
        databaseReference = FirebaseDatabase.getInstance().getReference("Posts For Instruments");

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
                    postListForInstrument postInstrument = postSnapshot.getValue(postListForInstrument.class);
                    list.add(postInstrument);
                }
                PostForInstrumentListAdapter postListAdapter = new PostForInstrumentListAdapter(DataRetrievedForInstrument.this,list);
                InslistView.setAdapter(postListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
