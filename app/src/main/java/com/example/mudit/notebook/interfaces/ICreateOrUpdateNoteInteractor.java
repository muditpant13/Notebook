package com.example.mudit.notebook.interfaces;

import com.example.mudit.notebook.model.Note;

/**
 * Created by mudit on 3/8/16.
 */
public interface ICreateOrUpdateNoteInteractor {

    public void createNote(Note note);
    public void updateNote(Note note);
    public void deleteNote(String noteId);

}
