package com.banregio.examenbregio.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.banregio.examenbregio.R;
import com.banregio.examenbregio.interfaces.LoginManager;
import com.banregio.examenbregio.net.data.LoginRequest;
import com.banregio.examenbregio.presenters.LoginPresenter;
import com.banregio.examenbregio.utils.ProgressLayout;
import com.banregio.examenbregio.utils.SharedPreferencesKeys;
import com.banregio.examenbregio.utils.SharedPrefsManager;
import com.banregio.examenbregio.utils.UI;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jordan on 15/03/2018.
 */

public class LoginActivity extends AppCompatActivity implements LoginManager, View.OnClickListener {


    @BindView(R.id.txtUser)
    EditText txtUser;
    @BindView(R.id.txtPassword)
    EditText txtPassword;
    @BindView(R.id.btnLogin)
    Button btnLogin;
    @BindView(R.id.imgFindSucursales)
    ImageView imgFindSucursales;

    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginPresenter = new LoginPresenter(this);
        SharedPrefsManager.initialize(this);
        ButterKnife.bind(this);

        btnLogin.setOnClickListener(this);
        imgFindSucursales.setOnClickListener(this);

        //ProgressLayout.show(this);
        //loginPresenter.getSucursales();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == imgFindSucursales.getId()) {
            //Toast.makeText(this, "Mostrar sucursales", Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this, SucursalesActivity.class));
        } else {
            ProgressLayout.show(LoginActivity.this);
            loginPresenter.login(new LoginRequest(txtUser.getText().toString().trim(), txtPassword.getText().toString().trim()));
        }
    }

    @Override
    public void showError(@StringRes int msg) {
        ProgressLayout.hide();
        UI.createSimpleCustomDialog(getString(msg), getSupportFragmentManager());
    }

    @Override
    public void goToNextActivity() {
        ProgressLayout.hide();
        SharedPrefsManager.getInstance().setBoolean(SharedPreferencesKeys.IS_LOGGED, true);
        //Toast.makeText(this, "Next Activity", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
        this.finish();
    }
}
