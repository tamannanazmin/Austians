package com.example.registration;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class Guest_SetUpActivity extends AppCompatActivity {
    private Button save;
    private EditText username,fullname,birth;
    private CircleImageView profilePic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest__set_up);
        save=(Button)findViewById(R.id.save);
        username=(EditText)findViewById(R.id.username);
        fullname=(EditText)findViewById(R.id.fullName);
        birth=(EditText)findViewById(R.id.birth);
        profilePic=(CircleImageView)findViewById(R.id.profilePic);
    }
}
