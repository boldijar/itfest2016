package com.bolnizar.itfest.events;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bolnizar.itfest.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Musafir on 11/26/2016.
 */
public class EventFragment extends Fragment {

    private static final String ARG_NAME = "name";
    private static final String ARG_DATE = "date";
    @BindView(R.id.fragment_event_date)
    TextView mDateTextView;
    @BindView(R.id.fragment_event_name)
    TextView mEventName;
    private SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("dd-M-yyyy hh:mm", Locale.getDefault());


    public static EventFragment newInstance(String name, long date) {

        Bundle args = new Bundle();

        EventFragment fragment = new EventFragment();
        args.putString(ARG_NAME, name);
        args.putLong(ARG_DATE, date);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Date date = new Date(getArguments().getLong(ARG_DATE));
        mDateTextView.setText(mSimpleDateFormat.format(date));
        mEventName.setText(getArguments().getString(ARG_NAME));
    }
}
