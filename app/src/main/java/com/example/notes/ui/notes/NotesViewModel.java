package com.example.notes.ui.notes;

import com.example.notes.domain.domain.MockNotesRepository;
import com.example.notes.domain.domain.Note;
import com.example.notes.domain.domain.NotesRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotesViewModel extends ViewModel {

    private final NotesRepository repository = MockNotesRepository.INSTANCE;

    private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();

    public void fetchNotes() {
        notesLiveData.setValue(repository.getNotes());
    }

    public LiveData<List<Note>> getNotesLiveData() {
        return notesLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}