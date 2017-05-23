/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;

/**
 *
 * @author goby
 */
public class SimulationStructure {
    // parameters (to figure elsewhere ?)
    double pO2infl=26.8;
    double C=1.0;
    double B=0.075;
    double alpha=0.044,beta=0.089;
    double m=3.0,k=3.0;
    
    double pO2=5.0;
    double timeStep=0.0;
    double currentTime=0.0;
    int nextTreatment=0;
    // superflu mais pratique pôur coder
    int currentSolution=0;
    TreatmentStructure treat=null;
    ThetaStructure[] theta=null;
    CellPopulation pop=null;
    }
