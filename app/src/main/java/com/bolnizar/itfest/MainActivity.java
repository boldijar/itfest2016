package com.bolnizar.itfest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.bolnizar.itfest.data.IntegerPreference;
import com.bolnizar.itfest.data.models.TestModel;
import com.bolnizar.itfest.data.models.TestResponse;
import com.bolnizar.itfest.di.InjectionHelper;
import com.bolnizar.itfest.test.TestPresenter;
import com.bolnizar.itfest.test.TestView;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements TestView {

    @Inject
    IntegerPreference mIntegerPreference;

    TestPresenter mTestPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InjectionHelper.getApplicationComponent(this).inject(this);
        mIntegerPreference.set(mIntegerPreference.get() + 1);

        mTestPresenter = new TestPresenter(this, this);
        mTestPresenter.load();
    }

    @Override
    public void showTest(TestResponse testResponse) {
        String showMsg = "";
        for (TestModel test : testResponse.test) {
            showMsg += test.name + " ";
        }
        Toast.makeText(MainActivity.this, showMsg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showError(Throwable e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
