package com.example.bin.data;

import java.util.List;

public interface BinRepository {

    List<SavedItem> fetchProductList(String rawJson) throws Exception;

    List<SavedItem> getProductList() throws Exception;

    List<SavedItem> addProductToBin(SavedItem item) throws Exception;

    List<SavedItem> removeItemFromBin(SavedItem item) throws Exception;

    List<SavedItem> dismissItemFromBin(SavedItem item) throws Exception;

    List<SavedItem> addItemToBin(SavedItem item) throws Exception;
}
