package com.bolnizar.itfest.activities;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by Musafir on 11/26/2016.
 */
public class BaseActivity extends AppCompatActivity {

    public void showMessage(int messageId) {
        Toast.makeText(BaseActivity.this, messageId, Toast.LENGTH_SHORT).show();
    }
}
