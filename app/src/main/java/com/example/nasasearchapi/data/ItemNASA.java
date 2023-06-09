package com.example.nasasearchapi.data;

import android.graphics.Bitmap;

public class ItemNASA {

    private int id;
    private String title;
    private String description;
    private String dateCreated;

    private String thumbLink;
    private String nasaID;
    private Bitmap thumbImage;

    public ItemNASA() {
        this.id = -1;
        this.title = "NA";
        this.description = "NA";
        this.dateCreated = "NA";
        this.thumbLink = "NA";
        this.nasaID = "NA";
        this.thumbImage = null;
    }

    public ItemNASA(int id, String title, String description, String dateCreated) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dateCreated = dateCreated;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getThumbLink() {
        return thumbLink;
    }

    public String getNasaID() {
        return nasaID;
    }

    public Bitmap getThumbImage() {
        return thumbImage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setThumbLink(String thumbLink) {
        this.thumbLink = thumbLink;
    }

    public void setNasaID(String nasaID) {
        this.nasaID = nasaID;
    }

    public void setThumbImage(Bitmap thumbImage) {
        this.thumbImage = thumbImage;
    }
}
