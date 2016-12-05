package com.fishfillet.shoal.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Stephen on 11/9/2016.
 */


public class Car {
    public String make;
    public String model;
    public String year;
    public String color;
    public String plate;
    public String driverid;

    public Car(){

    }

    public Car(String make, String model, String year, String color, String plate,String driverid){
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.plate = plate;
        this.driverid = driverid;
    }

    public Map<String, Object> toMap(){
        HashMap<String,Object> result = new HashMap<String, Object>();
        result.put("make",make);
        result.put("model",model);
        result.put("year",this.year);
        result.put("color",this.color);
        result.put("plate",this.plate);
        result.put("driverid",this.driverid);
        return result;
    }
    public static class CarBuilder {

        private String make, model, year, color, plate, driverid;

        public CarBuilder() {

        }

        public Car build() {
            return new Car(make, model, year, color, plate, driverid);
        }

        public CarBuilder setDriverId(String driverid) {
            this.driverid = driverid;
            return this;
        }

        public CarBuilder setModel(String model) {
            this.model = model;
            return this;
        }

        public CarBuilder setColor(String color) {
            this.color = color;
            return this;
        }

        public CarBuilder setMake(String make) {
            this.make = make;
            return this;
        }

        public CarBuilder setYear(String year) {
            this.year = year;
            return this;
        }

        public CarBuilder setPlate(String plate) {
            this.plate = plate;
            return this;
        }


    }
}
