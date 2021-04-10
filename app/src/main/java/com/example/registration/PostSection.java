package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.widget.Toolbar;


public class PostSection extends AppCompatActivity {
    private Toolbar toolbar;
    private Button helpPost,lendBook,lendInstrument,roomForRent,event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_section);

        //adding a toolbar
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Post Section");

        //entering in a help post page
        helpPost=(Button)findViewById(R.id.helpPost);
        helpPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Post();
            }
        });

        //entering in a book post page
        lendBook=(Button)findViewById(R.id.lendBooks);
        lendBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostForLendingBooks();
            }
        });

        //entering in a instrument post page
        lendInstrument=(Button)findViewById(R.id.lendInstruments);
        lendInstrument.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostForLendingInstruments();
            }
        });

        //Entering in a rent room post page
        roomForRent=(Button)findViewById(R.id.roomForRent);
        roomForRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostForRentingRoom();
            }
        });


        event=(Button)findViewById(R.id.addAEvent);
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PostForEvent();
            }
        });


    }
    private void Post()
    {
        Intent intent=new Intent(PostSection.this,Post.class);
        startActivity(intent);
    }
    private void PostForLendingBooks()
    {
        Intent intent=new Intent(PostSection.this,PostForLendingBooks.class);
        startActivity(intent);

    }
    private void PostForLendingInstruments()
    {
        Intent intent=new Intent(PostSection.this,PostForLendingInstruments.class);
        startActivity(intent);

    }
    private void PostForRentingRoom()
    {
        Intent intent=new Intent(PostSection.this,PostForRentingRoom.class);
        startActivity(intent);

    }
    private void PostForEvent()
    {
        Intent intent=new Intent(PostSection.this,AddNewEvent.class);
        startActivity(intent);

    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Home();
        return super.onOptionsItemSelected(item);
    }
    private void Home()
    {
        Intent intent=new Intent(PostSection.this,Home.class);
        startActivity(intent);
    }
}
