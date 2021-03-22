package com.example.notes.domain.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockNotesRepository implements NotesRepository {

    public static final NotesRepository INSTANCE = new MockNotesRepository();

    @Override
    public List<Note> getNotes() {
        ArrayList<Note> data = new ArrayList();

        data.add(new Note("id1", "Name1", "https://images.unsplash.com/photo-1584714268709-c3dd9c92b378", new Date()));
        data.add(new Note("id2", "Name2", "https://images.unsplash.com/photo-1598128558393-70ff21433be0", new Date()));
        data.add(new Note("id3", "Name1", "https://images.unsplash.com/photo-1584714268709-c3dd9c92b378", new Date()));
        data.add(new Note("id4", "Name2", "https://images.unsplash.com/photo-1598128558393-70ff21433be0", new Date()));
        data.add(new Note("id5", "Name1", "https://images.unsplash.com/photo-1584714268709-c3dd9c92b378", new Date()));
        data.add(new Note("id6", "Name2", "https://images.unsplash.com/photo-1598128558393-70ff21433be0", new Date()));
        data.add(new Note("id7", "Name1", "https://images.unsplash.com/photo-1584714268709-c3dd9c92b378", new Date()));
        data.add(new Note("id8", "Name2", "https://images.unsplash.com/photo-1598128558393-70ff21433be0", new Date()));

        return data;
    }
}
