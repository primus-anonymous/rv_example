package com.example.notes.domain.domain;

public interface Callback<T> {

    void onResult(T value);
}
