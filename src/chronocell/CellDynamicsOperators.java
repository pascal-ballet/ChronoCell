/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;
//import static chronocell.Operators.exp;

//import static chronocell.Operators.CropFunction;
import static chronocell.Operators.createProductFunction;
import static chronocell.Operators.createPowerOfFunction;

/**
 *
 * @author goby
 */
public class CellDynamicsOperators {
    public static void DynamicsFilling(CellDynamics dyn){
        FunctionStructure temp=null;
        double x=0.0;
        // G0
        dyn.G0.cumul.put("Death",Operators.createCumulativeFunction(dyn.G0.density.get("Death")));
        dyn.G0.cumul.put("G1",Operators.createCumulativeFunction(dyn.G0.density.get("G1")) );
        dyn.G0.oneMinCumul.put("Death", Operators.createAffineFunctionTransformation(-1.0, 1.0,dyn.G0.cumul.get("Death")));
        dyn.G0.oneMinCumul.put("G1", Operators.createAffineFunctionTransformation(-1.0, 1.0,dyn.G0.cumul.get("G1")));
        ///// distribution of time spent in phase
        dyn.G0.timeDensity=Operators.createSumFunction(Operators.createProductFunction(dyn.G0.density.get("G1"), dyn.G0.oneMinCumul.get("Death")),
        Operators.createProductFunction(dyn.G0.density.get("Death"), dyn.G0.oneMinCumul.get("G1")));
        dyn.G0.timeDensity.name="G0.timeDensity";
        dyn.G0.timeOneMinCumul=Operators.createProductFunction(dyn.G0.oneMinCumul.get("Death"), dyn.G0.oneMinCumul.get("G1"));
        
       // G1
        dyn.G1.cumul.put("G0",Operators.createCumulativeFunction(dyn.G1.density.get("G0")));
        dyn.G1.cumul.put("S",Operators.createCumulativeFunction(dyn.G1.density.get("S")) );
        dyn.G1.oneMinCumul.put("G0", Operators.createAffineFunctionTransformation(-1.0, 1.0,dyn.G1.cumul.get("G0")));
        dyn.G1.oneMinCumul.put("S", Operators.createAffineFunctionTransformation(-1.0, 1.0,dyn.G1.cumul.get("S")));
        ///// distribution of time spent in phase
        dyn.G1.timeDensity=Operators.createSumFunction(Operators.createProductFunction(dyn.G1.density.get("G0"), dyn.G1.oneMinCumul.get("S")),
        Operators.createProductFunction(dyn.G1.density.get("S"), dyn.G1.oneMinCumul.get("G0")));
        dyn.G1.timeDensity.name="G1.timeDensity";
        dyn.G1.timeOneMinCumul=Operators.createProductFunction(dyn.G1.oneMinCumul.get("G0"), dyn.G1.oneMinCumul.get("S"));
        // S
        dyn.S.cumul.put("G2",Operators.createCumulativeFunction(dyn.S.density.get("G2")) );
        dyn.S.oneMinCumul.put("G2", Operators.createAffineFunctionTransformation(-1.0, 1.0,dyn.S.cumul.get("G2")));
       ///// distribution of time spent in phase
        dyn.S.timeDensity=Operators.createFunctionCopy(dyn.S.density.get("G2"));
        dyn.S.timeDensity.name="S.timeDensity";
        dyn.S.timeOneMinCumul=Operators.createFunctionCopy(dyn.S.oneMinCumul.get("G2"));
        // G2
        dyn.G2.cumul.put("M",Operators.createCumulativeFunction(dyn.G2.density.get("M")) );
        dyn.G2.oneMinCumul.put("M", Operators.createAffineFunctionTransformation(-1.0, 1.0,dyn.G2.cumul.get("M")));
        ///// distribution of time spent in phase
        dyn.G2.timeDensity=Operators.createFunctionCopy(dyn.G2.density.get("M"));
        dyn.G2.timeDensity.name="G2.timeDensity";
        dyn.G2.timeOneMinCumul=Operators.createFunctionCopy(dyn.G2.oneMinCumul.get("M"));
        // M
        dyn.M.cumul.put("G1",Operators.createCumulativeFunction(dyn.M.density.get("G1")) );
        dyn.M.oneMinCumul.put("G1", Operators.createAffineFunctionTransformation(-1.0, 1.0,dyn.M.cumul.get("G1")));
        ///// distribution of time spent in phase
        dyn.M.timeDensity=Operators.createFunctionCopy(dyn.M.density.get("G1"));
        dyn.M.timeDensity.name="M.timeDensity";
        dyn.M.timeOneMinCumul=Operators.createFunctionCopy(dyn.M.oneMinCumul.get("G1"));
        
        // Solutions filters
        dyn.G0.solutionFilter=Operators.createProductFunction(dyn.G0.oneMinCumul.get("Death"),dyn.G0.oneMinCumul.get("G1"));
        dyn.G0.solutionFilter.checkAndAdjustSupport();
        
        dyn.G1.solutionFilter=Operators.createProductFunction(dyn.G1.oneMinCumul.get("G0"),dyn.G1.oneMinCumul.get("S"));
        dyn.G1.solutionFilter.checkAndAdjustSupport();
        
        dyn.S.solutionFilter=Operators.createFunctionCopy(dyn.S.oneMinCumul.get("G2"));
        dyn.S.solutionFilter.checkAndAdjustSupport();
        
        dyn.G2.solutionFilter=Operators.createFunctionCopy(dyn.G2.oneMinCumul.get("M"));
        dyn.G2.solutionFilter.checkAndAdjustSupport();
        
        dyn.M.solutionFilter=Operators.createFunctionCopy(dyn.M.oneMinCumul.get("G1"));
        dyn.M.solutionFilter.checkAndAdjustSupport();
        
//      phase G0, to be convolved with theta1
        dyn.G0.thetaConvolution=Operators.createProductFunction(dyn.G1.density.get("G0"),dyn.G1.oneMinCumul.get("S"));
        dyn.G0.thetaConvolution.checkAndAdjustSupport();
//      phase G1, part to be convolved with theta0
        dyn.G1.thetaConvolution=Operators.createProductFunction(dyn.G0.density.get("G1"),dyn.G0.oneMinCumul.get("Death"));
        dyn.G1.thetaConvolution.checkAndAdjustSupport();
//      phase S, to be convolved with theta1
        dyn.S.thetaConvolution=Operators.createProductFunction(dyn.G1.density.get("S"),dyn.G1.oneMinCumul.get("G0"));
        dyn.S.thetaConvolution.checkAndAdjustSupport();
//      phase G2  
        dyn.G2.thetaConvolution=Operators.createFunctionCopy(dyn.S.density.get("G2"));
//      phase M 
        dyn.M.thetaConvolution=Operators.createFunctionCopy(dyn.G2.density.get("M"));
//      
        
        
    }
    public static CellDynamics copyDynamics(CellDynamics dyn){
        CellDynamics copy= new CellDynamics();
        copy.G0=PhaseOperators.copyPhase(dyn.G0);
        copy.G1=PhaseOperators.copyPhase(dyn.G1);
        copy.S=PhaseOperators.copyPhase(dyn.S);
        copy.G2=PhaseOperators.copyPhase(dyn.G2);
        copy.M=PhaseOperators.copyPhase(dyn.M);
      return copy;  
    };
}
//import static chronocell.Operators.exp;
