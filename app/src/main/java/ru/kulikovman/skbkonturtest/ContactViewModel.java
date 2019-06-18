package ru.kulikovman.skbkonturtest;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import ru.kulikovman.skbkonturtest.data.model.Contact;
import ru.kulikovman.skbkonturtest.repository.DataRepository;

public class ContactViewModel extends ViewModel {

    private DataRepository data;

    private Contact selectedContact;

    public ContactViewModel() {
        data = App.getComponent().getDataRepository();
    }

    public LiveData<List<Contact>> getContactList() {
        return data.getContactList();
    }

    public void selectContact(Contact contact) {
        this.selectedContact = contact;
    }

    public Contact getSelectedContact() {
        return selectedContact;
    }

    public void clearContact() {
        this.selectedContact = null;
    }
}
