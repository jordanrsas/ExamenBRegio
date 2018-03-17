package com.banregio.examenbregio.presenters.interfaces;

import com.banregio.examenbregio.net.data.LoginRequest;

/**
 * Created by jordan on 15/03/2018.
 */

public interface ILoginPresenter {

    void login(LoginRequest loginRequest);

    void getSucursales();

    void onSuccessResponse(Object response);

    void onFailureResponse();
}
