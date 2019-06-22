package ru.kulikovman.skbkonturtest.repository;

import android.annotation.SuppressLint;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.android.schedulers.AndroidSchedulers;
import ru.kulikovman.skbkonturtest.api.TestApi;
import ru.kulikovman.skbkonturtest.data.model.Contact;

public class DataRepository {

    public static final int CONNECTION_OK = 100;
    public static final int CONNECTION_PARTLY = 101;
    public static final int NO_CONNECTION = 102;

    private TestApi api;

    private boolean sourceOneStatus;
    private boolean sourceTwoStatus;
    private boolean sourceThreeStatus;

    private MutableLiveData<List<Contact>> result;
    private MutableLiveData<Integer> connectionStatus;

    public DataRepository(TestApi testApi) {
        api = testApi;

        result = new MutableLiveData<>();
        connectionStatus = new MutableLiveData<>();
    }

    @SuppressLint("CheckResult")
    public LiveData<List<Contact>> getContacts() {
        final List<Contact> contactCollector = new ArrayList<>();

        // Статус соединения по умолчанию
        setDefaultConnectionStatus();
        connectionStatus.setValue(CONNECTION_OK);

        // Первая часть контактов
        api.getSourceOne()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contacts -> {
                    putContactsToResult(contacts, contactCollector, sourceOneStatus);
                }, throwable -> {
                    sourceOneStatus = false;
                    updateConnectionStatus();
                    throwable.printStackTrace();
                });

        // Вторая часть контактов
        api.getSourceTwo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contacts -> {
                    putContactsToResult(contacts, contactCollector, sourceTwoStatus);
                }, throwable -> {
                    sourceTwoStatus = false;
                    updateConnectionStatus();
                    throwable.printStackTrace();
                });

        // Третья часть контактов
        api.getSourceThree()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contacts -> {
                    putContactsToResult(contacts, contactCollector, sourceThreeStatus);
                }, throwable -> {
                    sourceThreeStatus = false;
                    updateConnectionStatus();
                    throwable.printStackTrace();
                });

        return result;
    }

    private void putContactsToResult(List<Contact> contacts, List<Contact> contactCollector, boolean souceStatus) {
        if (contacts.size() > 0) {
            // Перекладываем контакты в общий список
            for (Contact c : contacts) {
                contactCollector.add(new Contact(c.getId(), c.getName(), c.getPhone(), c.getHeight(),
                        c.getBiography(), c.getTemperament(), c.getEducationPeriod()));
            }

            Log.d("myLog", "After next source: " + contactCollector.size());

            // Обновляем возвращаемый список
            result.setValue(contactCollector);
        } else {
            souceStatus = false;
            updateConnectionStatus();
        }
    }

    private void setDefaultConnectionStatus() {
        sourceOneStatus = true;
        sourceTwoStatus = true;
        sourceThreeStatus = true;
    }

    private void updateConnectionStatus() {
        // Устанавливает текущий статус соединения
        if (sourceOneStatus && sourceTwoStatus && sourceThreeStatus) {
            connectionStatus.setValue(CONNECTION_OK);
        } else if (!sourceOneStatus && !sourceTwoStatus && !sourceThreeStatus) {
            connectionStatus.setValue(NO_CONNECTION);
        } else {
            connectionStatus.setValue(CONNECTION_PARTLY);
        }
    }

    public LiveData<Integer> getConnectionStatus() {
        return connectionStatus;
    }
}
