package com.example.bin.ui;

import com.example.bin.data.SavedItem;

import java.util.List;

public class SavedData {

    private final int totalPrice;
    private final int totalPriceOld;
    private final int itemsCount;
    private final List<SavedItem> savedItems;

    public SavedData(int totalPrice, int totalPriceOld, int itemsCount, List<SavedItem> savedItems) {
        this.totalPrice = totalPrice;
        this.totalPriceOld = totalPriceOld;
        this.itemsCount = itemsCount;
        this.savedItems = savedItems;
    }

    int getTotalPrice() {
        return totalPrice;
    }

    int getTotalPriceOld() {
        return totalPriceOld;
    }

    int getItemsCount() {
        return itemsCount;
    }

    List<SavedItem> getSavedItems() {
        return savedItems;
    }

    @Override
    public String toString() {
        return "SavedData{" +
                "totalPrice=" + totalPrice +
                ", totalPriceOld=" + totalPriceOld +
                ", itemsCount=" + itemsCount +
                ", savedItems=" + savedItems +
                '}';
    }
}

