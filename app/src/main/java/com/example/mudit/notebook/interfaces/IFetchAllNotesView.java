package com.example.mudit.notebook.interfaces;

import com.example.mudit.notebook.model.Note;

import java.util.List;

/**
 * Created by mudit on 3/8/16.
 */
public interface IFetchAllNotesView {
    public void onFetchAllNotesSuccess(List<Note> noteList);
    public void onFetchAllNotesFailure();
}
