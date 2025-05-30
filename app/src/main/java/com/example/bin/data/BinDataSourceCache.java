package com.example.bin.data;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class BinDataSourceCache implements BinDataSource {

    private final SharedPreferences productCache;
    private final Gson gson;

    public BinDataSourceCache(SharedPreferences productCache, Gson gson) {
        this.productCache = productCache;
        this.gson = gson;
    }

    public void saveItemsToCache(List<SavedItem> items) throws Exception {
        try {
            productCache.edit().putString(SAVED_ITEMS_KEY, gson.toJson(items)).apply();
        } catch (Exception e) {
            throw e;
        }
    }

    public List<SavedItem> getItemsFromCache() throws Exception {
        String cachedData = productCache.getString(SAVED_ITEMS_KEY, "[]");
        Type type = new TypeToken<List<SavedItem>>() {
        }.getType();
        return gson.fromJson(cachedData, type);
    }

    List<SavedItem> addProductToBin(SavedItem item) throws Exception {
        try {
            List<SavedItem> savedItems = new ArrayList<>();
            String data = productCache.getString(SAVED_ITEMS_KEY, "[]");
            if (!data.equals("[]")) {
                Type type = new TypeToken<List<SavedItem>>() {
                }.getType();
                savedItems = gson.fromJson(data, type);
            }

            boolean exists = false;
            for (SavedItem existingItem : savedItems) {
                //Если товар уже есть в корзине, то увеличиваем количество
                //Если нет, то добавляем
                //Добавлять лучше через полную копию
                if (existingItem.getId() == item.getId()) {
                    existingItem.setCount(existingItem.getCount() + 1);
                    exists = true;
                    break;
                }
            }

            if (!exists) {
                savedItems.add(item);
            }

            productCache.edit().putString(SAVED_ITEMS_KEY, gson.toJson(savedItems)).apply();
            return savedItems;
        } catch (Exception e) {
            throw e;
        }
    }

    List<SavedItem> removeProductFromBin(SavedItem item) throws Exception {
        try {
            List<SavedItem> savedItems = new ArrayList<>();
            String data = productCache.getString(SAVED_ITEMS_KEY, "[]");
            if (!data.equals("[]")) {
                Type type = new TypeToken<List<SavedItem>>() {
                }.getType();
                savedItems = gson.fromJson(data, type);
            }

            int position = -1;
            for (int i = 0; i < savedItems.size(); i++) {
                if (savedItems.get(i).getId() == item.getId()) {
                    position = i;
                    break;
                }
            }

            //Если это был последний товар, то удаляем элемент
            //Иначе уменьшаем количество на 1
            if (position >= 0) {
                SavedItem foundItem = savedItems.get(position);
                if (foundItem.getCount() > 1) {
                    foundItem.setCount(foundItem.getCount() - 1);
                } else {
                    savedItems.remove(position);
                }
            }

            productCache.edit().putString(SAVED_ITEMS_KEY, gson.toJson(savedItems)).apply();
            return savedItems;
        } catch (Exception e) {
            throw e;
        }
    }

    List<SavedItem> dismissItemFromBin(SavedItem item) throws Exception {
        try {
            List<SavedItem> savedItems = new ArrayList<>();
            String data = productCache.getString(SAVED_ITEMS_KEY, "[]");
            if (!data.equals("[]")) {
                Type type = new TypeToken<List<SavedItem>>() {
                }.getType();
                savedItems = gson.fromJson(data, type);
            }

            savedItems.removeIf(existingItem -> existingItem.getId() == item.getId());

            productCache.edit().putString(SAVED_ITEMS_KEY, gson.toJson(savedItems)).apply();
            return savedItems;
        } catch (Exception e) {
            throw e;
        }
    }

    List<SavedItem> addItemToBin(SavedItem item) throws Exception {
        try {
            List<SavedItem> savedItems = new ArrayList<>();
            String data = productCache.getString(SAVED_ITEMS_KEY, "[]");
            if (!data.equals("[]")) {
                Type type = new TypeToken<List<SavedItem>>() {
                }.getType();
                savedItems = gson.fromJson(data, type);
            }

            savedItems.add(item);
            productCache.edit().putString(SAVED_ITEMS_KEY, gson.toJson(savedItems)).apply();
            return savedItems;
        } catch (Exception e) {
            throw e;
        }
    }

    private static final String SAVED_ITEMS_KEY = "saved_items";
}
