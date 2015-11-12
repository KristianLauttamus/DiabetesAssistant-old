/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lauttadev.diabetesassistant.models;

import java.util.UUID;

public class User {
    private String name;
    private String id;
    
    public User(String name, String id){
        this.name = name;
        this.id = id;
    }
    public User(String name){
        this.name = name;
        this.id = UUID.randomUUID().toString();
    }
    
    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    
    public String getId(){
        return this.id;
    }
    
    @Override
    public String toString(){
        return this.name;
    }
}
