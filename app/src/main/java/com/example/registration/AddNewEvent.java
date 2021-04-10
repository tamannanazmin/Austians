package com.example.registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddNewEvent extends AppCompatActivity {
    private Toolbar toolbar;

    private ProgressDialog loadingBar;
    private ImageView addPhoto;
    private Button share;
    private EditText write,date,time,location;
    private Spinner category;
    private static final int gallery_pick=1;
    private Uri ImageUri;
    private String description,usercategory,userdate,usertime,userlocation;
    private String saveCurrentDate,saveCurrentTime,postRandomName,downloadUrl,current_user_id;
    private StorageReference postImagesReference;
    private DatabaseReference UserRef,PostRef;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_events);

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Add New Events");

        share = (Button)findViewById(R.id.shareOfEvents);
        addPhoto = (ImageView) findViewById(R.id.addPhotoOfEvents);
        write = (EditText) findViewById(R.id.writeOfEvents);
        category=(Spinner)findViewById(R.id.categoryOfEvents);
        time=(EditText) findViewById(R.id.timeOfEvents);
        location=(EditText)findViewById(R.id.placeOfEvents);
        date=(EditText)findViewById(R.id.dateOfEvents);
        loadingBar= new ProgressDialog(this);

        postImagesReference = FirebaseStorage.getInstance().getReference();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        PostRef = FirebaseDatabase.getInstance().getReference().child("Post For Events");
        mAuth= FirebaseAuth.getInstance();
        current_user_id = mAuth.getCurrentUser().getUid();

        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenGallery();
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidatePostInfo();
            }
        });


    }
    private void ValidatePostInfo() {
        description = write.getText().toString();
        userdate=date.getText().toString();
        userlocation=location.getText().toString();
        usertime=time.getText().toString();
        usercategory=category.getSelectedItem().toString();
        if(TextUtils.isEmpty(userdate))
        {
            date.setError("Please enter the date...");
            date.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(usertime))
        {
            time.setError("Please enter the time...");
            time.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(userlocation))
        {
            location.setError("Please enter the location...");
            location.requestFocus();
            return;
        }
        if(usercategory.equals("categories"))
        {
            ((TextView)category.getSelectedView()).setError("Please enter the category");
            category.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(description))
        {
            write.setError("Please enter the details...");
            write.requestFocus();
            return;
        }
        else
        {
            loadingBar.setTitle("Add new post");
            loadingBar.setMessage("Updating new post");
            loadingBar.show();
            StoringImageToFirebaseStorage();
        }

    }
    private void StoringImageToFirebaseStorage () {
        Calendar callForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(callForDate.getTime());

        Calendar callForTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(callForTime.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;

        StorageReference filePath = postImagesReference.child("Post images for events").child(ImageUri.getLastPathSegment() + postRandomName + ".jpg");
        filePath.putFile(ImageUri).addOnCompleteListener(AddNewEvent.this,new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
            {
                if(task.isSuccessful())
                {
                    downloadUrl = task.getResult().getStorage().getDownloadUrl().toString();
                    Toast.makeText(AddNewEvent.this,"Image uploaded", Toast.LENGTH_SHORT).show();
                    SavingPostInformationToDatabase();

                }
                else
                {
                    String message = task.getException().getMessage();
                    Toast.makeText(AddNewEvent.this, "Error occured: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void SavingPostInformationToDatabase()
    {
        UserRef.child(current_user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    String userFullName = dataSnapshot.child("Fullname").getValue().toString();

                    HashMap postMap = new HashMap();
                    postMap.put("uid",current_user_id);
                    postMap.put("date",saveCurrentDate);
                    postMap.put("time",saveCurrentTime);
                    postMap.put("EventsTime",usertime);
                    postMap.put("EventsDate",userdate);
                    postMap.put("EventsLocation",userlocation);
                    postMap.put("description",description);
                    postMap.put("postimage",downloadUrl);
                    postMap.put("fullname",userFullName);
                    postMap.put("Category",usercategory);


                    PostRef.child(current_user_id + postRandomName).updateChildren(postMap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        Events();
                                        Toast.makeText(AddNewEvent.this,"Post updated successfully", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                    else
                                    {
                                        Toast.makeText(AddNewEvent.this,"Failed to upload post", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    private void OpenGallery() {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,gallery_pick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==gallery_pick && resultCode==RESULT_OK)
        {
            ImageUri=data.getData();
            addPhoto.setImageURI(ImageUri);
        }
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        Events();
        return super.onOptionsItemSelected(item);
    }
    private void Events()
    {
        Intent intent=new Intent(AddNewEvent.this,PostSection.class);
        startActivity(intent);
    }
}
