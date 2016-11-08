package com.fishfillet.shoal;

import android.location.Location;

/**
 * Created by nader on 11/7/16.
 *
 */

public class RideModel {

    private String driver, carModel, carColor, carMake, plate, notes;
    private long timeDepart, timeCreated;
    private Location locDest, locDepart;

    private RideModel(
            String driver,
            String carModel,
            String carColor,
            String carMake,
            String plate,
            String notes,
            long timeDepart,
            long timeCreated,
            Location locDest,
            Location locDepart) {

        this.driver = driver;
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
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
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

        private String driver, carModel, carColor, carMake, plate, notes;
        private long timeDepart, timeCreated;
        private Location locDest, locDepart;

        public RideBuilder() {

        }

        public RideBuilder setDriver(String driver) {
            this.driver = driver;
            return this;
        }

        public RideModel build() {
            return new RideModel(driver, carModel, carColor, carMake, plate, notes, timeDepart,
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
}
