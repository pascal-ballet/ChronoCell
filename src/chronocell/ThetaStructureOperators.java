/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;

import static chronocell.Operators.IntegrateFunction;
import static chronocell.Operators.createProductFunction;
//import static chronocell.Operators.DoubleValuesArraySizeToLeft;
import static chronocell.Operators.createPowerOfFunction;
import static chronocell.Operators.createTranslatedFunction;

/**
 *
 * @author goby
 */
public class ThetaStructureOperators {
    public static void ComputeThetaNextValues(CellPopulation pop){
        // If solutions' support is filled, increase the size of array sol.values
        for (int i=0;i<pop.dynamics.phaseNb;i++){
            if (pop.theta.get(pop.currentTheta).getPhase(i).minIndex==0){
                pop.theta.get(pop.currentTheta).getPhase(i).increaseValuesSizeLeft(pop.theta.get(pop.currentTheta).getPhase(i).values.length);
            }
        } 
        double nextVal= 0.0;
        FunctionStructure temp= new FunctionStructure();
        FunctionStructure tempConvolution= new FunctionStructure();
        // phase G0
        tempConvolution=createTranslatedFunction(pop.theta.get(pop.currentTheta).G0.min, pop.dynamics.G0.thetaConvolution);
        nextVal=IntegrateFunction(createProductFunction(tempConvolution,pop.theta.get(pop.currentTheta).G1),tempConvolution.min,tempConvolution.max);
        pop.theta.get(pop.currentTheta).G0.SetFunctionValueRegardlessOfSidesValues(pop.theta.get(pop.currentTheta).G0.min-pop.timeStep, nextVal);
        // phase G1
            // from G0
        tempConvolution=createTranslatedFunction(pop.theta.get(pop.currentTheta).G1.min, pop.dynamics.G1.thetaConvolution);
        nextVal=IntegrateFunction(createProductFunction(tempConvolution,pop.theta.get(pop.currentTheta).G0),tempConvolution.min,tempConvolution.max);
            // from M
        tempConvolution=createTranslatedFunction(pop.theta.get(pop.currentTheta).G1.min, pop.dynamics.M.density.get("G1"));
        temp=createProductFunction(tempConvolution,pop.theta.get(pop.currentTheta).M);
        nextVal+=2*IntegrateFunction(temp,tempConvolution.min,tempConvolution.max);
        pop.theta.get(pop.currentTheta).G1.SetFunctionValueRegardlessOfSidesValues(pop.theta.get(pop.currentTheta).G1.min-pop.timeStep, nextVal);
        // phase S
        tempConvolution=createTranslatedFunction(pop.theta.get(pop.currentTheta).S.min, pop.dynamics.S.thetaConvolution);
        nextVal=IntegrateFunction(createProductFunction(tempConvolution,pop.theta.get(pop.currentTheta).G1),tempConvolution.min,tempConvolution.max);
        pop.theta.get(pop.currentTheta).S.SetFunctionValueRegardlessOfSidesValues(pop.theta.get(pop.currentTheta).S.min-pop.timeStep, nextVal);
        // phase G2
        tempConvolution=createTranslatedFunction(pop.theta.get(pop.currentTheta).G2.min, pop.dynamics.G2.thetaConvolution);
        nextVal=IntegrateFunction(createProductFunction(tempConvolution,pop.theta.get(pop.currentTheta).S),tempConvolution.min,tempConvolution.max);
        pop.theta.get(pop.currentTheta).G2.SetFunctionValueRegardlessOfSidesValues(pop.theta.get(pop.currentTheta).G2.min-pop.timeStep, nextVal);
        // phase M
        tempConvolution=createTranslatedFunction(pop.theta.get(pop.currentTheta).M.min, pop.dynamics.M.thetaConvolution);
        nextVal=IntegrateFunction(createProductFunction(tempConvolution,pop.theta.get(pop.currentTheta).G2),tempConvolution.min,tempConvolution.max);
        pop.theta.get(pop.currentTheta).M.SetFunctionValueRegardlessOfSidesValues(pop.theta.get(pop.currentTheta).M.min-pop.timeStep, nextVal);
    
        pop.time+=pop.timeStep; 
    }
    
    
    //***************************************************************
    
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
