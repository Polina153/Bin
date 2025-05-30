package com.example.bin.data;

import com.example.bin.data.model.Product;
import com.google.gson.Gson;

public class BinDataSourceProducts implements BinDataSource {

    private final SavedItemsCreator savedItemsCreator = new SavedItemsCreator();
    private final Gson gson;

    public BinDataSourceProducts(Gson gson) {
        this.gson = gson;
    }

    Product fetchProductList(String rawJson) throws Exception {
        return savedItemsCreator.parseJsonToModel(rawJson, gson);
    }
}


