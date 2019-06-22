package ru.kulikovman.skbkonturtest.api;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import ru.kulikovman.skbkonturtest.data.model.Contact;

public interface TestApi {

    @GET("generated-01.json")
    Observable<List<Contact>> getSourceOne();

    @GET("generated-02.json")
    Observable<List<Contact>> getSourceTwo();

    @GET("generated-03.json")
    Observable<List<Contact>> getSourceThree();

}
