package com.example.registration;


public class PostList {


    private String date;
    private String description;
    private String fullname;
    private String postimage;
    private String time;
    public PostList() {
    }

    public PostList(String date) {
        this.date = date;
        this.description = description;
        this.fullname = fullname;
        this.postimage=postimage;
        this.time = time;


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
