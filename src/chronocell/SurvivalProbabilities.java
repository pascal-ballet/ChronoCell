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
            while(dose>data.getPhase(phase)[iDose][0]) iDose++;
            iDose--; // la dose est comprise entre la ligne i et la ligne i+1
            // à terme il faudrait interpoler, mais on peut commencer simplement par une approximation de la dose par celle de la ligne i
            double[] p=new double[(data.getPhase(phase)[0].length-1)*2];
            for (int j=0;j<p.length/2;j++){
                p[2*j]=data.getPhase(phase)[0][j+1];
                p[2*j+1]=data.getPhase(phase)[iDose][j+1];
            }
            FunctionStructure pData=Operators.createFunction(0.0, 1.0, timeToNextPhase.step);
            pData.name="donnéesSurviePhase."+phase;
            pData.SetFunctionValuesFromInterface(0.0, 1.0, Operators.piecewiseLinear, p);
            pData.setSideValues();
            FunctionStructure survival=Operators.createFunction(0.0,timeToNextPhase.max,timeToNextPhase.step);
            survival.name="survival";
//            A t=0 l'homographie n'est pas définie, mais la proba est p[0]
            survival.values[0]=pData.values[0];
            
            double t=survival.closestGridPoint(survival.min+survival.step);
            
            
            
            FunctionStructure temp=new FunctionStructure();
            
                
            for (int i=survival.minIndex+1;i<=survival.maxIndex;i++){
            FunctionStructure homographie=Operators.createFunction(t, timeToNextPhase.max, timeToNextPhase.step,"homo");
            homographie.SetFunctionValuesFromInterface(homographie.min,homographie.max , Operators.homographie, 0.0,t,1.0,0.0);
            homographie.setSideValues();
                
            temp=Operators.ComposeFunctionInterfaceWithFunction(Operators.piecewiseLinear,homographie,p);
                 if ((phase==3)&&(i==20))  {
                  Operators.plotFunction(timeToNextPhase);
                  Operators.plotFunction(pData);
                  Operators.plotFunction(temp);
              }
            temp.name="temp";
//            Operators.PrintFunction(temp,false);
//                Operators.affineFunctionTransformation(temp, 1, -pData.values[pData.values.length-1]);
            FunctionStructure temp2=Operators.createProductFunction(temp, timeToNextPhase);
            temp2.name="temp2";
              
//                Operators.PrintFunction(temp2, false);
//                if (Operators.IntegrateFunction(temp2, t, timeToNextPhase.max)>=0.000001){
//            survival.values[i]=Operators.IntegrateFunction(temp2, t, timeToNextPhase.max)/timeToNextPhaseOneMinCumul.GetFunctionValue(t);//+pData.values[pData.values.length-1];
//       
//                }
//                else {
                    survival.values[i]= pData.values[pData.values.length-1]
                    +(pData.values[pData.values.length-1]-pData.values[pData.values.length-3])/(2*pData.step)*(t-timeToNextPhase.max)/(2*t);
//
//                }
            t=survival.closestGridPoint(t+survival.step);
//                   System.out.println("t= "+t+", 1-F(t)= "+timeToNextPhaseOneMinCumul.GetFunctionValue(t)+", integrale= "+Operators.IntegrateFunction(temp2, t, temp2.max));
//               }
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
            
//                Operators.plotFunction(temp);
           survival.name="survivalPhase."+phase;
//           Operators.plotFunction(survival);
           return survival;
       }
       
       }
//    
//        Hashtable<String,FunctionStructure> survivalRaw = new Hashtable<String,FunctionStructure>(); 
//        FunctionStructure survivalG1=Operators.createFunction(Numbers.CGN(0.0), Numbers.CGN(1.0), step);
//        Operators.MapFunctionValues(survivalG1,0.0,1.0,Operators.piecewiseLinear,0.0,);
//        survivalRaw.put("G1", );

    

