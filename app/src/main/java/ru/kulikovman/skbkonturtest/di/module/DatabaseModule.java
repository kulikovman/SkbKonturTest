package ru.kulikovman.skbkonturtest.di.module;

import android.content.Context;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import ru.kulikovman.skbkonturtest.db.AppDatabase;
import ru.kulikovman.skbkonturtest.db.dao.ContactDao;

@Module(includes = ContextModule.class)
public class DatabaseModule {

    @Singleton
    @Provides
    AppDatabase appDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "database")
                .fallbackToDestructiveMigration() // обнуляет базу, если нет подходящей миграции
                //.addMigrations(AppDatabase.MIGRATION_4_5, AppDatabase.MIGRATION_5_6)
                .build();
    }

    @Provides
    ContactDao contactDao(AppDatabase appDatabase) {
        return appDatabase.contactDao();
    }
}
