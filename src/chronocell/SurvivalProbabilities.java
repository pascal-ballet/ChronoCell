/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;
//import static chronocell.CsvToArrayList.csvToArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 *
 * @author goby
 */
public class SurvivalProbabilities {
    //-------------- Probabilités de survie (lecture dans un .csv)
// Chaque colone correspond à un dosage, chaque ligne à un temps dans le cycle
// On décompose ces données en une courbe pour chaque phase, en fonction de son avancement (donc une courbe définie sur [0,1]
// que l'on va ensuite transformer en fonction du temps aléatoire passé dans la phase
       public static void survivalProbabilities(double dose,int phase,CellDynamics dynamics, FunctionStructure curve){
           // il faut ici utiliser la distribution de la durée de la phase, à définir dans dynamics
           FunctionStructure duree= Operators.copyFunction(dynamics.getPhase(phase).timeDensity); 
           FunctionStructure OneMinCumulDuree= Operators.copyFunction(dynamics.getPhase(phase).timeOneMinCumul); 
           // créer la piecewise issue des data 
           for (int i=curve.minIndex;i<curve.maxIndex;i++){
               
           }
           Operators.curve
       }
//    
//        Hashtable<String,FunctionStructure> survivalRaw = new Hashtable<String,FunctionStructure>(); 
//        FunctionStructure survivalG1=Operators.createFunction(Numbers.CGN(0.0), Numbers.CGN(1.0), step);
//        Operators.MapFunctionValues(survivalG1,0.0,1.0,Operators.piecewiseLinear,0.0,);
//        survivalRaw.put("G1", );
}
    

