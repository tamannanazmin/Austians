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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import org.w3c.dom.Text;

import java.util.HashMap;

public class

MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private TextView heading;
    private Button register;
    private Spinner semester,department;
    private EditText Username,Useremail,Userphn,Userpassword,UserconfirmPassword,userid;
    private CheckBox Useragree;
    private ProgressDialog loadingBar;


    private FirebaseAuth mauth;
    DatabaseReference ref;
    FirebaseDatabase database;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser CurrentUser=mauth.getCurrentUser();
        if(CurrentUser!=null)
        {
            //handle the already login user
           SendUserToTheHomePage();

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //font for "registration" word
        heading=(TextView)findViewById(R.id.heading);
        Typeface myCustomFont= Typeface.createFromAsset(getAssets(),"fonts/Pacifico.ttf");
        heading.setTypeface(myCustomFont);
        register=(Button)findViewById(R.id.registerId);

        //using spinner for  semester

        semester=(Spinner)findViewById(R.id.semester);
        ArrayAdapter adapter2=ArrayAdapter.createFromResource(
                this,
                R.array.semester,
                R.layout.semester_color_spinner_layout
        );
        adapter2.setDropDownViewResource(R.layout.semester_spinner_dropdown_layout);
        semester.setAdapter(adapter2);
        semester.setOnItemSelectedListener(this);


        //using spinner for department
        department=(Spinner)findViewById(R.id.department);
        ArrayAdapter adapter3=ArrayAdapter.createFromResource(
                this,
                R.array.department,
                R.layout.department_color_spinner_layout
        );
        adapter3.setDropDownViewResource(R.layout.department_spinner_dropdown_layout);
        department.setAdapter(adapter3);
        department.setOnItemSelectedListener(this);


//////////////////////////////////////////////////////////////////////////////////////////////////
        Username=(EditText)findViewById(R.id.name);
        Useremail=(EditText)findViewById(R.id.email);
        Userphn=(EditText)findViewById(R.id.phn);
        userid=(EditText)findViewById(R.id.ID);
        Userpassword=(EditText)findViewById(R.id.password);
        UserconfirmPassword=(EditText)findViewById(R.id.confirmPassword);
        Useragree=(CheckBox)findViewById(R.id.agreeCheckBox);
        register=(Button)findViewById(R.id.registerId);
        loadingBar=new ProgressDialog(this);

//////////////////////////////////////////////////////////////////////////////////////////////////
        //firebase
        mauth=FirebaseAuth.getInstance();



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               createNewAccount();
            }
        });

    }

    private void createNewAccount() {
        final String name=Username.getText().toString();
        final String email= Useremail.getText().toString();
        final String phn=Userphn.getText().toString();
        final String sem=semester.getSelectedItem().toString();
        final String dept=department.getSelectedItem().toString();
        final String id=userid.getText().toString();
        String password=Userpassword.getText().toString();
        String confirmPassword=UserconfirmPassword.getText().toString();

        if(TextUtils.isEmpty(name))
        {
            Username.setError("Please enter your name");
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
        if(dept.equals("Department"))
        {
            ((TextView)department.getSelectedView()).setError("Please enter you department");
            department.requestFocus();
            return;
        }
        if(sem.equals("Semester"))
        {
            ((TextView)semester.getSelectedView()).setError("Please enter you semester");
            semester.requestFocus();
            return;
        }
        if(TextUtils.isEmpty(id))
        {
            userid.setError("Please enter your  Student ID.");
            userid.requestFocus();
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
                               /*User user=new User(
                                       name,
                                       email,
                                       phn,
                                       sem,
                                       dept,
                                       id

                               );*/
                               HashMap usermap=new HashMap();
                               usermap.put("name",name);
                               usermap.put("email",email);
                               usermap.put("phone", phn);
                               usermap.put("semester",sem);
                               usermap.put("deptartment",dept);
                               usermap.put("id",id);
                               usermap.put("username","none");
                               usermap.put("Fullname","none");
                               usermap.put("status", "hey there ,I am using this app");
                               usermap.put("gender","none");
                               usermap.put("dateOfBirth","none");
                               usermap.put("RelationshipSatus","none");

                              /* String n;
                               Intent i=new Intent(MainActivity.this,SetUpActivity.class);
                               n=Useremail.getText().toString();
                               i.putExtra("name",n);
                               startActivity(i);
                               finish();*/




                               FirebaseDatabase.getInstance().getReference("Users")
                               .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(usermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                   @Override
                                   public void onComplete(@NonNull Task<Void> task) {
                                      if(task.isSuccessful())
                                      {
                                          sendUserToSetUpActiviy();
                                          Toast.makeText(MainActivity.this, "your registration is complete", Toast.LENGTH_SHORT).show();
                                          loadingBar.dismiss();
                                      }
                                   }
                               });
                           }
                           else
                           {
                               String message= task.getException().getMessage();
                               Toast.makeText(MainActivity.this, "Error occured: "+message, Toast.LENGTH_SHORT).show();
                               loadingBar.dismiss();

                           }
                        }
                    });
        }


    }
public void sendUserToSetUpActiviy()
{
    Intent intent=new Intent(MainActivity.this,SetUpActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
}

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public void SendUserToTheHomePage()
    {
        Intent reg=new Intent(MainActivity.this,Home.class);
        reg.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|reg.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(reg);
        finish();
    }
}
