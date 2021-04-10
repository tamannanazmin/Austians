package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.print.PrinterId;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Guest_Login extends AppCompatActivity {

    private Button signin;
    private EditText useremail,userpassword;
    private TextView gotoregister;
    private TextView heading;
    private FirebaseAuth mauth;
    private ProgressDialog loadingBar;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //firebase
        mauth=FirebaseAuth.getInstance();
        //font for sign in word
        heading = (TextView) findViewById(R.id.heading);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        heading.setTypeface(myCustomFont);

        /////////////////////////////////////////////////////////////////////////////
        signin=(Button)findViewById(R.id.signin);
        useremail=(EditText) findViewById(R.id.username);
        userpassword=(EditText) findViewById(R.id.password);
        gotoregister=(TextView)findViewById(R.id.gotoregister);
        loadingBar=new ProgressDialog(this);
        /////////////////////////////////////////////////////////////////////////////
        gotoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // gotoregister();
            }
        });

        //sigin button action
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AllowingUserToLogin();
            }
        });
    }
    //onstart method

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser=mauth.getCurrentUser();
        if(currentuser!=null)
        {
            SendUserToTheGuestHomePage();
        }
    }
    //inside sign in button
    private void AllowingUserToLogin() {
        String email=useremail.getText().toString();
        String password=userpassword.getText().toString();
        if(TextUtils.isEmpty(email))
        {
            useremail.setError("Please enter your email...");
            useremail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            useremail.setError("Enter a valid email");
            useremail.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(password))
        {
            userpassword.setError("Please enter your  password.");
            userpassword.requestFocus();
            return;
        }
        else
        {
            loadingBar.setTitle("Sign in");
            loadingBar.setMessage("Please wait for a while...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
            mauth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                SendUserToTheGuestHomePage();
                                Toast.makeText(Guest_Login.this, "you are successfully Signed in", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                            else
                            {
                                String message=task.getException().getMessage();
                                Toast.makeText(Guest_Login.this,"Error accured"+message,Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }
                        }
                    });

        }
    }

    public void SendUserToTheGuestHomePage()
    {
        Intent login=new Intent(Guest_Login.this,Guest_Home.class);
        login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|login.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(login);
        finish();
    }
    /*public void gotoregister()
    {
        Intent register=new Intent(Guest_Login.this,AsGuestOrAsStudent.class);
        startActivity(register);
    }*/
}
