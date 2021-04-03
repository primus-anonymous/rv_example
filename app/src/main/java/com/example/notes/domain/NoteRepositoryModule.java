package com.example.notes.domain;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NoteRepositoryModule {

    @Singleton //Чтобы не пересоздавать объект репозитория, который будем инжектить
    @Provides
    NotesRepository providesNoteRepository() {
        return new FirestoreNotesRepository();
    }
}
