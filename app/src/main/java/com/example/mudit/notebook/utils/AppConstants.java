package com.example.mudit.notebook.utils;

/**
 * Created by mudit on 3/8/16.
 */
public class AppConstants {

    public static final String DB_NAME = "notebook_db";


    //Date format String
    public static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    public static final int VERTICAL_ITEM_SPACE = 10;

    public static final String NOTE_OPERATION = "operation";
    public static final String NOTE_CREATE_OPERATION = "create";
    public static final String NOTE_EDIT_OPERATION = "edit";
    public static final String NOTE_ID = "noteID";
    public static final String NOTE_TITLE = "noteTitle";
    public static final String NOTE_TEXT = "noteText";
    public static final String NOTE_CREATION_TIME = "noteCreationTime";

    //for prefix of note document
    public class Document{
        public static final String NOTE_PREFIX = "note_1_";
        //for fetching all notes
        public static final String NOTE_START_KEY = "note_1_";
        public static final String NOTE_END_KEY = "note_2_";
    }
}
