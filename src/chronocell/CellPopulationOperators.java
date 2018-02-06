/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;

import static chronocell.Operators.survivalProbability;
import static chronocell.SurvivalProbabilities.survivalProbabilities;
import java.time.Clock;
import java.util.ArrayList;
//import static chronocell.SimulationStructureOperators.ApplyTreatment;

/**
 *
 * @author goby
 */
public class CellPopulationOperators {
    public static void ApplyTreatment(double dose,CellPopulation pop,SurvivalDataStructure data){
        System.out.println("traitement au temps "+pop.time+", dose = "+dose);
        ThetaStructure theta=new ThetaStructure();
        theta.startingTime=pop.time;
        for (int i=0;i<pop.dynamics.phaseNb;i++){
            FunctionStructure survival = SurvivalProbabilities.survivalProbabilities(dose, i, pop.dynamics, data);
            survival.name="survival.phase"+i;
            Operators.translateFunction(-pop.time, survival);
            theta.setPhase(i,Operators.createProductFunction(survival,pop.theta.get(pop.currentTheta).getPhase(i)));
        }
        pop.theta.add(theta);
        pop.currentTheta+=1;
    }

//******************************************************************    
    public static double GetPhaseValue(CellPopulation pop, int phase, double T, double t){
        int thetaNumber=0;
//        while (T>pop.theta.get(thetaNumber).startingTime){
////            System.out.println("T="+T+", thetaNumber="+thetaNumber+", starting="+pop.theta.get(thetaNumber).startingTime);
//            thetaNumber+=1;
//        }
// moche, à réparer
        for (int i=1;i<=pop.currentTheta;i++){
            if (T>=pop.theta.get(i).startingTime){
                thetaNumber+=1;
            }
        }
//        System.out.println("theta= "+thetaNumber);
        return pop.theta.get(thetaNumber).getPhase(phase).getFunctionValue(t-T)
              *pop.dynamics.getPhase(phase).solutionFilter.getFunctionValue(t);
    };
    
    public static double GetPopulationSize(CellPopulation pop, double T){
        FunctionStructure temp=null;
        double size=0.0;
        for (int phase=0;phase<pop.dynamics.phaseNb;phase++){
            temp=Operators.createFunction(0.0,pop.dynamics.getPhase(phase).solutionFilter.max,pop.timeStep);
            for (int i=temp.minIndex;i<temp.maxIndex;i++){
                temp.values[i]=GetPhaseValue(pop, phase, T,i*temp.step);
            }
            size+=Operators.IntegrateFunction(temp,temp.min, temp.max);
        }
        return size;
    };
    
//    public static void simulateCellPopulation(CellPopulation pop,TreatmentStructure treat,double time){
//        for (int i=0;i<=(Math.round(time/pop.timeStep));i++){            
//            ComputeOneTimeStep(pop,treat);
////            System.out.println("time="+i*pop.timeStep);
////            population.values[i]=SimulationStructureOperators.GetSimulationPopulationSize(simulation, i*simulation.timeStep);
//        }
//    };
    
    public static CellPopulation copyCellPopulation(CellPopulation pop){
        CellPopulation copy=new CellPopulation();
        copy.size=pop.size;
        copy.dynamics=CellDynamicsOperators.copyDynamics(pop.dynamics);
        for (int i=0;i<pop.theta.size();i++){
            copy.theta.add(ThetaStructureOperators.copyTheta(pop.theta.get(i)));
        }
        copy.currentTheta=pop.currentTheta;
        copy.time=pop.time;
        copy.timeStep=pop.timeStep;
//        copy.pO2=pop.pO2;
//        copy.alpha=pop.alpha;
//        copy.beta=pop.beta;
//        copy.m=pop.m;
//        copy.k=pop.k;
        return copy;
    };
    
}