package ru.kulikovman.skbkonturtest;

import android.app.Application;

import ru.kulikovman.skbkonturtest.di.AppComponent;
import ru.kulikovman.skbkonturtest.di.DaggerAppComponent;
import ru.kulikovman.skbkonturtest.di.module.ContextModule;


public class App extends Application {

    private static AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        // Подключаем Dagger
        component = DaggerAppComponent.builder()
                .contextModule(new ContextModule(getApplicationContext()))
                .build();
    }

    public static AppComponent getComponent() {
        return component;
    }
}
