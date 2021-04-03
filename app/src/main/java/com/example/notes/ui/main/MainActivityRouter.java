package com.example.notes.ui.main;

import com.example.notes.R;
import com.example.notes.domain.Note;
import com.example.notes.ui.edit.EditNoteFragment;
import com.example.notes.ui.home.HomeFragment;
import com.example.notes.ui.notes.NotesFragment;
import com.example.notes.ui.router.Router;
import com.example.notes.ui.settings.SettingsFragment;

import androidx.fragment.app.FragmentManager;

import static androidx.fragment.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

public class MainActivityRouter implements Router {

    private final FragmentManager fragmentManager;

    public MainActivityRouter(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    @Override
    public void openHome() {
        closeEditNotes();

        fragmentManager
                .beginTransaction()
                .replace(R.id.host_fragment, new HomeFragment(), HomeFragment.TAG)
                .commit();

    }

    @Override
    public void openNotes() {
        closeEditNotes();

        fragmentManager
                .beginTransaction()
                .replace(R.id.host_fragment, new NotesFragment(), NotesFragment.TAG)
                .commit();

    }

    @Override
    public void openSettings() {
        closeEditNotes();

        fragmentManager
                .beginTransaction()
                .replace(R.id.host_fragment, new SettingsFragment(), SettingsFragment.TAG)
                .commit();

    }

    @Override
    public void openEditNotes(Note note) {
        fragmentManager
                .beginTransaction()
                .replace(R.id.host_fragment, EditNoteFragment.newInstance(note), EditNoteFragment.TAG)
                .addToBackStack(EditNoteFragment.TAG)
                .commit();
    }

    @Override
    public void closeEditNotes() {
        fragmentManager
                .popBackStack(EditNoteFragment.TAG, POP_BACK_STACK_INCLUSIVE);
    }
}
