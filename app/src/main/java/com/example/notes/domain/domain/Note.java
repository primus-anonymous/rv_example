package com.example.notes.domain.domain;

import java.util.Date;

public class Note {

    private String id;

    private String name;

    private String imageUrl;

    private Date date;

    public Note(String id, String name, String imageUrl, Date date) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Date getDate() {
        return date;
    }
}
