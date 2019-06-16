package ru.kulikovman.skbkonturtest.di.module;

import android.content.Context;
import android.util.Log;

import java.io.File;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.kulikovman.skbkonturtest.BuildConfig;
import ru.kulikovman.skbkonturtest.api.TestApi;

@Module(includes = ContextModule.class)
public class NetworkModule {

    @Provides
    public TestApi testApi(Retrofit retrofit) {
        return retrofit.create(TestApi.class);
    }

    @Provides
    public Retrofit retrofit(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory) {
        return new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://raw.githubusercontent.com/SkbkonturMobile/mobile-test-droid/master/json/")
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @Provides
    public GsonConverterFactory gsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Provides
    public OkHttpClient okHttpClient(Cache cache, HttpLoggingInterceptor httpLoggingInterceptor) {
        return new OkHttpClient()
                .newBuilder()
                //.cache(cache)
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Provides
    public Cache cache(File cacheFile) {
        return new Cache(cacheFile, 10 * 1000 * 1000); // 10 MB
    }

    @Provides
    public File file(Context context) {
        File file = new File(context.getCacheDir(), "HttpCache");
        file.mkdirs();
        return file;
    }

    @Provides
    public HttpLoggingInterceptor httpLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d("HttpLoggingInterceptor", message);
            }
        });

        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return httpLoggingInterceptor;
    }
}
