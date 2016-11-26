package com.bolnizar.itfest.events;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Musafir on 11/26/2016.
 */
public class EventPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments = new ArrayList<>();
    private static final int MAX_EVENTS = 5;

    public EventPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void update() {
        mFragments.clear();
        List<SubscribedEventRecord> subscribedEventRecords = SubscribedEventRecord.listAll(SubscribedEventRecord.class);
        int count = 0;
        for (SubscribedEventRecord subscribedEventRecord : subscribedEventRecords) {
            count++;
            if (count > MAX_EVENTS) {
                break;
            }
            mFragments.add(EventFragment.newInstance(subscribedEventRecord.name, subscribedEventRecord.practicDate));
        }
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
