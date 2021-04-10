package com.example.registration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Survey extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText link;
    private Button share;
    private String copyLink;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        link = (EditText) findViewById(R.id.surveyText);
        share = (Button)findViewById(R.id.share);

        toolbar=(androidx.appcompat.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Survey");

        copyLink = link.getText().toString();

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(copyLink));
                startActivity(browserIntent);
            }
        });

    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Home();
        return super.onOptionsItemSelected(item);
    }
    private void Home()
    {
        Intent intent=new Intent(Survey.this,Home.class);
        startActivity(intent);
    }
}
