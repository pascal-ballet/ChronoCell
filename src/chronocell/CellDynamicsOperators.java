
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
//        Operators.plotFunction(dyn.G0.cumul.get("Death"));
        dyn.G0.cumul.put("G1",Operators.createCumulativeFunction(dyn.G0.density.get("G1")) );
        dyn.G0.oneMinCumul.put("Death", Operators.createAffineFunctionTransformation(-1.0, 1.0,dyn.G0.cumul.get("Death")));
        dyn.G0.oneMinCumul.put("G1", Operators.createAffineFunctionTransformation(-1.0, 1.0,dyn.G0.cumul.get("G1")));
        ///// distribution of time spent in phase
        dyn.G0.timeToNextPhaseDensity=Operators.createSumFunction(Operators.createProductFunction(dyn.G0.density.get("G1"), dyn.G0.oneMinCumul.get("Death")),
        Operators.createProductFunction(dyn.G0.density.get("Death"), dyn.G0.oneMinCumul.get("G1")));
        dyn.G0.timeToNextPhaseDensity.name="G0.timeDensity";
        dyn.G0.timeToNextPhaseOneMinCumul=Operators.createProductFunction(dyn.G0.oneMinCumul.get("Death"), dyn.G0.oneMinCumul.get("G1"));
//        Operators.plotFunction(dyn.G0.timeToNextPhaseOneMinCumul);
                
        
       // G1
        dyn.G1.cumul.put("G0",Operators.createCumulativeFunction(dyn.G1.density.get("G0")));
        dyn.G1.cumul.put("S",Operators.createCumulativeFunction(dyn.G1.density.get("S")) );
        dyn.G1.oneMinCumul.put("G0", Operators.createAffineFunctionTransformation(-1.0, 1.0,dyn.G1.cumul.get("G0")));
        dyn.G1.oneMinCumul.put("S", Operators.createAffineFunctionTransformation(-1.0, 1.0,dyn.G1.cumul.get("S")));
        ///// distribution of time spent in phase, replaced simply by density to next phase in cycle
//        dyn.G1.timeDensity=Operators.createSumFunction(Operators.createProductFunction(dyn.G1.density.get("G0"), dyn.G1.oneMinCumul.get("S")),
//        Operators.createProductFunction(dyn.G1.density.get("S"), dyn.G1.oneMinCumul.get("G0")));
        dyn.G1.timeToNextPhaseDensity=Operators.createFunctionCopy(dyn.G1.density.get("S"));
        dyn.G1.timeToNextPhaseDensity.name="G1.timeToNextPhaseDensity";
        dyn.G1.timeToNextPhaseOneMinCumul=Operators.createFunctionCopy(dyn.G1.oneMinCumul.get("S"));
        dyn.G1.timeToNextPhaseOneMinCumul.name="G1.timeToNextPhaseOneMinCumul";
        // S
        dyn.S.cumul.put("G2",Operators.createCumulativeFunction(dyn.S.density.get("G2")) );
        dyn.S.oneMinCumul.put("G2", Operators.createAffineFunctionTransformation(-1.0, 1.0,dyn.S.cumul.get("G2")));
       ///// distribution of time spent in phase
        dyn.S.timeToNextPhaseDensity=Operators.createFunctionCopy(dyn.S.density.get("G2"));
        dyn.S.timeToNextPhaseDensity.name="S.timeToNextPhaseDensity";
        dyn.S.timeToNextPhaseOneMinCumul=Operators.createFunctionCopy(dyn.S.oneMinCumul.get("G2"));
        dyn.S.timeToNextPhaseOneMinCumul.name="S.timeToNextPhaseOneMinCumul";
        // G2
        dyn.G2.cumul.put("M",Operators.createCumulativeFunction(dyn.G2.density.get("M")) );
        dyn.G2.oneMinCumul.put("M", Operators.createAffineFunctionTransformation(-1.0, 1.0,dyn.G2.cumul.get("M")));
        ///// distribution of time spent in phase
        dyn.G2.timeToNextPhaseDensity=Operators.createFunctionCopy(dyn.G2.density.get("M"));
        dyn.G2.timeToNextPhaseDensity.name="G2.timeToNextPhaseDensity";
        dyn.G2.timeToNextPhaseOneMinCumul=Operators.createFunctionCopy(dyn.G2.oneMinCumul.get("M"));
        dyn.G2.timeToNextPhaseOneMinCumul.name="G2.timeToNextPhaseOneMinCumul";
        // M
        dyn.M.cumul.put("G1",Operators.createCumulativeFunction(dyn.M.density.get("G1")) );
        dyn.M.oneMinCumul.put("G1", Operators.createAffineFunctionTransformation(-1.0, 1.0,dyn.M.cumul.get("G1")));
        ///// distribution of time spent in phase
        dyn.M.timeToNextPhaseDensity=Operators.createFunctionCopy(dyn.M.density.get("G1"));
        dyn.M.timeToNextPhaseDensity.name="M.timeToNextPhaseDensity";
        dyn.M.timeToNextPhaseOneMinCumul=Operators.createFunctionCopy(dyn.M.oneMinCumul.get("G1"));
        dyn.M.timeToNextPhaseOneMinCumul.name="M.timeToNextPhaseOneMinCumul";
        
        // Solutions filters
        dyn.G0.solutionFilter=Operators.createProductFunction(dyn.G0.oneMinCumul.get("Death"),dyn.G0.oneMinCumul.get("G1"));
        dyn.G0.solutionFilter.checkAndAdjustSupport();
//        Operators.plotFunction(dyn.G0.solutionFilter);
        
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
//      Operators.plotFunction(dyn.M.thetaConvolution);
        
        
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
