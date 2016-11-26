package com.bolnizar.itfest.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.data.BooleanPreference;
import com.bolnizar.itfest.di.InjectionHelper;
import com.bolnizar.itfest.utils.Constants;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.home_add_classes)
    View mAddClass;
    @BindView(R.id.home_ken)
    KenBurnsView mKenBurnsView;
    @Inject
    @Named(Constants.PREF_USER_MODERATOR)
    BooleanPreference mUserModerator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectionHelper.getApplicationComponent(this).inject(this);
        Timber.d("User is moderator ? " + mUserModerator.get());
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mAddClass.setVisibility(mUserModerator.get() ? View.VISIBLE : View.GONE);
        setTitle(R.string.app_name);

        startActivity(AddEventToClassActivity.createIntent(this, 3, 1));
    }

    @OnClick(R.id.home_search_classes)
    void search() {
        startActivity(new Intent(this, ClassesActivity.class));
    }

    @OnClick(R.id.home_add_classes)
    void addClass() {
        startActivity(new Intent(this, AddClassActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (mUserModerator.get()) {
            getMenuInflater().inflate(R.menu.home_moderator, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_start_moderator_panel) {
            startActivity(new Intent(this, ModeratorPanelActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
