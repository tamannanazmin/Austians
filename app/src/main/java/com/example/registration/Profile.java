package com.example.registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    private TextView userName,userProfileName,userStatus,userCountrty,userGender,userDOB;
    private CircleImageView userProfileImage;

    private DatabaseReference profileUserRef;
    private FirebaseAuth mauth;
    private String currentUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userName=(TextView)findViewById(R.id.username);
        userProfileName=(TextView)findViewById(R.id.profileName);
        userStatus=(TextView)findViewById(R.id.profileStatus);
        userCountrty=(TextView)findViewById(R.id.profilCountry);
        userGender=(TextView)findViewById(R.id.profileGender);
        userDOB=(TextView)findViewById(R.id.profileDateOfBirth);
        userProfileImage=(CircleImageView) findViewById(R.id.profilePic);

        mauth=FirebaseAuth.getInstance();
        currentUserId=mauth.getCurrentUser().getUid();
        profileUserRef= FirebaseDatabase.getInstance().getReference().child("Users").child(currentUserId);


        profileUserRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String myProfileImage=dataSnapshot.child("profileImage").getValue().toString();
                    String myUserName=dataSnapshot.child("username").getValue().toString();
                    String myProfileName=dataSnapshot.child("Fullname").getValue().toString();
                    String myDOB=dataSnapshot.child("dateOfBirth").getValue().toString();
                    String myStatus=dataSnapshot.child("status").getValue().toString();
                    String mygender=dataSnapshot.child("gender").getValue().toString();

                    // Picasso.get().load(myProfileImage).resize(100,100).centerCrop().into(userProfileImage);
                  //  Picasso.get().load(myProfileImage).resize(100,100).centerCrop().placeholder(R.drawable.profile).into(userProfileImage);

                    userName.setText("@"+ myUserName);
                    userProfileName.setText(myProfileName);
                    userStatus.setText(myStatus);
                    userGender.setText("Gender: "+mygender);
                    userDOB.setText("Date Of Birth: "+myDOB);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
        }

