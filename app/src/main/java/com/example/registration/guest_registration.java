package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class guest_registration extends AppCompatActivity {

    private TextView heading;
    private Button register;
    private EditText Username,Useremail,Userphn,Userpassword,UserconfirmPassword;
    private CheckBox Useragree;
    private ProgressDialog loadingBar;

    private FirebaseAuth mauth;
    DatabaseReference ref;
    FirebaseDatabase database;
    protected void onStart() {
        super.onStart();
        FirebaseUser CurrentUser=mauth.getCurrentUser();
        if(CurrentUser!=null)
        {
            //handle the already login user
            SendUserToTheGuestHomePage();

        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_registration);
        heading = (TextView) findViewById(R.id.heading);
        Typeface myCustomFont = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        heading.setTypeface(myCustomFont);


        //////////////////////////////////////////////////////////////////////////////////////////////////
        Username=(EditText)findViewById(R.id.guest_name);
        Useremail=(EditText)findViewById(R.id.guest_email);
        Userphn=(EditText)findViewById(R.id.guest_phn);
        Userpassword=(EditText)findViewById(R.id.guest_password);
        UserconfirmPassword=(EditText)findViewById(R.id.guest_confirmPassword);
        Useragree=(CheckBox)findViewById(R.id.guest_checkbox);
        register=(Button)findViewById(R.id.guest_register);
        loadingBar=new ProgressDialog(this);
//////////////////////////////////////////////////////////////////////////////////////////////////
        //firebase
        mauth=FirebaseAuth.getInstance();



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                createNewAccountForGuest();
            }
        });


    }
    private void createNewAccountForGuest() {
        final String name=Username.getText().toString();
        final String email= Useremail.getText().toString();
        final String phn=Userphn.getText().toString();
        String password=Userpassword.getText().toString();
        String confirmPassword=UserconfirmPassword.getText().toString();

        if(TextUtils.isEmpty(name))
        {
            Username.setError("Please enter your your name");
            Username.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(email))
        {
            Useremail.setError("Please enter your email...");
            Useremail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            Useremail.setError("Enter a valid email");
            Useremail.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(phn))
        {
            Userphn.setError("Please enter your phone number");
            Userphn.requestFocus();
            return;
        }
        if(phn.length()!=11)
        {
            Userphn.setError("Enter a valid phone number");
            Userphn.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(password))
        {
            Userpassword.setError("Please enter your  password.");
            Userpassword.requestFocus();
            return;
        }
        if(password.length()<6)
        {
            Userpassword.setError("Password should be atleast 6 character long");
            Userpassword.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(confirmPassword))
        {
            UserconfirmPassword.setError("Please enter your Confirm Password.");
            UserconfirmPassword.requestFocus();
            return;
        }
        if(!password.equals(confirmPassword))
        {
            Toast.makeText(this, "Your password and confirm password do not match.", Toast.LENGTH_SHORT).show();
        }
        if(!Useragree.isChecked())
        {
            Useragree.setError("Please click on 'I agree with the terms and condition for registration'");
            Useragree.requestFocus();
            return;
        }
        else
        {
            loadingBar.setTitle("creating new account");
            loadingBar.setMessage("Please wait,while we are creating your new account...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
            mauth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Guest_User user=new Guest_User(
                                        name,
                                        email,
                                        phn

                                );
                                FirebaseDatabase.getInstance().getReference("Guest_Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful())
                                        {
                                            sendUserToSetUpActiviy();
                                            Toast.makeText(guest_registration.this, "your registration is complete", Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();
                                        }
                                    }
                                });
                            }
                            else
                            {
                                String message= task.getException().getMessage();
                                Toast.makeText(guest_registration.this, "Error occured: "+message, Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();

                            }
                        }
                    });
        }


    }
    public void sendUserToSetUpActiviy()
    {
        Intent intent=new Intent(guest_registration.this,SetUpActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    public void SendUserToTheGuestHomePage()
    {
        Intent login=new Intent(guest_registration.this,Guest_Home.class);
        login.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|login.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(login);
        finish();
    }


}

