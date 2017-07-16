package com.magdy.instructme;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Bundle extra = getIntent().getExtras();
        if (savedInstanceState == null) {
            DetailsFragment detailActivityFragment = new DetailsFragment();
            detailActivityFragment.setArguments(extra);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fl_panel2, detailActivityFragment)
                    .commit();
        }
    }
}
