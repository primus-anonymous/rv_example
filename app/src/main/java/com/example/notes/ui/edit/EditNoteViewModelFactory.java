package com.example.notes.ui.edit;

import com.example.notes.domain.NotesRepository;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class EditNoteViewModelFactory implements ViewModelProvider.Factory {

    private NotesRepository repository;

    @Inject
    public EditNoteViewModelFactory(NotesRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new EditNoteViewModel(repository);
    }
}
