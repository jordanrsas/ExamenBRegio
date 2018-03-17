package com.banregio.examenbregio.net;

import com.banregio.examenbregio.net.data.LoginResponse;
import com.banregio.examenbregio.net.data.Sucursal;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by jordan on 16/03/2018.
 */

public interface IApiClient {

    @GET("/login")
    Call<LoginResponse> login();

    @GET("/sucursales")
    Call<List<Sucursal>> getSucursales();
}
