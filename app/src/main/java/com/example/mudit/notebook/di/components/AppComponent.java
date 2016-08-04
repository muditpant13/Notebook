package com.example.mudit.notebook.di.components;

import com.example.mudit.notebook.NotebookApp;
import com.example.mudit.notebook.dbHelpers.CouchBaseLiteHelper;
import com.example.mudit.notebook.di.modules.AppModule;
import com.example.mudit.notebook.di.modules.DataModule;
import com.example.mudit.notebook.interactors.FetchAllNotesInteractor;
import com.example.mudit.notebook.presenter.CreateOrUpdateNotePresenter;
import com.example.mudit.notebook.presenter.FetchAllNotesPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by mudit on 3/8/16.
 */
@Singleton
@Component(modules = {AppModule.class, DataModule.class})
public interface AppComponent {
    void inject (NotebookApp notebookApp);
    void inject (CouchBaseLiteHelper couchBaseLiteHelper);
    void inject (FetchAllNotesPresenter fetchAllNotesPresenter);
    void inject (CreateOrUpdateNotePresenter createOrUpdateNotePresenter);
}
