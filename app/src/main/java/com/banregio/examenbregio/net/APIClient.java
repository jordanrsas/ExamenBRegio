package com.banregio.examenbregio.net;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.banregio.examenbregio.utils.Recursos.URL;

/**
 * Created by jordan on 16/03/2018.
 */

public class APIClient {
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
