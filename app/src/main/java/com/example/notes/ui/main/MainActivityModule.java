package com.example.notes.ui.main;

import com.example.notes.ui.router.Router;

import androidx.fragment.app.FragmentManager;
import dagger.Module;
import dagger.Provides;

@Module
public class MainActivityModule {

    private final MainActivity mainActivity;

    public MainActivityModule(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Provides
    FragmentManager providesFragmentManager() {
        return mainActivity.getSupportFragmentManager();
    }

    @Provides
    @ActivityScope
    Router providesRouter(FragmentManager fragmentManager) {
        return new MainActivityRouter(fragmentManager);
    }
}
