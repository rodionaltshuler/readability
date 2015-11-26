package com.ottamotta.readability;

import android.app.Application;
import android.support.annotation.NonNull;

import com.facebook.stetho.Stetho;

public class ReadabilityApp extends Application {

    private static ReadabilityApp instance;

    public static synchronized @NonNull ReadabilityApp getInstance() {
        return instance;
    }

    public ReadabilityApp() {
        instance = this;
    }

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
