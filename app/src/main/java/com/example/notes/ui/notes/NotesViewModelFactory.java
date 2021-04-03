package com.example.notes.ui.notes;

import com.example.notes.domain.NotesRepository;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class NotesViewModelFactory implements ViewModelProvider.Factory {

    private final NotesRepository repository;

    @Inject //Dagger будет использовать этот конструктор для создания NotesViewModelFactory
    // и искать как зарезолвить зависимость NotesRepository (реализовано в NotesRepositoryModule)
    public NotesViewModelFactory(NotesRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new NotesViewModel(repository);
    }
}
