/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lauttadev.diabetesassistant.gui;

import com.lauttadev.diabetesassistant.LoginManager;
import com.lauttadev.diabetesassistant.files.Database;
import com.lauttadev.diabetesassistant.models.BloodSugar;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

public class BloodSugarsChartController implements Initializable, GUIController{
    private Database db = new Database();
    private ScreenManager screenManager;
    
    @FXML private LineChart chart;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        XYChart.Series series = new XYChart.Series();
        
        for(BloodSugar bloodSugar : db.getBloodSugars(LoginManager.getUser())){
            XYChart.Data data = new XYChart.Data(bloodSugar.getTimestamp(), bloodSugar.getValue());
            series.getData().add(data);
        }
        
        chart.getData().add(series);
    }

    @Override
    public void setManager(ScreenManager screenManager) {
        this.screenManager = screenManager;
    }
}
