package com.example.notes.ui.main;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.notes.App;
import com.example.notes.R;
import com.example.notes.ui.router.Router;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Inject
    Router router;

    private MainActivitySubcomponent activitySubcomponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        activitySubcomponent = ((App) getApplication()).getComponent().plustMainActivityComponent(new MainActivityModule(this));

        activitySubcomponent.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    router.openHome();
                    return true;
                } else if (itemId == R.id.navigation_notes) {
                    router.openNotes();
                    return true;
                } else if (itemId == R.id.navigation_settings) {
                    router.openSettings();
                    return true;
                }

                return false;
            }
        });

        if (savedInstanceState == null) {
            router.openHome();
        }
    }

    @Override
    protected void onDestroy() {
        activitySubcomponent = null;
        super.onDestroy();
    }


    public MainActivitySubcomponent getActivitySubcomponent() {
        return activitySubcomponent;
    }
}