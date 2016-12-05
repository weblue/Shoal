package com.fishfillet.shoal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.fishfillet.shoal.Viewholder.RideViewHolder;
import com.fishfillet.shoal.model.Ride;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
//missing r

public class RiderActivity extends BaseActivity {

    // [START define_database_reference]
    private DatabaseReference mDatabase;
    // [END define_database_reference]

    private FirebaseRecyclerAdapter<Ride, RideViewHolder> mAdapter;
    private RecyclerView mRecycler;
    private LinearLayoutManager mManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("activity", "create");

        setContentView(R.layout.activity_rider);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        myToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        myToolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));
        setSupportActionBar(myToolbar);

        // [START create_database_reference]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        // [END create_database_reference]

        mRecycler = (RecyclerView) findViewById(R.id.rides_list);
        mRecycler.setHasFixedSize(true);

        mManager = new LinearLayoutManager(this);
        mManager.setReverseLayout(true);
        mManager.setStackFromEnd(true);
        mRecycler.setLayoutManager(mManager);

        // Set up FirebaseRecyclerAdapter with the Query
        Query postsQuery = getQuery(mDatabase);
        final Intent detailIntent = new Intent(this, RideDetailActivity.class);
        mAdapter = new FirebaseRecyclerAdapter<Ride, RideViewHolder>(Ride.class, R.layout.item_ride,
                RideViewHolder.class, postsQuery) {
            @Override
            protected void populateViewHolder(final RideViewHolder viewHolder, final Ride model, final int position) {
                Log.d("adapter", "populate");
                final DatabaseReference rideRef = getRef(position);

                // Set click listener for the whole post view
                final String rideKey = rideRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Launch RideDetailActivity
                        Bundle bundle = new Bundle();
                        bundle.putString(RideDetailActivity.EXTRA_RIDE_KEY, rideKey);
                        //detailIntent.putExtra(RideDetailActivity.EXTRA_RIDE_KEY, rideKey);
                        detailIntent.putExtras(bundle);
                        startActivity(detailIntent);
                    }
                });

                // Bind Ride to ViewHolder, setting OnClickListener for the star button
                viewHolder.bindToRide(model);
            }
        };
        Log.d("Activity", "set adapter");
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

    public Query getQuery(DatabaseReference databaseReference){
        FirebaseDatabase ref = databaseReference.child("rides").getDatabase();
        Query rides = databaseReference.child("rides").orderByChild("timecreated");
        return rides;

    }

}