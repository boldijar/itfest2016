package com.bolnizar.itfest.loginregister;

import android.content.Context;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.base.Presenter;
import com.bolnizar.itfest.data.models.User;
import com.bolnizar.itfest.data.models.UserType;
import com.bolnizar.itfest.di.InjectionHelper;
import com.bolnizar.itfest.utils.Utils;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Musafir on 11/26/2016.
 */
public class LoginRegisterPresenter extends Presenter<LoginRegisterView> {

    @Inject
    LoginRegisterService mLoginRegisterService;

    public LoginRegisterPresenter(LoginRegisterView loginRegisterView, Context context) {
        super(loginRegisterView, context);
        InjectionHelper.getApplicationComponent(context).inject(this);
    }

    public void login(final String email, String password) {
        password = Utils.md5(password);

//        Request request=new Request.Builder().

        mLoginRegisterService.login(String.format("password,eq,%s", password, email))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginRegisterResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getView().showMessage(R.string.unknown_error);
                    }

                    @Override
                    public void onNext(LoginRegisterResponse loginRegisterResponse) {
                        gotResponse(loginRegisterResponse, email);
                    }
                });
    }

    private void gotResponse(LoginRegisterResponse loginRegisterResponse, String email) {
        if (loginRegisterResponse != null && loginRegisterResponse.users != null && loginRegisterResponse.users.size() != 0) {
            for (User user : loginRegisterResponse.users) {
                if (email.equals(user.email)) {
                    getView().showMessage(R.string.login_success);
                    getView().showSuccess(true);
                    return;
                }
            }

        }
        getView().showMessage(R.string.invalid_login);

    }

    public void register(final String email, final String password) {
        mLoginRegisterService.login(String.format("email,eq,%s", email))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginRegisterResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getView().showMessage(R.string.unknown_error);
                    }

                    @Override
                    public void onNext(LoginRegisterResponse loginRegisterResponse) {
                        gotRegisterResponse(loginRegisterResponse, email, Utils.md5(password));
                    }
                });
    }

    private void gotRegisterResponse(LoginRegisterResponse loginRegisterResponse, String email, String password) {
        if (loginRegisterResponse != null && loginRegisterResponse.users != null) {
            if (loginRegisterResponse.users.size() != 0) {
                getView().showMessage(R.string.email_used);
                return;
            }
            doRegister(email, password);
            return;
        }
        getView().showMessage(R.string.unknown_error);
    }

    private void doRegister(String email, String password) {
        mLoginRegisterService.addUser(email, password, UserType.TYPE_NORMAL)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        getView().showMessage(R.string.unknown_error);
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Integer userId) {
                        if (userId == null) {
                            getView().showMessage(R.string.unknown_error);
                            return;
                        }
                        getView().showMessage(R.string.register_success);
                        getView().showSuccess(true);
                    }
                });
    }
}
