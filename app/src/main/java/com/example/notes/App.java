package com.example.notes;

import android.app.Application;

import com.example.notes.di.ApplicationComponent;
import com.example.notes.di.DaggerApplicationComponent;
import com.example.notes.domain.NotesRepository;

import javax.inject.Inject;

public class App extends Application {

    //Храним component в Application, чтобы у нас здесь хранились зависимости для жизненного цикла
    //всего приложения
    private ApplicationComponent component;

    @Inject
    NotesRepository repository; //допустим нам зачем-то понадобился здесь репозиторий

    @Override
    public void onCreate() {
        super.onCreate();

        component = DaggerApplicationComponent.builder()
                .build();

        component.inject(this);
    }

    public ApplicationComponent getComponent() {
        return component;
    }
}
