package ru.kulikovman.skbkonturtest.repository;


import java.util.List;

import androidx.lifecycle.LiveData;
import ru.kulikovman.skbkonturtest.data.model.Contact;
import ru.kulikovman.skbkonturtest.db.dao.ContactDao;

public class DatabaseRepository {

    private ContactDao contactDao;

    public DatabaseRepository(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public LiveData<List<Contact>> getContacts() {
        return contactDao.getAllContacts();
    }

    public void saveContactList(List<Contact> contacts) {
        contactDao.insert(contacts);
    }
}
