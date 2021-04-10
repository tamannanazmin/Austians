package com.example.registration;

public class User {
    public String name,email,phn;
    public String sem,dept;
    public String id;
    public String username,fullname;
    public String status,gender,relationshipStatus,dateofBirth;
    public User()
    {

    }
    public User(String name,String email,String phn,String sem,String dept,String id)
    {
        this.name=name;
        this.email=email;
        this.phn=phn;
        this.sem=sem;
        this.dept=dept;
        this.id=id;

    }
    public User(String username,String fullname)
    {
        this.username=username;
        this.fullname=fullname;
        // this.status=status;
        // this.gender=gender;
        // this.relationshipStatus=relationshipStatus;
        //this.dateofBirth=dateofBirth;

    }
}
