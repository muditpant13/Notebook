package com.example.mudit.notebook.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mudit.note.R;
import com.example.mudit.notebook.interfaces.ICreateOrUpdateNotePresenter;
import com.example.mudit.notebook.interfaces.ICreateOrUpdateNoteView;
import com.example.mudit.notebook.model.Note;
import com.example.mudit.notebook.presenter.CreateOrUpdateNotePresenter;
import com.example.mudit.notebook.utils.AppConstants;

/**
 * Created by mudit on 4/8/16.
 */
public class ViewNoteActivity extends AppCompatActivity implements ICreateOrUpdateNoteView,
        View.OnClickListener {

    private String mNoteId;
    private String mNoteTitle;
    private String mNoteText;

    private EditText mEtNoteTitle;
    private EditText mEtNoteText;
    private TextView mTvRemoveNoteButton;

    private ICreateOrUpdateNotePresenter mCreateOrUpdateNotePresenter;

    private View mActivityView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note);
        //get extra data that was sent to this activity
        Bundle extras = getIntent().getExtras();
        mNoteId = extras.getString(AppConstants.NOTE_ID);
        mNoteText = extras.getString(AppConstants.NOTE_TEXT);
        mNoteTitle = extras.getString(AppConstants.NOTE_TITLE);

        mEtNoteText = (EditText) findViewById(R.id.etNoteText);
        mEtNoteTitle = (EditText) findViewById(R.id.etNoteTitle);
        mTvRemoveNoteButton = (TextView) findViewById(R.id.tvRemoveNoteButton);

        mEtNoteTitle.setText(mNoteTitle);
        mEtNoteText.setText(mNoteText);

        mEtNoteText.setKeyListener(null);
        mEtNoteTitle.setKeyListener(null);

        mCreateOrUpdateNotePresenter = new CreateOrUpdateNotePresenter(getApplication(), this);

        mTvRemoveNoteButton.setOnClickListener(this);

        mActivityView = this.findViewById(android.R.id.content).getRootView();

        setToolbarForActivity();

    }

    private void setToolbarForActivity() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.viewNoteToolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewNoteActivity.this, MainActivity.class);
                ViewNoteActivity.this.startActivity(intent);
                ViewNoteActivity.this.finish();
            }
        });
    }

    @Override
    public void onCreateNoteSuccess(Note note) {

    }

    @Override
    public void onCreateNoteFailure() {

    }

    @Override
    public void onUpdateNoteSuccess(Note note) {

    }

    @Override
    public void onUpdateNoteFailure() {

    }

    @Override
    public void onDeleteNoteSuccess(String noteId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(mActivityView, "Note cannot be removed. Please Try again", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(ViewNoteActivity.this, MainActivity.class);
                ViewNoteActivity.this.startActivity(intent);
                ViewNoteActivity.this.finish();
            }
        });
    }

    @Override
    public void onDeleteNoteFailure(String noteId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(mActivityView, "Note cannot be removed. Please Try again", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.tvRemoveNoteButton){
            mCreateOrUpdateNotePresenter.deleteNote(mNoteId);
        }
    }
}
