package com.bolnizar.itfest.comments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.data.models.Event;
import com.bolnizar.itfest.di.InjectionHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Musafir on 11/26/2016.
 */
public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder> {

    private List<Comment> mComments = new ArrayList<>();

    public void setComments(List<Comment> comments) {
        mComments = comments;
        notifyDataSetChanged();
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentHolder(parent);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, final int position) {
        Comment comment = mComments.get(position);
        holder.author.setText(comment.users[0].email);
        holder.text.setText(comment.text);
    }


    @Override
    public int getItemCount() {
        return mComments.size();
    }


    public static class CommentHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.comment_author)
        TextView author;
        @BindView(R.id.comment_text)
        TextView text;

        public CommentHolder(ViewGroup parent) {
            super(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false));
            ButterKnife.bind(this, itemView);
        }

    }
}
