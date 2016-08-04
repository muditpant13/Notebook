package com.example.mudit.notebook.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mudit.note.R;
import com.example.mudit.notebook.interfaces.IFetchAllNotesPresenter;
import com.example.mudit.notebook.interfaces.IFetchAllNotesView;
import com.example.mudit.notebook.model.Note;
import com.example.mudit.notebook.presenter.FetchAllNotesPresenter;
import com.example.mudit.notebook.ui.adapters.RecyclerViewListAdapter;
import com.example.mudit.notebook.ui.decorators.VerticalSpaceItemDecoration;
import com.example.mudit.notebook.utils.AppConstants;
import com.example.mudit.notebook.utils.AppUtils;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity implements IFetchAllNotesView{

    private IFetchAllNotesPresenter mFetchAllNotesPresenter;
    private List<Note> mNoteList = new ArrayList<>();

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mRecycilerViewListAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private static final String TAG = MainActivity.class.getName();
    Set<String> mNoteIDSet = new HashSet<>();

    private View mActivityView;
    private TextView mTvNoNotesText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFetchAllNotesPresenter = new FetchAllNotesPresenter(getApplication(), this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle createNoteExtraData = new Bundle();
                createNoteExtraData.putString(AppConstants.NOTE_OPERATION, AppConstants.NOTE_CREATE_OPERATION);

                Intent intent = new Intent(MainActivity.this, CreateEditNoteActivity.class);
                intent.putExtras(createNoteExtraData);

                MainActivity.this.startActivity(intent);

            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.rvNoteList);

        //add decoration here

        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(AppConstants.VERTICAL_ITEM_SPACE));

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Initialize a new instance of RecyclerView Adapter instance
        mRecycilerViewListAdapter = new RecyclerViewListAdapter(mNoteList, this, this);

        mRecyclerView.setAdapter(mRecycilerViewListAdapter);

        mActivityView = this.findViewById(android.R.id.content).getRootView();

        mTvNoNotesText = (TextView) findViewById(R.id.tvNoNotesText);
        mTvNoNotesText.setVisibility(View.GONE);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNoteList.clear();
        mNoteIDSet.clear();
        mTvNoNotesText.setVisibility(View.GONE);
        mFetchAllNotesPresenter.fetchAllNotes();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onFetchAllNotesSuccess(final List<Note> noteList) {
        Log.d(TAG, "Fetch Notes Success ");

        List<Note> sortedNoteList = sortNoteList(noteList);
        //for duplicate entries
        for(Note note : sortedNoteList){
            if(!mNoteIDSet.contains(note.getId())){
                mNoteList.add(note);
                mNoteIDSet.add(note.getId());
            }
        }

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if(noteList == null || noteList.size() == 0){
                    mTvNoNotesText.setVisibility(View.VISIBLE);
                }else{
                    mTvNoNotesText.setVisibility(View.GONE);
                }
                mRecycilerViewListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onFetchAllNotesFailure() {
        Log.d(TAG, "Fetch Notes Error ");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvNoNotesText.setVisibility(View.VISIBLE);
                Snackbar.make(mActivityView, "Notes cannot be fetched.", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public void startViewNoteActivity(Note note){

        Bundle viewNoteExtraData = new Bundle();
        viewNoteExtraData.putString(AppConstants.NOTE_ID, note.getId());
        viewNoteExtraData.putString(AppConstants.NOTE_TITLE, note.getData().getTitle());
        viewNoteExtraData.putString(AppConstants.NOTE_TEXT, note.getData().getNote());

        Intent intent = new Intent(MainActivity.this, ViewNoteActivity.class);
        intent.putExtras(viewNoteExtraData);
        MainActivity.this.startActivity(intent);
    }

    public void startEditNoteActivity(Note note){
        Bundle editNoteExtraData = new Bundle();
        editNoteExtraData.putString(AppConstants.NOTE_ID, note.getId());
        editNoteExtraData.putString(AppConstants.NOTE_TITLE, note.getData().getTitle());
        editNoteExtraData.putString(AppConstants.NOTE_TEXT, note.getData().getNote());
        editNoteExtraData.putString(AppConstants.NOTE_CREATION_TIME, note.getData().getCreatedAt());
        editNoteExtraData.putString(AppConstants.NOTE_OPERATION, AppConstants.NOTE_EDIT_OPERATION);

        Intent intent = new Intent(MainActivity.this, CreateEditNoteActivity.class);
        intent.putExtras(editNoteExtraData);
        MainActivity.this.startActivity(intent);
    }

    public List<Note> sortNoteList(List<Note> noteList) {
        List<Note> sortedNoteList;
        sortedNoteList = noteList;

        final String currentTime = new SimpleDateFormat(AppConstants.DATE_FORMAT).format
                (System.currentTimeMillis() - 5.5 * 60 * 60 * 1000);

        //sort note now
        Collections.sort(sortedNoteList, new Comparator<Note>() {

            @Override
            public int compare(Note noteObject1, Note noteObject2) {

               long timeDifferenceObject1 = AppUtils.getTimeDifference(noteObject1.getData().getCreatedAt(),
                       currentTime);

                long timeDifferenceObject2 = AppUtils.getTimeDifference(noteObject2.getData().getCreatedAt(),
                        currentTime);

                if(timeDifferenceObject1 > timeDifferenceObject2){
                    return -1;
                }else if(timeDifferenceObject1 < timeDifferenceObject2){
                    return 1;
                }
                return 0;

            }
        });
        Collections.reverse(sortedNoteList);
        return sortedNoteList;
    }
}
