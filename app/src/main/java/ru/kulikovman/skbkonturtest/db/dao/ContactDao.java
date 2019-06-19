package ru.kulikovman.skbkonturtest.db.dao;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import ru.kulikovman.skbkonturtest.data.model.Contact;
import ru.kulikovman.skbkonturtest.data.model.SimpleContact;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM Contact ORDER BY name ASC")
    LiveData<List<Contact>> getAllContacts();

    @Query("SELECT id, name, phone, height FROM Contact ORDER BY name ASC")
    LiveData<List<SimpleContact>> getAllSimpleContacts();

    @Query("SELECT id, name, phone, height FROM Contact WHERE name LIKE '%' || :query || '%' ORDER BY name ASC")
    LiveData<List<SimpleContact>> getSimpleContactsByQuery(String query);

    @Query("SELECT * FROM Contact WHERE id = :id")
    LiveData<Contact> getContactById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Contact> contacts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Contact contact);

    @Update
    void update(Contact contact);

    @Delete
    void delete(Contact contact);

}
