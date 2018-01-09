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
// Chaque ligne correspond à un dosage, chaque colonne à un temps dans le cycle
// On décompose ces données en une courbe pour chaque phase, en fonction de son avancement (donc une courbe définie sur [0,1]
// que l'on va ensuite transformer en fonction du temps aléatoire passé dans la phase
       public static FunctionStructure survivalProbabilities(double dose,int phase,CellDynamics dynamics, SurvivalDataStructure data){
           // il faut ici utiliser la distribution de la durée de la phase, à définir dans dynamics
           FunctionStructure duree= Operators.copyFunction(dynamics.getPhase(phase).timeDensity); 
           FunctionStructure OneMinCumulDuree= Operators.copyFunction(dynamics.getPhase(phase).timeOneMinCumul); 
           // créer la piecewise issue des data 
           // On commence par situer le dosage dans le tableau
           int iDose=0;
           while(dose>data.getPhase(phase)[iDose][0]) iDose++;
           iDose--;
           // la dose est comprise entre la ligne i et la ligne i+1
           // à terme il faudrait interpoler, mais on peut commencer simplement par une approximation de la dose par celle de la ligne i
           double[] p=new double[(data.getPhase(phase)[0].length-1)*2];
           for (int j=0;j<p.length;j++){
               p[2*j]=data.getPhase(phase)[0][j];
               p[2*j+1]=data.getPhase(phase)[iDose][j];
           }
           //FunctionStructure survivalProbabilitiesData=Operators.createFunction(0.0, 1.0, duree.step);
           //Operators.MapFunctionValues(survivalProbabilitiesData, 0.0, 1.0, Operators.piecewiseLinear, p);
           FunctionStructure survival=Operators.createFunction(duree.min,duree.max,duree.step);
           for (double t=survival.min;t<=survival.max;t=t+survival.step){
                FunctionStructure temp=Operators.AffineFunctionTransformation(1/Operators.GetFunctionValue(OneMinCumulDuree,t),0.0,duree);
                // création de la fonction t/s (on pourrait créer 1/s et en prendre des trasnformation affine, mais le gain n'est pas clair
                FunctionStructure homo=Operators.createFunction(t, duree.max, duree.step);
                Operators.MapFunctionValues(homo, homo.min,homo.max , Operators.homographie, 0.0,t,1.0,0.0);
                temp=Operators.MultiplyFunctions(Operators.ComposeFunctionInterfaceFunctionStructure(homo, Operators.piecewiseLinear, p),temp);
                survival.values[i]=Operators.IntegrateFunction(temp, t, temp.max);
           }
           return survival;
       }
       
       }
//    
//        Hashtable<String,FunctionStructure> survivalRaw = new Hashtable<String,FunctionStructure>(); 
//        FunctionStructure survivalG1=Operators.createFunction(Numbers.CGN(0.0), Numbers.CGN(1.0), step);
//        Operators.MapFunctionValues(survivalG1,0.0,1.0,Operators.piecewiseLinear,0.0,);
//        survivalRaw.put("G1", );
}
    

