package com.bolnizar.itfest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.classes.ClassesAdapter;
import com.bolnizar.itfest.classes.ClassesPresenter;
import com.bolnizar.itfest.classes.ClassesView;
import com.bolnizar.itfest.data.models.*;
import com.bolnizar.itfest.data.models.Class;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTextChanged;
import timber.log.Timber;

public class ClassesActivity extends BaseActivity implements ClassesView {
    @BindView(R.id.classes_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.classes_empty)
    View mEmpty;
    private ClassesPresenter mClassesPresenter;
    private ClassesAdapter mClassesAdapter = new ClassesAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);
        setTitle(getString(R.string.search_classes));
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mClassesAdapter);
        mClassesPresenter = new ClassesPresenter(this, this);
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
}
