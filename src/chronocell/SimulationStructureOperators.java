/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;

import static chronocell.CellPopulationOperators.ApplyTreatment;
import static chronocell.Operators.createFunction;
import static chronocell.Operators.survivalProbability;

/**
 *
 * @author goby
 */
public class SimulationStructureOperators {
    public static void run(SimulationStructure simu){
        for (int i=0;i<simu.duration/simu.timeStep;i++){   
            ComputeOneTimeStep(simu);
        }
    };
    
    public static void ComputeOneTimeStep(SimulationStructure simu){
            if (simu.currentTime>=simu.treat.times[simu.treat.nextDose]){
                ApplyTreatment(simu.treat.doses[simu.treat.nextDose],simu.pop,ChronoCell._survivalData);
                simu.treat.nextDose+=1;
            }
        simu.currentTime+=simu.timeStep;
        while (simu.pop.time<simu.currentTime){
            ThetaStructureOperators.ComputeThetaNextValues(simu.pop);
        }
        // unifier les step entre toutes les phases ?
    }
    
    public static void plotSimulation(SimulationStructure simu){
        GUIPopulation win =new GUIPopulation();
        win.SetFunction(simu.pop);
        win.setVisible(true);
    };
    
    public static void plotPopulationEvolution(SimulationStructure simu){
        FunctionStructure populationSize=Operators.createFunction(0.0, simu.duration, simu.timeStep);
        for (int i=0;i<=Math.round(simu.duration/simu.timeStep);i++){
            populationSize.values[i]=CellPopulationOperators.GetPopulationSize(simu.pop, simu.timeStep*i);
        }
        Operators.plotFunction(populationSize);
    };
    
    
    public static double cumulatedPopulation(SimulationStructure simu){
        FunctionStructure populationSize=Operators.createFunction(0.0, simu.duration, simu.timeStep);
        for (int i=0;i<=Math.round(simu.duration/simu.timeStep);i++){
            populationSize.values[i]=CellPopulationOperators.GetPopulationSize(simu.pop, simu.timeStep*i);
        }
        return Operators.IntegrateFunction(populationSize, populationSize.min, populationSize.max);
    };
}
