/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lauttadev.diabetesassistant.files;

import com.lauttadev.diabetesassistant.models.BloodSugar;
import com.lauttadev.diabetesassistant.models.User;
import java.util.ArrayList;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Database {
    // Retrieve the user preference node for the package com.mycompany
    private Preferences prefs;

    // Preference Keys
    final String USERS_KEY = "users";
    final String BLOODSUGARS_KEY = "bloodsugars";
    
    public Database(){
        prefs = Preferences.userNodeForPackage(com.lauttadev.diabetesassistant.Main.class);
    }
    public Database(String node){
        prefs = Preferences.userRoot().node(node);
    }
    
    /**
     * Get all users from a JSON'd preference
     * @throws ParseException 
     */
    public ArrayList<User> getUsers() throws ParseException{
        JSONArray usersJson = (JSONArray)new JSONParser().parse(prefs.get(USERS_KEY, "[]"));
        ArrayList<User> users = new ArrayList<User>();
        
        for(int i = 0; i < usersJson.size(); i++){
            JSONObject user = (JSONObject)usersJson.get(i);
            
            users.add(new User((String)user.get("name"), (String)user.get("id")));
        }
        
        return users;
    }
    
    /**
     * Save all users to preferences
     * @param users 
     */
    public void saveUsers(JSONArray users){
        prefs.put(USERS_KEY, users.toJSONString());
    }
    
    /**
     * Save users from ArrayList
     * @param ArrayList<User> users 
     */
    public void setUsers(ArrayList<User> newUsers){
        JSONArray users = new JSONArray();
        
        for(int i = 0; i < newUsers.size(); i++){
            User user = newUsers.get(i);
            
            JSONObject userObj = new JSONObject();
            userObj.put("id", user.getId());
            userObj.put("name", user.getName());
            
            users.add(userObj);
        }
        
        this.saveUsers(users);
    }
    
    /**
     * Save a single user
     * @param user 
     */
    public void saveUser(User user) throws ParseException{
        ArrayList<User> users = this.getUsers();
        boolean found = false;
        
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getId() == user.getId()){
                users.get(i).setName(user.getName());
                found = true;
            }
        }
        
        if(!found){
            users.add(user);
        }
        
        this.setUsers(users);
    }
    
    /**
     * Update a single user
     * @param user 
     */
    public void updateUser(User user) throws ParseException{
        ArrayList<User> users = this.getUsers();
        
        for(int i = 0; i < users.size(); i++){
            if(users.get(i).getId() == user.getId()){
                users.get(i).setName(user.getName());
            }
        }
        
        this.setUsers(users);
    }
    
    /**
     * Get BloodSugars for a single user
     * @param user
     * @return 
     */
    public ArrayList<BloodSugar> getBloodSugars(User user) throws ParseException {
        JSONObject bloodSugarsJson = (JSONObject)new JSONParser().parse(prefs.get(BLOODSUGARS_KEY, "{}"));
        
        // Check if user is found in the BloodSugars Object
        if(bloodSugarsJson.containsKey(user.getId())){
            ArrayList<BloodSugar> bloodSugars = new ArrayList<BloodSugar>();
            JSONArray bloodSugarsTemp = (JSONArray)bloodSugarsJson.get(user.getId());
            
            for(int i = 0; i < bloodSugarsTemp.size(); i++){
                JSONObject bloodSugar = (JSONObject)bloodSugarsTemp.get(i);
                
                bloodSugars.add(new BloodSugar((String)bloodSugar.get("value"), (long)bloodSugar.get("timestamp")));
            }
            
            return bloodSugars;
        }
        
        return new ArrayList<BloodSugar>();
    }
    
    /**
     * Add BloodSugar for the User
     * @param bs, 
     * @param user 
     */
    public void addBloodsugar(String bs, User user) throws ParseException {
        ArrayList<BloodSugar> bloodSugars = this.getBloodSugars(user);
        BloodSugar bloodSugar = new BloodSugar(bs);
        
        bloodSugars.add(bloodSugar);
        
        this.saveBloodSugars(bloodSugars, user);
    }
    
    /**
     * Save users Bloodsugar
     * @param user
     * @return 
     */
    public void saveBloodSugars(ArrayList<BloodSugar> bloodSugars, User user) throws ParseException {
        JSONObject bloodSugarsJson = (JSONObject)new JSONParser().parse(prefs.get(BLOODSUGARS_KEY, "{}"));
        
        JSONArray bloodSugarsArray = new JSONArray();
        for(int i = 0; i < bloodSugars.size(); i++){
            // Create JSONObject from BloodSugar
            JSONObject bloodSugar = new JSONObject();
            bloodSugar.put("value", bloodSugars.get(i).getValue());
            bloodSugar.put("timestamp", bloodSugars.get(i).getTimestamp());
            
            // Add it to the array
            bloodSugarsArray.add(bloodSugar);
        }
        bloodSugarsJson.put(user.getId(), bloodSugarsArray);
        
        // Overwrite all the values
        prefs.put(BLOODSUGARS_KEY, bloodSugarsJson.toJSONString());
    }
    
    public void deleteNode() throws BackingStoreException{
        prefs.removeNode();
    }
}
