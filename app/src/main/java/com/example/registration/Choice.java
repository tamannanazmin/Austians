package com.example.registration;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class Choice extends AppCompatActivity {
    private Button signin,signup;
    private ImageView image;
    private TextView text;
    Animation bottom,top;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice);
        signin=(Button)findViewById(R.id.signin);
        bottom = AnimationUtils.loadAnimation(this,R.anim.from_bottom);
        signin.setAnimation(bottom);

        signup=(Button)findViewById(R.id.signup);
        signup.setAnimation(bottom);

        image=(ImageView) findViewById(R.id.image);
        top = AnimationUtils.loadAnimation(this,R.anim.from_top);
        image.setAnimation(top);

        text=(TextView) findViewById(R.id.text);
        text.setAnimation(top);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity();
            }
        });
    }
    private void Login()
    {
        Intent intent=new Intent(Choice.this,Login.class);
        startActivity(intent);
    }
    private void MainActivity()
    {
        Intent intent=new Intent(Choice.this,MainActivity.class);
        startActivity(intent);
    }
}
