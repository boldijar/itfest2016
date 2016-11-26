package com.bolnizar.itfest.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.loginregister.LoginRegisterPresenter;
import com.bolnizar.itfest.loginregister.LoginRegisterView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity implements LoginRegisterView {

    @BindView(R.id.register_email)
    EditText mEmail;
    @BindView(R.id.register_password)
    EditText mPassword;

    private LoginRegisterPresenter mLoginRegisterPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle(getString(R.string.register_new_acc));
        mLoginRegisterPresenter = new LoginRegisterPresenter(this, this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.register_button)
    void onRegister() {
        mLoginRegisterPresenter.register(mEmail.getText().toString(), mPassword.getText().toString());
    }

    @Override
    public void showSuccess(boolean success) {
        if (success) {
            Intent intent = new Intent();
            intent.putExtra("email", mEmail.getText().toString());
            intent.putExtra("password", mPassword.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
