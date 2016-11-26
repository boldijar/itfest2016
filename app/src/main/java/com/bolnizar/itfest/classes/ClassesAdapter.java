package com.bolnizar.itfest.classes;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.data.models.*;
import com.bolnizar.itfest.data.models.Class;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Musafir on 11/26/2016.
 */
public class ClassesAdapter extends RecyclerView.Adapter<ClassesAdapter.ClassesHolder> {

    private List<Class> mClasses = new ArrayList<>();

    public void setClasses(List<Class> classes) {
        mClasses = classes;
        notifyDataSetChanged();
    }

    @Override
    public ClassesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClassesHolder(parent);
    }

    @Override
    public void onBindViewHolder(ClassesHolder holder, int position) {
        Class clas = mClasses.get(position);
        holder.name.setText(clas.name);
        holder.school.setText(clas.schools[0].name);
    }

    @Override
    public int getItemCount() {
        return mClasses.size();
    }

    public static class ClassesHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.class_name)
        TextView name;
        @BindView(R.id.class_school)
        TextView school;

        public ClassesHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_class, parent, false));
            ButterKnife.bind(this, itemView);
        }
    }
}
