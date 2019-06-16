package ru.kulikovman.skbkonturtest.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.kulikovman.skbkonturtest.data.model.Contact;

public interface ApiInterface {

    @GET("generated-01.json")
    Call<List<Contact>> getFirstPartContacts();

}
