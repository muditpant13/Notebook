package com.example.mudit.notebook.interactors;

import com.couchbase.lite.Document;
import com.couchbase.lite.QueryEnumerator;
import com.couchbase.lite.QueryRow;
import com.example.mudit.notebook.dbHelpers.CouchBaseLiteHelper;
import com.example.mudit.notebook.interfaces.IFetchAllNotesFinishedListener;
import com.example.mudit.notebook.interfaces.IFetchAllNotesInteractor;
import com.example.mudit.notebook.model.Note;
import com.example.mudit.notebook.utils.AppConstants;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by mudit on 3/8/16.
 */
public class FetchAllNotesInteractor implements IFetchAllNotesInteractor {

    private
    CouchBaseLiteHelper mDatabaseHelper;
    private
    ObjectMapper mMapper;

    private IFetchAllNotesFinishedListener mFetchAllNotesFinishedListener;

    public void setFetchAllNotesFinishedListener
            (IFetchAllNotesFinishedListener fetchAllNotesFinishedListener){
        mFetchAllNotesFinishedListener = fetchAllNotesFinishedListener;
    }

    @Inject
    public FetchAllNotesInteractor(CouchBaseLiteHelper helper, ObjectMapper mapper){
        mDatabaseHelper = helper;
        mMapper = mapper;
    }

    public void fetchAllNotesAsync(){
        QueryEnumerator result = mDatabaseHelper.getAllDocs(AppConstants.Document.NOTE_START_KEY,
                AppConstants.Document.NOTE_END_KEY);

        if (result == null) {
            mFetchAllNotesFinishedListener.onFetchAllNotesFailure();
            return;
        }

        List<Note> noteList = new ArrayList<>();
        for (Iterator<QueryRow> it = result; it.hasNext(); ) {
            Note printer;
            QueryRow row = it.next();
            String docId = row.getDocumentId();

            Document printerDoc = mDatabaseHelper.readDocument(docId);

            Map<String, Object> printerMap = printerDoc.getProperties();

            printer = mMapper.convertValue(printerMap, Note.class);

            noteList.add(printer);
        }
        mFetchAllNotesFinishedListener.onFetchAllNotesSuccess(noteList);
    }

    @Override
    public void fetchAllNotes() {
        Thread fetchAllNotesThread = new Thread(new Runnable() {
            @Override
            public void run() {
                fetchAllNotesAsync();
            }
        });
        fetchAllNotesThread.start();
    }
}
