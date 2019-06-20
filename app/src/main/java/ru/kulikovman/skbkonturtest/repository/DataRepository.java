package ru.kulikovman.skbkonturtest.repository;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

    private MutableLiveData<Integer> connectionStatus = new MutableLiveData<>();

    public DataRepository(TestApi testApi) {
        api = testApi;
    }

    public LiveData<List<Contact>> getContacts() {
        final List<Contact> contactCollector = new ArrayList<>();
        final MutableLiveData<List<Contact>> contacts = new MutableLiveData<>();

        // Статус соединения по умолчанию
        setDefaultConnectionStatus();
        connectionStatus.setValue(CONNECTION_OK);

        // Первая часть контактов
        api.getSourceOne().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    contactCollector.addAll(response.body());
                    contacts.setValue(contactCollector);

                    Log.d("myLog", "After source 1: " + contactCollector.size());
                } else {
                    sourceOneStatus = false;
                    updateConnectionStatus();
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Log.d("myLog", "Ошибка при getSourceOne / Throwable: " + t.getMessage());
                sourceOneStatus = false;
                updateConnectionStatus();
            }
        });

        // Вторая часть контактов
        api.getSourceTwo().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    contactCollector.addAll(response.body());
                    contacts.setValue(contactCollector);

                    Log.d("myLog", "After source 2: " + contactCollector.size());
                } else {
                    sourceTwoStatus = false;
                    updateConnectionStatus();
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Log.d("myLog", "Ошибка при getSourceTwo / Throwable: " + t.getMessage());
                sourceTwoStatus = false;
                updateConnectionStatus();
            }
        });

        // Третья часть контактов
        api.getSourceThree().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    contactCollector.addAll(response.body());
                    contacts.setValue(contactCollector);

                    Log.d("myLog", "After source 3: " + contactCollector.size());
                } else {
                    sourceThreeStatus = false;
                    updateConnectionStatus();
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Log.d("myLog", "Ошибка при getSourceThree / Throwable: " + t.getMessage());
                sourceThreeStatus = false;
                updateConnectionStatus();
            }
        });

        return contacts;
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
