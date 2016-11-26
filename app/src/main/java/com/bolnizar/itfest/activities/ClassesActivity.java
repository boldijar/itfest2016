package com.bolnizar.itfest.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.classes.ClassesAdapter;
import com.bolnizar.itfest.classes.ClassesPresenter;
import com.bolnizar.itfest.classes.ClassesView;
import com.bolnizar.itfest.classes.SubscriptionsPresenter;
import com.bolnizar.itfest.classes.SubscriptionsView;
import com.bolnizar.itfest.data.BooleanPreference;
import com.bolnizar.itfest.data.models.Class;
import com.bolnizar.itfest.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;

public class ClassesActivity extends BaseActivity implements ClassesView, SubscriptionsView, ClassesAdapter.OnClassClick {
    @BindView(R.id.classes_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.classes_empty)
    View mEmpty;

    private ClassesPresenter mClassesPresenter;
    private ClassesAdapter mClassesAdapter = new ClassesAdapter(this);
    private SubscriptionsPresenter mSubscriptionsPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        setTitle(getString(R.string.search_classes));
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mClassesAdapter);
        mClassesPresenter = new ClassesPresenter(this, this);
        mSubscriptionsPresenter = new SubscriptionsPresenter(this, this);
        mSubscriptionsPresenter.loadSubs();
        showClasses(new ArrayList<Class>());
    }

    @OnTextChanged(R.id.classes_name)
    void classChanged(CharSequence charSequence) {
        mClassesPresenter.inputChanged(charSequence.toString());
    }

    @Override
    public void showClasses(List<Class> classes) {
        mEmpty.setVisibility(classes.size() != 0 ? View.GONE : View.VISIBLE);
        mClassesAdapter.setClasses(classes);
    }

    @Override
    public void classButtonclicked(Class clas, boolean wantsToSubscribe) {
        if (wantsToSubscribe) {
            mSubscriptionsPresenter.subscribe(clas.id);
        } else {
            mSubscriptionsPresenter.unsubscribe(clas.id);
        }
    }

    @Override
    public void classClicked(Class clas) {
        startActivity(EventsActivity.createIntent(this, clas.id, clas.schoolId));
    }

    @Override
    public void subsWereUpdated() {
        mClassesAdapter.updateSubs();
    }
}
