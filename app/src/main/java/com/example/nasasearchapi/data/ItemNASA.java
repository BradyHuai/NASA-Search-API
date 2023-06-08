package com.example.nasasearchapi.data;

public class ItemNASA {

    private int id;
    private String title;
    private String description;
    private String dateCreated;

    public ItemNASA() {
        this.id = -1;
        this.title = "NA";
        this.description = "NA";
        this.dateCreated = "NA";
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
}
