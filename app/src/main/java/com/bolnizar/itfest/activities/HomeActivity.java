package com.bolnizar.itfest.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.data.BooleanPreference;
import com.bolnizar.itfest.data.IntegerPreference;
import com.bolnizar.itfest.di.InjectionHelper;
import com.bolnizar.itfest.events.EventPagerAdapter;
import com.bolnizar.itfest.events.SubscribedEventRecord;
import com.bolnizar.itfest.persistance.SubscriptionRecord;
import com.bolnizar.itfest.subscriptionnextevent.NextEventPresenter;
import com.bolnizar.itfest.subscriptionnextevent.NextEventView;
import com.bolnizar.itfest.utils.Constants;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.RandomTransitionGenerator;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class HomeActivity extends AppCompatActivity implements NextEventView {

    @BindView(R.id.home_add_classes)
    View mAddClass;
    @BindView(R.id.home_ken)
    KenBurnsView mKenBurnsView;
    @Inject
    @Named(Constants.PREF_USER_MODERATOR)
    BooleanPreference mUserModerator;
    @Inject
    @Named(Constants.PREF_USER_ID)
    IntegerPreference mUserId;

    @BindView(R.id.pager)
    ViewPager mViewPager;
    private NextEventPresenter mNextEventPresenter;

    private EventPagerAdapter mEventPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectionHelper.getApplicationComponent(this).inject(this);
        Timber.d("User is moderator ? " + mUserModerator.get());
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        mAddClass.setVisibility(mUserModerator.get() ? View.VISIBLE : View.GONE);
        setTitle(R.string.app_name);
        mNextEventPresenter = new NextEventPresenter(this, this);
        mNextEventPresenter.load();

        mEventPagerAdapter = new EventPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mEventPagerAdapter);
        mEventPagerAdapter.update();
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
        } else {
            getMenuInflater().inflate(R.menu.home_normal, menu);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_start_moderator_panel) {
            startActivity(new Intent(this, ModeratorPanelActivity.class));
            return true;
        }
        if (item.getItemId() == R.id.action_logout) {
            SubscriptionRecord.deleteAll(SubscriptionRecord.class);
            SubscribedEventRecord.deleteAll(SubscribedEventRecord.class);
            mUserId.set(-1);
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void eventsSaved() {
        Timber.d("Saved events: " + SubscribedEventRecord.listAll(SubscribedEventRecord.class));
        mEventPagerAdapter.update();
    }
}
