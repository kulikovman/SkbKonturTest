package ru.kulikovman.skbkonturtest.di;

import javax.inject.Singleton;

import dagger.Component;
import ru.kulikovman.skbkonturtest.di.module.ContextModule;
import ru.kulikovman.skbkonturtest.di.module.DatabaseModule;
import ru.kulikovman.skbkonturtest.di.module.NetworkModule;
import ru.kulikovman.skbkonturtest.di.module.RepositoryModule;

@Singleton
@Component(modules = {RepositoryModule.class, NetworkModule.class, DatabaseModule.class, ContextModule.class})
public interface AppComponent {



}
