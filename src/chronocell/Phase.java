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
    FunctionStructure SolutionFilter = new FunctionStructure();
    FunctionStructure ThetaConvolution = new FunctionStructure();
    Hashtable<String,Double> weight = new Hashtable<String,Double>();
}