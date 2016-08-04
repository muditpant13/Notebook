package com.example.mudit.notebook.presenter;

import android.app.Application;

import com.example.mudit.notebook.NotebookApp;
import com.example.mudit.notebook.interactors.FetchAllNotesInteractor;
import com.example.mudit.notebook.interfaces.IFetchAllNotesFinishedListener;
import com.example.mudit.notebook.interfaces.IFetchAllNotesPresenter;
import com.example.mudit.notebook.interfaces.IFetchAllNotesView;
import com.example.mudit.notebook.model.Note;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by mudit on 3/8/16.
 */
public class FetchAllNotesPresenter implements IFetchAllNotesPresenter, IFetchAllNotesFinishedListener {

    private IFetchAllNotesView mFetchAllNotesView;

    @Inject
    FetchAllNotesInteractor mFetchAllNotesInteractor;

    public FetchAllNotesPresenter(Application application, IFetchAllNotesView fetchAllNotesView){
        mFetchAllNotesView = fetchAllNotesView;
        ((NotebookApp) application).getAppComponent().inject(this);

        mFetchAllNotesInteractor.setFetchAllNotesFinishedListener(this);
    }

    @Override
    public void onFetchAllNotesSuccess(List<Note> noteList) {
        mFetchAllNotesView.onFetchAllNotesSuccess(noteList);
    }

    @Override
    public void onFetchAllNotesFailure() {
        mFetchAllNotesView.onFetchAllNotesFailure();
    }

    @Override
    public void fetchAllNotes() {
        mFetchAllNotesInteractor.fetchAllNotes();
    }
}
