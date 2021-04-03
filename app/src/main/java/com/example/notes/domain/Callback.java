package com.example.notes.domain;

public interface Callback<T> {

    void onResult(T value);
}
