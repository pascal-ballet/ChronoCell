/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// Checked: FunctionStructure.java, Operators.java, CellDynamicsOperators.java, CellDynamics.java
//          CellPopulation;
// Current:  cellpopOPerators apply treatment      

// Todo :   * différencier l'utilisation des différents pas de calculs (simu...) test
//          * vérifier le calcul intégral avec l'utilisation de l'interpolation dans getFunctionValue
//          * généraliser l'utilisation de closestGridPoint pour eviter certains bugs
//          * gérer la pO2 
//          * généraliser l'utilisation de checkAndAdjustSupport dans les fonctions (repérer le bug quand utilisé dans productFunction
//          * espilon dans la transformée de laplace inverse est fixé
//          * vérifier la pertinence de CGN
//          * généraliser l'utilisation de setFunctionValue
//          * vérifier que les compositions de focntions n'utilisent pas des valeurs nulles en dehors du support (exp(0)=1 par exemple)
//          * Implement constant solution for initial condition (if extinction, take Laplace transform for lambda=0).
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
import java.util.ArrayList;
import static chronocell.CsvToArrayList.readTXTFile;
//import com.oracle.webservices.internal.api.message.MessageContextFactory;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
import javax.print.attribute.HashAttributeSet;
import static chronocell.Operators.createProductFunction;
import static chronocell.Operators.createPowerOfFunction;
import static chronocell.Operators.createTranslatedFunction;
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
    
    public static double integrationStep=0.005;
    
    public static void main(String[] args) {
        
//        
//     FunctionStructure test = new FunctionStructure();
//    test =Operators.createFunction(-1, 2, (int) 11);
//    Operators.PrintFunction(test, true);
//    test.SetFunctionValuesFromInterface(0, 1, Operators.pow, 1);
//    Operators.PrintFunction(test, true);
//    test.checkAndAdjustSupport();
//    Operators.PrintFunction(test, true);
//    
//    FunctionStructure test2 =Operators.createProductFunction(test,test);
//    Operators.PrintFunction(test2, true);
    
////        System.out.println("int="+Operators.IntegrateFunction(test, 0.0, 1.0));
//        test.SetFunctionValue(-0.2, 2);
//    Operators.PrintFunction(test, true);
//        System.out.println(test.indexPoint(12));
//    for (int i=0;i<=50;i++){
//        System.out.println("f("+((double) i/50)+")="+test.getFunctionValue(((double) i/50)));
//    }
//    Operators.DoubleValuesArraySizeToLeft(test);
//    Operators.plotFunction(test);    
//    -------------- Probabilités de survie (lecture dans un .csv)
//     Chaque ligne correspond à un dosage(sauf la première), chaque colonne à un temps dans le cycle
//     les temps sont des temps de références, mais on les normalise pour avoir une courbe définie sur [0,1] que l'on distribuera plus tard
//
//    
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
//////  
////////-------------- Dynamique initiale des phases ---------------------------------
            double support0=40.0,support2=15.0;
            double step=0.05;
            // G0->Death
            FunctionStructure G0ToDeath=Operators.createFunction(Numbers.CGN(0.0),support0,step,"G0ToDeath.Density"); 
//            Operators.MapFunctionValues(G0ToDeath,0.0,8.0,Operators.gaussian,7.0,1.0);
            G0ToDeath.SetFunctionValuesFromInterface(Operators.boundedExponentialDistribution,6.0,4.0,support0);
            Operators.makeDistributionFromFunction(G0ToDeath);
            G0ToDeath.checkAndAdjustSupport();
            // G0->G1
            FunctionStructure G0ToG1=Operators.createFunction(Numbers.CGN(0.0),support0,step,"G0ToG1.Density");  
            G0ToG1.SetFunctionValuesFromInterface(Operators.boundedExponentialDistribution,3.0,2.0,support0);
            Operators.makeDistributionFromFunction(G0ToG1);
            G0ToG1.checkAndAdjustSupport();
            // G1->G0
            FunctionStructure G1ToG0=Operators.createFunction(Numbers.CGN(0.0),support0,step,"G1ToG0.Density"); 
            G1ToG0.SetFunctionValuesFromInterface(Operators.boundedExponentialDistribution,30.0,31.0,support0);
//            Operators.MapFunctionValues(G1ToG0,50.0,51.0,Operators.constant,1.0);
            Operators.makeDistributionFromFunction(G1ToG0);
            G1ToG0.checkAndAdjustSupport();
             // G1->S
            FunctionStructure G1ToS=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(support0),step,"G1ToS.Density");
//            Operators.PrintFunction(G1ToS, false);
//            Operators.MapFunctionValues(G1ToS,0.0,support0,Operators.continuousGeometricDistribution,15.0,pO2,C,B,m);
            G1ToS.SetFunctionValuesFromInterface(Operators.boundedExponentialDistribution,4.8,4.1,support0);
//            Operators.PrintFunction(G1ToS, false);
            Operators.makeDistributionFromFunction(G1ToS);
            G1ToS.checkAndAdjustSupport();
//            Operators.PrintFunction(G1ToS, false);
             // S->G2
            FunctionStructure SToG2=Operators.createFunction(0.0,Numbers.CGN(support0),step,"SToG2.Density"); 
            SToG2.SetFunctionValuesFromInterface(Operators.boundedExponentialDistribution,3.3,4.8,support0);
//            Operators.MapFunctionValues(SToG2,5.0,8.0,Operators.gaussian,6.0,2.0);
            Operators.makeDistributionFromFunction(SToG2);
            SToG2.checkAndAdjustSupport();
            
             // G2->M
//            Operators.plotFunction(SToG2);
            FunctionStructure G2ToM=Operators.createFunction(0.0,Numbers.CGN(support0),step,"G2ToM.Density"); 
//            Operators.MapFunctionValues(G2ToM,3.0,support2,Operators.continuousGeometricDistribution,3.0,pO2,C,B,m);
            G2ToM.SetFunctionValuesFromInterface(Operators.boundedExponentialDistribution,3.6,0.0,support0);
            Operators.makeDistributionFromFunction(G2ToM);
            G2ToM.checkAndAdjustSupport();
             // M->G1
             
             /// modéliser les dirac comme min=max et 0 partout sauf Nan au point considéré, et ajouter partout des tests pour effectuer des calculs particuliers.
            FunctionStructure MToG1=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(2.0),20,"MToG1.Density"); 
            MToG1.SetFunctionValuesFromInterface(0,2.0,Operators.constant,1.0);
//            Operators.MapFunctionValues(MToG1,0.0,2.0,Operators.gaussian,1.0,1.0);
            Operators.makeDistributionFromFunction(MToG1);
            MToG1.checkAndAdjustSupport();
//            Operators.plotFunction(MToG1);
//            System.out.println("chronocell.ChronoCell.main()"+Operators.LaplaceTransform(1, MToG1));
////  
////        
//////-------------- Creation de la population de cellules -------------------------
            CellPopulation pop=new CellPopulation();
            pop.timeStep=0.1;
            // Taille initiale
            pop.size=1.0;
            // Dynamique  
            pop.dynamics.phaseNb=5;
//            pop.pO2=20.0;
            pop.dynamics.G0.density.put("Death",Operators.createFunctionCopy(G0ToDeath));
            pop.dynamics.G0.density.put("G1", Operators.createFunctionCopy(G0ToG1));
            pop.dynamics.G1.density.put("G0", Operators.createFunctionCopy(G1ToG0));
            pop.dynamics.G1.density.put("S", Operators.createFunctionCopy(G1ToS));
            pop.dynamics.S.density.put("G2", Operators.createFunctionCopy(SToG2));
            pop.dynamics.G2.density.put("M", Operators.createFunctionCopy(G2ToM));
            pop.dynamics.M.density.put("G1", Operators.createFunctionCopy(MToG1)); 
            // Complétions des différentes fonctions utiles pour la dynamique
            CellDynamicsOperators.DynamicsFilling(pop.dynamics);
//////        
////
////   
////            
////
////
////    
////////-------------- Premier jeux de fonctions theta -------------------------------
            ThetaStructure initTheta= new ThetaStructure();
//            initTheta.phaseNb=pop.dynamics.phaseNb;
            StableSolution.StableInitialCondition(initTheta,pop.dynamics);
            pop.theta.add(initTheta);
            pop.currentTheta=0;
//            
            
            pop.theta.get(0).G1.SetFunctionValuesFromInterface(pop.theta.get(0).G1.min,pop.theta.get(0).G1.max,Operators.constant,0.0);
            pop.theta.get(0).G0.SetFunctionValuesFromInterface(pop.theta.get(0).G0.min,pop.theta.get(0).G0.max,Operators.constant,0.0);
            pop.theta.get(0).S.SetFunctionValuesFromInterface(pop.theta.get(0).S.min,pop.theta.get(0).S.max,Operators.constant,0.0);
            pop.theta.get(0).G2.SetFunctionValuesFromInterface(pop.theta.get(0).G2.min,pop.theta.get(0).G2.max,Operators.constant,0.0);
            pop.theta.get(0).M.SetFunctionValuesFromInterface(pop.theta.get(0).M.min,pop.theta.get(0).M.max,Operators.constant,1.0);
//        
//////        // Un traitement
//////        ArrayList<Double> results=new ArrayList<>();
//////        
////////-------------- treatment -----------------------------------------------------
            double duration =100;
            int fractions=1;
            double totalDose =1;
            double fractionDose=totalDose/fractions;
            SimulationStructure simulation=new SimulationStructure();
            simulation.duration=duration;
            simulation.timeStep=pop.timeStep;
            simulation.duration=duration*2;
            simulation.pop=CellPopulationOperators.copyCellPopulation(pop);
            simulation.treat= new TreatmentStructure();
            simulation.treat.times= new double[fractions+1];
            simulation.treat.doses= new double[fractions+1];
            simulation.treat.times[0]=Double.NaN;
//            simulation.treat.times[1]=200;
            simulation.treat.doses[0]=fractionDose;
//            simulation.treat.doses[1]=fractionDose;
            simulation.treat.times[fractions]=Double.NaN;
            simulation.treat.doses[fractions]=0.0;
            SimulationStructureOperators.run(simulation);
Operators.plotFunction(simulation.pop.theta.get(0).G1);
Operators.PrintFunction(simulation.pop.theta.get(0).G1, false);
            SimulationStructureOperators.plotSimulation(simulation);
            
            
            
            
            
            
//            for (int ii=)
//            for (int ii=0;ii<5;ii++){
//            Operators.plotFunction(simulation.pop.theta.get(0).getPhase(ii));
//            }
//            FunctionStructure comp=Operators.createFunction(0.0,(double) results.size(), 1.0);
//            for (int i=0;i<comp.maxIndex;i++){
//                comp.values[i]=results.get(i);
//            }
//            comp=Operators.CropFunction(comp);
//            Operators.plotFunction(comp);        
//        
//        
/////////////-------------- Optimisation du traitement ------------------------------------
////            double duration =500.0,delayBefore=8.0,delayAfter=72.0;
////            double timeIncrement=3.33333;
////            int fractions=10;
////            double totalDose =45.0;
////            double fractionDose=totalDose/fractions;
////            
////    //---------- Recherche du meilleur ----------------------
////            TreatmentStructure best=new TreatmentStructure();
////            best.times= new double[fractions+1];
////            best.doses= new double[fractions+1];
////
////            double temp=0.0;
////            double Max=0.0, Min=100000.0;
////            best.times[0]=1.0;
////            best.doses[0]=fractionDose;
////            for (int c=1;c<best.times.length;c++){
////                best.times[c]=Double.NaN;
////                best.doses[c]=fractionDose;
////            }
////            double lastBestTime=0.0;
////            double h=0.0;
////            for (int c=1;c<best.times.length-1;c++){
////    //                System.out.println(" c= "+c);
////                boolean firstRound=true;
////                h=best.times[c-1]+delayBefore;
////                while (h<=best.times[c-1]+delayAfter){
//////                for (int h=((int) (best.times[c-1]+delayBefore));h<=best.times[c-1]+delayAfter;h+=0.5){
////                    SimulationStructure simulation=new SimulationStructure();
////                    simulation.duration=duration;
////                    simulation.timeStep=step;
////                    simulation.pop=CellPopulationOperators.copyCellPopulation(pop);
////                    best.times[c]=h;
////                    best.nextDose=0;
////                    simulation.treat= best;
////                    SimulationStructureOperators.run(simulation);
////                    temp=SimulationStructureOperators.cumulatedPopulation(simulation);
////                    if (firstRound==true){
////                        lastBestTime=h;
////                        Min=temp;
////                        firstRound=false;
////                    }
////                    if (precision*Min<Min-temp){
////                        lastBestTime=h;
////                        Min=temp;
////                    }
////                    h+=timeIncrement;
////                }
////                best.times[c]=lastBestTime;
////            }
////            SimulationStructure bestSimu=new SimulationStructure();
////            bestSimu.duration=duration;
////            bestSimu.timeStep=step;
////            bestSimu.pop=CellPopulationOperators.copyCellPopulation(pop);
////            bestSimu.treat= best;
////            SimulationStructureOperators.run(bestSimu);
////            SimulationStructureOperators.plotPopulationEvolution(bestSimu);
////            System.out.println("min= "+Min);
////            TreatmentStructureOperators.displayTreatment(best);
////            SimulationStructureOperators.plotSimulation(bestSimu);
////            
////            
////    //---------- Recherche du pire ---------------------------        
////            TreatmentStructure worst=new TreatmentStructure();
////            worst.times= new double[fractions+1];
////            worst.doses= new double[fractions+1];
////            worst.times[0]=1.0;
////            worst.doses[0]=fractionDose;
////            for (int c=1;c<worst.times.length;c++){
////                worst.times[c]=Double.NaN;
////                worst.doses[c]=fractionDose;
////            }
////            double lastWorstTime=0.0;
////            for (int c=1;c<worst.times.length-1;c++){
////    //                System.out.println(" c= "+c);
////                boolean firstRound=true;
////                h=worst.times[c-1]+delayBefore;
////                while (h<=worst.times[c-1]+delayAfter){
//////                for (int h=((int) ());h<=;h+=1){
////                    SimulationStructure simulation=new SimulationStructure();
////                    simulation.duration=duration;
////                    simulation.timeStep=step;
////                    simulation.pop=CellPopulationOperators.copyCellPopulation(pop);
////                    worst.times[c]=h;
////                    worst.nextDose=0;
////                    simulation.treat=worst;
////    //                System.out.println("Worst :"+worst.times[0]+","+worst.times[1]+","+worst.times[2]+","+worst.times[3]+".");
////                    SimulationStructureOperators.run(simulation);
////    //                System.out.println("nextdose"+simulation.treat.nextDose);
////                    temp=SimulationStructureOperators.cumulatedPopulation(simulation);
////                    if (firstRound==true){
////                        lastWorstTime=h;
////                        Max=temp;
////                        firstRound=false;
////                    }
////                    if (temp-Max>Max*precision){
////                        lastWorstTime=h;
////                        Max=temp;
////                    }
////                    h+=timeIncrement;
////                }
////                worst.times[c]=lastWorstTime;
////            }
////            System.out.println("max= "+Max);
////            TreatmentStructureOperators.displayTreatment(worst);
////            
////           
    }
    
}
