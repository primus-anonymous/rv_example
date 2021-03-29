package com.example.notes.domain.domain;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MockNotesRepository implements NotesRepository {

    public static final NotesRepository INSTANCE = new MockNotesRepository();

    private final Executor executor = Executors.newCachedThreadPool();

    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    public void getNotes(Callback<List<Note>> callback) {

        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ArrayList<Note> data = new ArrayList();

                Calendar calendar = Calendar.getInstance();

                data.add(new Note("id1", "Name1", "https://images.unsplash.com/photo-1584714268709-c3dd9c92b378", calendar.getTime()));
                data.add(new Note("id2", "Name2", "https://images.unsplash.com/photo-1598128558393-70ff21433be0", calendar.getTime()));
                data.add(new Note("id3", "Name1", "https://images.unsplash.com/photo-1584714268709-c3dd9c92b378", calendar.getTime()));

                calendar.add(Calendar.DAY_OF_YEAR, -2);

                data.add(new Note("id4", "Name2", "https://images.unsplash.com/photo-1598128558393-70ff21433be0", calendar.getTime()));
                data.add(new Note("id5", "Name1", "https://images.unsplash.com/photo-1584714268709-c3dd9c92b378", calendar.getTime()));

                calendar.add(Calendar.DAY_OF_YEAR, -4);

                data.add(new Note("id6", "Name2", "https://images.unsplash.com/photo-1598128558393-70ff21433be0", calendar.getTime()));
                data.add(new Note("id7", "Name1", "https://images.unsplash.com/photo-1584714268709-c3dd9c92b378", calendar.getTime()));
                data.add(new Note("id8", "Name2", "https://images.unsplash.com/photo-1598128558393-70ff21433be0", calendar.getTime()));

                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        callback.onResult(data);
                    }
                });
            }
        });
    }

    @Override
    public void clearNotes(Callback<Object> voidCallback) {

        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        voidCallback.onResult(new Object());
                    }
                });

            }
        });
    }

    @Override
    public void addNewNote(Callback<Note> noteCallback) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mainThreadHandler.post(new Runnable() {
                    @Override
                    public void run() {

                        Note note = new Note("id6", "Name2", "https://images.unsplash.com/photo-1598128558393-70ff21433be0", new Date());

                        noteCallback.onResult(note);
                    }
                });

            }
        });

    }

    @Override
    public void deleteNote(Note note, Callback<Object> objectCallback) {
        objectCallback.onResult(new Object());
    }


}
