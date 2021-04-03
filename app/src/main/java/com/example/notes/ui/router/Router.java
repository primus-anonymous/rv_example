package com.example.notes.ui.router;

import com.example.notes.domain.Note;

public interface Router {

    void openHome();

    void openNotes();

    void openSettings();

    void openEditNotes(Note note);

    void closeEditNotes();
}
