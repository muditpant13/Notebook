package com.example.mudit.notebook.dbHelpers;

import android.app.Application;
import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.Query;
import com.couchbase.lite.QueryEnumerator;
import com.example.mudit.notebook.NotebookApp;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by mudit on 3/8/16.
 */
public class CouchBaseLiteHelper {

    //Injecting dependency via dagger 2
    @Inject
    Database mDatabase;

    public static String TAG = CouchBaseLiteHelper.class.getName();

    @Inject
    public CouchBaseLiteHelper(Application ctx){
        ((NotebookApp)ctx).getAppComponent().inject(this);
    }

    //Creating a document
    public boolean createDocument(Map<String, Object> propertyMap , String docID){
        Document documentToCreate = mDatabase.getDocument(docID);
        try {
            // Save the properties to the document
            propertyMap.remove("_rev");
            documentToCreate.putProperties(propertyMap);
            return true;
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Error putting", e);
        }
        return false;
    }

    //returns null if document does not exists
    public Document checkIfDocumentExists(String docID){

        Document retrievedDocument = mDatabase.getExistingDocument(docID);
        return retrievedDocument;

    }

    //read the document
    public Document readDocument(String docID){

        Document retrievedDocument = mDatabase.getDocument(docID);
        // return the retrieved document
        return retrievedDocument;
    }

    //delete the document
    public boolean deleteDocument(String docID){
        Document retrievedDocument = checkIfDocumentExists(docID);
        if (retrievedDocument == null) {
            return true;
        }
        try {
            retrievedDocument.delete();
            Log.d (TAG, "Deleted document, deletion status = " + retrievedDocument.isDeleted());
            return true;
        } catch (CouchbaseLiteException e) {
            Log.e (TAG, "Cannot delete document", e);
        }
        return false;
    }

    public boolean updateDocument(Map<String, Object> newPropertyMap, String docID){

        Document document = mDatabase.getDocument(docID);
        try {
            // Update the document with more data
            newPropertyMap.remove("_id");
            newPropertyMap.remove("_rev");
            Map<String, Object> updatedProperties = new HashMap<String, Object>();
            if (document.getProperties() != null) {
                updatedProperties.putAll(document.getProperties());
            }
            updatedProperties.putAll(newPropertyMap);
            // Save to the Couchbase local Couchbase Lite DB
            document.putProperties(updatedProperties);
            return true;
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Error putting", e);
        }
        return false;
    }

    public QueryEnumerator getAllDocs(String startKey, String endKey) {
        QueryEnumerator result = null;
        Query query = mDatabase.createAllDocumentsQuery();
        query.setAllDocsMode(Query.AllDocsMode.ALL_DOCS);
        query.setStartKey(startKey);
        query.setEndKey(endKey);
        try {
            result = query.run();
        } catch (CouchbaseLiteException e) {
            Log.e(TAG, "Error: get All Docs");
        }
        return result;
    }

}
