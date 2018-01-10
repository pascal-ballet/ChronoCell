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
//          * implement initial population size
//          * adapt survival probability to inner cell time
//          * move survival distribution to a java class
//          * reprendre l'exploitation des données de radiosensib. : interpoler les données de data en fonction de la dose
//          * écrire proprement la formule de calcul de la proba de survie (pb à la borne inférieure)
//          * survival non borné par 1, vérifier implémentation.
package chronocell;
import java.util.*;
import static chronocell.Operators.IntegrateFunction;
import static chronocell.Operators.MultiplyFunctions;
import static chronocell.Operators.PowerOfFunction;
import static chronocell.Operators.TranslateFunction;
import java.util.ArrayList;
import static chronocell.CsvToArrayList.readTXTFile;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import javax.print.attribute.HashAttributeSet;
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
    
//    public static Hashtable<String,double[][]> survivalData = new Hashtable<String,double[][]>(); 
    public static SurvivalDataStructure _survivalData = new SurvivalDataStructure();
    
    public static void main(String[] args) {
        Numbers.minStep=0.00001;
        double precision=0.01;
        double step=0.1;
        
    //-------------- Probabilités de survie (lecture dans un .csv)
    // Chaque ligne correspond à un dosage(sauf la première), chaque colonne à un temps dans le cycle
    // les temps sont des temps de références, mais on les normalise pour avoir une courbe définie sur [0,1] que l'on distribuera plus tard

    
    double[][] temp;        
       try{
           temp=CsvToArrayList.readTXTFile("/Data/Dropbox/Boulot/Recherche/Latim/BiblioModelisationTumeur/G0.csv");
           // normalisation 
            for (int j=1;j<temp[0].length;j++){
                temp[0][j]=(temp[0][j]-temp[0][1])/(temp[0][temp[0].length-1]-temp[0][1]);
            }
           
           _survivalData.G0=temp;
           temp=CsvToArrayList.readTXTFile("/Data/Dropbox/Boulot/Recherche/Latim/BiblioModelisationTumeur/G1.csv");
           // normalisation 
            for (int j=1;j<temp[0].length;j++){
                temp[0][j]=(temp[0][j]-temp[0][1])/(temp[0][temp[0].length-1]-temp[0][1]);
            }
           
           _survivalData.G1=temp;
           temp=CsvToArrayList.readTXTFile("/Data/Dropbox/Boulot/Recherche/Latim/BiblioModelisationTumeur/S.csv");
//           survivalData.put("S", CsvToArrayList.readTXTFile("/Data/Dropbox/Boulot/Recherche/Latim/BiblioModelisationTumeur/S.csv"));
           // normalisation 
            for (int j=1;j<temp[0].length;j++){
                temp[0][j]=(temp[0][j]-temp[0][1])/(temp[0][temp[0].length-1]-temp[0][1]);
            }
           _survivalData.S=temp;
           temp=CsvToArrayList.readTXTFile("/Data/Dropbox/Boulot/Recherche/Latim/BiblioModelisationTumeur/G2.csv");
           // normalisation 
            for (int j=1;j<temp[0].length;j++){
                temp[0][j]=(temp[0][j]-temp[0][1])/(temp[0][temp[0].length-1]-temp[0][1]);
            }
           _survivalData.G2=temp;
           temp=CsvToArrayList.readTXTFile("/Data/Dropbox/Boulot/Recherche/Latim/BiblioModelisationTumeur/M.csv");
           // normalisation 
            for (int j=1;j<temp[0].length;j++){
                temp[0][j]=(temp[0][j]-temp[0][1])/(temp[0][temp[0].length-1]-temp[0][1]);
            }
           _survivalData.M=temp;
        }
        catch(Exception e){
            e.printStackTrace();
            return;
    }
  
//-------------- Dynamique initiale des phases ---------------------------------
            double support0=40.0,support2=15.0;
            // G0->Death
            FunctionStructure G0ToDeath=Operators.createFunction(Numbers.CGN(0.0),support0,step); 
//            Operators.MapFunctionValues(G0ToDeath,0.0,8.0,Operators.gaussian,7.0,1.0);
            Operators.MapFunctionValues(G0ToDeath,0.0,support0,Operators.boundedExponentialDistribution,6.0,4.0,support0);
            G0ToDeath=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(G0ToDeath, G0ToDeath.min, G0ToDeath.max),0, G0ToDeath);
            // G0->G1
            FunctionStructure G0ToG1=Operators.createFunction(Numbers.CGN(0.0),support0,step);  
            Operators.MapFunctionValues(G0ToG1,0.0,support0,Operators.boundedExponentialDistribution,3.0,2.0,support0);
            G0ToG1=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(G0ToG1, G0ToG1.min, G0ToG1.max),0, G0ToG1);
            // G1->G0
            FunctionStructure G1ToG0=Operators.createFunction(Numbers.CGN(0.0),support0,step); 
            Operators.MapFunctionValues(G1ToG0,0.0,support0,Operators.boundedExponentialDistribution,3.0,6.0,support0);
//            Operators.MapFunctionValues(G1ToG0,50.0,51.0,Operators.constant,1.0);
            G1ToG0=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(G1ToG0, G1ToG0.min, G1ToG0.max),0, G1ToG0);
             // G1->S
            FunctionStructure G1ToS=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(support0),step);
//            Operators.MapFunctionValues(G1ToS,0.0,support0,Operators.continuousGeometricDistribution,15.0,pO2,C,B,m);
            Operators.MapFunctionValues(G1ToS,0.0,support0,Operators.boundedExponentialDistribution,4.8,4.1,support0);
            G1ToS=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(G1ToS, G1ToS.min, G1ToS.max),0, G1ToS);
             // S->G2
            FunctionStructure SToG2=Operators.createFunction(0.0,Numbers.CGN(support0),step); 
            Operators.MapFunctionValues(SToG2,0.0,support0,Operators.boundedExponentialDistribution,3.3,4.8,support0);
//            Operators.MapFunctionValues(SToG2,5.0,8.0,Operators.gaussian,6.0,2.0);
            SToG2=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(SToG2, SToG2.min, SToG2.max),0, SToG2);
             // G2->M
            Operators.plotFunction(SToG2);
            FunctionStructure G2ToM=Operators.createFunction(0.0,Numbers.CGN(support0),step); 
//            Operators.MapFunctionValues(G2ToM,3.0,support2,Operators.continuousGeometricDistribution,3.0,pO2,C,B,m);
            Operators.MapFunctionValues(G2ToM,0.0,support0,Operators.boundedExponentialDistribution,3.6,0.0,support0);
            G2ToM=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(G2ToM, G2ToM.min, G2ToM.max),0, G2ToM);
             // M->G1
            FunctionStructure MToG1=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(2.0),step); 
            Operators.MapFunctionValues(MToG1,2.0-step,2.0,Operators.constant,1.0);
//            Operators.MapFunctionValues(MToG1,0.0,2.0,Operators.gaussian,1.0,1.0);
            MToG1=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(MToG1, MToG1.min, MToG1.max),0, MToG1);
        
        
//-------------- Creation de la population de cellules -------------------------
            CellPopulation pop=new CellPopulation();
            pop.timeStep=step;
            // Taille initiale
            pop.size=100.0;
            // Dynamique  
            pop.dynamics.phaseNb=5;
            pop.pO2=20.0;
            pop.dynamics.G0.density.put("Death",Operators.copyFunction(G0ToDeath));
            pop.dynamics.G0.density.put("G1", Operators.copyFunction(G0ToG1));
            pop.dynamics.G1.density.put("G0", Operators.copyFunction(G1ToG0));
            pop.dynamics.G1.density.put("S", Operators.copyFunction(G1ToS));
            pop.dynamics.S.density.put("G2", Operators.copyFunction(SToG2));
            pop.dynamics.G2.density.put("M", Operators.copyFunction(G2ToM));
            pop.dynamics.M.density.put("G1", Operators.copyFunction(MToG1)); 
            // Complétions des différentes fonctions utiles pour la dynamique
            CellDynamicsOperators.DynamicsFilling(pop.dynamics);
        
//-------------- Premier jeux de fonctions theta -------------------------------
            ThetaStructure initTheta= new ThetaStructure();
//            initTheta.phaseNb=pop.dynamics.phaseNb;
            StableSolution.StableInitialCondition(initTheta,pop.dynamics);
            pop.theta.add(initTheta);
            pop.currentTheta=0;
            
            Operators.MapFunctionValues(pop.theta.get(0).G0,pop.theta.get(0).G1.min,pop.theta.get(0).G1.max,Operators.constant,0.0);
            Operators.MapFunctionValues(pop.theta.get(0).S,pop.theta.get(0).G1.min,pop.theta.get(0).G1.max,Operators.constant,0.0);
            Operators.MapFunctionValues(pop.theta.get(0).G2,pop.theta.get(0).G1.min,pop.theta.get(0).G1.max,Operators.constant,0.0);
            Operators.MapFunctionValues(pop.theta.get(0).M,pop.theta.get(0).G1.min,pop.theta.get(0).G1.max,Operators.constant,0.0);
        
        // Un traitement
        ArrayList<Double> results=new ArrayList<>();
        
//-------------- treatment -----------------------------------------------------
            double duration =300.0;
            int fractions=2;
            double totalDose =1.0;
            double fractionDose=totalDose/fractions;
            SimulationStructure simulation=new SimulationStructure();
            simulation.duration=duration;
            simulation.timeStep=step;
            simulation.pop=CellPopulationOperators.copyCellPopulation(pop);
            simulation.treat= new TreatmentStructure();
            simulation.treat.times= new double[fractions+1];
            simulation.treat.doses= new double[fractions+1];
            simulation.treat.times[0]=50;
            simulation.treat.times[1]=200;
            simulation.treat.doses[0]=fractionDose;
            simulation.treat.doses[1]=fractionDose;
            simulation.treat.times[fractions]=Double.NaN;
            simulation.treat.doses[fractions]=0.0;        
            SimulationStructureOperators.run(simulation);
            SimulationStructureOperators.plotSimulation(simulation);
        
//            FunctionStructure comp=Operators.createFunction(0.0,(double) results.size(), 1.0);
//            for (int i=0;i<comp.maxIndex;i++){
//                comp.values[i]=results.get(i);
//            }
//            comp=Operators.CropFunction(comp);
//            Operators.plotFunction(comp);        
        
        
///////////-------------- Optimisation du traitement ------------------------------------
//            double duration =500.0,delayBefore=8.0,delayAfter=72.0;
//            double timeIncrement=3.33333;
//            int fractions=10;
//            double totalDose =45.0;
//            double fractionDose=totalDose/fractions;
//            
//    //---------- Recherche du meilleur ----------------------
//            TreatmentStructure best=new TreatmentStructure();
//            best.times= new double[fractions+1];
//            best.doses= new double[fractions+1];
//
//            double temp=0.0;
//            double Max=0.0, Min=100000.0;
//            best.times[0]=1.0;
//            best.doses[0]=fractionDose;
//            for (int c=1;c<best.times.length;c++){
//                best.times[c]=Double.NaN;
//                best.doses[c]=fractionDose;
//            }
//            double lastBestTime=0.0;
//            double h=0.0;
//            for (int c=1;c<best.times.length-1;c++){
//    //                System.out.println(" c= "+c);
//                boolean firstRound=true;
//                h=best.times[c-1]+delayBefore;
//                while (h<=best.times[c-1]+delayAfter){
////                for (int h=((int) (best.times[c-1]+delayBefore));h<=best.times[c-1]+delayAfter;h+=0.5){
//                    SimulationStructure simulation=new SimulationStructure();
//                    simulation.duration=duration;
//                    simulation.timeStep=step;
//                    simulation.pop=CellPopulationOperators.copyCellPopulation(pop);
//                    best.times[c]=h;
//                    best.nextDose=0;
//                    simulation.treat= best;
//                    SimulationStructureOperators.run(simulation);
//                    temp=SimulationStructureOperators.cumulatedPopulation(simulation);
//                    if (firstRound==true){
//                        lastBestTime=h;
//                        Min=temp;
//                        firstRound=false;
//                    }
//                    if (precision*Min<Min-temp){
//                        lastBestTime=h;
//                        Min=temp;
//                    }
//                    h+=timeIncrement;
//                }
//                best.times[c]=lastBestTime;
//            }
//            SimulationStructure bestSimu=new SimulationStructure();
//            bestSimu.duration=duration;
//            bestSimu.timeStep=step;
//            bestSimu.pop=CellPopulationOperators.copyCellPopulation(pop);
//            bestSimu.treat= best;
//            SimulationStructureOperators.run(bestSimu);
//            SimulationStructureOperators.plotPopulationEvolution(bestSimu);
//            System.out.println("min= "+Min);
//            TreatmentStructureOperators.displayTreatment(best);
//            SimulationStructureOperators.plotSimulation(bestSimu);
//            
//            
//    //---------- Recherche du pire ---------------------------        
//            TreatmentStructure worst=new TreatmentStructure();
//            worst.times= new double[fractions+1];
//            worst.doses= new double[fractions+1];
//            worst.times[0]=1.0;
//            worst.doses[0]=fractionDose;
//            for (int c=1;c<worst.times.length;c++){
//                worst.times[c]=Double.NaN;
//                worst.doses[c]=fractionDose;
//            }
//            double lastWorstTime=0.0;
//            for (int c=1;c<worst.times.length-1;c++){
//    //                System.out.println(" c= "+c);
//                boolean firstRound=true;
//                h=worst.times[c-1]+delayBefore;
//                while (h<=worst.times[c-1]+delayAfter){
////                for (int h=((int) ());h<=;h+=1){
//                    SimulationStructure simulation=new SimulationStructure();
//                    simulation.duration=duration;
//                    simulation.timeStep=step;
//                    simulation.pop=CellPopulationOperators.copyCellPopulation(pop);
//                    worst.times[c]=h;
//                    worst.nextDose=0;
//                    simulation.treat=worst;
//    //                System.out.println("Worst :"+worst.times[0]+","+worst.times[1]+","+worst.times[2]+","+worst.times[3]+".");
//                    SimulationStructureOperators.run(simulation);
//    //                System.out.println("nextdose"+simulation.treat.nextDose);
//                    temp=SimulationStructureOperators.cumulatedPopulation(simulation);
//                    if (firstRound==true){
//                        lastWorstTime=h;
//                        Max=temp;
//                        firstRound=false;
//                    }
//                    if (temp-Max>Max*precision){
//                        lastWorstTime=h;
//                        Max=temp;
//                    }
//                    h+=timeIncrement;
//                }
//                worst.times[c]=lastWorstTime;
//            }
//            System.out.println("max= "+Max);
//            TreatmentStructureOperators.displayTreatment(worst);
//            
//           
    }
    
}
