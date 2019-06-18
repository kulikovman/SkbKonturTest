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

    private TestApi api;

    public DataRepository(TestApi testApi) {
        api = testApi;
    }

    public LiveData<List<Contact>> getContactList() {
        final List<Contact> contactCollector = new ArrayList<>();
        final MutableLiveData<List<Contact>> contacts = new MutableLiveData<>();

        // Первая часть контактов
        api.getFirstPart().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    contactCollector.addAll(response.body());
                    contacts.setValue(contactCollector);

                    Log.d("myLog", "part1: " + contactCollector.size());
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Log.d("myLog", "Ошибка при getFirstPart / Throwable: " + t.getMessage());
            }
        });

        // Вторая часть контактов
        api.getSecondPart().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    contactCollector.addAll(response.body());
                    contacts.setValue(contactCollector);

                    Log.d("myLog", "part2: " + contactCollector.size());
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Log.d("myLog", "Ошибка при getSecondPart / Throwable: " + t.getMessage());
            }
        });

        // Третья часть контактов
        api.getThirdPart().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    contactCollector.addAll(response.body());
                    contacts.setValue(contactCollector);

                    Log.d("myLog", "part3: " + contactCollector.size());
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Log.d("myLog", "Ошибка при getThirdPart / Throwable: " + t.getMessage());
            }
        });

        return contacts;
    }
}
