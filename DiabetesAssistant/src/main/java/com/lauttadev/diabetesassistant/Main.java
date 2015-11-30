/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lauttadev.diabetesassistant;

import com.lauttadev.diabetesassistant.gui.ScreenManager;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException{
        LoginManager loginManager = new LoginManager();
        
        ScreenManager manager = new ScreenManager(loginManager);
        
        // Load screens
        manager.load();
        
        // Set the starting screen
        manager.setScreen(ScreenManager.USERS_SCREEN_ID);
        
        Group root = new Group();
        root.getChildren().addAll(manager);
        
        primaryStage.setTitle("Diabetes Assistant");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("/img/icon.png"));
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }
}
