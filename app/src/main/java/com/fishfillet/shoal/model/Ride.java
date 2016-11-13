package com.fishfillet.shoal.model;

import android.location.Location;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nader on 11/7/16.
 */

public class Ride {

    public int maxpassengers;
    public String driverid, carmodel, carcolor, carmake, caryear, plate, notes;
    public String timedepart, timecreated;
    public String locstart, locdest;

    public Ride() {
    }

    public Ride(
            String driverid,
            String carModel,
            String carColor,
            String carMake,
            String carYear,
            String plate,
            String notes,
            String timeDepart,
            String timeCreated,
            String locStart,
            String locDest,
            int maxpassengers) {

        this.driverid = driverid;
        this.carmodel = carModel;
        this.carcolor = carColor;
        this.carmake = carMake;
        this.caryear = carYear;
        this.plate = plate;
        this.notes = notes;
        this.timedepart = timeDepart;
        this.timecreated = timeCreated;
        this.locdest = locDest;
        this.locstart = locStart;
        this.maxpassengers = maxpassengers;

    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<String, Object>();

        result.put("driverid", this.driverid);
        result.put("carmodel", this.carmodel);
        result.put("carcolor", this.carcolor);
        result.put("carmake", this.carmake);
        result.put("plate", this.plate);
        result.put("notes", this.notes);
        result.put("timedepart", this.timedepart);
        result.put("timecreated", this.timecreated);
        result.put("locdest", this.locdest);
        result.put("locdepart", this.locstart);
        result.put("maxpassengers",this.maxpassengers);

        return result;
    }

    public static class RideBuilder {

        private String driverId, carModel, carColor, carMake, carYear, plate, notes;
        private String timeDepart, timeCreated;
        private String locDest, locDepart;
        private int maxPassengers;

        public RideBuilder() {

        }

        public RideBuilder setDriverId(String driverId) {
            this.driverId = driverId;
            return this;
        }

        public Ride build() {
            return new Ride(driverId, carModel, carColor, carMake, carYear, plate, notes, timeDepart,
                    timeCreated, locDest, locDepart,maxPassengers);
        }

        public RideBuilder setCarModel(String carModel) {
            this.carModel = carModel;
            return this;
        }

        public RideBuilder setCarColor(String carColor) {
            this.carColor = carColor;
            return this;
        }

        public RideBuilder setCarMake(String carMake) {
            this.carMake = carMake;
            return this;
        }

        public RideBuilder setCarYear(String carYear) {
            this.carYear = carYear;
            return this;
        }

        public RideBuilder setPlate(String plate) {
            this.plate = plate;
            return this;
        }

        public RideBuilder setNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public RideBuilder setTimeCreated(String timeCreated) {
            this.timeCreated = timeCreated;
            return this;
        }

        public RideBuilder setTimeDepart(String timeDepart) {
            this.timeDepart = timeDepart;
            return this;
        }

        public RideBuilder setLocDest(String locDest) {
            this.locDest = locDest;
            return this;
        }

        public RideBuilder setLocDepart(String locDepart) {
            this.locDepart = locDepart;
            return this;
        }

        public RideBuilder setMaxPassengers(int maxPassengers){
            this.maxPassengers = maxPassengers;
            return this;
        }
    }
}
