package ru.kulikovman.skbkonturtest.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.kulikovman.skbkonturtest.data.model.Contact;

public interface TestApi {

    @GET("generated-01.json")
    Call<List<Contact>> getFirstPart();

    @GET("generated-02.json")
    Call<List<Contact>> getSecondPart();

    @GET("generated-03.json")
    Call<List<Contact>> getThirdPart();

}
