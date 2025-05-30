package com.example.bin.ui;

import static androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.viewmodel.ViewModelInitializer;

import com.example.bin.MyApplication;
import com.example.bin.data.BinDataSourceCache;
import com.example.bin.data.BinDataSourceProducts;
import com.example.bin.data.BinRepository;
import com.example.bin.data.BinRepositoryImpl;
import com.example.bin.data.SavedItem;
import com.example.bin.data.SavedItemsCreator;
import com.example.bin.ui.state.BinScreenState;
import com.example.bin.ui.state.Error;
import com.example.bin.ui.state.Loading;
import com.example.bin.ui.state.SuccessfulUpdate;
import com.google.gson.Gson;

import java.util.List;

public class BinViewModel extends ViewModel {

    private static final String SHARED_PREF_KEY = "shared_pref";
    private final MutableLiveData<BinScreenState> data = new MutableLiveData<>();
    private final BinRepository repository;
    SavedItemsCreator savedItemsCreator = new SavedItemsCreator();

    public BinViewModel(SharedPreferences productCache) {
        Gson gson = new Gson();
        repository = new BinRepositoryImpl(new BinDataSourceProducts(gson), new BinDataSourceCache(productCache, gson));
    }

    LiveData<BinScreenState> getData() {
        return data;
    }

    void getProductList(String rawJson) {
        data.setValue(new Loading());
        try {
            List<SavedItem> savedItems = repository.getProductList();
            if (savedItems.isEmpty()) {
                data.setValue(new SuccessfulUpdate(savedItemsCreator.createSavedData(repository.fetchProductList(rawJson))));
            } else {
                data.setValue(new SuccessfulUpdate(savedItemsCreator.createSavedData(savedItems)));
            }
        } catch (Exception e) {
            data.setValue(new Error(e));
        }
    }

    void addProductToBin(SavedItem item) {
        data.setValue(new Loading());
        try {
            data.setValue(new SuccessfulUpdate(savedItemsCreator.createSavedData(repository.addProductToBin(item))));
        } catch (Exception e) {
            data.setValue(new Error(e));
        }
    }

    void removeItemFromBin(SavedItem item) {
        data.setValue(new Loading());
        try {
            data.setValue(new SuccessfulUpdate(savedItemsCreator.createSavedData(repository.removeItemFromBin(item))));
        } catch (Exception e) {
            data.setValue(new Error(e));
        }
    }

    void dismissItemFromBin(SavedItem item) {
        data.setValue(new Loading());
        try {
            data.setValue(new SuccessfulUpdate(savedItemsCreator.createSavedData(repository.dismissItemFromBin(item))));
        } catch (Exception e) {
            data.setValue(new Error(e));
        }
    }

    void addItemToBin() {
        data.setValue(new Loading());
        try {
            data.setValue(new SuccessfulUpdate(savedItemsCreator.createSavedData(repository.addItemToBin(savedItemsCreator.createSavedItem()))));
        } catch (Exception e) {
            data.setValue(new Error(e));
        }
    }

    static final ViewModelInitializer<BinViewModel> initializer = new ViewModelInitializer<>(
            BinViewModel.class,
            creationExtras -> {
                MyApplication app = (MyApplication) creationExtras.get(APPLICATION_KEY);
                assert app != null;
                SharedPreferences productCache = app.getSharedPreferences(SHARED_PREF_KEY, Context.MODE_PRIVATE);
                return new BinViewModel(productCache);
            }
    );
}