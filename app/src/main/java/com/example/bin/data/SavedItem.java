package com.example.bin.data;

import java.util.List;
import java.util.Objects;

public class SavedItem {
    private final int id;
    private final String name;
    private final String imageUrl;
    private final int price;
    private final String description;
    private final int priceOld;
    private final List<Labels> labels;
    private int count;

    public SavedItem(int id, String name, String imageUrl, int price, String description, int priceOld, List<Labels> labels) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.priceOld = priceOld;
        this.labels = labels;
        this.count = 1;
    }

    public SavedItem(int id, String name, String imageUrl, int price, String description, int priceOld, List<Labels> labels, int count) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.price = price;
        this.description = description;
        this.priceOld = priceOld;
        this.labels = labels;
        this.count = count;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getPriceOld() {
        return priceOld;
    }

    public List<Labels> getLabels() {
        return labels;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SavedItem)) return false;
        SavedItem that = (SavedItem) o;
        return id == that.id &&
                price == that.price &&
                priceOld == that.priceOld &&
                name.equals(that.name) &&
                imageUrl.equals(that.imageUrl) &&
                description.equals(that.description) &&
                labels.equals(that.labels);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, imageUrl, price, description, priceOld, labels);
    }

    @Override
    public String toString() {
        return "SavedItem{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", priceOld=" + priceOld +
                ", labels=" + labels +
                ", count=" + count +
                '}';
    }
}
