package ru.kulikovman.skbkonturtest.repository;


import ru.kulikovman.skbkonturtest.db.AppDatabase;

public class DatabaseRepository {

    private final AppDatabase database;

    public DatabaseRepository(AppDatabase appDatabase) {
        this.database = appDatabase;
    }



}
