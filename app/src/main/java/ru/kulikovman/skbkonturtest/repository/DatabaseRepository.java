package ru.kulikovman.skbkonturtest.repository;


import java.util.List;

import androidx.lifecycle.LiveData;
import ru.kulikovman.skbkonturtest.data.model.Contact;
import ru.kulikovman.skbkonturtest.data.model.SimpleContact;
import ru.kulikovman.skbkonturtest.db.dao.ContactDao;

public class DatabaseRepository {

    private ContactDao contactDao;

    public DatabaseRepository(ContactDao contactDao) {
        this.contactDao = contactDao;
    }

    public LiveData<List<SimpleContact>> getContacts() {
        return contactDao.getAllSimpleContacts();
    }

    public LiveData<List<SimpleContact>> getContactsByName(String query) {
        return contactDao.getSimpleContactsByName(query);
    }

    public LiveData<List<SimpleContact>> getContactsByPhone(String query) {
        return contactDao.getSimpleContactsByPhone(query);
    }

    public LiveData<Contact> getContactById(String id) {
        return contactDao.getContactById(id);
    }

    public void saveContactList(List<Contact> contacts) {
        contactDao.insert(contacts);
    }
}
