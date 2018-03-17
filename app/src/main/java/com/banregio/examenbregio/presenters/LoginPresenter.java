package com.banregio.examenbregio.presenters;

import android.text.TextUtils;

import com.banregio.examenbregio.R;
import com.banregio.examenbregio.interactors.LoginInteractor;
import com.banregio.examenbregio.interactors.interfaces.ILoginInteractor;
import com.banregio.examenbregio.interfaces.LoginManager;
import com.banregio.examenbregio.net.data.LoginRequest;
import com.banregio.examenbregio.net.data.LoginResponse;
import com.banregio.examenbregio.presenters.interfaces.ILoginPresenter;

/**
 * Created by jordan on 15/03/2018.
 */

public class LoginPresenter implements ILoginPresenter {

    LoginManager loginManager;
    ILoginInteractor interactor;

    LoginRequest loginRequest;

    public LoginPresenter(LoginManager manager) {
        this.loginManager = manager;
        interactor = new LoginInteractor(this);
    }

    @Override
    public void login(LoginRequest request) {
        this.loginRequest = request;
        if (isValidData(request.getUser(), request.getPassword())) {
            //Login Interactor
            interactor.callLoginService();
        } else {
            //mensaje error
            loginManager.showError(R.string.error_empty_login);
        }
    }

    @Override
    public void getSucursales() {
        interactor.callGetSucursales();
    }

    @Override
    public void onSuccessResponse(Object response) {
        LoginResponse loginResponse = (LoginResponse) response;
        if (loginResponse.getUser().equals(loginRequest.getUser()) && loginResponse.getPassword().equals(loginRequest.getPassword())) {
            //success
            loginManager.goToNextActivity();
        } else {
            //fail
            loginManager.showError(R.string.error_login);
        }
    }

    @Override
    public void onFailureResponse() {
        loginManager.showError(R.string.error_generic_ws);
    }

    private boolean isValidData(String user, String password) {
        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(password))
            return false;
        return true;
    }
}


