package com.example.notes.domain.domain;

import java.util.List;

public interface NotesRepository {

    void getNotes(Callback<List<Note>> callback);

    void clearNotes(Callback<Object> voidCallback);

    void addNewNote(Callback<Note> noteCallback);

    void deleteNote(Note note, Callback<Object> objectCallback);

    void updateNote(Note note, Callback<Object> objectCallback);
}
