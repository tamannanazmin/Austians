package com.example.registration;

public class PostListForRentRooms {
    private String Cost;
    private String Location;
    private String Month;
    private String date;
    private String description;
    private String fullname;
    private String postimage;
    private String time;
    public PostListForRentRooms() {
    }
    public PostListForRentRooms(String date) {
        this.Cost=Cost;
        this.Location=Location;
        this.Month=Month;
        this.date = date;
        this.description = description;
        this.fullname = fullname;
        this.postimage=postimage;
        this.time = time;
    }

    public String getCost() {
        return Cost;
    }

    public String getLocation() {
        return Location;
    }

    public String getMonth() {
        return Month;
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
