package com.bolnizar.itfest.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bolnizar.itfest.R;

public class AddEventToClassActivity extends BaseActivity {

    public static final String ARG_CLASS_ID = "clasid";

    public static Intent createIntent(Context context, int classId) {
        Intent intent = new Intent(context, AddEventToClassActivity.class);
        intent.putExtra(ARG_CLASS_ID, classId);
        return intent;
    }

    private int mClassId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_to_class);
        setTitle(getString(R.string.addeventclass));
        mClassId = getIntent().getIntExtra(ARG_CLASS_ID, 0);
    }
}
