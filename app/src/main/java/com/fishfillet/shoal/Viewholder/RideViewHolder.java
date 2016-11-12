package com.fishfillet.shoal.Viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fishfillet.shoal.R;
import com.fishfillet.shoal.model.Ride;


public class RideViewHolder extends RecyclerView.ViewHolder {

    public TextView destinationView;
    public TextView departView;
    public ImageView starView;
    public TextView numStarsView;
    public TextView bodyView;

    public RideViewHolder(View itemView) {
        super(itemView);

        destinationView = (TextView) itemView.findViewById(R.id.destLoc);
        departView = (TextView) itemView.findViewById(R.id.departTime);
        //starView = (ImageView) itemView.findViewById(R.id.star);
        //numStarsView = (TextView) itemView.findViewById(R.id.post_num_stars);
        //bodyView = (TextView) itemView.findViewById(R.id.post_body);
    }

    public void bindToRide(Ride ride) {
        destinationView.setText(ride.locdest);
        departView.setText("Leaves at " + ride.timedepart);
       // titleView.setText(post.title);
       // authorView.setText(post.author);
       // numStarsView.setText(String.valueOf(post.starCount));
        //bodyView.setText(post.body);
    }
}