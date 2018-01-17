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
//import java.rmi.server.Operation;
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
           FunctionStructure duree= Operators.createFunctionCopy(dynamics.getPhase(phase).timeDensity); 
           FunctionStructure OneMinCumulDuree= Operators.createFunctionCopy(dynamics.getPhase(phase).timeOneMinCumul); 
           // créer la piecewise issue des data 
           // On commence par situer le dosage dans le tableau
           int iDose=1;
           
           while(dose>data.getPhase(phase)[iDose][0]){ iDose++;
           }
           iDose--;
           // la dose est comprise entre la ligne i et la ligne i+1
           // à terme il faudrait interpoler, mais on peut commencer simplement par une approximation de la dose par celle de la ligne i
           double[] p=new double[(data.getPhase(phase)[0].length-1)*2];
           for (int j=0;j<p.length/2;j++){
               p[2*j]=data.getPhase(phase)[0][j+1];
               p[2*j+1]=data.getPhase(phase)[iDose][j+1];
           }
           FunctionStructure pData=Operators.createFunction(0.0, 1.0, duree.step);
           pData.SetFunctionValuesFromInterface(0.0, 1.0, Operators.piecewiseLinear, p);
           Operators.plotFunction(pData);
           Operators.plotFunction(duree);
           FunctionStructure survival=Operators.createFunction(duree.min,duree.max,duree.step);
           FunctionStructure temp=new FunctionStructure();
           // Il faut initialiser la valeur de la proba de survie pour t=0, car celle-ci ne peut pas se calculer par une intégrale, puisque quel que soit
           // leur temps de passage, elles en sont au temps 0 de ce temps relatif. Après, on risque d'avoir un problème d'intégrale non unitaire 
//           survival.values[0]=p[1];
//           double t=survival.min+survival.step;
           double t=survival.min;
           for (int i=survival.minIndex;i<=survival.maxIndex;i++){
               // Si la fonction de répartition vaut 1, 1-F vaut 0, mais alors la densité est aussi nulle et il suffit de renvoyer 0 tout de suite 
               if (duree.GetFunctionValue(t)==0){
                   survival.values[i]=0;
               }
               else {
               //System.out.println("division par"+Operators.GetFunctionValue(OneMinCumulDuree,t));
//                temp=Operators.createAffineFunctionTransformation(1/OneMinCumulDuree.GetFunctionValue(t),0.0,duree);
                // création de la fonction t/s (on pourrait créer 1/s et en prendre des trasnformation affine, mais le gain n'est pas clair
                FunctionStructure homo=Operators.createFunction(t, duree.max, duree.step);
                homo.SetFunctionValuesFromInterface(homo.min,homo.max , Operators.homographie, 0.0,t,1.0,0.0);
                temp=Operators.ComposeFunctionInterfaceWithFunction(Operators.piecewiseLinear,homo,p);
//                if(t==survival.min) { 
////                    Operators.PrintFunction("homog", homo, true);
//                    Operators.plotFunction(homo);
//                    Operators.plotFunction(temp);
//                }
                temp=Operators.createProductFunction(temp, homo);
                survival.values[i]=Operators.IntegrateFunction(temp, t, duree.max)/OneMinCumulDuree.GetFunctionValue(t);
                t+=survival.step;
               }
           }
           return survival;
       }
       
       }
//    
//        Hashtable<String,FunctionStructure> survivalRaw = new Hashtable<String,FunctionStructure>(); 
//        FunctionStructure survivalG1=Operators.createFunction(Numbers.CGN(0.0), Numbers.CGN(1.0), step);
//        Operators.MapFunctionValues(survivalG1,0.0,1.0,Operators.piecewiseLinear,0.0,);
//        survivalRaw.put("G1", );

    

