package com.example.nasasearchapi.data;

import androidx.annotation.NonNull;

import com.example.nasasearchapi.tools.Constants;

import java.io.Serializable;
import java.util.Objects;

public class ItemNASA implements Serializable {

    private String title; // Title of the NASA image
    private String description; // Description of the NASA image
    private String dateCreated; // the date when the NASA image is created

    private String thumbLink; // The thumb image link for the image,
    // ex. https://images-assets.nasa.gov/image/EC97-44347-15/EC97-44347-15~thumb.jpg
    private String nasaID; // The unique id for the image

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

    @NonNull
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemNASA itemNASA = (ItemNASA) o;
        return Objects.equals(title, itemNASA.title) && Objects.equals(description, itemNASA.description) && Objects.equals(dateCreated, itemNASA.dateCreated) && Objects.equals(thumbLink, itemNASA.thumbLink) && Objects.equals(nasaID, itemNASA.nasaID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, dateCreated, thumbLink, nasaID);
    }
}
