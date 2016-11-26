package com.bolnizar.itfest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.bolnizar.itfest.R;
import com.bolnizar.itfest.modpanel.ModeratorPanelPresenter;
import com.bolnizar.itfest.modpanel.ModeratorPanelView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModeratorPanelActivity extends BaseActivity implements ModeratorPanelView {

    @BindView(R.id.moderator_email)
    EditText mEditText;

    private ModeratorPanelPresenter mModeratorPanelPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moderator_panel);
        setTitle(R.string.moderator_panel);
        ButterKnife.bind(this);
        mModeratorPanelPresenter = new ModeratorPanelPresenter(this, this);
    }

    @OnClick(R.id.moderator_enable)
    void enableClick() {
        mModeratorPanelPresenter.makeUserModerator(mEditText.getText().toString());
    }

    @Override
    public void showAccessAdded() {
        mEditText.setText(null);
    }
}
