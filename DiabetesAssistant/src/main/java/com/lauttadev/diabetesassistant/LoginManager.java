/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lauttadev.diabetesassistant;

import com.lauttadev.diabetesassistant.models.User;

public class LoginManager {
    public static User user = null;
    
    public static void login(User user){
        if(user != null){
            LoginManager.user = user;
        }
    }
    
    public static void logout(){
        LoginManager.user = null;
    }
    
    public static User getUser(){
        return LoginManager.user;
    }
}
