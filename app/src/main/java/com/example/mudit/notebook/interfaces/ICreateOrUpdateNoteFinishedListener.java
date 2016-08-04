package com.example.mudit.notebook.interfaces;

import com.example.mudit.notebook.model.Note;

/**
 * Created by mudit on 3/8/16.
 */

public interface ICreateOrUpdateNoteFinishedListener {

    public void onCreateNoteSuccess(Note note);
    public void onCreateNoteFailure();

    public void onUpdateNoteSuccess(Note note);
    public void onUpdateNoteFailure();

    public void onDeleteNoteSuccess(String noteId);
    public void onDeleteNoteFailure(String noteId);

}
