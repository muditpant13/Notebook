package com.example.mudit.notebook.interactors;

import com.couchbase.lite.Document;
import com.example.mudit.notebook.dbHelpers.CouchBaseLiteHelper;
import com.example.mudit.notebook.interfaces.ICreateOrUpdateNoteFinishedListener;
import com.example.mudit.notebook.interfaces.ICreateOrUpdateNoteInteractor;
import com.example.mudit.notebook.model.Note;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

import javax.inject.Inject;

/**
 * Created by mudit on 3/8/16.
 */
public class CreateOrUpdateNoteInteractor implements ICreateOrUpdateNoteInteractor {

    private
    CouchBaseLiteHelper mDatabaseHelper;
    private
    ObjectMapper mMapper;

    private ICreateOrUpdateNoteFinishedListener mCreateOrUpdateNoteFinishedListener;

    public void setCreateOrUpdateNoteFinishedListener(ICreateOrUpdateNoteFinishedListener listener){
        mCreateOrUpdateNoteFinishedListener = listener;
    }

    @Inject
    public CreateOrUpdateNoteInteractor(CouchBaseLiteHelper helper, ObjectMapper mapper){
        mDatabaseHelper = helper;
        mMapper = mapper;
    }

    public void createOrUpdateCustomerAsync(Note note) {

        //check if document exists
        Document retrievedDocument = mDatabaseHelper.checkIfDocumentExists(note.getId());
        if (retrievedDocument == null) {
            //document does not exists create it
            Map<String, Object> noteMap = mMapper.convertValue(note, Map.class);
            if (!mDatabaseHelper.createDocument(noteMap, note.getId())) {
                mCreateOrUpdateNoteFinishedListener.onCreateNoteFailure();
                return;
            } else {
                mCreateOrUpdateNoteFinishedListener.onCreateNoteSuccess(note);
                return;
            }
        } else {
            //update the document
            Map<String, Object> noteMap = mMapper.convertValue(note, Map.class);
            if (!mDatabaseHelper.updateDocument(noteMap, note.getId())) {
                mCreateOrUpdateNoteFinishedListener.onUpdateNoteFailure();
                return;
            } else {
                mCreateOrUpdateNoteFinishedListener.onUpdateNoteSuccess(note);
                return;
            }
        }
    }

    public void deleteNoteAsync(String noteId){
        if(! mDatabaseHelper.deleteDocument(noteId)){
            mCreateOrUpdateNoteFinishedListener.onDeleteNoteFailure(noteId);
        }else{
            mCreateOrUpdateNoteFinishedListener.onDeleteNoteSuccess(noteId);
        }
    }

    @Override
    public void createNote(final Note note) {
        Thread createOrUpdateNoteThread = new Thread(new Runnable() {
            @Override
            public void run() {
                createOrUpdateCustomerAsync(note);
            }
        });
        createOrUpdateNoteThread.start();
    }

    @Override
    public void updateNote(final Note note) {
        Thread createOrUpdateNoteThread = new Thread(new Runnable() {
            @Override
            public void run() {
                createOrUpdateCustomerAsync(note);
            }
        });
        createOrUpdateNoteThread.start();
    }

    @Override
    public void deleteNote(final String noteId) {
        Thread deleteNoteThread = new Thread(new Runnable() {
            @Override
            public void run() {
                deleteNoteAsync(noteId);
            }
        });
        deleteNoteThread.start();
    }
}
