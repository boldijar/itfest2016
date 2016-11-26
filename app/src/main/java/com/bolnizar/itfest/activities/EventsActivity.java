package com.bolnizar.itfest.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.data.BooleanPreference;
import com.bolnizar.itfest.data.models.Event;
import com.bolnizar.itfest.di.InjectionHelper;
import com.bolnizar.itfest.events.EventsAdapter;
import com.bolnizar.itfest.events.EventsPresenter;
import com.bolnizar.itfest.events.EventsView;
import com.bolnizar.itfest.utils.Constants;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventsActivity extends BaseActivity implements EventsView, SwipeRefreshLayout.OnRefreshListener, EventsAdapter.EventsListener {

    private static final int REQ_ADD_EVENT = 102;
    @BindView(R.id.events_empty)
    View mEmpty;
    @BindView(R.id.events_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.events_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private static final String ARG_SCHOOL_ID = "SCROLID";
    private static final String ARG_CLASS_ID = "clasid";

    private int mClassId;
    private int mSchoolId;

    private EventsPresenter mEventsPresenter;
    private EventsAdapter mEventsAdapter = new EventsAdapter(this);

    public static Intent createIntent(Context context, int classId, int schoolId) {
        Intent intent = new Intent(context, EventsActivity.class);
        intent.putExtra(ARG_CLASS_ID, classId);
        intent.putExtra(ARG_SCHOOL_ID, schoolId);
        return intent;
    }

    @Inject
    @Named(Constants.PREF_USER_MODERATOR)
    BooleanPreference mUserModerator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        setTitle(getString(R.string.class_events));
        InjectionHelper.getApplicationComponent(this).inject(this);
        mClassId = getIntent().getIntExtra(ARG_CLASS_ID, 0);
        mSchoolId = getIntent().getIntExtra(ARG_SCHOOL_ID, 0);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mEventsAdapter);
        mEventsPresenter = new EventsPresenter(this, this);
        mEventsPresenter.load(mClassId);
        mSwipeRefreshLayout.setOnRefreshListener(this);

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
            startActivityForResult(AddEventToClassActivity.createIntent(this, mClassId, mSchoolId), REQ_ADD_EVENT);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showEvent(List<Event> events) {
        mEventsAdapter.setEvents(events);
        mEmpty.setVisibility(events.size() == 0 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void eventDeleted() {
        mEventsPresenter.load(mClassId);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
        mEventsPresenter.load(mClassId);
    }

    @Override
    public void eventClicked(Event event) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse("http://maps.google.com/?q=" + event.rooms[0].address));
        startActivity(intent);
    }

    @Override
    public void deleteEvent(Event event) {
        mEventsPresenter.delete(event.id);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_ADD_EVENT && resultCode == RESULT_OK) {
            mEventsPresenter.load(mClassId);
        }
    }
}
