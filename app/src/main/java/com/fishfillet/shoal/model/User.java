package com.fishfillet.shoal.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Stephen on 11/8/2016.
 */


@IgnoreExtraProperties
public class User {
    //Needed fields.
    //TODO: Car Class
    public String username;
    public String email;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

}