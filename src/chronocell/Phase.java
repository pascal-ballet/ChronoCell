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
    String name=null;
    Hashtable<String,FunctionStructure> density = new Hashtable<String,FunctionStructure>();
    Hashtable<String,FunctionStructure> cumul = new Hashtable<String,FunctionStructure>();
    Hashtable<String,FunctionStructure> oneMinCumul = new Hashtable<String,FunctionStructure>();
    Hashtable<String,FunctionStructure> alpha = new Hashtable<String,FunctionStructure>();
    FunctionStructure solutionFilter = new FunctionStructure();
    FunctionStructure thetaConvolution = new FunctionStructure();
    FunctionStructure timeToNextPhaseDensity = new FunctionStructure();
    FunctionStructure timeToNextPhaseOneMinCumul = new FunctionStructure();
}