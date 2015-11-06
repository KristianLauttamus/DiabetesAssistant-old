/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lauttadev.diabetesassistant.files;

import com.lauttadev.diabetesassistant.models.User;
import java.util.ArrayList;
import java.util.prefs.Preferences;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Database {
    // Retrieve the user preference node for the package com.mycompany
    Preferences prefs = Preferences.userNodeForPackage(com.lauttadev.diabetesassistant.Main.class);

    // Preference Keys
    final String USERS_KEY = "users";
    
    /**
     * Get all users from a JSON'd preference
     * @throws ParseException 
     */
    public ArrayList<User> getUsers() throws ParseException{
        JSONArray usersJson = (JSONArray)new JSONParser().parse(prefs.get(USERS_KEY, "[]"));
        
        return new ArrayList<User>();
    }
    
    /**
     * Take these users and set them into preferences
     * @param users 
     */
    public void setUsers(ArrayList<User> newUsers){
        JSONArray users = new JSONArray();
        
        for(int i = 0; i < newUsers.size(); i++){
            User user = newUsers.get(i);
            
            JSONObject userObj = new JSONObject();
            userObj.put("Name", user.getName());
            
            users.add(userObj);
        }
        
        prefs.put(USERS_KEY, users.toJSONString());
    }
    
    /**
     * TODO Save user
     * @param user 
     */
    public void saveUser(User user){
        
    }
    
    /**
     * TODO Save BloodSugar for the User
     * @param bloodSugar, 
     * @param user 
     */
    public void saveBloodSugar(String bloodSugar, User user) {
        
    }
    
    /**
     * TODO Save user Bloodsugar
     * @param user
     * @return 
     */
    public String getLatestBloodSugar(User user) {
        return "10.1";
    }
}
