package com.bolnizar.itfest.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Toast;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.comments.Comment;
import com.bolnizar.itfest.comments.CommentAdapter;
import com.bolnizar.itfest.comments.CommentsPresenter;
import com.bolnizar.itfest.comments.CommentsView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Musafir on 11/26/2016.
 */
public class CommentsFragment extends DialogFragment implements CommentsView {

    public final static String ARG_EVENT_ID = "eventid";

    private int mEventId;

    @BindView(R.id.comments_recycler)
    RecyclerView mRecyclerView;
    @BindView(R.id.comments_text)
    EditText mCommentText;

    private CommentsPresenter mCommentsPresenter;
    private CommentAdapter mCommentAdapter = new CommentAdapter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_comments, container, false);
    }

    public static CommentsFragment newInstance(int eventId) {
        Bundle args = new Bundle();
        args.putInt(ARG_EVENT_ID, eventId);
        CommentsFragment fragment = new CommentsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mEventId = getArguments().getInt(ARG_EVENT_ID);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mCommentAdapter);
        mCommentsPresenter = new CommentsPresenter(this, getContext());
        mCommentsPresenter.loadComments(mEventId);
    }

    @OnClick(R.id.comments_send)
    void saveComment() {
        if (mCommentText.getText().toString().trim().length() == 0) {
            showMessage(R.string.comment_empty);
            return;
        }
        mCommentsPresenter.addComment(mCommentText.getText().toString(), mEventId);
    }

    @Override
    public void addSuccess() {
        mCommentText.setText(null);
        showMessage(R.string.comm_added);
        mCommentsPresenter.loadComments(mEventId);
    }

    @Override
    public void showComments(List<Comment> comments) {
        mCommentAdapter.setComments(comments);
    }

    @Override
    public void showMessage(int message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
