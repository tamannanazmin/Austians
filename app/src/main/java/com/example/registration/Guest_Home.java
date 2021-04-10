package com.example.registration;

import android.content.Intent;
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

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;


public class Guest_Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FirebaseAuth mauth;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest__home);

        //firebase
        mauth=FirebaseAuth.getInstance();
        userRef=(FirebaseDatabase.getInstance().getReference().child("Guest_Users"));
        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }
    //onstart
    protected void onStart() {
        super.onStart();
        FirebaseUser currentuser=mauth.getCurrentUser();
        if(currentuser==null)
        {
            sendUserToGuestLoginActiviy();
        }
        else {
            CheckUserExistance();
        }
    }

    private void CheckUserExistance() {
        final String current_user_id=mauth.getCurrentUser().getUid();
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.hasChild(current_user_id))
                {
                    SendUserToGuestSetUpActivity();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void SendUserToGuestSetUpActivity() {
        Intent setUpIntent=new Intent(Guest_Home.this,Guest_SetUpActivity.class);
        setUpIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(setUpIntent);
        finish();
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
        getMenuInflater().inflate(R.menu.guest__home, menu);
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
            SendUserToGuestHome();
            // Handle the camera action
        } else if (id == R.id.nav_post) {
            SendUserToGuestAddpostActivity();

        } else if (id == R.id.nav_profile) {
            SendUserToGuestProfileActivity();

        }
        else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_aboutUs) {

        } else if (id == R.id.nav_logout) {
            mauth.signOut();
            sendUserToGuestLoginActiviy();

        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void SendUserToGuestHome() {
        Intent intent =new Intent (Guest_Home.this,Guest_Home.class);
        startActivity(intent);
    }

    private void sendUserToGuestLoginActiviy()
    {
            Intent loginIntent=new Intent(Guest_Home.this,Login.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(loginIntent);
            finish();
    }

    private void SendUserToGuestProfileActivity() {
        Intent intent =new Intent (Guest_Home.this,Guest_Profile.class);
        startActivity(intent);
    }

    private void  SendUserToGuestAddpostActivity() {
        Intent intent =new Intent (Guest_Home.this,Guest_Addpost.class);
        startActivity(intent);
    }
}
