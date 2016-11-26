package com.bolnizar.itfest.classes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.data.models.*;
import com.bolnizar.itfest.data.models.Class;
import com.bolnizar.itfest.persistance.SubscriptionRecord;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Musafir on 11/26/2016.
 */
public class ClassesAdapter extends RecyclerView.Adapter<ClassesAdapter.ClassesHolder> {

    private List<Class> mClasses = new ArrayList<>();
    private List<SubscriptionRecord> mSubscriptionRecords = new ArrayList<>();

    private final OnClassClick mOnClassClick;

    public ClassesAdapter(OnClassClick onClassClick) {
        mOnClassClick = onClassClick;
        mSubscriptionRecords = SubscriptionRecord.listAll(SubscriptionRecord.class);
    }

    public void setClasses(List<Class> classes) {
        mClasses = classes;
        notifyDataSetChanged();
    }

    @Override
    public ClassesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClassesHolder(parent);
    }

    @Override
    public void onBindViewHolder(ClassesHolder holder, final int position) {
        Class clas = mClasses.get(position);
        holder.name.setText(clas.name);
        holder.school.setText(clas.schools[0].name);
        holder.save.setText(isSubscribed(clas) ? R.string.unsubscribe : R.string.subscribe);
        holder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedPosition(position);
            }
        });
    }

    private void clickedPosition(int position) {
        mOnClassClick.clicked(mClasses.get(position), !isSubscribed(mClasses.get(position)));
    }

    private boolean isSubscribed(Class clas) {
        for (SubscriptionRecord subscriptionRecord : mSubscriptionRecords) {
            if (clas.id == subscriptionRecord.classId)
                return true;
        }
        return false;
    }

    @Override
    public int getItemCount() {
        return mClasses.size();
    }

    public void updateSubs() {
        mSubscriptionRecords = SubscriptionRecord.listAll(SubscriptionRecord.class);
        notifyDataSetChanged();
    }

    public static class ClassesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.class_name)
        TextView name;
        @BindView(R.id.class_school)
        TextView school;
        @BindView(R.id.class_save)
        TextView save;

        public ClassesHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false));
            ButterKnife.bind(this, itemView);
        }

    }

    public interface OnClassClick {
        void clicked(Class clas, boolean wantsToSubscribe);
    }
}
