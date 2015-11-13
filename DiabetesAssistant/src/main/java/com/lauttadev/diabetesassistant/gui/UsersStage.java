/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lauttadev.diabetesassistant.gui;

import com.lauttadev.diabetesassistant.models.User;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class UsersStage extends Stage {
    private Text welcome = new Text("Hey, welcome to your personal Diabetes Assistant");
    private HBox space = new HBox();

    public UsersStage(ArrayList<User> users){
        
    }
    
    public void start(){
        space.getChildren().add(welcome);
        this.setScene(new Scene(space, 300, 300));
        this.show();
    }
}
