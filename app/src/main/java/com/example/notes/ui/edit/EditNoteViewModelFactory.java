package com.example.notes.ui.edit;

import com.example.notes.domain.domain.FirestoreNotesRepository;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class EditNoteViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new EditNoteViewModel(FirestoreNotesRepository.INSTANCE);
    }
}
