/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;

import static chronocell.Operators.createFunction;
import static chronocell.Operators.survivalProbability;

/**
 *
 * @author goby
 */
public class SimulationStructureOperators {
    
     public static void ApplyTreatment(int treatNb,SimulationStructure simulation){
        System.err.format("traitement %d à %f h\n", treatNb,simulation.currentTime);
//        simulation.theta[simulation.currentSolution+1]=new ThetaStructure();
        simulation.theta[simulation.currentSolution+1].dyn=simulation.theta[simulation.currentSolution].dyn;
        simulation.currentSolution+=1;
//        FunctionStructure ind=new FunctionStructure();
        for (int i=0;i<simulation.theta[simulation.currentSolution].phaseNb;i++){
            FunctionStructure temp = Operators.copyFunction(simulation.theta[simulation.currentSolution-1].getPhase(i));
//            if (i==2){Operators.plotFunction(temp);
//            try {Thread.sleep(10000);} catch(InterruptedException ex) {    Thread.currentThread().interrupt();}
//            Operators.plotFunction(Operators.AffineFunctionTransformation(survivalProbability.op(simulation.treat.doses[treatNb],i), 0.0,temp));
//            try {Thread.sleep(10000);} catch(InterruptedException ex) {    Thread.currentThread().interrupt();}}
            temp=Operators.AffineFunctionTransformation(survivalProbability.op(simulation.treat.doses[treatNb],i), 0.0,temp);
//            ind=createFunction(simulation.theta[simulation.currentSolution-1].dyn.getPhase(i).SolutionFilter.min,simulation.theta[simulation.currentSolution-1].dyn.getPhase(i).SolutionFilter.max,simulation.theta[simulation.currentSolution-1].dyn.getPhase(i).SolutionFilter.step);
//            simulation.theta[simulation.currentSolution].setPhase(i,Operators.MultiplyFunctions(ind, simulation.theta[simulation.currentSolution-1].getPhase(i)));
//            simulation.theta[simulation.currentSolution].setPhase(i,simulation.theta[simulation.currentSolution-1].getPhase(i));
//           if (i==2){Operators.plotFunction(temp);
//           try {Thread.sleep(10000);} catch(InterruptedException ex) {    Thread.currentThread().interrupt();}} 
           simulation.theta[simulation.currentSolution].setPhase(i,temp);
//           if (i==2){Operators.plotFunction(simulation.theta[simulation.currentSolution].getPhase(i));}
        }
//        try {Thread.sleep(1000000);} catch(InterruptedException ex) {    Thread.currentThread().interrupt();}
    }
    
    public static void ComputeSimulationNextValue(SimulationStructure simulation){
//            System.err.format("current.time= %f, treatTime= %f \n",simulation.currentTime, simulation.treat.times[simulation.nextTreatment]);
            if (simulation.currentTime>=simulation.treat.times[simulation.nextTreatment]){
                ApplyTreatment(simulation.nextTreatment,simulation);
                simulation.nextTreatment+=1;
            }
//        ComputeSolutionNextValue(simulation.solution[simulation.nextTreatment]);
        ThetaStructureOperators.ComputeThetaNextValues(simulation.theta[simulation.nextTreatment]);
        // unifier les step entre toutes les phases
        simulation.currentTime+=simulation.timeStep;
    }
    
    public static double GetSimulationValue(SimulationStructure simulation, int phase, double T, double s){
        int solNumber=0;
        for (int i=0;i<simulation.currentSolution;i++){
            if (T>=simulation.treat.times[i]){
                solNumber+=1;
            }
        }
//        System.out.println("solution n : "+solNumber);
        return Operators.GetFunctionValue(simulation.theta[solNumber].getPhase(phase),s-T)*Operators.GetFunctionValue(simulation.theta[solNumber].dyn.getPhase(phase).SolutionFilter,s);
    };
}
