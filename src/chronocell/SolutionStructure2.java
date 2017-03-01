package chronocell;


import chronocell.FunctionStructure;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author goby
 */
public class SolutionStructure2 {
    /// faire remonter phaseName, transitionProba et oneMinusCum dans simulation Structure
    String[] phaseName=null;
    FunctionStructure[] theta=null;
    FunctionStructure[][] transitionProbabilities=null;
    FunctionStructure[][] oneMinusCumulativeFunctions=null;
}
