package com.example.bin.data;

import com.example.bin.data.model.Catalog;
import com.example.bin.data.model.Item;
import com.example.bin.data.model.Product;
import com.example.bin.data.model.Products;
import com.example.bin.ui.SavedData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

public class SavedItemsCreator {
    public Product parseJsonToModel(String jsonString, Gson gson) {
        return gson.fromJson(jsonString, new TypeToken<Product>() {}.getType());
    }

    List<SavedItem> convertProductToSavedItem(Product product) {
        List<SavedItem> savedItems = new ArrayList<>();
        product.getProducts().forEach(item -> savedItems.add(createItem(item)));
        for (Catalog catalogItem : product.getCatalog()) {
            catalogItem.getItems().forEach(items ->
                    items.getItems().forEach(item ->savedItems.add(createItem(item))));

        }
        return savedItems;
    }

    SavedItem createItem(Item item) {
        return new SavedItem(
                Optional.ofNullable(item.getId()).orElse(0),
                Optional.ofNullable(item.getName()).orElse(""),
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxBxJ-fTjMxRROsMpovq9NLDhXcbLWT300Eg&s",
                Optional.ofNullable(item.getPrice()).orElse(0),
                Optional.ofNullable(item.getDescription()).orElse(""),
                ThreadLocalRandom.current().nextBoolean() ?
                        ThreadLocalRandom.current().nextInt(100, 2000) : 0,
                randomFlags()
        );
    }

    SavedItem createItem(Products product) {
        return new SavedItem(
                Optional.ofNullable(product.getId()).orElse(0),
                Optional.ofNullable(product.getName()).orElse(""),
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxBxJ-fTjMxRROsMpovq9NLDhXcbLWT300Eg&s",
                Optional.ofNullable(product.getPrice()).orElse(0),
                Optional.ofNullable(product.getDescription()).orElse(""),
                ThreadLocalRandom.current().nextBoolean() ?
                        ThreadLocalRandom.current().nextInt(1000, 200000) : 0,
                randomFlags()
        );
    }

    public SavedData createSavedData(List<SavedItem> list) {
        int totalPrice = 0;
        int itemsCount = 0;
        for (SavedItem item : list) {
            totalPrice += (item.getPrice() * item.getCount());
            itemsCount += item.getCount();
        }
        return new SavedData(totalPrice, totalPrice - 5000, itemsCount, list);
    }

    public List<Labels> randomFlags() {
        if (ThreadLocalRandom.current().nextBoolean()) {
            List<Labels> flags = new ArrayList<>();
            for (int i = 0; i < ThreadLocalRandom.current().nextInt(1, 5); i++) {
                switch (i) {
                    case 1:
                        flags.add(Labels.ADD);
                        break;
                    case 2:
                        flags.add(Labels.BESTSELLER);
                        break;
                    case 3:
                        flags.add(Labels.NEW);
                        break;
                    case 4:
                        flags.add(Labels.SALE);
                        break;
                    default:
                        flags.add(Labels.TIME);
                }
            }
            return flags;
        } else {
            return Collections.emptyList();
        }
    }

    public SavedItem createSavedItem() {
        return new SavedItem(
                ThreadLocalRandom.current().nextInt(10000, 2000000),
                "Наконечник стоматологический",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTxBxJ-fTjMxRROsMpovq9NLDhXcbLWT300Eg&s",
                12500,
                "Высокоскоростной стоматологический наконечник для препарирования зубов. Частота вращения до 400,000 об/мин. Эргономичный дизайн снижает усталость рук при длительной работе. Система охлаждения водой предотвращает перегрев. Совместим со стандартными борами. Корпус из авиационного алюминия. Уровень шума менее 65 дБ. Угол наклона головки 20°. Вес 85 гр. Стерилизуется автоклавированием при 135°C. Ресурс работы - более 500 часов. Поставляется с 6-месячной гарантией. В комплекте ключ для замены боров и инструкция. Произведено в Швейцарии по медицинским стандартам ISO 13485.",
                ThreadLocalRandom.current().nextBoolean() ? ThreadLocalRandom.current().nextInt(1000, 200000) : 0,
                randomFlags()
        );
    }
}
