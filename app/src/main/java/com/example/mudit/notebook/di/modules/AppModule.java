package com.example.mudit.notebook.di.modules;

import android.app.Application;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by mudit on 3/8/16.
 */
@Module
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    //provide application inject this where ever required
    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }

    //use where object mapper is required to convert json to pojo and vice versa
    @Provides
    ObjectMapper providesDataMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return  mapper;
    }
}
