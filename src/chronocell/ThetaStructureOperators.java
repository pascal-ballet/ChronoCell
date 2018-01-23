/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;

import static chronocell.Operators.IntegrateFunction;
import static chronocell.Operators.createProductFunction;
import static chronocell.Operators.DoubleValuesArraySizeToLeft;
import static chronocell.Operators.createPowerOfFunction;
import static chronocell.Operators.createTranslatedFunction;

/**
 *
 * @author goby
 */
public class ThetaStructureOperators {
    public static void ComputeThetaNextValues(ThetaStructure theta,CellDynamics dyn){
        // If solutions' support is filled, increase the size of array sol.values
        for (int i=0;i<dyn.phaseNb;i++){
            if (theta.getPhase(i).minIndex==0){
                Operators.PrintFunction(theta.getPhase(i), false);
                DoubleValuesArraySizeToLeft(theta.getPhase(i));
                Operators.PrintFunction(theta.getPhase(i), false);
            }
//            theta.getPhase(i).min=theta.getPhase(i).min-theta.getPhase(i).step;
//            theta.getPhase(i).minIndex-=1;
        } 
        double nextVal= 0.0;
        FunctionStructure tempConvolution= new FunctionStructure();
        // phase G0
            tempConvolution=createTranslatedFunction(theta.G0.min, dyn.G0.thetaConvolution);
            nextVal=IntegrateFunction(createProductFunction(tempConvolution,theta.G1),tempConvolution.min,tempConvolution.max,tempConvolution.step/10);
            theta.G0.min=theta.G0.floorPoint(theta.G0.min-theta.G0.step);
            theta.G0.minIndex-=1;
//            theta.G0.min=theta.G0.indexPoint(theta.G0.minIndex);
            theta.G0.values[theta.G0.minIndex]=nextVal;
            
        // phase G1
            // from G0
            tempConvolution=createTranslatedFunction(theta.G1.min, dyn.G1.thetaConvolution);
            nextVal=IntegrateFunction(createProductFunction(tempConvolution,theta.G0),tempConvolution.min,tempConvolution.max,tempConvolution.step/10);
            // from M
            tempConvolution=createTranslatedFunction(theta.G1.min, dyn.M.density.get("G1"));
            nextVal+=2*IntegrateFunction(createProductFunction(tempConvolution,theta.M),tempConvolution.min,tempConvolution.max,tempConvolution.step/10);
            theta.G1.min=theta.G1.floorPoint(theta.G1.min-theta.G1.step);
            theta.G1.minIndex-=1;
//            System.out.println("chronocell.ThetaStructureOperators.ComputeThetaNextValues()"+theta.G1.indexPoint(theta.G1.minIndex));
            theta.G1.values[theta.G1.minIndex]=nextVal;
            
//            System.out.println("next =:"+nextVal);
            
        // phase S
            tempConvolution=createTranslatedFunction(theta.S.min, dyn.S.thetaConvolution);
//           dyn.S.thetaConvolution.checkBounds();
           FunctionStructure temp=createProductFunction(tempConvolution,theta.G1);
           temp.checkBounds();
            nextVal=IntegrateFunction(createProductFunction(tempConvolution,theta.G1),tempConvolution.min,tempConvolution.max,tempConvolution.step/10);
            theta.S.min=theta.S.floorPoint(theta.S.min-theta.S.step);
            theta.S.minIndex-=1;
            theta.S.values[theta.S.minIndex]=nextVal;
            
        // phase G2
           tempConvolution=createTranslatedFunction(theta.G2.min, dyn.G2.thetaConvolution);
           
            nextVal=IntegrateFunction(createProductFunction(tempConvolution,theta.S),tempConvolution.min,tempConvolution.max,tempConvolution.step/10);
            theta.G2.min=theta.G2.floorPoint(theta.G2.min-theta.G2.step);
            theta.G2.minIndex-=1;
            theta.G2.values[theta.G2.minIndex]=nextVal;
           
        // phase M
           tempConvolution=createTranslatedFunction(theta.M.min, dyn.M.thetaConvolution);
           
//           nextVal=IntegrateFunction(MultiplyFunctions(theta.G2,tempConvolution),tempConvolution.min,tempConvolution.max);
           
           nextVal=IntegrateFunction(Operators.createProductFunction(tempConvolution,theta.G2),tempConvolution.min,tempConvolution.max,tempConvolution.step/10);
           theta.M.min=theta.M.floorPoint(theta.M.min-theta.M.step);
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
