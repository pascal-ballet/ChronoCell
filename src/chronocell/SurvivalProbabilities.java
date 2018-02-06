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
            FunctionStructure pData=Operators.createFunction(0.0, 1.0, p.length/2,"donnéesSurviePhase."+phase); // acune raison de considérer un pas plus fin
            pData.SetFunctionValuesFromInterface(Operators.piecewiseLinear, p);
            pData.setSideValues(); //            Operators.plotFunction(pData);
            
            FunctionStructure timeToNextPhase= Operators.createFunctionCopy(dynamics.getPhase(phase).timeToNextPhaseDensity); 
            FunctionStructure timeToNextPhaseOneMinCumul= Operators.createFunctionCopy(dynamics.getPhase(phase).timeToNextPhaseOneMinCumul); 
            FunctionStructure survival=Operators.createFunction(0.0,timeToNextPhase.max,100,"survival"+phase);
            System.out.println("chronocell.SurvivalProbabilities.survivalProbabilities()"+survival.step+", "+timeToNextPhase.max);
            survival.SetFunctionValue(0.0,pData.values[0] ); // A t=0 l'homographie n'est pas définie, mais la proba est p[0]
            
            double t=survival.step;
            FunctionStructure stretchedProba,temp=new FunctionStructure();
            while (t<survival.max){
                FunctionStructure homographie=Operators.createFunction(t, timeToNextPhase.max, 50,"homo"); // comment régler le pas ?
                if ((t-survival.max)<0.00001){
                    survival.SetFunctionValue(t, pData.getFunctionValue(1));
                    t+=survival.step;
                    continue;
                }
                homographie.SetFunctionValuesFromInterface(Operators.homographie, 0.0,t,1.0,0.0);
                homographie.setSideValues();
                stretchedProba=Operators.ComposeFunctionInterfaceWithFunction(Operators.piecewiseLinear,homographie,p);
                stretchedProba.name="stretchedProba";
                Operators.affineFunctionTransformation(stretchedProba, 1, -pData.values[pData.values.length-1]);
//                Operators.PrintFunction(stretchedProba, false);
                temp=Operators.createProductFunction(stretchedProba, timeToNextPhase);
                double integral =Operators.IntegrateFunction(temp, t, timeToNextPhase.max)/timeToNextPhaseOneMinCumul.getFunctionValue(t)+pData.values[pData.values.length-1]; 
                survival.SetFunctionValue(t, integral);
                System.out.println("chronocell.SurvivalProbabilities.survivalProbabilities(before)"+t+", "+(t<survival.max)+", "+homographie.step);
                t+=survival.step;
                System.out.println("chronocell.SurvivalProbabilities.survivalProbabilities(after)"+t+", "+(t<survival.max)+", "+survival.step);
            }   
                survival.checkAndAdjustSupport();
                Operators.plotFunction(survival);
                Operators.PrintFunction(survival, false);
//            for (int i=survival.minIndex+1;i<survival.maxIndex;i++){
//            temp2=Operators.createProductFunction(temp, timeToNextPhase);
//            temp2.name="temp2";
////              if ((i==23)||(i==24)|(i==25))  {
////                  Operators.plotFunction(timeToNextPhase);
////                  Operators.plotFunction(pData);
////                  Operators.plotFunction(temp);
////                  Operators.plotFunction(temp2);
////              }
////                Operators.PrintFunction(temp2, false);
//                if (timeToNextPhaseOneMinCumul.getFunctionValue(t)>=0.0001){
//            survival.values[i]=Operators.IntegrateFunction(temp2, t, timeToNextPhase.max)/timeToNextPhaseOneMinCumul.getFunctionValue(t)+pData.values[pData.values.length-1];
////                System.out.println("survival[i]= "+survival.values[i]+", x="+survival.pointWithIndex(i));
//////       
//                }
//                else {
//                    survival.values[i]= pData.right;
////                    +(pData.values[pData.values.length-1]-pData.values[pData.values.length-3])/(2*pData.step)*(t-timeToNextPhase.max)/(2*t);
//
//                }
////            survival.values[i]=pData.GetFunctionValue(t/timeToNextPhase.max);
////                System.out.println("t/max="+t/timeToNextPhase.max+", pdata(t/max)="+pData.GetFunctionValue(t/timeToNextPhase.max));
////                System.out.println("Indexunder="+pData.indexOfPoint(t/timeToNextPhase.max)+", point under="+pData.pointWithIndex(pData.indexOfPoint(t/timeToNextPhase.max)));
//            t=survival.roundPoint(t+survival.step);
////                   System.out.println("t= "+t+", 1-F(t)= "+timeToNextPhaseOneMinCumul.GetFunctionValue(t)+", integrale= "+Operators.IntegrateFunction(temp2, t, temp2.max));
////               }
////                else {
////                FunctionStructure homo=Operators.createFunction(t/timeToNextPhase.max,1.0, (1.0-t/timeToNextPhase.max)/timeToNextPhase.values.length);
////                homo.SetFunctionValuesFromInterface(homo.min,homo.max , Operators.homographie, 0.0,t,1.0,0.0);
////                temp=Operators.ComposeFunctions(timeToNextPhase,homo);
////                FunctionStructure x2=Operators.createFunction(temp.min, temp.max, temp.step);
////                x2.SetFunctionValuesFromInterface(x2.min, x2.max, Operators.pow, 2);
////                temp=Operators.createProductFunction(temp,x2); 
////                temp=Operators.createProductFunction(temp,pData); 
////                survival.values[i]=Operators.IntegrateFunction(temp,t/timeToNextPhase.max, 1.0)/timeToNextPhaseOneMinCumul.GetFunctionValue(t);
////                t+=survival.step;
////               }
////break;
//                
//           }
//            
////                Operators.plotFunction(temp);
//           survival.name="survivalPhase."+phase;
////           survival.setSideValues();
////           Operators.plotFunction(survival);
//survival.regularize();
           return survival;
           
       }
       
       }
//    
//        Hashtable<String,FunctionStructure> survivalRaw = new Hashtable<String,FunctionStructure>(); 
//        FunctionStructure survivalG1=Operators.createFunction(Numbers.CGN(0.0), Numbers.CGN(1.0), step);
//        Operators.MapFunctionValues(survivalG1,0.0,1.0,Operators.piecewiseLinear,0.0,);
//        survivalRaw.put("G1", );

    

