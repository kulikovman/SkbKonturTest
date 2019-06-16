package ru.kulikovman.skbkonturtest;

import androidx.lifecycle.ViewModel;
import ru.kulikovman.skbkonturtest.repository.DataRepository;

public class ContactViewModel extends ViewModel {

    private DataRepository data;

    public ContactViewModel() {
        data = App.getComponent().getDataRepository();
    }

    public void getContactList() {
        data.getContactList();
    }
}
