package com.bolnizar.itfest.base;

import android.content.Context;

/**
 * Created by Musafir on 11/25/2016.
 */
public class Presenter<View> {
    final View mView;
    final Context mContext;

    public Presenter(View view, Context context) {
        this.mView = view;
        this.mContext = context;
    }

    public View getView() {
        return mView;
    }
}
