/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.lauttadev.diabetesassistant;

import java.util.ArrayList;

public class DMCalculator {
    /**
     * Checks the average bloodsugar
     * @param bloodsugars
     * @return 
     */
    public float averageBloodsugar(ArrayList<Integer> bloodsugars){
        float average = 0f;
        
        for(int i = 0; i < bloodsugars.size(); i++){
            average += bloodsugars.get(i);
        }
        
        return average / bloodsugars.size();
    }
}
