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
           FunctionStructure timeToNextPhase= Operators.createFunctionCopy(dynamics.getPhase(phase).timeToNextPhaseDensity); 
           FunctionStructure timeToNextPhaseOneMinCumul= Operators.createFunctionCopy(dynamics.getPhase(phase).timeToNextPhaseOneMinCumul); 
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
           FunctionStructure pData=Operators.createFunction(0.0, 1.0, timeToNextPhase.step);
           pData.name="donnéesSurviePhase."+phase;
           pData.SetFunctionValuesFromInterface(0.0, 1.0, Operators.piecewiseLinear, p);
           Operators.plotFunction(pData);
//           Operators.plotFunction(pData);
//           Operators.plotFunction(timeToNextPhase);
//           Operators.plotFunction(timeToNextPhaseOneMinCumul);
//           Operators.PrintFunction(timeToNextPhase, true);
           FunctionStructure survival=Operators.createFunction(timeToNextPhase.min,timeToNextPhase.max,timeToNextPhase.step);
           survival.name="survival";
           FunctionStructure temp=new FunctionStructure();
           double t=survival.min+100*survival.step;
           for (int i=survival.minIndex;i<=survival.maxIndex;i++){
//               Operators.PrintFunction(survival, false);
               // Si la fonction de répartition vaut 1, 1-F vaut 0, mais alors la densité est aussi nulle et il suffit de renvoyer 0 tout de suite 
               if (timeToNextPhase.GetFunctionValue(t)<=0.0){
                   survival.values[i]=0;
               }
               else if (survival.max-t<=2*survival.step){
                   survival.values[i]=pData.values[pData.values.length-1];
               }
               else {
//                FunctionStructure homo=Operators.createFunction(t, timeToNextPhase.max, Math.max((timeToNextPhase.max-t)/(timeToNextPhase.values.length),Numbers.minStep),"homo");
                FunctionStructure homo=Operators.createFunction(t, timeToNextPhase.max, timeToNextPhase.step,"homo");
//              problème ici de resize du support lors du setFunctionValue
                homo.SetFunctionValuesFromInterface(homo.min,homo.max , Operators.homographie, 0.0,t,1.0,0.0);
                temp=Operators.ComposeFunctionInterfaceWithFunction(Operators.piecewiseLinear,homo,p);
//                   Operators.plotFunction(temp);
        temp.name="temp";
        FunctionStructure temp2=new FunctionStructure();
                temp2=Operators.createProductFunction(temp, timeToNextPhase);
        temp2.name="temp2";
                Operators.PrintFunction(temp2, false);
                survival.values[i]=Operators.IntegrateFunction(temp2, t, temp2.max)/timeToNextPhaseOneMinCumul.GetFunctionValue(t);
                t+=survival.step;
               }
//                else {
//                FunctionStructure homo=Operators.createFunction(t/timeToNextPhase.max,1.0, (1.0-t/timeToNextPhase.max)/timeToNextPhase.values.length);
//                homo.SetFunctionValuesFromInterface(homo.min,homo.max , Operators.homographie, 0.0,t,1.0,0.0);
//                temp=Operators.ComposeFunctions(timeToNextPhase,homo);
//                FunctionStructure x2=Operators.createFunction(temp.min, temp.max, temp.step);
//                x2.SetFunctionValuesFromInterface(x2.min, x2.max, Operators.pow, 2);
//                temp=Operators.createProductFunction(temp,x2); 
//                temp=Operators.createProductFunction(temp,pData); 
//                survival.values[i]=Operators.IntegrateFunction(temp,t/timeToNextPhase.max, 1.0)/timeToNextPhaseOneMinCumul.GetFunctionValue(t);
//                t+=survival.step;
//               }
//break;
                
           }
           survival.name="survivalPhase."+phase;
           Operators.plotFunction(survival);
           return survival;
       }
       
       }
//    
//        Hashtable<String,FunctionStructure> survivalRaw = new Hashtable<String,FunctionStructure>(); 
//        FunctionStructure survivalG1=Operators.createFunction(Numbers.CGN(0.0), Numbers.CGN(1.0), step);
//        Operators.MapFunctionValues(survivalG1,0.0,1.0,Operators.piecewiseLinear,0.0,);
//        survivalRaw.put("G1", );

    

