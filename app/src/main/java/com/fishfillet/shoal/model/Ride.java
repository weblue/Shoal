package com.fishfillet.shoal.model;

import android.location.Location;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by nader on 11/7/16.
 *
 */

public class Ride {

    private String driverId, carModel, carColor, carMake, plate, notes;
    private long timeDepart, timeCreated;
    private Location locDest, locDepart;

    public Ride(
            String driverId,
            String carModel,
            String carColor,
            String carMake,
            String plate,
            String notes,
            long timeDepart,
            long timeCreated,
            Location locDest,
            Location locDepart) {

        this.driverId = driverId;
        this.carModel = carModel;
        this.carColor = carColor;
        this.carMake = carMake;
        this.plate = plate;
        this.notes = notes;
        this.timeDepart = timeDepart;
        this.timeCreated = timeCreated;
        this.locDest = locDest;
        this.locDepart = locDepart;

    }

    public String getDriver() {
        return driverId;
    }

    public void setDriver(String driverId) {
        this.driverId = driverId;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public long getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(long timeCreated) {
        this.timeCreated = timeCreated;
    }

    public long getTimeDepart() {
        return timeDepart;
    }

    public void setTimeDepart(long timeDepart) {
        this.timeDepart = timeDepart;
    }

    public Location getLocDest() {
        return locDest;
    }

    public void setLocDest(Location locDest) {
        this.locDest = locDest;
    }

    public Location getLocDepart() {
        return locDepart;
    }

    public void setLocDepart(Location locDepart) {
        this.locDepart = locDepart;
    }

    public static class RideBuilder {

        private String driverId, carModel, carColor, carMake, plate, notes;
        private long timeDepart, timeCreated;
        private Location locDest, locDepart;

        public RideBuilder() {

        }

        public RideBuilder setDriverId(String driverId) {
            this.driverId = driverId;
            return this;
        }

        public Ride build() {
            return new Ride(driverId, carModel, carColor, carMake, plate, notes, timeDepart,
                    timeCreated, locDest, locDepart);
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

        public RideBuilder setPlate(String plate) {
            this.plate = plate;
            return this;
        }

        public RideBuilder setNotes(String notes) {
            this.notes = notes;
            return this;
        }

        public RideBuilder setTimeCreated(long timeCreated) {
            this.timeCreated = timeCreated;
            return this;
        }

        public RideBuilder setTimeDepart(long timeDepart) {
            this.timeDepart = timeDepart;
            return this;
        }

        public RideBuilder setLocDest(Location locDest) {
            this.locDest = locDest;
            return this;
        }

        public RideBuilder setLocDepart(Location locDepart) {
            this.locDepart = locDepart;
            return this;
        }
    }

    public Map<String,Object> toMap(){
        HashMap<String,Object> result = new HashMap<String, Object>();

        result.put("driverid", this.driverId);
        result.put("carmodel", this.carModel);
        result.put("carcolor", this.carColor);
        result.put("carmake", this.carMake);
        result.put("plate", this.plate);
        result.put("notes",this.notes);
        result.put("timedepart", this.timeDepart);
        result.put("timecreated", this.timeCreated);
        result.put("locdest", this.locDest);
        result.put("locdepart", this.locDepart);

        return result;
    }
}
