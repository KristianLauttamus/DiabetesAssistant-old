package com.lauttadev.diabetesassistant.files;

import com.lauttadev.diabetesassistant.models.BloodSugar;
import com.lauttadev.diabetesassistant.models.User;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Database {
    private final String users_file_location = System.getProperty("user.home") + "/dma_users_data.json";
    private final String bloodsugars_file_location = System.getProperty("user.home") + "/dma_bloodsugars_data.json";
    
    /**
     * Get the actual file that has all the users
     * @return 
     */
    public JSONArray getUsersData(){
        try (FileReader file = new FileReader(users_file_location)) {
            JSONParser parser = new JSONParser();
            
            return (JSONArray)parser.parse(file);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            
            return new JSONArray();
        }
    }
    
    /**
     * Get all users from a JSON'd preference
     * @return 
     * @throws ParseException 
     */
    public ArrayList<User> getUsers() throws ParseException{
        JSONArray allData = getUsersData();
        ArrayList<User> users = new ArrayList<>();
        
        for (Object data : allData) {
            JSONObject user = (JSONObject) data;
            users.add(new User((String)user.get("id"), (String)user.get("name")));
        }
        
        return users;
    }
    
    /**
     * Save all users to preferences
     * @param users 
     */
    public void saveJSONUsers(JSONArray users){
        try (FileWriter file = new FileWriter(users_file_location)) {
            file.write(users.toJSONString());
            file.close();
        } catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Save users from ArrayList
     * @param newUsers
     */
    public void saveUsers(ArrayList<User> newUsers){
        JSONArray users = new JSONArray();
        
        for (User user : newUsers) {
            JSONObject userObj = new JSONObject();
            userObj.put("id", user.getId());
            userObj.put("name", user.getName());
            
            users.add(userObj);
        }
        
        this.saveJSONUsers(users);
    }
    
    /**
     * Save a single user
     * @param newUser
     * @throws org.json.simple.parser.ParseException 
     */
    public void saveUser(User newUser) throws ParseException {
        ArrayList<User> users = this.getUsers();
        boolean found = false;
        
        for (User user : users) {
            if (user.getId().equals(newUser.getId())) {
                user.setName(newUser.getName());
                found = true;
            }
        }
        
        if(!found){
            users.add(newUser);
        }
        
        this.saveUsers(users);
    }
    
    /**
     * Update a single user
     * @param user 
     * @throws org.json.simple.parser.ParseException 
     */
    public void updateUser(User user) throws ParseException{
        ArrayList<User> users = this.getUsers();
        
        for (User findUser : users) {
            if (findUser.getId().equals(user.getId())) {
                findUser.setName(user.getName());
            }
        }
        
        this.saveUsers(users);
    }
    
    /**
     * Delete a single user
     * @param user 
     * @throws org.json.simple.parser.ParseException 
     */
    public void deleteUser(User user) throws ParseException{
        ArrayList<User> oldUsers = this.getUsers();
        ArrayList<User> users = new ArrayList<>();
        
        for (User oldUser : oldUsers) {
            if (!oldUser.getId().equals(user.getId())) {
                users.add(oldUser);
            }
        }
        
        this.saveUsers(users);
    }
    
    /**
     * Delete a single user
     * @param user 
     * @throws org.json.simple.parser.ParseException 
     */
    public void deleteUserInfo(User user) throws ParseException{
        JSONObject bloodSugarsObject = this.getBloodSugarsData();
        
        if(bloodSugarsObject.containsKey(user.getId())){
            bloodSugarsObject.remove(user.getId());

            // Overwrite all the values
            try(FileWriter file = new FileWriter(this.bloodsugars_file_location)){
                file.write(bloodSugarsObject.toJSONString());
                file.close();
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    
    /**
     * Get the actual file that has all the bloodsugars for each user
     * @return 
     */
    public JSONObject getBloodSugarsData(){
        try (FileReader file = new FileReader(bloodsugars_file_location)) {
            JSONParser parser = new JSONParser();
            return (JSONObject)parser.parse(file);
        } catch(Exception ex){
            System.out.println(ex.getMessage());
            return new JSONObject();
        }
    }
    
    /**
     * Get BloodSugars for a single user
     * @param user
     * @return 
     */
    public ArrayList<BloodSugar> getBloodSugars(User user) {
        JSONObject bloodSugarsJson = getBloodSugarsData();
        
        // Check if user is found in the BloodSugars Object
        if(bloodSugarsJson == null || bloodSugarsJson.containsKey(user.getId())){
            ArrayList<BloodSugar> bloodSugars = new ArrayList<>();
            JSONArray bloodSugarsTempData = (JSONArray)bloodSugarsJson.get(user.getId());
            
            for (Object bloodSugarsTemp : bloodSugarsTempData) {
                JSONObject bloodSugar = (JSONObject) bloodSugarsTemp;
                bloodSugars.add(new BloodSugar((String)bloodSugar.get("value"), (long)bloodSugar.get("timestamp")));
            }
            
            return bloodSugars;
        }
        
        return new ArrayList<>();
    }
    
    /**
     * Add BloodSugar for the User
     * @param newBloodSugar 
     * @param user 
     * @throws org.json.simple.parser.ParseException 
     */
    public void addBloodsugar(String newBloodSugar, User user) throws ParseException {
        ArrayList<BloodSugar> bloodSugars = this.getBloodSugars(user);
        BloodSugar bloodSugar = new BloodSugar(newBloodSugar);
        
        bloodSugars.add(bloodSugar);
        
        this.saveBloodSugars(bloodSugars, user);
    }
    
    /**
     * Save users Bloodsugar
     * @param bloodSugars
     * @param user
     * @throws org.json.simple.parser.ParseException 
     */
    public void saveBloodSugars(ArrayList<BloodSugar> bloodSugars, User user) throws ParseException {
        JSONObject bloodSugarsJson = this.getBloodSugarsData();
        
        JSONArray bloodSugarsArray = new JSONArray();
        for (BloodSugar bloodSugar : bloodSugars) {
            // Create JSONObject from BloodSugar
            JSONObject newBloodSugar = new JSONObject();
            newBloodSugar.put("value", bloodSugar.getValue());
            newBloodSugar.put("timestamp", bloodSugar.getTimestamp());
            // Add it to the array
            bloodSugarsArray.add(newBloodSugar);
        }
        bloodSugarsJson.put(user.getId(), bloodSugarsArray);
        
        // Overwrite all the values
        try(FileWriter file = new FileWriter(this.bloodsugars_file_location)){
            file.write(bloodSugarsJson.toJSONString());
            file.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    /**
     * Destroy everything
     */
    public void destroy() {
        try(FileWriter file = new FileWriter(this.users_file_location)){
            file.write("[]");
            file.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
        
        try(FileWriter file = new FileWriter(this.bloodsugars_file_location)){
            file.write("{}");
            file.close();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
