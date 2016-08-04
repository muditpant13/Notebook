package com.example.mudit.notebook.di.modules;

import android.content.Context;
import android.util.Log;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Manager;
import com.couchbase.lite.android.AndroidContext;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mudit on 3/8/16.
 */
@Module
public class DataModule {
    private String mDatabaseName;
    private Context mContext;

    private static final String TAG = DataModule.class.getName();

    public DataModule(Context context, String database) {

        if (!Manager.isValidDatabaseName(database)) {
            throw new IllegalArgumentException("Invalid database name: " + database);
        }
        this.mContext = context;
        this.mDatabaseName = database;
    }

    @Singleton
    @Provides
    Manager provideManager() {
        Manager manager = null;
        try {
            manager = new Manager(new AndroidContext(mContext), Manager.DEFAULT_OPTIONS);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  manager;
    }

    @Singleton
    @Provides
    Database provideDatabase (Manager manager) {

        Database database = null;
        try {
            database = manager.getDatabase(mDatabaseName);
        } catch (CouchbaseLiteException e) {
           Log.d(TAG,"Exception thrown creating database");
            e.printStackTrace();
        }
        return database;
    }
}
