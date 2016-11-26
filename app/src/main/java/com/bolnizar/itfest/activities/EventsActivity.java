package com.bolnizar.itfest.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.data.models.Event;
import com.bolnizar.itfest.events.EventsAdapter;
import com.bolnizar.itfest.events.EventsPresenter;
import com.bolnizar.itfest.events.EventsView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventsActivity extends BaseActivity implements EventsView, SwipeRefreshLayout.OnRefreshListener, EventsAdapter.EventsListener {

    @BindView(R.id.events_empty)
    View mEmpty;
    @BindView(R.id.events_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.events_swipe_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private static final String ARG_CLASS_ID = "clasid";
    private int mClassId;

    private EventsPresenter mEventsPresenter;

    private EventsAdapter mEventsAdapter = new EventsAdapter(this);

    public static Intent createIntent(Context context, int classId) {
        Intent intent = new Intent(context, EventsActivity.class);
        intent.putExtra(ARG_CLASS_ID, classId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        setTitle(getString(R.string.class_events));
        mClassId = getIntent().getIntExtra(ARG_CLASS_ID, 0);
        ButterKnife.bind(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mEventsAdapter);
        mEventsPresenter = new EventsPresenter(this, this);
        mEventsPresenter.load(mClassId);
        mSwipeRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public void showEvent(List<Event> events) {
        mEventsAdapter.setEvents(events);
        mEmpty.setVisibility(events.size() == 0 ? View.VISIBLE : View.GONE);
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
}
