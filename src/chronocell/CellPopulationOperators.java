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
            FunctionStructure temp = Operators.createFunctionCopy(pop.theta.get(pop.currentTheta).getPhase(i));
            // on crée la fonction de survie
            //FunctionStructure survival = Operators.createFunction(temp.min, temp.max, temp.step);
            // Pour l'instant, on transmet le numero de la phase et toute la dynamique car certaines phases mènent à une phase, d'autre à deux
            FunctionStructure survival = SurvivalProbabilities.survivalProbabilities(dose, i, pop.dynamics, data);
//            FunctionStructure survival=Operators.createFunction(0.0, 0.0, 0.1, "survie");
            survival.name="survival.phase"+i;
            Operators.translateFunction(-pop.time, survival);
//            if (i==4){
//              Operators.PrintFunction(temp,false);
//            
//            }
            FunctionStructure temp2=Operators.createProductFunction(survival,temp);
            temp2.name="temp2";
//            if (i==4){
//              Operators.PrintFunction(temp2,false);
            Operators.plotFunction(survival);  
//            }
            theta.setPhase(i,temp2);
        }
        pop.theta.add(theta);
        pop.currentTheta+=1;
    }
    
    public static void ComputeOneTimeStep(CellPopulation pop,TreatmentStructure treat){
//        System.out.println("nextDOse="+treat.nextDose);
            if (pop.time>=treat.times[treat.nextDose]){
                ApplyTreatment(treat.doses[treat.nextDose],pop,ChronoCell._survivalData);
                treat.nextDose+=1;
            }
        ThetaStructureOperators.ComputeThetaNextValues(pop.theta.get(pop.currentTheta),pop.dynamics);
        // unifier les step entre toutes les phases
        pop.time+=pop.timeStep;
    }
    
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
        return pop.theta.get(thetaNumber).getPhase(phase).GetFunctionValue(t-T)
              *pop.dynamics.getPhase(phase).solutionFilter.GetFunctionValue(t);
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
    
    public static void simulateCellPopulation(CellPopulation pop,TreatmentStructure treat,double time){
        for (int i=0;i<=(Math.round(time/pop.timeStep));i++){            
            ComputeOneTimeStep(pop,treat);
//            System.out.println("time="+i*pop.timeStep);
//            population.values[i]=SimulationStructureOperators.GetSimulationPopulationSize(simulation, i*simulation.timeStep);
        }
    };
    
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
