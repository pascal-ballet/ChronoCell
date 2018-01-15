/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;

import static chronocell.Operators.DoubleArraySizeToLeft;
import static chronocell.Operators.IntegrateFunction;
import static chronocell.Operators.PowerOfFunction;
import static chronocell.Operators.TranslateFunction;
import static chronocell.Operators.createProductFunction;

/**
 *
 * @author goby
 */
public class ThetaStructureOperators {
    public static void ComputeThetaNextValues(ThetaStructure theta,CellDynamics dyn){
        // If solutions' support is filled, increase the size of array sol.values
        for (int i=0;i<dyn.phaseNb;i++){
            if (theta.getPhase(i).minIndex==0){
                DoubleArraySizeToLeft(theta.getPhase(i));
            }
//            theta.getPhase(i).min=theta.getPhase(i).min-theta.getPhase(i).step;
//            theta.getPhase(i).minIndex-=1;
        } 
        double nextVal= 0.0;
        FunctionStructure tempConvolution= new FunctionStructure();
        // phase G0
            tempConvolution=TranslateFunction(theta.G0.min, dyn.G0.thetaConvolution);
            nextVal=IntegrateFunction(createProductFunction(theta.G1,tempConvolution),tempConvolution.min,tempConvolution.max);
            theta.G0.min=theta.G0.min-theta.G0.step;
            theta.G0.minIndex-=1;
            theta.G0.values[theta.G0.minIndex]=nextVal;
//            System.out.println("next =:"+theta.dyn.G1.weight.get("G0"));
            
        // phase G1
            // from G0
            tempConvolution=TranslateFunction(theta.G1.min, dyn.G1.thetaConvolution);
            nextVal=IntegrateFunction(createProductFunction(theta.G0,tempConvolution),tempConvolution.min,tempConvolution.max);
            // from M
            tempConvolution=TranslateFunction(theta.G1.min, dyn.M.density.get("G1"));
            nextVal+=2*IntegrateFunction(createProductFunction(theta.M,tempConvolution),tempConvolution.min,tempConvolution.max);
            theta.G1.min=theta.G1.min-theta.G1.step;
            theta.G1.minIndex-=1;
            theta.G1.values[theta.G1.minIndex]=nextVal;
            
        // phase S
            tempConvolution=TranslateFunction(theta.S.min, dyn.S.thetaConvolution);
           
            nextVal=IntegrateFunction(createProductFunction(theta.G1,tempConvolution),tempConvolution.min,tempConvolution.max);
            theta.S.min=theta.S.min-theta.S.step;
            theta.S.minIndex-=1;
            theta.S.values[theta.S.minIndex]=nextVal;
            
        // phase G2
           tempConvolution=TranslateFunction(theta.G2.min, dyn.G2.thetaConvolution);
           
            nextVal=IntegrateFunction(createProductFunction(theta.S,tempConvolution),tempConvolution.min,tempConvolution.max);
            theta.G2.min=theta.G2.min-theta.G2.step;
            theta.G2.minIndex-=1;
            theta.G2.values[theta.G2.minIndex]=nextVal;
           
        // phase M
           tempConvolution=TranslateFunction(theta.M.min, dyn.M.thetaConvolution);
           
//           nextVal=IntegrateFunction(MultiplyFunctions(theta.G2,tempConvolution),tempConvolution.min,tempConvolution.max);
           
           nextVal=IntegrateFunction(Operators.MultiplyFunctionRaw(theta.G2,tempConvolution),tempConvolution.min,tempConvolution.max);
           theta.M.min=theta.M.min-theta.M.step;
            theta.M.minIndex-=1;
           theta.M.values[theta.M.minIndex]=nextVal;
    }
    
    public static ThetaStructure copyTheta(ThetaStructure theta){
      ThetaStructure copy=new ThetaStructure();
      copy.startingTime=theta.startingTime;
      copy.G0=Operators.createFunctionCopy(theta.G0);
      copy.G1=Operators.createFunctionCopy(theta.G1);
      copy.S=Operators.createFunctionCopy(theta.S);
      copy.G2=Operators.createFunctionCopy(theta.G2);
      copy.M=Operators.createFunctionCopy(theta.M);    
      return copy;
    };
}
