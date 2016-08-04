package com.example.mudit.notebook;

import android.app.Application;

import com.example.mudit.notebook.di.components.AppComponent;
import com.example.mudit.notebook.di.components.DaggerAppComponent;
import com.example.mudit.notebook.di.modules.AppModule;
import com.example.mudit.notebook.di.modules.DataModule;
import com.example.mudit.notebook.utils.AppConstants;

import javax.inject.Inject;

/**
 * Created by mudit on 3/8/16.
 */
public class NotebookApp extends Application {

    private AppComponent mAppComponent = createAppComponent();

    private final String TAG = NotebookApp.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        getAppComponent().inject(this);
    }

    protected AppComponent createAppComponent() {
        return DaggerAppComponent.builder()
                .dataModule(new DataModule(this, AppConstants.DB_NAME))
                .appModule(new AppModule(this))
                .build();
    }


    public AppComponent getAppComponent() {
        return mAppComponent;
    }

}
