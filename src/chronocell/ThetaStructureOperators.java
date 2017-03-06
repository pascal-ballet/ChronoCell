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
        for (int i=0;i<theta.tab.length;i++){
            if (theta.tab[i].minIndex==0){
                DoubleArraySizeToLeft(theta.tab[i]);
            }
            theta.tab[i].min=theta.tab[i].min-theta.tab[i].step;
            theta.tab[i].minIndex-=1;
        } 
        double nextVal= 0.0;
        FunctionStructure tempProb= new FunctionStructure();
        FunctionStructure tempCumul= new FunctionStructure();
        FunctionStructure tempCumulComp= new FunctionStructure();
        // phase G0
            tempProb=TranslateFunction(sol.theta[1].min, sol.transitionProbabilities[2]);
            tempCumul=TranslateFunction(sol.theta[1].min, sol.oneMinusCumulativeFunctions[2]);
            tempCumulComp=TranslateFunction(sol.theta[1].min, sol.oneMinusCumulativeFunctions[3]);
            nextVal=IntegrateFunction( MultiplyFunctions( sol.theta[1], MultiplyFunctions( MultiplyFunctions(PowerOfFunction(tempCumulComp,1.0-sol.probaSBeforeG0),tempProb),PowerOfFunction(tempCumul, sol.probaSBeforeG0-1))),tempProb.min,tempProb.max);
           sol.theta[0].values[sol.theta[0].minIndex]=nextVal;
            
        // phase G1
            // from G0
            tempProb=TranslateFunction(sol.theta[0].min, sol.transitionProbabilities[1]);
            tempCumul=TranslateFunction(sol.theta[0].min, sol.oneMinusCumulativeFunctions[1]);
            tempCumulComp=TranslateFunction(sol.theta[0].min, sol.oneMinusCumulativeFunctions[0]);
            nextVal=IntegrateFunction( MultiplyFunctions( sol.theta[0], MultiplyFunctions( MultiplyFunctions(PowerOfFunction(tempCumulComp,1.0-sol.probaDeathBeforeG1),tempProb),PowerOfFunction(tempCumul, sol.probaDeathBeforeG1-1))),tempProb.min,tempProb.max);
            // from M
            tempProb=TranslateFunction(sol.theta[4].min, sol.transitionProbabilities[6]);
            nextVal+=2*IntegrateFunction(MultiplyFunctions(sol.theta[4],tempProb),tempProb.min,tempProb.max);
            sol.theta[1].values[sol.theta[1].minIndex]=nextVal;  
            
        // phase S
            tempProb=TranslateFunction(sol.theta[1].min, sol.transitionProbabilities[3]);
            tempCumul=TranslateFunction(sol.theta[1].min, sol.oneMinusCumulativeFunctions[3]);
            tempCumulComp=TranslateFunction(sol.theta[1].min, sol.oneMinusCumulativeFunctions[2]);
            FunctionStructure temp=MultiplyFunctions( MultiplyFunctions(PowerOfFunction(tempCumulComp,sol.probaSBeforeG0),tempProb),PowerOfFunction(tempCumul, -sol.probaDeathBeforeG1));
            nextVal=IntegrateFunction( MultiplyFunctions( sol.theta[1], MultiplyFunctions( MultiplyFunctions(PowerOfFunction(tempCumulComp,sol.probaSBeforeG0),tempProb),PowerOfFunction(tempCumul, -sol.probaDeathBeforeG1))),tempProb.min,tempProb.max);
            sol.theta[2].values[sol.theta[2].minIndex]=nextVal;
        
        // phase G2
           tempProb=TranslateFunction(theta.G2.min, theta.dyn.S.density.get("G2"));
           nextVal=IntegrateFunction(MultiplyFunctions(theta.S,tempProb),tempProb.min,tempProb.max);
           theta.S.values[theta.S.minIndex]=nextVal;
           
        // phase M
           tempProb=TranslateFunction(theta.M.min, theta.dyn.G2.density.get("M"));
           nextVal=IntegrateFunction(MultiplyFunctions(theta.G2,tempProb),tempProb.min,tempProb.max);
           theta.M.values[theta.M.minIndex]=nextVal;
    }
}
