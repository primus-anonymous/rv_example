package com.example.notes.di;

import com.example.notes.App;
import com.example.notes.domain.NoteRepositoryModule;
import com.example.notes.ui.main.MainActivityModule;
import com.example.notes.ui.main.MainActivitySubcomponent;

import javax.inject.Singleton;

import dagger.Component;

@Singleton //так как в модулях есть такой Scope надо его выставить у компонента
//озночает, что помеченные данным scope будут создавать 1 раз за время жизни компонента
//не помеченные каждый раз при обращении
@Component(modules = {AppModule.class, NoteRepositoryModule.class})
//из модулей Dagger узнает как собирать те или иные объекты
//помимо модулей он может это узнать по конструктору, отмеченному @Inject, пример NotesViewModelFactory
public interface ApplicationComponent {

    //методы позволят заинжектить завиисимости в класс переданный аргементом
    void inject(App app);

    //создает сабкомпонент из компонента, т.е у сабкомпонента будут все модули компонента + собственнный
    //жизненный цикл и scope
    MainActivitySubcomponent plustMainActivityComponent(MainActivityModule module);
}
