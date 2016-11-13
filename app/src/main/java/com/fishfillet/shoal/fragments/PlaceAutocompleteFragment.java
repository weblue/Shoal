//package com.fishfillet.shoal.fragments;
//
//import android.util.Log;
//
//import com.fishfillet.shoal.R;
//
//import static com.google.android.gms.wearable.DataMap.TAG;
//
///**
// * Created by TravisNguyen on 11/12/16.
// */
//
//public class PlaceAutocompleteFragment {
//
//    PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
//            getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
//
//    autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//        @Override
//        public void onPlaceSelected(Place place) {
//            // TODO: Get info about the selected place.
//            Log.i(TAG, "Place: " + place.getName());
//        }
//
//        @Override
//        public void onError(Status status) {
//            // TODO: Handle the error.
//            Log.i(TAG, "An error occurred: " + status);
//        }
//    });
//}
