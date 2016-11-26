package com.bolnizar.itfest.activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.classes.AddClassPresenter;
import com.bolnizar.itfest.classes.AddClassView;
import com.bolnizar.itfest.data.models.School;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
import timber.log.Timber;

public class AddClassActivity extends BaseActivity implements AddClassView {

    @BindView(R.id.add_class_name)
    EditText mClassName;
    @BindView(R.id.add_class_school)
    EditText mSchoolText;

    private List<School> mSchools;
    private AddClassPresenter mAddClassPresenter;
    private School mSelectedSchool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);
        setTitle(getString(R.string.addclass));
        ButterKnife.bind(this);
        mAddClassPresenter = new AddClassPresenter(this, this);
        mAddClassPresenter.loadSchools();
    }

    @Override
    public void showSchools(List<School> schools) {
        mSchools = schools;
    }

    @Override
    public void showSuccess(final Integer id) {
        mSchoolText.setText(null);
        mClassName.setText(null);
        mSelectedSchool = null;

        AlertDialog.Builder builder =
                new AlertDialog.Builder(this);
        builder.setMessage("Class successfully created. Want to add events to it?");
        builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(AddEventToClassActivity.createIntent(AddClassActivity.this, id));
            }
        });
        builder.setNegativeButton(android.R.string.no, null);
        builder.show();
    }

    @OnClick(R.id.add_class_school)
    void containerClick() {
        Timber.d("Touching container...");
        if (mSchools == null || mSchools.size() == 0) {
            Toast.makeText(AddClassActivity.this, R.string.schools_not_loadd, Toast.LENGTH_SHORT).show();
            return;
        }

        PopupMenu menu = new PopupMenu(this, mSchoolText);
        for (School school : mSchools) {
            menu.getMenu().add(school.name);
        }
        menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                for (School school : mSchools) {
                    if (school.name.equals(item.getTitle().toString())) {
                        clickedSchool(school);
                    }
                }
                return false;
            }
        });
        menu.show();
    }

    @OnClick(R.id.home_add_classes)
    void add() {
        if (mSchoolText.getText().toString().trim().length() == 0 || mClassName.getText().toString().trim().length() == 0
                || mSelectedSchool == null) {
            Toast.makeText(AddClassActivity.this, R.string.fillfields, Toast.LENGTH_SHORT).show();
            return;
        }
        mAddClassPresenter.addClass(mSelectedSchool.id, mClassName.getText().toString());
    }

    private void clickedSchool(School school) {
        mSelectedSchool = school;
        mSchoolText.setText(school.name);
    }

}
