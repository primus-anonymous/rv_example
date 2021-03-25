package com.example.notes.ui.notes;

import com.example.notes.domain.domain.Note;

public class NoteAdapterItem implements AdapterItem {

    private Note note;

    public NoteAdapterItem(Note note) {
        this.note = note;
    }

    public Note getNote() {
        return note;
    }
}
