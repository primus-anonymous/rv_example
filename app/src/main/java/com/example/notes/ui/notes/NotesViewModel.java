package com.example.notes.ui.notes;

import com.example.notes.domain.domain.Callback;
import com.example.notes.domain.domain.Note;
import com.example.notes.domain.domain.NotesRepository;
import com.example.notes.ui.notes.adapter.AdapterItem;
import com.example.notes.ui.notes.adapter.HeaderAdapterItem;
import com.example.notes.ui.notes.adapter.NoteAdapterItem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

public class NotesViewModel extends ViewModel {

    public NotesViewModel(NotesRepository repository) {
        this.repository = repository;
    }

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());

    private final NotesRepository repository;

    private final MutableLiveData<ArrayList<Note>> notesLiveData = new MutableLiveData<>();

    private final MutableLiveData<Boolean> progressLiveData = new MutableLiveData<>();

    private final MutableLiveData<String> selectedDateLiveData = new MutableLiveData<>();

    public void fetchNotes() {
        progressLiveData.setValue(true);
        repository.getNotes(new Callback<List<Note>>() {
            @Override
            public void onResult(List<Note> value) {
                notesLiveData.postValue(new ArrayList<>(value));
                progressLiveData.setValue(false);
            }
        });
    }

    public LiveData<List<AdapterItem>> getNotesLiveData() {
        return Transformations.map(notesLiveData, new Function<ArrayList<Note>, List<AdapterItem>>() {
            @Override
            public List<AdapterItem> apply(ArrayList<Note> input) {

                ArrayList<AdapterItem> result = new ArrayList<>();

                Collections.sort(input, new Comparator<Note>() {
                    @Override
                    public int compare(Note o1, Note o2) {
                        return o1.getDate().compareTo(o2.getDate());
                    }
                });

                Date currentDate = null;

                for (Note note : input) {

                    Date noteDate = note.getDate();

                    if (!noteDate.equals(currentDate)) {
                        currentDate = noteDate;
                        result.add(new HeaderAdapterItem(simpleDateFormat.format(currentDate)));
                    }

                    result.add(new NoteAdapterItem(note));
                }

                return result;
            }
        });
    }

    public LiveData<Boolean> getProgressLiveData() {
        return progressLiveData;
    }

    public void addNewNote() {
        progressLiveData.setValue(true);

        repository.addNewNote(new Callback<Note>() {
            @Override
            public void onResult(Note value) {
                progressLiveData.setValue(false);

                ArrayList<Note> currentNotes = notesLiveData.getValue();

                currentNotes.add(value);

                notesLiveData.postValue(currentNotes);
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

    public void deleteAtPosition(Note note) {
        progressLiveData.setValue(true);

        repository.deleteNote(note, new Callback<Object>() {
            @Override
            public void onResult(Object value) {
                progressLiveData.setValue(false);

                ArrayList<Note> currentNotes = notesLiveData.getValue();

                currentNotes.remove(note);

                notesLiveData.postValue(currentNotes);

            }
        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public LiveData<String> getSelectedDateLiveData() {
        return selectedDateLiveData;
    }
}