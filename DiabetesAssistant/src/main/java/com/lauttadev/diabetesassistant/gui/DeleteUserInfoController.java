/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lauttadev.diabetesassistant.gui;

import com.lauttadev.diabetesassistant.LoginManager;
import com.lauttadev.diabetesassistant.files.Database;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.json.simple.parser.ParseException;

public class DeleteUserInfoController implements Initializable, GUIController{
    private Database db = new Database();
    private ScreenManager screenManager;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @Override
    public void setManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }
    
    @FXML private void handleAgreeDelete() throws ParseException{
        db.deleteUserInfo(LoginManager.getUser());
        screenManager.setScreen(ScreenManager.CONTROL_PANEL_SCREEN_ID);
    }
    
    @FXML private void handleDeclineDelete(){
        screenManager.setScreen(ScreenManager.CONTROL_PANEL_SCREEN_ID);
    }
}
