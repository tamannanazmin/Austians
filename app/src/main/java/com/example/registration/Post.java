package com.example.registration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.print.PrinterId;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

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

public class Post extends AppCompatActivity {
        private Toolbar toolbar;
        private ProgressDialog loadingBar;
        private Button codeButton,hideCodeText;
        private ImageView addPhoto;
        private EditText codeText;
        private Button share;
        private MultiAutoCompleteTextView write;
        private static final int gallery_pick=1;
        private Uri ImageUri;
        private String description;
        private String saveCurrentDate,saveCurrentTime,postRandomName,downloadUrl,current_user_id;
        private StorageReference postImagesReference;
        private DatabaseReference UserRef,PostRef;
        private FirebaseAuth mAuth;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_post);
            //toolbar
            toolbar=(Toolbar)findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Post");

            codeText=(EditText)findViewById(R.id.codeText) ;
            hideCodeText=(Button)findViewById(R.id.HideCodeText);



            codeButton=(Button)findViewById(R.id.codeButton);
            codeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    codeText.setVisibility(View.VISIBLE);
                    hideCodeText.setVisibility(View.VISIBLE);

                }
            });
            hideCodeText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    codeText.setVisibility(View.INVISIBLE);
                    hideCodeText.setVisibility(View.INVISIBLE);
                }
            });

            share = (Button)findViewById(R.id.shareForPost);
            addPhoto = (ImageView) findViewById(R.id.addPhotoForPost);
            write = (MultiAutoCompleteTextView)findViewById(R.id.writeForPost);
            loadingBar= new ProgressDialog(this);

            postImagesReference = FirebaseStorage.getInstance().getReference();
            UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
            PostRef = FirebaseDatabase.getInstance().getReference().child("Posts");
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
            if(TextUtils.isEmpty(description))
            {
                write.setError("Please enter the details");
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

            StorageReference filePath = postImagesReference.child("Post images").child(ImageUri.getLastPathSegment() + postRandomName + ".jpg");
            filePath.putFile(ImageUri).addOnCompleteListener(com.example.registration.Post.this,new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
                {
                    if(task.isSuccessful())
                    {

                        downloadUrl = task.getResult().getStorage().getDownloadUrl().toString();
                        Toast.makeText(com.example.registration.Post.this,"Image uploaded", Toast.LENGTH_SHORT).show();
                        SavingPostInformationToDatabase();

                    }
                    else
                    {
                        String message = task.getException().getMessage();
                        Toast.makeText(com.example.registration.Post.this, "Error occured: " + message, Toast.LENGTH_SHORT).show();
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
                        postMap.put("description",description);
                        postMap.put("postimage",downloadUrl);
                        postMap.put("fullname",userFullName);

                        PostRef.child(current_user_id + postRandomName).updateChildren(postMap)
                                .addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task)
                                    {
                                        if(task.isSuccessful())
                                        {
                                            PostSection();
                                            Toast.makeText(com.example.registration.Post.this,"Post updated successfully", Toast.LENGTH_SHORT).show();
                                            loadingBar.dismiss();
                                        }
                                        else
                                        {
                                            Toast.makeText(com.example.registration.Post.this,"Failed to upload post", Toast.LENGTH_SHORT).show();
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

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {

            PostSection();
            return super.onOptionsItemSelected(item);
        }
        private void PostSection()
        {
            Intent intent=new Intent(com.example.registration.Post.this,PostSection.class);
            startActivity(intent);
        }


    }
