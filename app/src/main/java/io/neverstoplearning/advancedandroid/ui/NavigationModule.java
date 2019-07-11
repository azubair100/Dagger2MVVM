package io.neverstoplearning.advancedandroid.ui;

import dagger.Binds;
import dagger.Module;
import io.neverstoplearning.advancedandroid.di.ActivityScope;

@Module
public abstract class NavigationModule {

    @Binds
    @ActivityScope
    abstract ScreenNavigator provideScreenNavigator(DefaultScreenNavigator screenNavigator);
}
