package ru.kulikovman.skbkonturtest.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import ru.kulikovman.skbkonturtest.data.model.Contact;
import ru.kulikovman.skbkonturtest.db.dao.ContactDao;

@Database(entities = {Contact.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract ContactDao contactDao();

}
