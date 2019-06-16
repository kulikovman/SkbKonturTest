package ru.kulikovman.skbkonturtest.repository;

import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.kulikovman.skbkonturtest.api.ApiInterface;
import ru.kulikovman.skbkonturtest.data.model.Contact;

public class DataRepository {

    private ApiInterface api;

    public DataRepository(ApiInterface apiInterface) {
        api = apiInterface;
    }


    public void requestContactList() {
        api.getFirstPartContacts().enqueue(new Callback<List<Contact>>() {
            @Override
            public void onResponse(Call<List<Contact>> call, Response<List<Contact>> response) {
                List<Contact> contacts = response.body();

                if (contacts != null) {
                    Log.d("myLog", "Получен список контактов: " + contacts.size());
                } else {
                    Log.d("myLog", "Получен пустой список контактов...");
                }
            }

            @Override
            public void onFailure(Call<List<Contact>> call, Throwable t) {
                Log.d("myLog", "Ошибка при getFirstPartContacts / Throwable: " + t.getMessage());
            }
        });
    }
}
