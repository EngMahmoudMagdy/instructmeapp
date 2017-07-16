package com.magdy.instructme;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class OneFragment extends Fragment {

    String category = "" ;
    FirebaseAuth mauth ;
    Map<String,Boolean> saveMap;

    List<Course> courses ;
    GridRecyclerAdapter gridAdapter ;
    RecyclerView gridView ;
    private GridInfoListener fListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setListInfoListenter(GridInfoListener lsn) {
        fListener = lsn;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_one, container, false);
        gridView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        courses = new ArrayList<>();

       // course.setImage("https://udemy-images.udemy.com/course/750x422/24823_963e_12.jpg");
        //course.setReviews(new ArrayList<Reviewer>());
        gridAdapter = new GridRecyclerAdapter(getContext(),courses , fListener);
        AutoFitGridLayoutManager layoutManager = new AutoFitGridLayoutManager(getContext(), 350);
        gridView.setLayoutManager(layoutManager);
        gridView.setAdapter(gridAdapter);
        gridAdapter.notifyDataSetChanged();

        mauth = FirebaseAuth.getInstance();
        saveMap = new HashMap<>();
        DatabaseReference dbref =  FirebaseDatabase.getInstance().getReference();

        dbref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    try {
                        if (mauth.getCurrentUser()!=null) {
                            DataSnapshot userSnapShot = dataSnapshot.child("users").child(mauth.getCurrentUser().getUid());

                            Iterable<DataSnapshot> wishShots = userSnapShot.child("saved").getChildren();
                            String s ;


                            saveMap.clear();

                            for (DataSnapshot daas : wishShots)
                            {
                                s = daas.getKey();
                                saveMap.put(s,true);
                            }
                        }



                        DataSnapshot productSnap = dataSnapshot.child("courses");

                        Iterable<DataSnapshot> dsdent=  productSnap.child(category).getChildren();
                        courses.clear();
                        boolean bw  = false;
                        for (DataSnapshot das : dsdent)
                        {
                            if(saveMap.containsKey(das.getKey())) {
                                bw =saveMap.get(das.getKey());
                            }
                            int p = 0 ;
                            Integer integer =das.child("price").getValue(Integer.class);
                            if (integer!=null)
                            {
                                p=integer;
                            }
                            Course course1 = new Course();
                            course1.setSaved(bw);
                            course1.setName(das.child("name").getValue(String.class));
                            course1.setImage(das.child("image").getValue(String.class));
                            course1.setDescription(das.child("description").getValue(String.class));
                            course1.setCategory(category);
                            course1.setCurrency(das.child("currency").getValue(String.class));
                            course1.setId(das.getKey());
                            course1.setPrice(p);
                             p = 0 ;
                             integer =das.child("oldprice").getValue(Integer.class);
                            if (integer!=null)
                            {
                                p=integer;
                            }
                            course1.setOldPrice(p);
                            courses.add(course1);
                            bw=false ;

                        }


                        gridAdapter.notifyDataSetChanged();

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




        return rootView ;
    }


}
