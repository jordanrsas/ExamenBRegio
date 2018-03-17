package com.banregio.examenbregio.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.banregio.examenbregio.App;
import com.banregio.examenbregio.R;
import com.banregio.examenbregio.interfaces.DialogDoubleActions;
import com.banregio.examenbregio.interfaces.UserManager;
import com.banregio.examenbregio.utils.ImageUtils;
import com.banregio.examenbregio.utils.SharedPrefsManager;
import com.banregio.examenbregio.utils.UI;
import com.banregio.examenbregio.utils.Utils;
import com.banregio.examenbregio.utils.ValidatePermissions;
import com.banregio.examenbregio.utils.ViewUtils;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.banregio.examenbregio.utils.SharedPreferencesKeys.PHOTO_PATH;
import static com.banregio.examenbregio.utils.SharedPreferencesKeys.USER_BIRTHDAY;
import static com.banregio.examenbregio.utils.SharedPreferencesKeys.USER_LASTNAME;
import static com.banregio.examenbregio.utils.SharedPreferencesKeys.USER_NAME;

/**
 * Created by jordan on 16/03/2018.
 */

public class UserActivity extends AppCompatActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, View.OnFocusChangeListener, UserManager {
    String mCurrentPhotoPath;
    /*Permissions*/
    public static final int PERMISSION_GENERAL = 111;
    static final int REQUEST_IMAGE_CAPTURE = 211;


    @BindView(R.id.imageProfile)
    CircleImageView imageUser;
    @BindView(R.id.btnSave)
    Button btnSave;
    @BindView(R.id.editBirthdaty)
    EditText editBirthdaty;
    @BindView(R.id.layOutBirthDay)
    TextInputLayout layOutBirthDay;
    @BindView(R.id.editName)
    EditText editName;
    @BindView(R.id.editLastName)
    EditText editLastName;
    @BindView(R.id.editDirection)
    EditText editDirection;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        App.getInstance();
        SharedPrefsManager.initialize(this);
        ButterKnife.bind(this);
        checkPermissions();

        imageUser.setOnClickListener(this);
        btnSave.setOnClickListener(this);

        editBirthdaty.setInputType(InputType.TYPE_NULL);
        editBirthdaty.setOnClickListener(this);
        editBirthdaty.setOnFocusChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageProfile:
                dispatchTakePictureIntent();
                break;
            case R.id.editBirthdaty:
                UI.showDialogPicker(UserActivity.this, UserActivity.this);
                break;
            case R.id.btnSave:
                saveDataUser();
                break;
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            ViewUtils.hideKeyBoard(UserActivity.this);
            UI.showDialogPicker(UserActivity.this, UserActivity.this);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = ImageUtils.createImageFile();
                mCurrentPhotoPath = photoFile.getPath();
            } catch (IOException ex) {
                ex.printStackTrace();
            }


            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.banregio.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            ImageUtils.setPic(mCurrentPhotoPath, imageUser);
        }
    }


    protected void checkPermissions() {
        if (!ValidatePermissions.isAllPermissionsActives(this, ValidatePermissions.getPermissionsCheck())) {
            ValidatePermissions.checkPermissions(this, ValidatePermissions.getPermissionsCheck(), PERMISSION_GENERAL);
        }
    }

    private void saveDataUser() {
        String name = editName.getText().toString().trim();
        String lastName = editLastName.getText().toString().trim();
        String birthday = editBirthdaty.getText().toString().trim();
        String direction = editDirection.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            showError(R.string.error_name);
            editName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(lastName)) {
            showError(R.string.error_lastname);
            editLastName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(birthday)) {
            showError(R.string.error_birthday);
            editBirthdaty.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(direction)) {
            showError(R.string.error_direction);
            editDirection.requestFocus();
            return;
        }

        if (!TextUtils.isEmpty(mCurrentPhotoPath)) {
            SharedPrefsManager.getInstance().setString(PHOTO_PATH, mCurrentPhotoPath);
        }

        SharedPrefsManager.getInstance().setString(USER_NAME, name);
        SharedPrefsManager.getInstance().setString(USER_LASTNAME, lastName);
        SharedPrefsManager.getInstance().setString(USER_BIRTHDAY, birthday);

        goToNextActivity();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        editBirthdaty.setText(Utils.getBirthDayDateFormat(year, month, day));
    }

    @Override
    public void showError(int msg) {
        UI.createSimpleCustomDialog(getString(msg), getSupportFragmentManager());
    }

    @Override
    public void goToNextActivity() {
        UI.createSimpleCustomDialog(getString(R.string.save_user_success), getSupportFragmentManager(), new DialogDoubleActions() {
            @Override
            public void actionConfirm(Object... params) {
                //Intent intent = new Intent()
                //Toast.makeText(UserActivity.this, "Ir siguiente activity", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(UserActivity.this, MainActivity.class);
                startActivity(intent);
                UserActivity.this.finish();
            }

            @Override
            public void actionCancel(Object... params) {

            }
        }, true, false);
    }
}
