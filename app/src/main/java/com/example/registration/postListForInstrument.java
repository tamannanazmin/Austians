package com.example.registration;

public class postListForInstrument {
    private String date;
    private String description;
    private String fullname;
    private String postimageforInstruments;
    private String time;
    public postListForInstrument() {
    }

    public postListForInstrument(String date) {
        this.date = date;
        this.description = description;
        this.fullname = fullname;
        this.postimageforInstruments=postimageforInstruments;
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
    public String getPostimageforInstruments()
    {return postimageforInstruments;}

    public String getTime() {
        return time;
    }
}
