package com.magdy.instructme;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Locale;

public class DetailsFragment extends Fragment {


    private Context mContext;
    private Course course;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    private boolean isTwoPane;
    private AppCompatActivity detailsActivity;
    private ImageView img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        if (savedInstanceState == null) {
            course = (Course) getArguments().getSerializable("course");
            isTwoPane = getArguments().getBoolean("pane");
        } else {
            course = (Course) savedInstanceState.getSerializable("course");
            //isFav = savedInstanceState.getBoolean("fav");
            isTwoPane = savedInstanceState.getBoolean("pane");
        }

        mContext = getActivity();

        collapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(course.getName());
        collapsingToolbarLayout.setContentDescription(course.getName());
        TextView title = (TextView) rootView.findViewById(R.id.title);
        title.setText(course.getName());
        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        if (isTwoPane) {
            detailsActivity = (MainActivity) getActivity();
        } else {
            detailsActivity = (DetailsActivity) getActivity();
            detailsActivity.setSupportActionBar(toolbar);
            if (detailsActivity.getSupportActionBar() != null) {
                detailsActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                detailsActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
            }
        }

        TextView overview = ((TextView) rootView.findViewById(R.id.overView));
        overview.setText(course.getDescription());
        overview.setContentDescription(course.getDescription());

        TextView price = ((TextView) rootView.findViewById(R.id.price));
        price.setText(String.format(Locale.getDefault(),"(%d %s)",course.getPrice(),course.getCurrency()));
        price.setContentDescription(String.format(Locale.getDefault(),"(%d %s)",course.getPrice(),course.getCurrency()));

        img = (ImageView) rootView.findViewById(R.id.toolbar_photo);
        img.setContentDescription(course.getName());
        Picasso.with(getActivity())
                .load(course.getImage())
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                        img.setImageBitmap(bitmap);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                    }
                });

        return rootView ;
    }

}
