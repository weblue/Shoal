package com.fishfillet.shoal;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by TravisNguyen on 11/9/16.
 */

public class WaitingScreenActivity extends BaseActivity {
    private static final int mMaxDuration = 40000;
    private static final int mMinDuration = 25000;
    private static String time;
    private TextView mSeatsRemainingTextView;
    private TextView mDepartureTimeTextView;
    ///Fish animation
    private Random mRandom = new Random();
    private ImageView mFishImage1, mFishImage2, mFishImage3, mFishImage4, mFishImage5, mFishImage6;
    private ArrayList<ImageView> fishList;
    private float mLogicalDensity;
    private int mScreenWidth, mScreenHeight;
    private int passengers;

    public WaitingScreenActivity() {
        //empty constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_waiting_screen);

        Bundle bundle = getIntent().getExtras();
        time = bundle.getString("time");
        passengers = bundle.getInt("passengers");

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        mLogicalDensity = metrics.density;
        mScreenWidth = metrics.widthPixels;
        mScreenHeight = metrics.heightPixels;

        mDepartureTimeTextView = (TextView) findViewById(R.id.departureTimeTextView);
        mDepartureTimeTextView.setText("Leaving at " + time);

        //TODO show a button to return to main screen after the timer is done
        //Calendar cal = Calendar.getInstance();
        //String[] timeSplit = time.split(":");
        //int hour = Integer.parseInt(timeSplit[0]);

        //String[] timeSplitSplit = time.split(" ");
        //if (timeSplitSplit[0].equals("pm"))
        //   hour += 12;
        //int min = Integer.parseInt(timeSplitSplit[0]);

        //cal.set(cal.YEAR, cal.MONTH, cal.DATE, hour, min);

        mSeatsRemainingTextView = (TextView) findViewById(R.id.seatsRemainingTextView);
        mSeatsRemainingTextView.setText("x out of " + passengers + " passengers");

        mFishImage1 = (ImageView) findViewById(R.id.iv_onePassengers);
        mFishImage2 = (ImageView) findViewById(R.id.iv_twoPassengers);
        mFishImage3 = (ImageView) findViewById(R.id.iv_threePassengers);
        mFishImage4 = (ImageView) findViewById(R.id.iv_fourPassengers);
        mFishImage5 = (ImageView) findViewById(R.id.iv_fivePassengers);
        mFishImage6 = (ImageView) findViewById(R.id.iv_sixPassengers);

        this.fishList = new ArrayList<ImageView>();
        this.fishList.add(mFishImage1);
        this.fishList.add(mFishImage2);
        this.fishList.add(mFishImage3);
        this.fishList.add(mFishImage4);
        this.fishList.add(mFishImage5);
        this.fishList.add(mFishImage6);


//        root.findViewById(android.R.id.content).post(new Runnable() {
//            @Override
//            public void run() {
//                startAnimation();
//
//            }
//        });

    }

    /*public static String getDate(long milliSeconds)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }*/


    /**
     * start germ animation code
     */

    public void startAnimation() {

        stopAllAnimation();

        List<ImageView> fishes = new ArrayList<>();
        fishes.add(mFishImage1);
        fishes.add(mFishImage2);
        fishes.add(mFishImage3);
        fishes.add(mFishImage4);
        fishes.add(mFishImage5);
        fishes.add(mFishImage6);

        final int CAPACITY = 2; // how many germs to allow at one time

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

        //int corner = mRandom.nextInt(4) + 1; //select a random number 1 - 4

            xStart = mScreenWidth + dpToPixels(mRandom.nextInt(40) + 140);
            xEnd = dpToPixels(-140);


        ObjectAnimator moveHorizontal = ObjectAnimator.ofFloat(view, "translationX", xStart, xEnd);

        moveHorizontal.setRepeatCount(ValueAnimator.INFINITE);
        moveHorizontal.setStartDelay(mRandom.nextInt(delay / 3) + 2 * (delay / 3));
        moveHorizontal.setDuration(mRandom.nextInt(mMaxDuration - mMinDuration) + mMinDuration);
        moveHorizontal.start();

        view.setVisibility(View.VISIBLE);
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

