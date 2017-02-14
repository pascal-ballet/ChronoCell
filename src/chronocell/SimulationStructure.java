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
    double currentTime=0.0;
    int currentTreatment=0;
    // superflu mais pratique pôur coder
    int currentSolution=0;
    TreatmentStructure treat=null;
    SolutionStructure[] solution=null;
    private double G(int phase, double T, double s){
        return T-s;
    };
}
