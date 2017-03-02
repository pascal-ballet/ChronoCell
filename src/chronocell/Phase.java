/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;

import java.util.Hashtable;

/**
 *
 * @author goby
 */
public class Phase {
    Hashtable<String,FunctionStructure> density = null;
    Hashtable<String,FunctionStructure> Cumul = null;
    Hashtable<String,FunctionStructure> oneMinCumul = null;
    Hashtable<String,FunctionStructure> alpha = null;
    FunctionStructure thetaFilter = null;
    Hashtable<String,Double> weight = null;
}
