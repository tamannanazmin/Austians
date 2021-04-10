package com.example.registration;

public class PostListForBooks {
    private String Department;
    private String Semester;
    private String date;
    private String description;
    private String fullname;
    private String postimage;
    private String time;

    public PostListForBooks() {
    }
    public PostListForBooks(String date) {
        this.Department=Department;
        this.Semester=Semester;
        this.date = date;
        this.description = description;
        this.fullname = fullname;
        this.postimage=postimage;
        this.time = time;
    }

    public String getDepartment() {
        return Department;
    }

    public String getSemester() {
        return Semester;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public String getFullname() {
        return fullname;

    }
    public String getPostimage()
    {return postimage;}

    public String getTime() {
        return time;
    }
}
