package com.example.bin.ui.state;

import com.example.bin.ui.SavedData;

public class SuccessfulUpdate implements BinScreenState {

    private final SavedData binSavedData;

    public SuccessfulUpdate(SavedData binSavedData) {
        this.binSavedData = binSavedData;
    }

    public SavedData getBinSavedData() {
        return binSavedData;
    }
}
