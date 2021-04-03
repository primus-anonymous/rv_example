package com.example.notes.domain;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.NonNull;

public class FirestoreNotesRepository implements NotesRepository {

    public static final String FIELD_NAME = "name";
    public static final String FIELD_IMAGE_URL = "imageUrl";
    public static final String FIELD_DATE = "date";
    private static final String NOTES_COLLECTION = "notes";
    private final FirebaseFirestore fireStore = FirebaseFirestore.getInstance();

    @Override
    public void getNotes(Callback<List<Note>> callback) {

        fireStore.collection(NOTES_COLLECTION).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        List<DocumentSnapshot> documents = task.getResult().getDocuments();

                        ArrayList<Note> result = new ArrayList<>();

                        for (DocumentSnapshot doc : documents) {

                            String name = doc.getString(FIELD_NAME);
                            String imageUrl = doc.getString(FIELD_IMAGE_URL);
                            Date date = doc.getDate(FIELD_DATE);

                            Note note = new Note(doc.getId(), name, imageUrl, date);

                            result.add(note);
                        }

                        callback.onResult(result);
                    }
                });

    }

    @Override
    public void clearNotes(Callback<Object> voidCallback) {

    }

    @Override
    public void addNewNote(Callback<Note> noteCallback) {

        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Note note = new Note("", "name 1", "https://images.unsplash.com/photo-1584714268709-c3dd9c92b378", calendar.getTime());

        HashMap<String, Object> data = new HashMap<>();
        data.put(FIELD_NAME, note.getName());
        data.put(FIELD_IMAGE_URL, note.getImageUrl());
        data.put(FIELD_DATE, note.getDate());

        fireStore.collection(NOTES_COLLECTION)
                .add(data).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
            @Override
            public void onComplete(@NonNull Task<DocumentReference> task) {

                String id = task.getResult().getId();

                note.setId(id);

                noteCallback.onResult(note);

            }
        });
    }

    @Override
    public void deleteNote(Note note, Callback<Object> objectCallback) {

        fireStore.collection(NOTES_COLLECTION)
                .document(note.getId())
                .delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        objectCallback.onResult(new Object());
                    }
                });
    }

    @Override
    public void updateNote(Note note, Callback<Object> objectCallback) {

        HashMap<String, Object> data = new HashMap<>();
        data.put(FIELD_NAME, note.getName());
        data.put(FIELD_IMAGE_URL, note.getImageUrl());
        data.put(FIELD_DATE, note.getDate());

        fireStore.collection(NOTES_COLLECTION)
                .document(note.getId()).update(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        objectCallback.onResult(new Object());
                    }
                });
    }
}
