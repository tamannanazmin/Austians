package com.example.registration;

public class PostListForEvents {
    private String Category;
    private String EventsDate;
    private String EventsLocation;
    private String EventsTime;
    private String date;
    private String description;
    private String fullname;
    private String postimage;
    private String time;
    public PostListForEvents()
    {
    }

    public PostListForEvents(String date) {
        this.Category=Category;
        this.EventsDate=EventsDate;
        this.EventsLocation=EventsLocation;
        this.EventsTime=EventsTime;
        this.date = date;
        this.description = description;
        this.fullname = fullname;
        this.postimage=postimage;
        this.time = time;


    }

    public String getCategory() {
        return Category;
    }

    public String getEventsDate() {
        return EventsDate;
    }

    public String getEventsLocation() {
        return EventsLocation;
    }

    public String getEventsTime() {
        return EventsTime;
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
