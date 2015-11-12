package com.lauttadev.diabetesassistant.models;

import java.util.Date;

public class BloodSugar {
    private String value;
    private long timestamp;
    
    public BloodSugar(String value, long timestamp) {
        this.value = value;
        this.timestamp = timestamp;
    }
    public BloodSugar(String value){
        this.value = value;
        this.timestamp = new Date().getTime();
    }
    
    public String getValue(){
        return this.value;
    }
    
    public long getTimestamp(){
        return this.timestamp;
    }
    
    @Override
    public String toString(){
        return value + " - " + new Date(timestamp);
    }
}
