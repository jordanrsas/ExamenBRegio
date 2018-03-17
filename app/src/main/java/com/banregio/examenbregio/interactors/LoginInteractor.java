package com.banregio.examenbregio.interactors;

import com.banregio.examenbregio.interactors.interfaces.ILoginInteractor;
import com.banregio.examenbregio.net.APIClient;
import com.banregio.examenbregio.net.IApiClient;
import com.banregio.examenbregio.net.data.LoginResponse;
import com.banregio.examenbregio.net.data.Sucursal;
import com.banregio.examenbregio.presenters.interfaces.ILoginPresenter;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jordan on 15/03/2018.
 */

public class LoginInteractor implements ILoginInteractor {

    private final IApiClient apiClient;
    ILoginPresenter presenter;

    public LoginInteractor(ILoginPresenter iLoginPresenter) {
        this.presenter = iLoginPresenter;
        apiClient = APIClient.getClient().create(IApiClient.class);
    }

    @Override
    public void callLoginService() {
        apiClient.login().enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                presenter.onSuccessResponse(response.body());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                presenter.onFailureResponse();
            }
        });
    }

    @Override
    public void callGetSucursales() {
        apiClient.getSucursales().enqueue(new Callback<List<Sucursal>>() {
            @Override
            public void onResponse(Call<List<Sucursal>> call, Response<List<Sucursal>> response) {
                response.body();
            }

            @Override
            public void onFailure(Call<List<Sucursal>> call, Throwable t) {
                call.request();
            }
        });
    }
}
