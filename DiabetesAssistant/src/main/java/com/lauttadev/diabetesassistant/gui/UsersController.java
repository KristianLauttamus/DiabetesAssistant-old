/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lauttadev.diabetesassistant.gui;

import com.lauttadev.diabetesassistant.files.Database;
import com.lauttadev.diabetesassistant.models.User;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.json.simple.parser.ParseException;

/**
 * FXML Controller class
 *
 * @author Kristian
 */
public class UsersController implements Initializable {
    private Database db = new Database();
    
    @FXML public TextField nameTextField;
    @FXML public ListView<User> users;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateUsers();
    }
    
    @FXML private void handleCreateUserClick(ActionEvent event){
        if(!nameTextField.getText().equals("")){
            try {
                db.saveUser(new User(nameTextField.getText()));
                updateUsers();
                nameTextField.setText("");
            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    private void updateUsers(){
        ObservableList<User> list = users.getItems();
        
        list.clear();
        
        try {
            list.addAll(db.getUsers());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
