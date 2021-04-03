package com.example.notes.ui.edit;

import android.text.Editable;

import com.example.notes.domain.Callback;
import com.example.notes.domain.Note;
import com.example.notes.domain.NotesRepository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class EditNoteViewModel extends ViewModel {

    private final NotesRepository repository;

    public EditNoteViewModel(NotesRepository repository) {
        this.repository = repository;
    }

    private MutableLiveData<Boolean> progress = new MutableLiveData<>(false);

    public LiveData<Boolean> getProgress() {
        return progress;
    }

    private MutableLiveData<Boolean> saveEnabled = new MutableLiveData<>(false);

    public LiveData<Boolean> saveEnabled() {
        return saveEnabled;
    }

    private MutableLiveData<Object> saveSucceed = new MutableLiveData<>();

    public LiveData<Object> saveSucceed() {
        return saveSucceed;
    }

    public void validateInput(String newName) {
        saveEnabled.setValue(!newName.isEmpty());
    }

    public void saveNote(Editable text, Note note) {
        note.setName(text.toString());

        progress.setValue(true);

        repository.updateNote(note, new Callback<Object>() {
            @Override
            public void onResult(Object value) {

                progress.setValue(false);
                saveSucceed.setValue(new Object());
            }
        });

    }
}
