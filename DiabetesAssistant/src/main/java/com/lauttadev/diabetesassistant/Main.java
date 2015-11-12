/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lauttadev.diabetesassistant;

import java.util.Scanner;
import org.json.simple.parser.ParseException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hey, welcome to your personal Diabetes Assistant");
        Scanner reader = new Scanner(System.in);
        Assistant assistant = new Assistant();
        LoginManager login = new LoginManager();
        
        while(true){
            try {
                if(!login.loggedIn){
                    login.login(assistant.login());
                }
                System.out.println("");
                System.out.println("Hey, " + login.getUser());
                
                assistant.actions();
                assistant.ask();
                int action = reader.nextInt();
                if(action == 0){
                    login.logout();
                    continue;
                }
                assistant.action(action, login.getUser());
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
