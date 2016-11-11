package com.fishfillet.shoal.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.fishfillet.shoal.R;
import com.fishfillet.shoal.Viewholder.RideViewHolder;
import com.fishfillet.shoal.model.Ride;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
//missing r

public abstract class RideListFragment extends Fragment {

    private static final String TAG = "RideListFragment";

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Ride, RideViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    public RideListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.activity_rider, container, false);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = (RecyclerView) rootView.findViewById(R.id.rides_list);
        mRecycler.setHasFixedSize(true);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set up Layout Manager, reverse layout
        mManager = new LinearLayoutManager(getActivity());
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = getQuery(mDatabase);
        mAdapter = new FirebaseRecyclerAdapter<Ride, RideViewHolder>(Ride.class, R.layout.ride_post,
                RideViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final RideViewHolder viewHolder, final Ride model, final int position) {
                final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view
                final String postKey = postRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch RideDetailActivity
                        Intent intent = new Intent(getActivity(), RideDetailActivity.class);
                        intent.putExtra(RideDetailActivity.EXTRA_POST_KEY, postKey);
                        startActivity(intent);
                    }
                });

                // Determine if the current user has liked this post and set UI accordingly
                // if (model.stars.containsKey(getUid())) {
                // viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_24);
                // } else {
                // viewHolder.starView.setImageResource(R.drawable.ic_toggle_star_outline_24);
                // }

                // Bind Ride to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToRide(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        // Need to write to both places the post is stored
                        DatabaseReference globalRideRef = mDatabase.child("posts").child(postRef.getKey());
                        DatabaseReference userRideRef = mDatabase.child("user-posts").child(getUid()).child(postRef.getKey());

                        // Run two transactions
                        //     onStarClicked(globalRideRef);
                        //    onStarClicked(userRideRef);
                    }
                });
            }
        };
        mRecycler.setAdapter(mAdapter);
    }

    // [START post_stars_transaction]
    /*private void onStarClicked(DatabaseReference postRef) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Ride r = mutableData.getValue(Ride.class);
                if (r == null) {
                    return Transaction.success(mutableData);
                }

                if (r.stars.containsKey(getUid())) {
                    // Unstar the post and remove self from stars
                    r.starCount = r.starCount - 1;
                    r.stars.remove(getUid());
                } else {
                    // Star the post and add self to stars
                    r.starCount = r.starCount + 1;
                    r.stars.put(getUid(), true);
                }

                // Set value and report transaction success
                mutableData.setValue(r);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }*/
    // [END post_stars_transaction]

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mAdapter != null) {
            mAdapter.cleanup();
        }
    }

    public String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public abstract Query getQuery(DatabaseReference databaseReference);

}