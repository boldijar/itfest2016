package com.bolnizar.itfest.comments;

import java.util.List;

/**
 * Created by Musafir on 11/26/2016.
 */
public interface CommentsView {

    void addSuccess();

    void showComments(List<Comment> comments);

    void showMessage(int message);
}
