package ru.kulikovman.skbkonturtest.di.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import ru.kulikovman.skbkonturtest.api.TestApi;
import ru.kulikovman.skbkonturtest.db.dao.ContactDao;
import ru.kulikovman.skbkonturtest.repository.DataRepository;
import ru.kulikovman.skbkonturtest.repository.DatabaseRepository;

@Module(includes = {NetworkModule.class, DatabaseModule.class})
public class RepositoryModule {

    @Singleton
    @Provides
    DataRepository dataRepository(TestApi testApi) {
        return new DataRepository(testApi);
    }

    @Singleton
    @Provides
    DatabaseRepository databaseRepository(ContactDao contactDao) {
        return new DatabaseRepository(contactDao);
    }
}
