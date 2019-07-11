package io.neverstoplearning.advancedandroid.base;

import android.app.Application;

import javax.inject.Inject;

import io.neverstoplearning.advancedandroid.BuildConfig;
import io.neverstoplearning.advancedandroid.di.ActivityInjector;
import timber.log.Timber;

public class MyApplication extends Application {

    @Inject ActivityInjector activityInjector;

    protected ApplicationComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        component = initComponent();
        component.inject(this);

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }

    protected ApplicationComponent initComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ActivityInjector getActivityInjector() {
        return activityInjector;
    }
}
