package com.example.mudit.notebook.presenter;

import android.app.Application;

import com.example.mudit.notebook.NotebookApp;
import com.example.mudit.notebook.interactors.CreateOrUpdateNoteInteractor;
import com.example.mudit.notebook.interfaces.ICreateOrUpdateNoteFinishedListener;
import com.example.mudit.notebook.interfaces.ICreateOrUpdateNotePresenter;
import com.example.mudit.notebook.interfaces.ICreateOrUpdateNoteView;
import com.example.mudit.notebook.model.Note;

import javax.inject.Inject;

/**
 * Created by mudit on 3/8/16.
 */
public class CreateOrUpdateNotePresenter implements
        ICreateOrUpdateNotePresenter, ICreateOrUpdateNoteFinishedListener {


    private ICreateOrUpdateNoteView mCreateOrUpdateNoteView;

    @Inject
    CreateOrUpdateNoteInteractor mCreateOrUpdateNoteInteractor;


    public CreateOrUpdateNotePresenter(Application application, ICreateOrUpdateNoteView createOrUpdateNoteView){
        mCreateOrUpdateNoteView = createOrUpdateNoteView;

        ((NotebookApp) application).getAppComponent().inject(this);

        mCreateOrUpdateNoteInteractor.setCreateOrUpdateNoteFinishedListener(this);
    }

    @Override
    public void onCreateNoteSuccess(Note note) {
        mCreateOrUpdateNoteView.onCreateNoteSuccess(note);
    }

    @Override
    public void onCreateNoteFailure() {
        mCreateOrUpdateNoteView.onUpdateNoteFailure();
    }

    @Override
    public void onUpdateNoteSuccess(Note note) {
        mCreateOrUpdateNoteView.onUpdateNoteSuccess(note);
    }

    @Override
    public void onUpdateNoteFailure() {
        mCreateOrUpdateNoteView.onUpdateNoteFailure();
    }

    @Override
    public void onDeleteNoteSuccess(String noteId) {
        mCreateOrUpdateNoteView.onDeleteNoteSuccess(noteId);
    }

    @Override
    public void onDeleteNoteFailure(String noteId) {
        mCreateOrUpdateNoteView.onDeleteNoteFailure(noteId);
    }

    @Override
    public void createNote(Note note) {
        mCreateOrUpdateNoteInteractor.createNote(note);
    }

    @Override
    public void updateNote(Note note) {
        mCreateOrUpdateNoteInteractor.updateNote(note);
    }

    @Override
    public void deleteNote(String noteId) {
        mCreateOrUpdateNoteInteractor.deleteNote(noteId);
    }
}
