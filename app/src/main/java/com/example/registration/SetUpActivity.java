package com.example.registration;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.nio.channels.FileLock;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SetUpActivity extends AppCompatActivity {
    private Button save;
    private EditText UserName,FullName,birth,gender,status;
    private CircleImageView ProfilePic;

    private FirebaseAuth mauth;
    private DatabaseReference userRef,profilRef;
    private ProgressDialog loadingBar;
    final static int Gallery_Pick=1;
    private StorageReference UserProfileImageRef;
    private String downloadUrl;
    private String username,fullname,UserStatus,Usergender,Userdateofbirth;
private Uri ImageUri;

    String currentUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up);

        mauth=FirebaseAuth.getInstance();
        currentUserId=mauth.getCurrentUser().getUid();
        userRef= FirebaseDatabase.getInstance().getReference().child("Users");
        UserProfileImageRef= FirebaseStorage.getInstance().getReference();
        profilRef=FirebaseDatabase.getInstance().getReference().child("profile Image");



        save=(Button)findViewById(R.id.save);
        UserName=(EditText)findViewById(R.id.username);
        FullName=(EditText)findViewById(R.id.fullName);
        birth=(EditText)findViewById(R.id.setup_dateOfBirth);
        ProfilePic=(CircleImageView)findViewById(R.id.profilePic);
        gender=(EditText)findViewById(R.id.setup_gender);
        status=(EditText)findViewById(R.id.setup_status);


        loadingBar=new ProgressDialog(this);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // saveAccountSetUpInformation();
                ValidationProfileInfo();
            }
        });
        //code for profile image
        ProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent=new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
               startActivityForResult(galleryIntent,Gallery_Pick);
            }
        });



    }
    public void ValidationProfileInfo()
    {
         username=UserName.getText().toString();
         fullname=FullName.getText().toString();
         Userdateofbirth=birth.getText().toString();
         Usergender=gender.getText().toString();
         UserStatus=status.getText().toString();

        if(TextUtils.isEmpty(username))
        {
            UserName.setError("please enter your Username");
            UserName.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(fullname))
        {
            FullName.setError("please enter your Username");
            FullName.requestFocus();
            return;

        }
        if(TextUtils.isEmpty(UserStatus))
        {
            status.setError("please enter your Status");
            status.requestFocus();
            return;

        }
        if(TextUtils.isEmpty(Userdateofbirth))
        {
            birth.setError("please enter your date of birth");
            birth.requestFocus();
            return;

        }
        if(TextUtils.isEmpty(Usergender))
        {
            gender.setError("please enter your gender");
            gender.requestFocus();
            return;

        }

        else {

            loadingBar.setTitle("Saving Information");
            loadingBar.setMessage("Please wait,while we are creating your new Account...");
            loadingBar.show();
            loadingBar.setCanceledOnTouchOutside(true);
            StoringImageToFirebaseStorage();
        }
    }
    private void StoringImageToFirebaseStorage () {

        StorageReference filePath = UserProfileImageRef.child("profile Image").child(ImageUri.getLastPathSegment()+ ".jpg");
        filePath.putFile(ImageUri).addOnCompleteListener(SetUpActivity.this,new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if(task.isSuccessful())
                {
                    downloadUrl = task.getResult().getStorage().getDownloadUrl().toString();
                    Toast.makeText(SetUpActivity.this,"profile image stored successfully....",Toast.LENGTH_SHORT).show();
                   // SavingPostInformationToDatabase();
                    saveAccountSetUpInformation();

                }
                else
                {
                    String message = task.getException().getMessage();
                    Toast.makeText(SetUpActivity.this, "Error occured: " + message, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

/*

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Gallery_Pick && requestCode==RESULT_OK && data!=null)
        {
            Uri ImageUri=data.getData();
            ProfilePic.setImageURI(ImageUri);
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);



        }

        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK)
            {
                loadingBar.setTitle("profile Image");
                loadingBar.setMessage("Please wait,while we are updating your profile image");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(true);


                Uri resultUri=result.getUri();
                StorageReference filePath=UserProfileImageRef.child(currentUserId+".jpg");
                filePath.putFile(resultUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if(task.isSuccessful())
                        {

                            Toast.makeText(SetUpActivity.this,"profile Image stored successfully...",Toast.LENGTH_SHORT).show();
                            final String downloadUri=task.getResult().getStorage().getDownloadUrl().toString();
                            userRef.child("profileImage").setValue(downloadUri)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {
                                                Intent setupintent=new Intent(SetUpActivity.this,SetUpActivity.class);
                                                startActivity(setupintent);
                                                Toast.makeText(SetUpActivity.this,"profile image stored successfully....",Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                            }

                                            else
                                            {
                                                String message=task.getException().getMessage();
                                                Toast.makeText(SetUpActivity.this,"Error occured:"+message,Toast.LENGTH_SHORT).show();
                                                loadingBar.dismiss();
                                            }

                                        }
                                    });


                        }
                    }
                });
            }
           else {
               Toast.makeText(SetUpActivity.this,"error occured: image can't be cropped, try again",Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }

        }
    }
    */
protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if(requestCode==Gallery_Pick && resultCode==RESULT_OK)
    {
        ImageUri=data.getData();
        ProfilePic.setImageURI(ImageUri);
    }
}

    private void saveAccountSetUpInformation() {

        userRef.child(currentUserId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    HashMap usermap=new HashMap();

                    usermap.put("username",username);
                    usermap.put("Fullname",fullname);
                    usermap.put("status", UserStatus);
                    usermap.put("gender",Usergender);
                    usermap.put("dateOfBirth",Userdateofbirth);
                    usermap.put("RelationshipSatus","none");
                    usermap.put("profileImage",downloadUrl);


            /*String val = (String)usermap.get(getIntent().getExtras().getString("name"));
            usermap.put("name:",val);*/


           /* User user=new User(
                    username,
                    fullname
                    );*/
                    FirebaseDatabase.getInstance().getReference("Users")
                            .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {

                                Toast.makeText(SetUpActivity.this,"your Account is created Successfully...",Toast.LENGTH_LONG).show();
                                SendUserToHomepage();
                                loadingBar.dismiss();
                            }
                            else
                            {
                                String message= task.getException().getMessage();
                                Toast.makeText(SetUpActivity.this,"Error occured:"+message,Toast.LENGTH_LONG).show();
                                loadingBar.dismiss();
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

    private void SendUserToHomepage() {

        Intent intent=new Intent(SetUpActivity.this,Home.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
