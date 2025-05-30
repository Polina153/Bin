package com.example.bin.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {
    @SerializedName("catalog")
    private List<Catalog> catalog;

    @SerializedName("products")
    private List<Products> products;

    public Product(List<Catalog> catalog, List<Products> products) {
        this.catalog = catalog;
        this.products = products;
    }

    public List<Catalog> getCatalog() {
        return catalog;
    }

    public void setCatalog(List<Catalog> catalog) {
        this.catalog = catalog;
    }

    public List<Products> getProducts() {
        return products;
    }

    public void setProducts(List<Products> products) {
        this.products = products;
    }
}