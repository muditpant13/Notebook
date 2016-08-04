package com.example.mudit.notebook.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by mudit on 3/8/16.
 */
public class Note {


    @JsonProperty("_id")
    private String Id;
    @JsonProperty("_rev")
    private String Rev;
    @JsonProperty("data")
    private Data data;

    @JsonProperty("_revisions")
    private Object revisions;


    /**
     *
     * @return
     * The revisions
     */
    @JsonProperty("_revisions")
    public Object getRevisions() {
        return revisions;
    }

    /**
     *
     * @param revisions
     * The _revisions
     */
    @JsonProperty("_revisions")
    public void setRevisions(Object revisions) {
        this.revisions = revisions;
    }

    /**
     *
     * @return
     * The Id
     */
    @JsonProperty("_id")
    public String getId() {
        return Id;
    }

    /**
     *
     * @param Id
     * The _id
     */
    @JsonProperty("_id")
    public void setId(String Id) {
        this.Id = Id;
    }

    /**
     *
     * @return
     * The Rev
     */
    @JsonProperty("_rev")
    public String getRev() {
        return Rev;
    }

    /**
     *
     * @param Rev
     * The _rev
     */
    @JsonProperty("_rev")
    public void setRev(String Rev) {
        this.Rev = Rev;
    }

    /**
     *
     * @return
     * The data
     */
    public Data getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(Data data) {
        this.data = data;
    }

    public class Data implements Serializable {

        @JsonProperty("title")
        private String title;
        @JsonProperty("note")
        private String note;
        @JsonProperty("createdAt")
        private String createdAt;

        /**
         *
         * @return
         * The title
         */
        public String getTitle() {
            return title;
        }

        /**
         *
         * @param title
         * The title
         */
        public void setTitle(String title) {
            this.title = title;
        }


        /**
         *
         * @return
         * The note
         */
        public String getNote() {
            return note;
        }

        /**
         *
         * @param note
         * The note
         */
        public void setNote(String note) {
            this.note = note;
        }

        /**
         *
         * @return
         * The createdAt
         */
        @JsonProperty("createdAt")
        public String getCreatedAt() {
            return createdAt;
        }

        /**
         *
         * @param createdAt
         * The createdAt
         */
        @JsonProperty("createdAt")
        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }


    }

}



