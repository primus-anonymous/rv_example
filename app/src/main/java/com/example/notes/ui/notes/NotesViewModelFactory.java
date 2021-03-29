package com.example.notes.ui.notes;

import com.example.notes.domain.domain.FirestoreNotesRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class NotesViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NotesViewModel(FirestoreNotesRepository.INSTANCE);
    }
}
