package com.example.registration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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


public class PostForLendingBooks extends AppCompatActivity {



    private Toolbar toolbar;
    private ProgressDialog loadingBar;
    private ImageView addPhoto;
    private Button share;
    private EditText write;
    private Spinner department,semester;
    private static final int gallery_pick=1;
    private Uri ImageUri;
    private String description,userDepartment,userSemester;
    private String saveCurrentDate,saveCurrentTime,postRandomName,downloadUrl,current_user_id;
    private StorageReference postImagesReference;
    private DatabaseReference UserRef,PostRef;
    private FirebaseAuth mAuth;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_for_lending_books);

        //adding a toolbar
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Lend Books");


        share = (Button)findViewById(R.id.shareforBooks);
        addPhoto = (ImageView) findViewById(R.id.addPhotoforBooks);
        write = (EditText) findViewById(R.id.writeforBooks);
        department=(Spinner)findViewById(R.id.departmentforBooks);
        semester=(Spinner)findViewById(R.id.semesterforBooks);
        loadingBar= new ProgressDialog(this);

        postImagesReference = FirebaseStorage.getInstance().getReference();
        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");
        PostRef = FirebaseDatabase.getInstance().getReference().child("PostsForBooks");
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
        userSemester=semester.getSelectedItem().toString();
        userDepartment=department.getSelectedItem().toString();
        if(userDepartment.equals("Department"))
        {
            ((TextView)department.getSelectedView()).setError("Please enter you department");
            department.requestFocus();
            return;
        }
        if(userSemester.equals("Semester"))
        {
            ((TextView)semester.getSelectedView()).setError("Please enter you semester");
            semester.requestFocus();
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

        StorageReference filePath = postImagesReference.child("Post images for books").child(ImageUri.getLastPathSegment() + postRandomName + ".jpg");
        filePath.putFile(ImageUri).addOnCompleteListener(PostForLendingBooks.this,new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
            {
                if(task.isSuccessful())
                {
                    downloadUrl = task.getResult().getStorage().getDownloadUrl().toString();
                    Toast.makeText(PostForLendingBooks.this,"Image uploaded", Toast.LENGTH_SHORT).show();
                    SavingPostInformationToDatabase();

                }
                else
                {
                    String message = task.getException().getMessage();
                    Toast.makeText(PostForLendingBooks.this, "Error occured: " + message, Toast.LENGTH_SHORT).show();
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
                    postMap.put("Department",userDepartment);
                    postMap.put("Semester",userSemester);

                    PostRef.child(current_user_id + postRandomName).updateChildren(postMap)
                            .addOnCompleteListener(new OnCompleteListener() {
                                @Override
                                public void onComplete(@NonNull Task task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        PostSection();
                                        Toast.makeText(PostForLendingBooks.this,"Post updated successfully", Toast.LENGTH_SHORT).show();
                                        loadingBar.dismiss();
                                    }
                                    else
                                    {
                                        Toast.makeText(PostForLendingBooks.this,"Failed to upload post", Toast.LENGTH_SHORT).show();
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

        PostSection();
        return super.onOptionsItemSelected(item);
    }
    private void PostSection()
    {
        Intent intent=new Intent(PostForLendingBooks.this,PostSection.class);
        startActivity(intent);
    }
}
