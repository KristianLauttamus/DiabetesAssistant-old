package com.lauttadev.diabetesassistant.gui;

import com.lauttadev.diabetesassistant.LoginManager;
import com.lauttadev.diabetesassistant.files.Database;
import java.awt.Dialog;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.simple.parser.ParseException;

public class ControlPanelController implements Initializable, GUIController {
    private Database db = new Database();
    private ScreenManager screenManager;
    
    @FXML private Label user_name;
    @FXML private TextField bloodSugar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        user_name.setText(LoginManager.getUser().toString());
    }

    @Override
    public void setManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
        
    }
    
    @FXML private void handleBackButton(){
        screenManager.setScreen(ScreenManager.USERS_SCREEN_ID);
    }
    
    @FXML private void handleDeleteUserButton(ActionEvent event) {
        screenManager.setScreen(ScreenManager.DELETE_USER_SCREEN_ID);
    }
    @FXML private void handleDeleteUserInfoButton(ActionEvent event) {
        screenManager.setScreen(ScreenManager.DELETE_USER_INFO_SCREEN_ID);
    }
    
    @FXML private void handleAddBloodSugar(ActionEvent event) throws ParseException{
        if(!bloodSugar.getText().equals("")){
            db.addBloodsugar(bloodSugar.getText(), LoginManager.getUser());
            bloodSugar.setText("");
        }
    }
}
