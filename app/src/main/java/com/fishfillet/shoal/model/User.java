package com.fishfillet.shoal.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Stephen on 11/8/2016.
 */


@IgnoreExtraProperties
public class User {
    //Needed fields.
    //TODO: Car Class
    private String username;
    private String email;
    private String first_name;
    private String last_name;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
    }

    public User(String username, String email, String first_name, String last_name) {
        this.username = username;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String u_last_name) {
        this.last_name = u_last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String u_id) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

}