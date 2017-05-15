/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;

import static chronocell.Operators.DoubleArraySizeToLeft;
import static chronocell.Operators.IntegrateFunction;
import static chronocell.Operators.MultiplyFunctions;
import static chronocell.Operators.PowerOfFunction;
import static chronocell.Operators.TranslateFunction;

/**
 *
 * @author goby
 */
public class ThetaStructureOperators {
    public static void ComputeThetaNextValues(ThetaStructure theta){
        // If solutions' support is filled, increase the size of array sol.values
        for (int i=0;i<theta.phaseNb;i++){
            if (theta.getPhase(i).minIndex==0){
                DoubleArraySizeToLeft(theta.getPhase(i));
            }
//            theta.getPhase(i).min=theta.getPhase(i).min-theta.getPhase(i).step;
//            theta.getPhase(i).minIndex-=1;
        } 
        double nextVal= 0.0;
        FunctionStructure tempConvolution= new FunctionStructure();
        // phase G0
            tempConvolution=TranslateFunction(theta.G0.min, theta.dyn.G0.ThetaConvolution);
//            nextVal=theta.dyn.G1.weight.get("G0")*IntegrateFunction(MultiplyFunctions(theta.G1,tempConvolution),tempConvolution.min,tempConvolution.max);
            nextVal=IntegrateFunction(MultiplyFunctions(theta.G1,tempConvolution),tempConvolution.min,tempConvolution.max);
            theta.G0.min=theta.G0.min-theta.G0.step;
            theta.G0.minIndex-=1;
            theta.G0.values[theta.G0.minIndex]=nextVal;
//            System.out.println("next =:"+theta.dyn.G1.weight.get("G0"));
            
        // phase G1
            // from G0
            tempConvolution=TranslateFunction(theta.G1.min, theta.dyn.G1.ThetaConvolution);
            nextVal=IntegrateFunction(MultiplyFunctions(theta.G0,tempConvolution),tempConvolution.min,tempConvolution.max);
            // from M
            tempConvolution=TranslateFunction(theta.G1.min, theta.dyn.M.density.get("G1"));
            nextVal+=2*IntegrateFunction(MultiplyFunctions(theta.M,tempConvolution),tempConvolution.min,tempConvolution.max);
            theta.G1.min=theta.G1.min-theta.G1.step;
            theta.G1.minIndex-=1;
            theta.G1.values[theta.G1.minIndex]=nextVal;
            
        // phase S
            tempConvolution=TranslateFunction(theta.S.min, theta.dyn.S.ThetaConvolution);
           
            nextVal=IntegrateFunction(MultiplyFunctions(theta.G1,tempConvolution),tempConvolution.min,tempConvolution.max);
            theta.S.min=theta.S.min-theta.S.step;
            theta.S.minIndex-=1;
            theta.S.values[theta.S.minIndex]=nextVal;
            
        // phase G2
           tempConvolution=TranslateFunction(theta.G2.min, theta.dyn.S.density.get("G2"));
           
            nextVal=IntegrateFunction(MultiplyFunctions(theta.S,tempConvolution),tempConvolution.min,tempConvolution.max);
            theta.G2.min=theta.G2.min-theta.G2.step;
            theta.G2.minIndex-=1;
            theta.G2.values[theta.G2.minIndex]=nextVal;
           
        // phase M
           tempConvolution=TranslateFunction(theta.M.min, theta.dyn.G2.density.get("M"));
           
           nextVal=IntegrateFunction(MultiplyFunctions(theta.G2,tempConvolution),tempConvolution.min,tempConvolution.max);
           theta.M.min=theta.M.min-theta.M.step;
            theta.M.minIndex-=1;
           theta.M.values[theta.M.minIndex]=nextVal;
    }
}
