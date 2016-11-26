package com.bolnizar.itfest.modpanel;

import android.content.Context;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.base.Presenter;
import com.bolnizar.itfest.data.models.User;
import com.bolnizar.itfest.data.models.UserType;
import com.bolnizar.itfest.di.InjectionHelper;
import com.bolnizar.itfest.loginregister.LoginRegisterResponse;

import javax.inject.Inject;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Musafir on 11/26/2016.
 */
public class ModeratorPanelPresenter extends Presenter<ModeratorPanelView> {

    @Inject
    ModeratorPanelService mModeratorPanelService;

    public ModeratorPanelPresenter(ModeratorPanelView moderatorPanelView, Context context) {
        super(moderatorPanelView, context);
        InjectionHelper.getApplicationComponent(context).inject(this);
    }

    public void makeUserModerator(String email) {
        mModeratorPanelService.getUsers("email,eq," + email)
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
                        if (loginRegisterResponse != null && loginRegisterResponse.users != null) {
                            if (loginRegisterResponse.users.size() == 0) {
                                getView().showMessage(R.string.email_not_found);
                                return;
                            }
                            makeMod(loginRegisterResponse.users.get(0));
                            return;
                        }
                        getView().showMessage(R.string.unknown_error);
                    }
                });
    }

    private void makeMod(User user) {
        if (UserType.TYPE_MODERATOR.equals(user.type)) {
            getView().showMessage(R.string.user_is_mod);
            return;
        }
        mModeratorPanelService.changeUserType(user.id, UserType.TYPE_MODERATOR)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getView().showMessage(R.string.unknown_error);
                    }

                    @Override
                    public void onNext(Integer integer) {
                        if (integer == null) {
                            getView().showMessage(R.string.unknown_error);
                            return;
                        }
                        getView().showMessage(R.string.gave_access);
                        getView().showAccessAdded();
                    }
                });
    }
}
