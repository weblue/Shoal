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

    public Car(String make, String model, String year, String color, String plate){
        this.make = make;
        this.model = model;
        this.year = year;
        this.color = color;
        this.plate = plate;
    }

    public Map<String, Object> toMap(){
        HashMap<String,Object> result = new HashMap<String, Object>();
        result.put("make",this.make = make);
        result.put("model",this.model = model);
        result.put("year",this.year);
        result.put("color",this.color);
        result.put("plate",this.plate);
        return result;
    }
}
