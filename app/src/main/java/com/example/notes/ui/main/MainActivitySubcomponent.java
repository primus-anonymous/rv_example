package com.example.notes.ui.main;

import com.example.notes.ui.edit.EditNoteFragment;
import com.example.notes.ui.notes.NotesFragment;

import dagger.Subcomponent;

@ActivityScope //создаем кастомный scope, будем привязывать его к жизненному циклу активити
@Subcomponent(modules = MainActivityModule.class)
public interface MainActivitySubcomponent {

    void inject(MainActivity mainActivity);

    void inject(EditNoteFragment editNoteFragment);

    void inject(NotesFragment notesFragment);
}
