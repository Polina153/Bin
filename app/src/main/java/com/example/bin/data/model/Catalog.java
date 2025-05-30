package com.example.bin.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Catalog {
    @SerializedName("id")
    private Integer id;

    @SerializedName("name")
    private String name;

    @SerializedName("image")
    private String image;

    @SerializedName("description")
    private String description;

    @SerializedName("items")
    private List<Items> items;

    public Catalog(Integer id, String name, String image, String description, List<Items> items) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
        this.items = items;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Items> getItems() {
        return items;
    }

    public void setItems(List<Items> items) {
        this.items = items;
    }
}
