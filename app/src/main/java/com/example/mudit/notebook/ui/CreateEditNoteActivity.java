package com.example.mudit.notebook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mudit.note.R;
import com.example.mudit.notebook.interfaces.ICreateOrUpdateNotePresenter;
import com.example.mudit.notebook.interfaces.ICreateOrUpdateNoteView;
import com.example.mudit.notebook.model.Note;
import com.example.mudit.notebook.presenter.CreateOrUpdateNotePresenter;
import com.example.mudit.notebook.utils.AppConstants;
import com.example.mudit.notebook.utils.AppUtils;

/**
 * Created by mudit on 3/8/16.
 */
public class CreateEditNoteActivity extends AppCompatActivity implements View.OnClickListener, ICreateOrUpdateNoteView {

    private EditText mEtNoteTitle;
    private EditText mEtNoteText;
    private TextView mTvEditCreateNoteButton;
    private ICreateOrUpdateNotePresenter mCreateOrUpdateNotePresenter;

    private static final String TAG = CreateEditNoteActivity.class.getName();

    private String mOperation;
    private String mNoteText;
    private String mNoteId;
    private String mNoteTitle;
    private String mNoteCreationTime;

    private View mActivityView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_edit_note);

        mEtNoteText = (EditText) findViewById(R.id.etNoteText);
        mEtNoteTitle = (EditText) findViewById(R.id.etNoteTitle);
        mTvEditCreateNoteButton = (TextView) findViewById(R.id.tvEditCreateNoteButton);

        mTvEditCreateNoteButton.setOnClickListener(this);

        mCreateOrUpdateNotePresenter = new CreateOrUpdateNotePresenter(getApplication(), this);

        //get extra vale send to this activity
        Bundle extras = getIntent().getExtras();
        mOperation = extras.getString(AppConstants.NOTE_OPERATION);

        if (mOperation.equals(AppConstants.NOTE_CREATE_OPERATION)) {
            mTvEditCreateNoteButton.setText("CREATE");
        } else if (mOperation.equals(AppConstants.NOTE_EDIT_OPERATION)) {
            mTvEditCreateNoteButton.setText("EDIT");
            mNoteText = extras.getString(AppConstants.NOTE_TEXT);
            mNoteTitle = extras.getString(AppConstants.NOTE_TITLE);
            mNoteId = extras.getString(AppConstants.NOTE_ID);
            mNoteCreationTime = extras.getString(AppConstants.NOTE_CREATION_TIME);

            mEtNoteText.setText(mNoteText);
            mEtNoteTitle.setText(mNoteTitle);
        }

        mActivityView = this.findViewById(android.R.id.content).getRootView();

        setToolbarForActivity();

    }

    private void setToolbarForActivity() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.createEditNoteToolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateEditNoteActivity.this, MainActivity.class);
                CreateEditNoteActivity.this.startActivity(intent);
                CreateEditNoteActivity.this.finish();
            }
        });
    }

    private void createNewNote() {
        //create the new note here
        Note note = new Note();
        Note.Data noteData = note.new Data();

        note.setId(AppUtils.getDocumentId(AppConstants.Document.NOTE_PREFIX));
        noteData.setCreatedAt(AppUtils.getCurrentDateTime());
        noteData.setTitle(mEtNoteTitle.getEditableText().toString());
        noteData.setNote(mEtNoteText.getEditableText().toString());

        note.setData(noteData);

        mCreateOrUpdateNotePresenter.createNote(note);

    }

    private void updateNote() {
        //create the new note here
        Note note = new Note();
        Note.Data noteData = note.new Data();

        note.setId(mNoteId);
        noteData.setCreatedAt(AppUtils.getCurrentDateTime());
        noteData.setTitle(mEtNoteTitle.getEditableText().toString());
        noteData.setNote(mEtNoteText.getEditableText().toString());

        note.setData(noteData);

        mCreateOrUpdateNotePresenter.updateNote(note);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvEditCreateNoteButton) {
            if (mEtNoteTitle.getEditableText().toString() != null && !mEtNoteTitle.getEditableText().toString().trim().equals("")) {

                //check for note text
                if (mEtNoteText.getEditableText().toString() != null && !mEtNoteText.getEditableText().toString().equals("")) {
                    if (mOperation.equals(AppConstants.NOTE_CREATE_OPERATION)) {

                        createNewNote();

                    } else {

                        updateNote();
                    }

                } else {
                    Snackbar.make(v, "Note text cannot be empty ", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

            } else {
                Snackbar.make(v, "Note title cannot be empty ", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        }
    }

    @Override
    public void onCreateNoteSuccess(Note note) {
        Log.d(TAG, "Note created with id " + note.getId());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(CreateEditNoteActivity.this, MainActivity.class);
                CreateEditNoteActivity.this.startActivity(intent);
                CreateEditNoteActivity.this.finish();
            }
        });
    }

    @Override
    public void onCreateNoteFailure() {
        Snackbar.make(mActivityView, "Note cannot be created. Please Try again", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onUpdateNoteSuccess(Note note) {
        Log.d(TAG, "Note created with id " + note.getId());
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(CreateEditNoteActivity.this, MainActivity.class);
                CreateEditNoteActivity.this.startActivity(intent);
                CreateEditNoteActivity.this.finish();
            }
        });
    }

    @Override
    public void onUpdateNoteFailure() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Snackbar.make(mActivityView, "Note cannot be updated. Please Try again", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public void onDeleteNoteSuccess(String noteId) {

    }

    @Override
    public void onDeleteNoteFailure(String noteId) {

    }
}
