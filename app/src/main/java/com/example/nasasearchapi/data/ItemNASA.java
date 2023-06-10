package com.example.nasasearchapi.data;

import com.example.nasasearchapi.tools.Constants;

import java.io.Serializable;

public class ItemNASA implements Serializable {

    private String title;
    private String description;
    private String dateCreated;

    private String thumbLink;
    private String nasaID;

    public ItemNASA() {
        this.title = Constants.STRING_NA;
        this.description = Constants.STRING_NA;
        this.dateCreated = Constants.STRING_NA;
        this.thumbLink = Constants.STRING_NA;
        this.nasaID = Constants.STRING_NA;
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

    @Override
    public String toString() {
        return "ItemNASA{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", dateCreated='" + dateCreated + '\'' +
                ", thumbLink='" + thumbLink + '\'' +
                ", nasaID='" + nasaID + '\'' +
                '}';
    }
}
