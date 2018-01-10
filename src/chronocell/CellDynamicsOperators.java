/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;
//import static chronocell.Operators.exp;

import static chronocell.Operators.CropFunction;
import static chronocell.Operators.MultiplyFunctions;
import static chronocell.Operators.PowerOfFunction;

/**
 *
 * @author goby
 */
public class CellDynamicsOperators {
    public static void DynamicsFilling(CellDynamics dyn){
        FunctionStructure temp=null;
        double x=0.0;
        // G0
        dyn.G0.cumul.put("Death",Operators.CumulativeFunction(dyn.G0.density.get("Death")));
        dyn.G0.cumul.put("G1",Operators.CumulativeFunction(dyn.G0.density.get("G1")) );
        dyn.G0.oneMinCumul.put("Death", Operators.CropFunction(Operators.AffineFunctionTransformation(-1.0, 1.0,dyn.G0.cumul.get("Death"))));
        dyn.G0.oneMinCumul.put("G1", Operators.CropFunction(Operators.AffineFunctionTransformation(-1.0, 1.0,dyn.G0.cumul.get("G1"))));
        ///// time in phase
        dyn.G0.timeDensity=Operators.AddFunctions(Operators.MultiplyFunctions(dyn.G0.density.get("G1"), dyn.G0.oneMinCumul.get("Death")),
        Operators.MultiplyFunctions(dyn.G0.density.get("Death"), dyn.G0.oneMinCumul.get("G1")));
        dyn.G0.timeOneMinCumul=Operators.MultiplyFunctions(dyn.G0.oneMinCumul.get("Death"), dyn.G0.oneMinCumul.get("G1"));
       // G1
        dyn.G1.cumul.put("G0",Operators.CumulativeFunction(dyn.G1.density.get("G0")));
        dyn.G1.cumul.put("S",Operators.CumulativeFunction(dyn.G1.density.get("S")) );
        dyn.G1.oneMinCumul.put("G0", Operators.AffineFunctionTransformation(-1.0, 1.0,dyn.G1.cumul.get("G0")));
        dyn.G1.oneMinCumul.put("S", Operators.AffineFunctionTransformation(-1.0, 1.0,dyn.G1.cumul.get("S")));
        ///// time in phase
        dyn.G1.timeDensity=Operators.AddFunctions(Operators.MultiplyFunctions(dyn.G1.density.get("G0"), dyn.G1.oneMinCumul.get("S")),
        Operators.MultiplyFunctions(dyn.G1.density.get("S"), dyn.G1.oneMinCumul.get("G0")));
        dyn.G1.timeOneMinCumul=Operators.MultiplyFunctions(dyn.G1.oneMinCumul.get("G0"), dyn.G1.oneMinCumul.get("S"));
        // S
        dyn.S.cumul.put("G2",Operators.CumulativeFunction(dyn.S.density.get("G2")) );
        dyn.S.oneMinCumul.put("G2", Operators.CropFunction(Operators.AffineFunctionTransformation(-1.0, 1.0,dyn.S.cumul.get("G2"))));
        ///// time in phase
        dyn.S.timeDensity=Operators.copyFunction(dyn.S.density.get("G2"));
        dyn.S.timeOneMinCumul=Operators.copyFunction(dyn.S.oneMinCumul.get("G2"));
        // G2
        dyn.G2.cumul.put("M",Operators.CumulativeFunction(dyn.G2.density.get("M")) );
        dyn.G2.oneMinCumul.put("M", Operators.CropFunction(Operators.AffineFunctionTransformation(-1.0, 1.0,dyn.G2.cumul.get("M"))));
        ///// time in phase
        dyn.G2.timeDensity=Operators.copyFunction(dyn.G2.density.get("M"));
        dyn.G2.timeOneMinCumul=Operators.copyFunction(dyn.G2.oneMinCumul.get("M"));
        // M
        dyn.M.cumul.put("G1",Operators.CumulativeFunction(dyn.M.density.get("G1")) );
        dyn.M.oneMinCumul.put("G1", Operators.CropFunction(Operators.AffineFunctionTransformation(-1.0, 1.0,dyn.M.cumul.get("G1"))));
        ///// time in phase
        dyn.M.timeDensity=Operators.copyFunction(dyn.M.density.get("G1"));
        dyn.M.timeOneMinCumul=Operators.copyFunction(dyn.M.oneMinCumul.get("G1"));
        // Solutions filters
        dyn.G0.solutionFilter=Operators.MultiplyFunctions(dyn.G0.oneMinCumul.get("Death"),dyn.G0.oneMinCumul.get("G1"));
        dyn.G1.solutionFilter=Operators.MultiplyFunctions(dyn.G1.oneMinCumul.get("G0"),dyn.G1.oneMinCumul.get("S"));
        dyn.S.solutionFilter=Operators.copyFunction(dyn.S.oneMinCumul.get("G2"));
        dyn.G2.solutionFilter=Operators.copyFunction(dyn.G2.oneMinCumul.get("M"));
        dyn.M.solutionFilter=Operators.copyFunction(dyn.M.oneMinCumul.get("G1"));
//      phase G0, to be convolved with theta1
        dyn.G0.thetaConvolution=Operators.MultiplyFunctions(dyn.G1.density.get("G0"),dyn.G1.oneMinCumul.get("S"));
//      phase G1, part to be convolved with theta0
        dyn.G1.thetaConvolution=Operators.MultiplyFunctions(dyn.G0.density.get("G1"),dyn.G0.oneMinCumul.get("Death"));
//      phase S, to be convolved with theta1
        dyn.S.thetaConvolution=Operators.MultiplyFunctions(dyn.G1.density.get("S"),dyn.G1.oneMinCumul.get("G0"));
//      phase G2  
        dyn.G2.thetaConvolution=Operators.copyFunction(dyn.S.density.get("G2"));
//      phase M 
        dyn.M.thetaConvolution=Operators.copyFunction(dyn.G2.density.get("M"));
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
