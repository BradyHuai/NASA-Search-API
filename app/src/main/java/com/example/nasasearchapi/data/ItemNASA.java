package com.example.nasasearchapi.data;

public class ItemNASA {

    private String title;
    private String description;
    private String dateCreated;

    public ItemNASA() {
        this.title = "NA";
        this.description = "NA";
        this.dateCreated = "NA";
    }

    public ItemNASA(String title, String description, String dateCreated) {
        this.title = title;
        this.description = description;
        this.dateCreated = dateCreated;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }
}
