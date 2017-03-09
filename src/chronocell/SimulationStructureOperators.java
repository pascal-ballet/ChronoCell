/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;

import static chronocell.Operators.ComputeSolutionNextValue;
import static chronocell.Operators.createFunction;
import static chronocell.Operators.createSolutionStructure;
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
        FunctionStructure ind=new FunctionStructure();
        for (int i=0;i<simulation.theta[simulation.currentSolution].phaseNb;i++){
            ind=createFunction(simulation.theta[simulation.currentSolution-1].dyn.getPhase(i).SolutionFilter.min,simulation.theta[simulation.currentSolution-1].dyn.getPhase(i).SolutionFilter.max,simulation.theta[simulation.currentSolution-1].dyn.getPhase(i).SolutionFilter.step);
            simulation.theta[simulation.currentSolution].setPhase(i,Operators.MultiplyFunctions(ind, simulation.theta[simulation.currentSolution-1].getPhase(i)));
            for (int j=simulation.theta[simulation.currentSolution].getPhase(i).minIndex;j<simulation.theta[simulation.currentSolution].getPhase(i).maxIndex;j++){
                simulation.theta[simulation.currentSolution].getPhase(i).values[j]*=survivalProbability.op(simulation.treat.doses[treatNb],i);
            }
        }
        
        
//        simulation.solution[simulation.currentSolution+1].probaDeathBeforeG1=simulation.solution[simulation.currentSolution].probaDeathBeforeG1;
//        simulation.solution[simulation.currentSolution+1].probaSBeforeG0=simulation.solution[simulation.currentSolution].probaSBeforeG0;
//        simulation.solution[simulation.currentSolution+1]=createSolutionStructure(simulation.solution[simulation.currentSolution].phaseName.length);
//        simulation.solution[simulation.currentSolution+1].phaseName=simulation.solution[simulation.currentSolution].phaseName;
//        simulation.solution[simulation.currentSolution+1].transitionProbabilities=simulation.solution[simulation.currentSolution].transitionProbabilities;
//        simulation.solution[simulation.currentSolution+1].oneMinusCumulativeFunctions=simulation.solution[simulation.currentSolution].oneMinusCumulativeFunctions;
//        
//        for (int i=0;i<simulation.solution[simulation.currentSolution].phaseName.length;i++){
//            simulation.solution[simulation.currentSolution].theta[i]=createFunction(simulation.solution[simulation.currentSolution].transitionProbabilities[i].min,simulation.solution[treatNb].transitionProbabilities[i].max, simulation.solution[treatNb].transitionProbabilities[i].step);
//            simulation.solution[simulation.currentSolution].theta[i].min=simulation.solution[simulation.currentSolution-1].theta[i].min;
//            simulation.solution[simulation.currentSolution].theta[i].max=simulation.solution[treatNb].theta[i].min+simulation.solution[simulation.currentSolution].theta[i].max;  
////            PrintFunction("theta",simulation.solution[simulation.currentSolution].theta[i] , false);
//            for (int j=simulation.solution[simulation.currentSolution].theta[i].minIndex;j<simulation.solution[simulation.currentSolution].theta[i].maxIndex;j++){
//               simulation.solution[simulation.currentSolution].theta[i].values[j]=simulation.solution[simulation.currentSolution-1].theta[i].values[simulation.solution[simulation.currentSolution-1].theta[i].minIndex+j]*survivalProbability.op(simulation.treat.doses[treatNb],i);
//            }
//        }
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
        return Operators.GetFunctionValue(simulation.theta[solNumber].getPhase(phase),s-T)*Operators.GetFunctionValue(simulation.theta[solNumber].dyn.getPhase(phase).SolutionFilter,s);
////        System.err.format("lasttreat=%d \n", solNumber);
//        if (phase!=1){
//            if (T<= simulation.treat.times[solNumber]+simulation.solution[solNumber].transitionProbabilities[phase].max){
//                return Operators.GetFunctionValue(simulation.solution[solNumber].theta[phase], s-T);
//            }
//            else{
//                return Operators.GetFunctionValue(simulation.solution[solNumber].theta[phase], s-T)*Operators.GetFunctionValue(simulation.solution[solNumber].oneMinusCumulativeFunctions[phase], s);
//            }
//        }
//        else{
//            if (T<= simulation.treat.times[solNumber]+simulation.solution[solNumber].transitionProbabilities[phase].max){
//                return Operators.GetFunctionValue(simulation.solution[solNumber].theta[phase], s-T);
//            }
//            else{
//                return Operators.GetFunctionValue(simulation.solution[solNumber].theta[phase], s-T)*Operators.GetFunctionValue(simulation.solution[solNumber].oneMinusCumulativeFunctions[phase], s)*Operators.GetFunctionValue(simulation.solution[solNumber].oneMinusCumulativeFunctions[5], s);
//            }
//        }
    };
}
