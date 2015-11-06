/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lauttadev.diabetesassistant;

import com.lauttadev.diabetesassistant.files.Database;
import com.lauttadev.diabetesassistant.models.User;
import java.util.ArrayList;
import java.util.Scanner;
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
        String selection = "";
        
        while(selection == ""){
            if(users.size() > 0){
                for(int i = 0; i < users.size(); i++){
                    User user = users.get(i);
                    System.out.println(i + " - " + user.getName());
                }
                System.out.println(users.size() + " - Create a new user");
                selection = "" + reader.nextInt();
                
                return users.get(reader.nextInt());
            } else {
                System.out.println("Who are you?");
                selection = reader.nextLine();
                
                User newUser = new User(selection);
                db.saveUser(newUser);
                
                return newUser;
            }
        }
        
        return null;
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
                
                db.saveBloodSugar(bloodsugar, user);
                break;
            case 2:
                System.out.println("Your latest Bloodsugar: " + db.getLatestBloodSugar(user));
                System.out.println("");
                break;
                
            default: 
                System.out.println("No input found");
                System.out.println("");
        }
    }
}
