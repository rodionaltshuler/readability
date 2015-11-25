package com.ottamotta.readability;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class ReadabilityApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initStetho();
    }

    private void initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initialize(
                    Stetho.newInitializerBuilder(this)
                            .enableDumpapp(
                                    Stetho.defaultDumperPluginsProvider(this))
                            .enableWebKitInspector(
                                    Stetho.defaultInspectorModulesProvider(this))
                            .build());
        }
    }
}
