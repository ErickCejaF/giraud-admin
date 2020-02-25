package mx.com.giraud.giraudadmin.Rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import static mx.com.giraud.giraudadmin.BuildConfig.BASE_URL;
import static mx.com.giraud.giraudadmin.Utils.Utils.getSavedUser;


public class ApiRestAdapter {
    private OkHttpClient okHttpClient;

    public ApiRestTasks getApiRest() {
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    Response origResponse = chain.proceed(request);
                    String responseStringOrig = origResponse.body().string();
                    return origResponse
                            .newBuilder()
                            .body(ResponseBody.create(origResponse.body().contentType(), responseStringOrig))
                            .build();
                })
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(ApiRestTasks.class);
    }

    public ApiRestTasks getAuthenticatedApiRest() {

        String authToken = getSavedUser().getAuthorization();

        System.out.println("auth token " + authToken);

        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request.Builder request = chain.request().newBuilder()
                            .addHeader("Accept", "application/json")
                            .addHeader("Authorization", authToken);
                    Response origResponse = chain.proceed(request.build());
                    String responseStringOrig = origResponse.body().string();
                    return origResponse
                            .newBuilder()
                            .body(ResponseBody.create(origResponse.body().contentType(), responseStringOrig))
                            .build();
                })
                .build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(ApiRestTasks.class);

    }

}
