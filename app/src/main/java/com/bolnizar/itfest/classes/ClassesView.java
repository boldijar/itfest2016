package com.bolnizar.itfest.classes;

import com.bolnizar.itfest.data.models.*;
import com.bolnizar.itfest.data.models.Class;

import java.util.List;

/**
 * Created by Musafir on 11/26/2016.
 */
public interface ClassesView {

    void showClasses(List<Class> classes);

    void showMessage(int messageId);
}
