package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.widget.Toolbar;

public class Guest_Addpost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toolbar toolbar;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest__addpost);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Post");
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        SendUserToGuestHome();
        return super.onOptionsItemSelected(item);
    }
    private void SendUserToGuestHome()
    {
        Intent intent=new Intent(Guest_Addpost.this,Guest_Home.class);
        startActivity(intent);
    }
}
