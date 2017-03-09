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
            theta.getPhase(i).min=theta.getPhase(i).min-theta.getPhase(i).step;
            theta.getPhase(i).minIndex-=1;
        } 
        double nextVal= 0.0;
        FunctionStructure tempConvolution= new FunctionStructure();
        FunctionStructure tempDensity= new FunctionStructure();
        FunctionStructure tempCumulComp= new FunctionStructure();
        // phase G0
            tempConvolution=TranslateFunction(theta.G0.min, theta.dyn.G0.ThetaConvolution);
            nextVal=theta.dyn.G1.weight.get("G0")*IntegrateFunction(MultiplyFunctions(theta.G1,tempConvolution),tempConvolution.min,tempConvolution.max);
            theta.G0.values[theta.G0.minIndex]=nextVal;
            
        // phase G1
            // from G0
            tempConvolution=TranslateFunction(theta.G1.min, theta.dyn.G1.ThetaConvolution);
            nextVal=theta.dyn.G0.weight.get("G1")*IntegrateFunction(MultiplyFunctions(theta.G0,tempConvolution),tempConvolution.min,tempConvolution.max);
            // from M
            tempConvolution=TranslateFunction(theta.G1.min, theta.dyn.M.density.get("G1"));
            nextVal+=2*IntegrateFunction(MultiplyFunctions(theta.M,tempConvolution),tempConvolution.min,tempConvolution.max);
            theta.G1.values[theta.G1.minIndex]=nextVal;
            
        // phase S
            tempConvolution=TranslateFunction(theta.S.min, theta.dyn.S.ThetaConvolution);
            nextVal=theta.dyn.G1.weight.get("S")*IntegrateFunction(MultiplyFunctions(theta.G1,tempConvolution),tempConvolution.min,tempConvolution.max);
            theta.S.values[theta.S.minIndex]=nextVal;
            
        // phase G2
           tempConvolution=TranslateFunction(theta.G2.min, theta.dyn.S.density.get("G2"));
            nextVal=IntegrateFunction(MultiplyFunctions(theta.S,tempConvolution),tempConvolution.min,tempConvolution.max);
            theta.G2.values[theta.G2.minIndex]=nextVal;
           
        // phase M
           tempConvolution=TranslateFunction(theta.M.min, theta.dyn.G2.density.get("M"));
           nextVal=IntegrateFunction(MultiplyFunctions(theta.G2,tempConvolution),tempConvolution.min,tempConvolution.max);
           theta.M.values[theta.M.minIndex]=nextVal;
    }
}
