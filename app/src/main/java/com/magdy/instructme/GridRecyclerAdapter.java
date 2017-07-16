package com.magdy.instructme;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Locale;

/**
 * Created by engma on 7/14/2017.
 */

 class GridRecyclerAdapter  extends RecyclerView.Adapter<GridRecyclerAdapter.SimpleViewHolder> {

    private List<Course> courses;
    private Toast toast ;
    private Context context;
    private DatabaseReference dbrefUsers ;
    private FirebaseAuth auth ;
    GridInfoListener fListener;
    GridRecyclerAdapter(Context context , List<Course>courses , GridInfoListener fl)
    {
        this.courses = courses ;
        this.context = context ;
        fListener = fl ;
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser()!=null)
            dbrefUsers =  FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid());

    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.grid_item, parent, false);
        return new GridRecyclerAdapter.SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        final Course course = courses.get(position);
        if(toast!=null)
        toast.cancel();
        Picasso.with(context).load(course.getImage()).into(holder.headImage);
        holder.ratingBar.setRating((float) 4.5);
        //holder.reviewers.setText(String.format(Locale.getDefault(),"(%d)",course.getReviews().size()));
        holder.reviewers.setText(String.format(Locale.getDefault(),"(%d)",5));
        holder.name.setText(course.getName());
        holder.price.setText(String.format(Locale.getDefault(),"(%d)",course.getPrice()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fListener.setSelected(course);
                /*toast = Toast.makeText(context,String.format(Locale.US, "%d clicked",position),Toast.LENGTH_SHORT);
                toast.show();*/
            }
        });
    }

    @Override
    public int getItemCount() {
        return courses.size();
    }

     class SimpleViewHolder extends RecyclerView.ViewHolder{
         TextView name , price , reviewers ;
         RatingBar ratingBar ;
         ImageView  headImage, saveButton ;
         SimpleViewHolder(View itemView) {
            super(itemView);
             name = (TextView) itemView.findViewById(R.id.name);
             price = (TextView) itemView.findViewById(R.id.price);
             reviewers = (TextView) itemView.findViewById(R.id.rate_num);
             ratingBar = (RatingBar) itemView.findViewById(R.id.rate);
             headImage = (ImageView) itemView.findViewById(R.id.head_image);
             saveButton = (ImageView) itemView.findViewById(R.id.save);
        }
    }
}
