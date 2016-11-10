package com.fishfillet.shoal.Viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fishfillet.shoal.R;
import com.fishfillet.shoal.model.Ride;


public class RideViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView authorView;
    public ImageView starView;
    public TextView numStarsView;
    public TextView bodyView;

    public RideViewHolder(View itemView) {
        super(itemView);

        titleView = (TextView) itemView.findViewById(R.id.post_title);
        authorView = (TextView) itemView.findViewById(R.id.post_author);
        //starView = (ImageView) itemView.findViewById(R.id.star);
        numStarsView = (TextView) itemView.findViewById(R.id.post_num_stars);
        bodyView = (TextView) itemView.findViewById(R.id.post_body);
    }

    public void bindToRide(Ride post, View.OnClickListener starClickListener) {
       // titleView.setText(post.title);
       // authorView.setText(post.author);
       // numStarsView.setText(String.valueOf(post.starCount));
        //bodyView.setText(post.body);

        starView.setOnClickListener(starClickListener);
    }
}