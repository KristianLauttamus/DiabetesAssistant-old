/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lauttadev.diabetesassistant;

import com.lauttadev.diabetesassistant.models.User;

public class LoginManager {
    private User user = null;
    public boolean loggedIn = false;
    
    public void login(User user){
        if(user != null){
            user = user;
            loggedIn = true;
        }
    }
    
    public void logout(){
        user = null;
        loggedIn = false;
    }
    
    public User getUser(){
        return user;
    }
}
