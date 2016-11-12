package com.fishfillet.shoal.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Fragment;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.fishfillet.shoal.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by TravisNguyen on 11/9/16.
 */

public class WaitingScreenFragment extends Fragment
{
    private TextView mSeatsRemainingTextView;
    private TextView mDepartureTimeTextView;

    ///Fish animation
    private Random mRandom = new Random();
    private ImageView mFishImage1, mFishImage2, mFishImage3, mFishImage4, mFishImage5, mFishImage6;
    private float mLogicalDensity;
    private int mScreenWidth, mScreenHeight;
    private static final int mMaxDuration = 40000;
    private static final int mMinDuration = 25000;

    public WaitingScreenFragment() {
        //empty constructor
    }

    public static WaitingScreenFragment newInstance(Bundle args)
    {
        WaitingScreenFragment fragment = new WaitingScreenFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public static final String TAG = WaitingScreenFragment.class.getName();

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        DisplayMetrics metrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mLogicalDensity = metrics.density;
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
         View root = inflater.inflate(R.layout.fragment_waiting_screen, container, false);


        mSeatsRemainingTextView = (TextView)root.findViewById(R.id.seatsRemainingTextView);
        mSeatsRemainingTextView.setText(savedInstanceState.getInt("passengers"));

        mDepartureTimeTextView = (TextView)root.findViewById(R.id.departureTimeTextView);
        mDepartureTimeTextView.setText(getDate(savedInstanceState.getLong("time")));

        mFishImage1 = (ImageView) root.findViewById(R.id.iv_onePassengers);
        mFishImage2 = (ImageView) root.findViewById(R.id.iv_twoPassengers);
        mFishImage3 = (ImageView) root.findViewById(R.id.iv_threePassengers);
        mFishImage4 = (ImageView) root.findViewById(R.id.iv_fourPassengers);
        mFishImage5 = (ImageView) root.findViewById(R.id.iv_fivePassengers);
        mFishImage6 = (ImageView) root.findViewById(R.id.iv_sixPassengers);

        root.findViewById(android.R.id.content).post(new Runnable() {
            @Override
            public void run() {
                startAnimation();

            }
        });
        return root;

    }

    public static String getDate(long milliSeconds)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }


    /**
     * start germ animation code
     */

    public void startAnimation() {

        stopAllAnimation();

        mFishImage1.setVisibility(View.VISIBLE);
        mFishImage2.setVisibility(View.VISIBLE);
        mFishImage3.setVisibility(View.VISIBLE);
        mFishImage4.setVisibility(View.VISIBLE);
        mFishImage5.setVisibility(View.VISIBLE);
        mFishImage6.setVisibility(View.VISIBLE);

        List<ImageView> fishes = new ArrayList<>();
        fishes.add(mFishImage1);
        fishes.add(mFishImage2);
        fishes.add(mFishImage3);
        fishes.add(mFishImage4);
        fishes.add(mFishImage5);
        fishes.add(mFishImage6);

        final int CAPACITY = 4; // how many germs to allow at one time

        for (int i = 0; i <= CAPACITY; i++) {
            int r = mRandom.nextInt(fishes.size());

            startAltFishAnimation(fishes.get(r), 3);
            fishes.remove(r);
        }

        while (!fishes.isEmpty()) {
            startAltFishAnimation(fishes.get(0), 7500);
            fishes.remove(0);
        }
    }

    private int dpToPixels(float dp) {
        return (int) (dp * mLogicalDensity + 0.5f);
    }


    /**
     * animates the given image view with some amount of randomness
     *
     * @param view  the image view to animate
     * @param delay slightly variated delay till animation start; must be >= 3
     */
    private void startAltFishAnimation(ImageView view, int delay) {

        float xStart = dpToPixels(-140);
        float xEnd = mScreenWidth;
        float yStart = dpToPixels(-140);
        float yEnd = mScreenHeight;

        int corner = mRandom.nextInt(4) + 1; //select a random number 1 - 4

        if (corner == 1) {
            //remain unchanged
        } else if (corner == 2) {
            xStart = mScreenWidth + dpToPixels(mRandom.nextInt(40) + 140);
            xEnd = dpToPixels(-140);
            //mScreenWidth + 100 to -100
            //mScreenHeight unchanged
        } else if (corner == 3) {
            //mScreenWidth unchanged
            yStart = mScreenHeight + dpToPixels(mRandom.nextInt(40) + 140);
            yEnd = dpToPixels(-140);
            //mScreenHeight + 100 to -100
        } else if (corner == 4) {
            xStart = mScreenWidth + dpToPixels(mRandom.nextInt(40) + 140);
            xEnd = dpToPixels(-140);
            //mScreenWidth + 100 to -100
            yStart = mScreenHeight + dpToPixels(mRandom.nextInt(40) + 140);
            yEnd = dpToPixels(-140);
            //mScreenHeight + 100 to -100
        }

        ObjectAnimator moveHorizontal = ObjectAnimator.ofFloat(view, "translationX", xStart, xEnd);
        ObjectAnimator moveVertical = ObjectAnimator.ofFloat(view, "translationY", yStart, yEnd);

        moveVertical.setRepeatMode(ValueAnimator.RESTART);
        moveHorizontal.setRepeatCount(ValueAnimator.INFINITE);
        moveVertical.setRepeatCount(ValueAnimator.INFINITE);
        moveHorizontal.setStartDelay(mRandom.nextInt(delay / 3) + 2 * (delay / 3));
        moveVertical.setStartDelay(mRandom.nextInt(delay / 3) + 2 * (delay / 3));
        moveHorizontal.setDuration(mRandom.nextInt(mMaxDuration - mMinDuration) + mMinDuration);
        moveVertical.setDuration(mRandom.nextInt(mMaxDuration - mMinDuration) + mMinDuration);
        moveHorizontal.start();
        moveVertical.start();
    }

    private void stopAllAnimation() {

        stopAnimation(mFishImage1);
        stopAnimation(mFishImage2);
        stopAnimation(mFishImage3);
        stopAnimation(mFishImage4);
        stopAnimation(mFishImage5);
        stopAnimation(mFishImage6);
    }

    private void stopAnimation(ImageView image) {
        image.setVisibility(View.GONE);
        image.clearAnimation();
        image.animate().cancel();
        image.setAnimation(null);
        image.animate().setListener(null);
    }

    @Override
    public void onPause() {
        stopAllAnimation();
        super.onPause();
    }

    @Override
    public void onStop() {
        stopAllAnimation();
        super.onStop();
    }

    @Override
    public void onResume() {
        startAnimation();
        super.onResume();
    }
}

