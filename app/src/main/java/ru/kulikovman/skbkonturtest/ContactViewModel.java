package ru.kulikovman.skbkonturtest;

import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import ru.kulikovman.skbkonturtest.data.model.Contact;
import ru.kulikovman.skbkonturtest.data.model.SimpleContact;
import ru.kulikovman.skbkonturtest.repository.DataRepository;
import ru.kulikovman.skbkonturtest.repository.DatabaseRepository;

public class ContactViewModel extends ViewModel {

    private DataRepository data;
    private DatabaseRepository database;

    private SimpleContact selectedContact;
    private long lastUpdate = 0;

    public ContactViewModel() {
        data = App.getComponent().getDataRepository();
        database = App.getComponent().getDatabaseRepository();
    }

    public LiveData<List<SimpleContact>> getContactsFromDatabase() {
        return database.getSimpleContacts();
    }

    public LiveData<List<Contact>> getContactsFromServer() {
        return data.getContacts();
    }

    public void updateContacts(List<Contact> contacts) {
        lastUpdate = System.currentTimeMillis();
        database.saveContactList(contacts);
    }

    public boolean isNeedUpdateContacts() {
        long minute = TimeUnit.MINUTES.toMillis(1);
        return System.currentTimeMillis() - lastUpdate > minute;
    }

    public void selectContact(SimpleContact simpleContact) {
        this.selectedContact = simpleContact;
    }

    public LiveData<Contact> getSelectedContact() {
        return database.getContactById(selectedContact.getId());
    }

    public void clearContact() {
        this.selectedContact = null;
    }
}
