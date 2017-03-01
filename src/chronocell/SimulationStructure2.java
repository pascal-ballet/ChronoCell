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
public class SimulationStructure2 {
    double timeStep=0.0;
    double currentTime=0.0;
    int nextTreatment=0;
    // superflu mais pratique pour coder
    int currentSolution=0;
    TreatmentStructure treat=null;
    SolutionStructure[] solution=null;
}
