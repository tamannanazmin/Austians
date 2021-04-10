package com.example.registration;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mauth;
    private DatabaseReference userRef;
    private ImageView navProfileImage;
    private TextView navProfileUserName;
    String currentUserId;
    private RecyclerView postList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //for validation
        mauth=FirebaseAuth.getInstance();
        userRef=FirebaseDatabase.getInstance().getReference().child("users");
        currentUserId=mauth.getCurrentUser().getUid();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);



    }

    //for validation

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser=mauth.getCurrentUser();
        if(currentuser==null)
        {
            sendUserToLoginActiviy();
        }
       /* else{
            CheckUserExistance();
        }*/


    }

    private void CheckUserExistance() {
        final String current_user_id=mauth.getCurrentUser().getUid();
       userRef.addValueEventListener(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(!dataSnapshot.hasChild(current_user_id))
               {
                   SendUserToSetUpActicity();
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

    }

    private void SendUserToSetUpActicity() {
        Intent setUpIntent=new Intent(Home.this,SetUpActivity.class);
        setUpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setUpIntent);
        finish();
    }


    private void sendUserToLoginActiviy() {
        Intent loginIntent=new Intent(Home.this,Login.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();

    }

    public void PostSection()
    {
        Intent intent =new Intent (this,PostSection.class);
        startActivity(intent);
    }


    public void Profile()
    {
        Intent intent =new Intent (this,Profile.class);
        startActivity(intent);
    }
    public void Home()
    {
        Intent intent =new Intent (this,DataRetrieved.class);
        startActivity(intent);
    }
    public void Events()
    {
        Intent intent = new Intent(this,DataRetrievedForEvents.class);
        startActivity(intent);
    }
    public void Survey()
    {
        Intent intent = new Intent(this,Survey.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
            Home();
        }
        else if (id == R.id.nav_post) {
            PostSection();

        }
        else if (id == R.id.nav_profile) {
            Profile();


        }
        else if (id == R.id.nav_borrowBooks) {
            sendUserToBooks();
        }
        else if (id == R.id.nav_borrowInstruments) {
                sendUserToInstrument();

        }
        else if (id == R.id.nav_rentRooms) {
                sendUserToRentRoom();

        }
        else if (id == R.id.nav_events) {
            Events();

        }

        else if (id == R.id.nav_aboutUs) {

        }
        else if (id == R.id.nav_logout) {
            mauth.signOut();
            sendUserToLoginActiviy();
        }
        else if (id == R.id.nav_survey) {
            Survey();
        }

        else if (id == R.id.nav_iums) {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://iums.aust.edu/ums-web/login/"));
            startActivity(browserIntent);

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void sendUserToRentRoom() {
        Intent intent =new Intent (Home.this,DataRetrievedForRentRoom.class);
        startActivity(intent);
    }

    private void sendUserToBooks() {
        Intent intent =new Intent (Home.this,DataRetrievedForBooks.class);
        startActivity(intent);
    }

    private void sendUserToInstrument() {
        Intent intent =new Intent (Home.this,DataRetrievedForInstrument.class);
        startActivity(intent);
    }
}
