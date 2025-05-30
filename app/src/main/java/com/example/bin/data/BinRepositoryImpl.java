package com.example.bin.data;

import com.example.bin.data.model.Product;

import java.util.ArrayList;
import java.util.List;

public class BinRepositoryImpl implements BinRepository {

    private final SavedItemsCreator savedItemsCreator = new SavedItemsCreator();
    private final BinDataSourceProducts productSource;
    private final BinDataSourceCache binProductCache;

    public BinRepositoryImpl(BinDataSourceProducts productSource, BinDataSourceCache binProductCache) {
        this.productSource = productSource;
        this.binProductCache = binProductCache;
    }

    @Override
    public List<SavedItem> fetchProductList(String rawJson) throws Exception {
        Product rawProductList = productSource.fetchProductList(rawJson);
        List<SavedItem> localProductList = savedItemsCreator.convertProductToSavedItem(rawProductList);
        binProductCache.saveItemsToCache(localProductList);
        return localProductList;
    }

    @Override
    public List<SavedItem> getProductList() throws Exception {
        return binProductCache.getItemsFromCache();
    }

    @Override
    public List<SavedItem> addProductToBin(SavedItem item) throws Exception {
        return binProductCache.addProductToBin(item);
    }

    @Override
    public List<SavedItem> removeItemFromBin(SavedItem item) throws Exception {
        return binProductCache.removeProductFromBin(item);
    }

    @Override
    public List<SavedItem> dismissItemFromBin(SavedItem item) throws Exception {
        return binProductCache.dismissItemFromBin(item);
    }

    @Override
    public List<SavedItem> addItemToBin(SavedItem item) throws Exception {
        return binProductCache.addItemToBin(item);
    }
}

