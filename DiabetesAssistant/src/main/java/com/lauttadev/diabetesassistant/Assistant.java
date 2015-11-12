/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lauttadev.diabetesassistant;

import com.lauttadev.diabetesassistant.files.Database;
import com.lauttadev.diabetesassistant.models.BloodSugar;
import com.lauttadev.diabetesassistant.models.User;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;

public class Assistant {
    private Scanner reader;
    private Database db = new Database();
    
    public Assistant(){
        reader = new Scanner(System.in);
    }
    public Assistant(Scanner scanner){
        reader = scanner;
    }
    
    /**
     * Login actions with Assistant
     * @return User
     * @throws ParseException 
     */
    public User login() throws ParseException{
        ArrayList<User> users = db.getUsers();
        User selected = null;
        
        while(selected == null){
            System.out.println("0 - Create a new user");
            for(int i = 0; i < users.size(); i++){
                User user = users.get(i);
                System.out.println((i+1) + " - " + user.getName());
            }
            
            int selection = reader.nextInt();
            if(selection == 0){
                selected = this.createUser();
                db.saveUser(selected);
            } else if(selection > 0 && selection <= users.size()) {
                selected = users.get(selection - 1);
            }
        }
        
        return selected;
    }
    
    /**
     * Create a new user
     * @return 
     */
    private User createUser(){
        System.out.println("Who are you?");
        String name = reader.nextLine();

        return new User(name);
    }
    
    /**
     * Print actions
     */
    public void actions(){
        System.out.println("1 - Tell your BloodSugar");
        System.out.println("2 - Print latest BloodSugar");
        System.out.println("0 - Logout");
        System.out.println("");
    }
    
    /**
     * Print the question for action
     */
    public void ask(){
        System.out.println("What would you like to do?");
    }
    
    /**
     * Perform action
     * @param action
     * @param user 
     */
    public void action(int action, User user){
        switch(action){
            case 1:
                System.out.print("Insert Bloodsugar (example: xx.xx): ");
                
                String bloodsugar = reader.nextLine();
                while(!bloodsugar.matches("\\d{1,2}\\.?\\d{0,2}") &&
                        !bloodsugar.toUpperCase().equals("HI") &&
                        !bloodsugar.toUpperCase().equals("LO")){
                     bloodsugar = reader.nextLine();
                }
                
                {
                    try {
                        db.addBloodsugar(bloodsugar, user);
                    } catch (ParseException ex) {
                        Logger.getLogger(Assistant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            case 2:
                {
                    try {
                        ArrayList<BloodSugar> bloodSugars = db.getBloodSugars(user);
                        if(bloodSugars.size() > 0){
                            System.out.println("Your latest Bloodsugar: ");
                            System.out.println(bloodSugars.get(bloodSugars.size()-1));
                            System.out.println("");
                        } else {
                            System.out.println("No BloodSugars in the records");
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(Assistant.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                                        
                break;
                
            default: 
                System.out.println("No input found");
                System.out.println("");
        }
    }
}
