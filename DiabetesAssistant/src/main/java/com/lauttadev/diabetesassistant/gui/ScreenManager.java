/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lauttadev.diabetesassistant.gui;

import com.lauttadev.diabetesassistant.LoginManager;
import java.util.HashMap;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class ScreenManager extends StackPane {
    private final LoginManager loginManager;
    
    // IDS
    public static final String USERS_SCREEN_ID = "users";
    public static final String DELETE_USER_SCREEN_ID = "delete_user";
    public static final String DELETE_USER_INFO_SCREEN_ID = "delete_user_info";
    public static final String CONTROL_PANEL_SCREEN_ID = "control_panel";
    
    // FILES
    public static final String USERS_FILE = "fxml/Users.fxml";
    public static final String DELETE_USER_FILE = "fxml/DeleteUser.fxml";
    public static final String DELETE_USER_INFO_FILE = "fxml/DeleteUserInfo.fxml";
    public static final String CONTROL_PANEL_FILE = "fxml/ControlPanel.fxml";
    
    private final HashMap<String, Node> screens = new HashMap();
    
    public ScreenManager(LoginManager loginManager){
        super();
        
        this.loginManager = loginManager;
    }
    
    /**
     * Add screen
     * @param id
     * @param node 
     */
    public void addScreen(String id, Node node){
        screens.put(id, node);
    }
    
    /**
     * Give screen Node
     * @param id
     * @return 
     */
    public Node getScreen(String id){
        return screens.get(id);
    }
    
    /**
     * Load screens
     */
    public void load(){
        loadScreen(ScreenManager.USERS_SCREEN_ID, ScreenManager.USERS_FILE);
        loadScreen(ScreenManager.DELETE_USER_SCREEN_ID, ScreenManager.DELETE_USER_FILE);
        loadScreen(ScreenManager.DELETE_USER_INFO_SCREEN_ID, ScreenManager.DELETE_USER_INFO_FILE);
        loadScreen(ScreenManager.CONTROL_PANEL_SCREEN_ID, ScreenManager.CONTROL_PANEL_FILE);
    }
    
    /**
     * Load specific screen
     * @param id
     * @param resource
     * @return 
     */
    public boolean loadScreen(String id, String resource){
        try {
            System.out.println(this.getClass().getClassLoader().getResource(resource));
            FXMLLoader loader = new FXMLLoader(this.getClass().getClassLoader().getResource(resource));
            Parent screen = (Parent) loader.load();
            final GUIController guiController = (GUIController) loader.getController();
            guiController.setManager(this);
            addScreen(id, screen);
            
            return true;
        } catch(Exception e) {
            System.out.println(e.getMessage());
            System.out.println("ID: " + id);
            System.out.println("Resource: " + resource);
            return false;
        }
    }
    
    /**
     * Set screen visible
     * @param id
     * @return 
     */
    public boolean setScreen(final String id){
        if(screens.get(id) != null){
            final DoubleProperty opacity = opacityProperty();
            if(!getChildren().isEmpty()){
                Timeline fadeOut = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(100), new EventHandler<ActionEvent>(){

                            @Override
                            public void handle(ActionEvent event) {
                                getChildren().remove(0);
                                getChildren().add(0, screens.get(id));
                                
                                Timeline fadeIn = new Timeline(
                                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                        new KeyFrame(new Duration(800), new KeyValue(opacity, 1.0))
                                );
                                fadeIn.play();
                            }
                            
                        }, new KeyValue(opacity, 0.0))
                );
                fadeOut.play();
            } else {
                setOpacity(0.0);
                getChildren().add(screens.get(id));
                
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(100), new KeyValue(opacity, 1.0))
                );
                fadeIn.play();
            }
            
            return true;
        } else {
            System.out.println("Screen hasn't been loaded");
            return false;
        }
    }
    
    /**
     * Unload screen completely
     * @param id
     * @return 
     */
    public boolean unloadScreen(String id){
        if(screens.remove(id) == null){
            System.out.println("Screen didn't exist");
            
            return false;
        } else {
            return true;
        }
    }
    
    /**
     * Unload every screen and set specific screen visible
     * @param id 
     */
    public void resetWith(String id){
        screens.clear();
        load();
        setScreen(id);
    }
}
