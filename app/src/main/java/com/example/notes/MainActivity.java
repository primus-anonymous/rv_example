package com.example.notes;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.notes.domain.domain.Note;
import com.example.notes.ui.edit.EditNoteFragment;
import com.example.notes.ui.home.HomeFragment;
import com.example.notes.ui.notes.NotesFragment;
import com.example.notes.ui.settings.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity implements NotesFragment.OnNoteSelected, EditNoteFragment.OnNoteSaved {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    openTab(new HomeFragment(), HomeFragment.TAG);
                    return true;
                } else if (itemId == R.id.navigation_notes) {
                    openTab(new NotesFragment(), NotesFragment.TAG);
                    return true;
                } else if (itemId == R.id.navigation_settings) {
                    openTab(new SettingsFragment(), SettingsFragment.TAG);
                    return true;
                }

                return false;
            }
        });

        if (savedInstanceState == null) {
            openTab(new HomeFragment(), HomeFragment.TAG);
        }
    }

    private void openTab(Fragment fragment, String tag) {
        Fragment addedFragment = getSupportFragmentManager().findFragmentByTag(tag);

        getSupportFragmentManager().popBackStack();

        if (addedFragment == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.host_fragment, fragment, tag)
                    .commit();


        }
    }

    @Override
    public void onNoteSelected(Note note) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.host_fragment, EditNoteFragment.newInstance(note), EditNoteFragment.TAG)
                .addToBackStack(EditNoteFragment.TAG)
                .commit();
    }

    @Override
    public void onNoteSaved() {
        getSupportFragmentManager()
                .popBackStack();
    }
}