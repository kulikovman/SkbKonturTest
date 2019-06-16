package ru.kulikovman.skbkonturtest.repository;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.kulikovman.skbkonturtest.api.TestApi;
import ru.kulikovman.skbkonturtest.data.model.Contact;

public class DataRepository {

    private TestApi api;

    private List<Contact> contacts = new ArrayList<>();

    public DataRepository(TestApi testApi) {
        api = testApi;
    }

    public List<Contact> getContactList() {
        // Первая часть контактов
        api.getFirstPart().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    contacts.addAll(response.body());

                    Log.d("myLog", "Получено контактов: " + contacts.size());
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
                    contacts.addAll(response.body());

                    Log.d("myLog", "Получено контактов: " + contacts.size());
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Log.d("myLog", "Ошибка при getFirstPart / Throwable: " + t.getMessage());
            }
        });

        // Третья часть контактов
        api.getThirdPart().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    contacts.addAll(response.body());

                    Log.d("myLog", "Получено контактов: " + contacts.size());
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Log.d("myLog", "Ошибка при getFirstPart / Throwable: " + t.getMessage());
            }
        });

        return contacts;
    }
}
