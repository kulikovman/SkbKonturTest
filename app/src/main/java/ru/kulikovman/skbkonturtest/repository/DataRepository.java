package ru.kulikovman.skbkonturtest.repository;

import android.annotation.SuppressLint;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ru.kulikovman.skbkonturtest.api.TestApi;
import ru.kulikovman.skbkonturtest.data.model.Contact;

public class DataRepository {

    public static final int CONNECTION_OK = 101;
    public static final int NO_CONNECTION = 102;

    private TestApi api;

    private MutableLiveData<List<Contact>> result;
    private MutableLiveData<Integer> connectionStatus;

    public DataRepository(TestApi testApi) {
        api = testApi;

        result = new MutableLiveData<>();
        connectionStatus = new MutableLiveData<>();
    }

    @SuppressLint("CheckResult")
    public LiveData<List<Contact>> getContacts() {
        // Статус соединения по умолчанию
        connectionStatus.setValue(CONNECTION_OK);

        // Получение и обработка контактов
        Observable.merge(api.getSourceOne(), api.getSourceTwo(), api.getSourceThree())
                .subscribeOn(Schedulers.io())
                .flatMap(Observable::fromIterable)
                .doOnNext(Contact::createClearPhone) // Добавление чистого номера (только цифры)
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(contacts -> result.setValue(contacts),
                        throwable -> {
                            connectionStatus.setValue(NO_CONNECTION);
                            throwable.printStackTrace();
                        });

        return result;
    }

    public LiveData<Integer> getConnectionStatus() {
        return connectionStatus;
    }
}
