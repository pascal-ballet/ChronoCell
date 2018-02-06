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
    double timeStep=0.0;
    double currentTime=0.0;
    double duration=0.0;
    TreatmentStructure treat=null;
    CellPopulation pop=null;
}
