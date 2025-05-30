package com.example.bin.ui.state;

public class Error implements BinScreenState {

    private final Throwable error;

    public Error(Throwable error) {
        this.error = error;
    }

    public Throwable getError() {
        return error;
    }
}
