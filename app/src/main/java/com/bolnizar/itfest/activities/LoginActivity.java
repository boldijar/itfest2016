package com.bolnizar.itfest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.data.IntegerPreference;
import com.bolnizar.itfest.di.InjectionHelper;
import com.bolnizar.itfest.loginregister.LoginRegisterPresenter;
import com.bolnizar.itfest.loginregister.LoginRegisterView;
import com.bolnizar.itfest.utils.Constants;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity implements LoginRegisterView {

    private static final int REQ_REGISTER = 101;
    @BindView(R.id.register_email)
    EditText mEmail;
    @BindView(R.id.register_password)
    EditText mPassword;

    private LoginRegisterPresenter mLoginRegisterPresenter;
    @Inject
    @Named(Constants.PREF_USER_ID)
    IntegerPreference mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectionHelper.getApplicationComponent(this).inject(this);
        setContentView(R.layout.activity_login);
        setTitle(R.string.login);
        mLoginRegisterPresenter = new LoginRegisterPresenter(this, this);
        ButterKnife.bind(this);

        if (mUserId.get() != -1) {
            showSuccess(true);
        }
    }

    @OnClick(R.id.register_button)
    void onClicked() {
        mLoginRegisterPresenter.login(mEmail.getText().toString(), mPassword.getText().toString());
    }

    @OnClick(R.id.register_register)
    void onClickedRegister() {
        startActivityForResult(new Intent(this, RegisterActivity.class), REQ_REGISTER);
    }

    @Override
    public void showSuccess(boolean success) {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQ_REGISTER) {
            mEmail.setText(data.getStringExtra("email"));
            mPassword.setText(data.getStringExtra("password"));
        }
    }
}
