/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


// Todo :   * Implement constant solution for initial condition (if extinction, take Laplace transform for lambda=0).
//          * CRUCIAL : check dependency on timestep ! -> solve dirac problem
//          *  write properly solutions for initial condition to solve the shift of time between bifurcations
//          * improve to transition probabilities that can evolve along time and depend on pO2
//          * implement population size calculus
//          * check oprators that copy functions or not
//          * check every loop that involves <fct.maxIndex
package chronocell;

import static chronocell.Operators.IntegrateFunction;
import static chronocell.Operators.MultiplyFunctions;
import static chronocell.Operators.PowerOfFunction;
import static chronocell.Operators.TranslateFunction;
import java.util.ArrayList;
//import java.math.BigDecimal;
//import java.math.BigInteger;
//import java.math.MathContext;
//import java.math.RoundingMode;
/**
 *
 * @author pascal
 */
public class ChronoCell {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Numbers.minStep=0.00001;
        
        
        SimulationStructure simulation1=new SimulationStructure();
        SimulationStructure simulation2=new SimulationStructure();
        
        simulation1.timeStep=0.1;
        simulation2.timeStep=0.1;
        
        // Dynamique initiale des phases
        double support0=30.0,support2=15.0;
        double step=simulation1.timeStep;
            // G0->Death
            FunctionStructure G0ToDeath=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(30.0),step); 
    //        Operators.MapFunctionValues(G0ToDeath,0.0,8.0,Operators.gaussian,7.0,1.0);
            Operators.MapFunctionValues(G0ToDeath,0.0,10.0,Operators.gaussian,5.0,1.0);
            G0ToDeath=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(G0ToDeath, G0ToDeath.min, G0ToDeath.max),0, G0ToDeath);
            // G0->G1
            FunctionStructure G0ToG1=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(8.0),step);  
            Operators.MapFunctionValues(G0ToG1,0.0,8.0,Operators.gaussian,5.0,1.0);
            G0ToG1=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(G0ToG1, G0ToG1.min, G0ToG1.max),0, G0ToG1);
            // G1->G0
            FunctionStructure G1ToG0=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(100.0),step); 
            Operators.MapFunctionValues(G1ToG0,12.0,30.0,Operators.gaussian,16.3,2.0);
    //        Operators.MapFunctionValues(G1ToG0,50.0,51.0,Operators.constant,1.0);
            G1ToG0=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(G1ToG0, G1ToG0.min, G1ToG0.max),0, G1ToG0);
             // G1->S
            FunctionStructure G1ToS=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(support0),step);
            Operators.MapFunctionValues(G1ToS,0.0,support0,Operators.continuousGeometricDistribution,15.0,simulation1.pO2,simulation1.C,simulation1.B,simulation1.m);
            G1ToS=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(G1ToS, G1ToS.min, G1ToS.max),0, G1ToS);
             // S->G2
            FunctionStructure SToG2=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(8.0),step); 
            Operators.MapFunctionValues(SToG2,8.0-step,8.0,Operators.constant,1.0);
    //        Operators.MapFunctionValues(SToG2,5.0,8.0,Operators.gaussian,6.0,2.0);
            SToG2=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(SToG2, SToG2.min, SToG2.max),0, SToG2);
             // G2->M
            FunctionStructure G2ToM=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(support2),step); 
            Operators.MapFunctionValues(G2ToM,3.0,support2,Operators.continuousGeometricDistribution,3.0,simulation1.pO2,simulation1.C,simulation1.B,simulation1.m);
            G2ToM=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(G2ToM, G2ToM.min, G2ToM.max),0, G2ToM);
             // M->G1
            FunctionStructure MToG1=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(2.0),step); 
            Operators.MapFunctionValues(MToG1,2.0-step,2.0,Operators.constant,1.0);
    //        Operators.MapFunctionValues(MToG1,0.0,2.0,Operators.gaussian,1.0,1.0);
            MToG1=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(MToG1, MToG1.min, MToG1.max),0, MToG1);
        
        
        // Creation de la population de cellules
        CellPopulation pop=new CellPopulation();
        // Taille initiale
        pop.size=1.0;
        // Dynamique  
        pop.dynamics.phaseNb=5;
        pop.pO2=simulation1.pO2;
        pop.dynamics.G0.density.put("Death",Operators.copyFunction(G0ToDeath));
        pop.dynamics.G0.density.put("G1", Operators.copyFunction(G0ToG1));
        pop.dynamics.G1.density.put("G0", Operators.copyFunction(G1ToG0));
        pop.dynamics.G1.density.put("S", Operators.copyFunction(G1ToS));
        pop.dynamics.S.density.put("G2", Operators.copyFunction(SToG2));
        pop.dynamics.G2.density.put("M", Operators.copyFunction(G2ToM));
        pop.dynamics.M.density.put("G1", Operators.copyFunction(MToG1)); 
        // Complétions des différentes fonctions utiles pour la dynamique
        CellDynamicsOperators.DynamicsFilling(pop.dynamics);
        
        // Premier jeux de fonctions theta
        ThetaStructure initTheta= new ThetaStructure();
//        initTheta.phaseNb=pop.dynamics.phaseNb;
        StableSolution.StableInitialCondition(initTheta,pop.dynamics);
        pop.theta.add(initTheta);
        pop.currentTheta=0;
        
        
         // treatment
        simulation1.duration =336.0;
        int fractions=10;
        double totalDose =45.0;
        double intervalBetweenDose=simulation1.duration/(fractions+1);
        double fractionDose=totalDose/fractions;
        simulation1.pop=CellPopulationOperators.copyCellPopulation(pop);
        simulation1.treat= new TreatmentStructure();
        simulation1.treat.times= new double[fractions+1];
        simulation1.treat.doses= new double[fractions+1];
        for (int i=0;i<fractions;i++){
            simulation1.treat.times[i]=(i+1)*intervalBetweenDose;
            simulation1.treat.doses[i]=fractionDose;
        }
        simulation1.treat.times[fractions]=Double.NaN;
        simulation1.treat.doses[fractions]=0.0;        
        SimulationStructureOperators.run(simulation1);
        
        
        simulation2.duration =120.0;
        fractions=5;
        totalDose =45.0;
        intervalBetweenDose=simulation2.duration/(fractions+1);
        fractionDose=totalDose/fractions;
        simulation2.pop=CellPopulationOperators.copyCellPopulation(pop);
        simulation2.treat= new TreatmentStructure();
        simulation2.treat.times= new double[fractions+1];
        simulation2.treat.doses= new double[fractions+1];
        for (int i=0;i<fractions;i++){
            simulation2.treat.times[i]=(i+1)*intervalBetweenDose;
            simulation2.treat.doses[i]=fractionDose;
        }
        simulation2.treat.times[fractions]=Double.NaN;
        simulation2.treat.doses[fractions]=0.0;        
        SimulationStructureOperators.run(simulation2);
        
//        FunctionStructure populationSize=Operators.createFunction(0.0, simulationTime, step);
//        for (int i=0;i<=Math.round(simulationTime/step);i++){
//            populationSize.values[i]=CellPopulationOperators.GetPopulationSize(pop, step*i);
//        }
        
        GUIPopulation win =new GUIPopulation();
        win.SetFunction(simulation1.pop);
        win.setVisible(true);
        
        GUIPopulation win2 =new GUIPopulation();
        win2.SetFunction(simulation2.pop);
        win2.setVisible(true);
//        Operators.plotFunction(populationSize);
 
    }
    
}
