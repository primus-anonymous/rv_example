package com.example.notes.ui.notes;

import com.example.notes.domain.domain.Callback;
import com.example.notes.domain.domain.Note;
import com.example.notes.domain.domain.NotesRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotesViewModel extends ViewModel {

    public NotesViewModel(NotesRepository repository) {
        this.repository = repository;
    }

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    private final NotesRepository repository;

    private final MutableLiveData<List<Note>> notesLiveData = new MutableLiveData<>();

    private final MutableLiveData<Boolean> progressLiveData = new MutableLiveData<>();

    private final MutableLiveData<Note> newNoteAddedLiveData = new MutableLiveData<>();

    private final MutableLiveData<Integer> removedItemPositionLiveData = new MutableLiveData<>();

    private final MutableLiveData<String> selectedDateLiveData = new MutableLiveData<>();

    public void fetchNotes() {
        progressLiveData.setValue(true);
        repository.getNotes(new Callback<List<Note>>() {
            @Override
            public void onResult(List<Note> value) {
                notesLiveData.postValue(value);
                progressLiveData.setValue(false);
            }
        });
    }

    public LiveData<List<Note>> getNotesLiveData() {
        return notesLiveData;
    }

    public LiveData<Boolean> getProgressLiveData() {
        return progressLiveData;
    }

    public void addNewNote() {
        progressLiveData.setValue(true);

        repository.addNewNote(new Callback<Note>() {
            @Override
            public void onResult(Note value) {
                newNoteAddedLiveData.postValue(value);
                progressLiveData.setValue(false);
            }
        });
    }

    public void clearNotes() {
        progressLiveData.setValue(true);

        repository.clearNotes(new Callback<Object>() {
            @Override
            public void onResult(Object value) {
                notesLiveData.postValue(new ArrayList<>());
                progressLiveData.setValue(false);
            }
        });
    }

    public void dateSelected(Long selection) {
        selectedDateLiveData.setValue(simpleDateFormat.format(new Date(selection)));
    }

    public LiveData<Note> getNewNoteAddedLiveData() {
        return newNoteAddedLiveData;
    }
    public void deleteAtPosition(int contextMenuItemPosition) {
        removedItemPositionLiveData.setValue(contextMenuItemPosition);
    }

    public LiveData<Integer> getRemovedItemPositionLiveData() {
        return removedItemPositionLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public LiveData<String> getSelectedDateLiveData() {
        return selectedDateLiveData;
    }
}