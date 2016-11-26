package com.bolnizar.itfest.comments;

import android.content.Context;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.base.Presenter;
import com.bolnizar.itfest.data.IntegerPreference;
import com.bolnizar.itfest.di.InjectionHelper;
import com.bolnizar.itfest.utils.Constants;

import javax.inject.Inject;
import javax.inject.Named;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Musafir on 11/26/2016.
 */
public class CommentsPresenter extends Presenter<CommentsView> {

    @Inject
    CommentsService mCommentsService;

    @Inject
    @Named(Constants.PREF_USER_ID)
    IntegerPreference mUserId;

    public CommentsPresenter(CommentsView commentsView, Context context) {
        super(commentsView, context);
        InjectionHelper.getApplicationComponent(context).inject(this);
    }

    public void loadComments(int eventId) {
        mCommentsService.getComments("eventId,eq," + eventId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CommentResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        getView().showMessage(R.string.unknown_error);
                    }

                    @Override
                    public void onNext(CommentResponse commentResponse) {
                        getView().showComments(commentResponse.comments);
                    }
                });
    }

    public void addComment(String text, int eventId) {
        mCommentsService.addComment(mUserId.get(),
                eventId,
                text).subscribeOn(Schedulers.io())
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
                        getView().addSuccess();
                    }
                });
    }
}
