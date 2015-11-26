package com.ottamotta.readability.common.ui;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ottamotta.readability.R;

import butterknife.Bind;

public abstract class BaseActivity extends AppCompatActivity {

    @Bind(R.id.coordinator_layout)
    protected CoordinatorLayout coordinatorLayout;

    @Bind(R.id.toolbar)
    protected Toolbar toolbar;

    protected void showMessage(String message) {
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        setupActionBar();
    }

    protected void setupActionBar() {
            setSupportActionBar(toolbar);
            ActionBar supportActionBar = getSupportActionBar();
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setHomeAsUpIndicator(R.drawable.ic_arrow_back_white_24dp);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
