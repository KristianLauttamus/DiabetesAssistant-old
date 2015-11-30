/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lauttadev.diabetesassistant.gui;

import com.lauttadev.diabetesassistant.LoginManager;
import com.lauttadev.diabetesassistant.files.Database;
import com.lauttadev.diabetesassistant.models.User;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.json.simple.parser.ParseException;

/**
 * FXML Controller class
 *
 * @author Kristian
 */
public class UsersController implements Initializable, GUIController {
    private Database db = new Database();
    private ScreenManager screenManager;
    
    @FXML public TextField name;
    @FXML public ListView<User> users;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateUsers();
    }
    
    /**
     * Handles user creation
     * @param event 
     */
    @FXML private void handleCreateUserClick(ActionEvent event){
        if(!name.getText().equals("")){
            try {
                db.saveUser(new User(name.getText()));
                updateUsers();
                name.setText("");
            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    @FXML private void handleUserSelect(MouseEvent event) throws IOException{
        LoginManager.login(users.getSelectionModel().getSelectedItem());
        
        
        screenManager.loadScreen(ScreenManager.CONTROL_PANEL_SCREEN_ID, ScreenManager.CONTROL_PANEL_FILE);
        screenManager.setScreen(ScreenManager.CONTROL_PANEL_SCREEN_ID);
    }
    
    /**
     * Update users in the list
     */
    private void updateUsers(){
        ObservableList<User> list = users.getItems();
        
        list.clear();
        
        try {
            list.addAll(db.getUsers());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void setManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }
}
