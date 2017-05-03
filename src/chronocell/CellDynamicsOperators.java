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
    
//    public static Network createNetwork(){
//        Network dyn = new Network();
//        dyn.G0 = new Hashtable<String,FunctionStructure>();
//        return dyn;
//    }
    
    
    
    public static void DynamicsFilling(CellDynamics dyn){
        FunctionStructure temp=null;
        double x=0.0;
        // G0
        dyn.G0.cumul.put("Death",Operators.CumulativeFunction(dyn.G0.density.get("Death")));
        dyn.G0.cumul.put("G1",Operators.CumulativeFunction(dyn.G0.density.get("G1")) );
        dyn.G0.oneMinCumul.put("Death", Operators.CropFunction(Operators.AffineFunctionTransformation(-1.0, 1.0,dyn.G0.cumul.get("Death"))));
        dyn.G0.oneMinCumul.put("G1", Operators.CropFunction(Operators.AffineFunctionTransformation(-1.0, 1.0,dyn.G0.cumul.get("G1"))));
        
        
        

        // G1
        dyn.G1.cumul.put("G0",Operators.CumulativeFunction(dyn.G1.density.get("G0")));
        dyn.G1.cumul.put("S",Operators.CumulativeFunction(dyn.G1.density.get("S")) );
        dyn.G1.oneMinCumul.put("G0", Operators.AffineFunctionTransformation(-1.0, 1.0,dyn.G1.cumul.get("G0")));
        dyn.G1.oneMinCumul.put("S", Operators.AffineFunctionTransformation(-1.0, 1.0,dyn.G1.cumul.get("S")));
        
       
        
        
        


        // S
        dyn.S.cumul.put("G2",Operators.CumulativeFunction(dyn.S.density.get("G2")) );
        dyn.S.oneMinCumul.put("G2", Operators.CropFunction(Operators.AffineFunctionTransformation(-1.0, 1.0,dyn.S.cumul.get("G2"))));
        
        
        // G2
        dyn.G2.cumul.put("M",Operators.CumulativeFunction(dyn.G2.density.get("M")) );
        dyn.G2.oneMinCumul.put("M", Operators.CropFunction(Operators.AffineFunctionTransformation(-1.0, 1.0,dyn.G2.cumul.get("M"))));


        // M
        dyn.M.cumul.put("G1",Operators.CumulativeFunction(dyn.M.density.get("G1")) );
        dyn.M.oneMinCumul.put("G1", Operators.CropFunction(Operators.AffineFunctionTransformation(-1.0, 1.0,dyn.M.cumul.get("G1"))));
        
        // Solutions filters
        
        dyn.G0.SolutionFilter=Operators.MultiplyFunctions(dyn.G0.oneMinCumul.get("Death"),dyn.G0.oneMinCumul.get("G1"));
        dyn.G1.SolutionFilter=Operators.MultiplyFunctions(dyn.G1.oneMinCumul.get("G0"),dyn.G1.oneMinCumul.get("S"));
        dyn.S.SolutionFilter=dyn.S.oneMinCumul.get("G2");
        dyn.G2.SolutionFilter=dyn.G2.oneMinCumul.get("M");
        dyn.M.SolutionFilter=dyn.M.oneMinCumul.get("G1");
     
//      phase G0, to be convolved with theta1
        dyn.G0.ThetaConvolution=Operators.MultiplyFunctions(dyn.G1.density.get("G0"),dyn.G1.oneMinCumul.get("S"));
//      phase G1, part to be convolved with theta0
        dyn.G1.ThetaConvolution=Operators.MultiplyFunctions(dyn.G0.density.get("G1"),dyn.G0.oneMinCumul.get("Death"));
//      phase S, to be convolved with theta1
        dyn.S.ThetaConvolution=Operators.MultiplyFunctions(dyn.G1.density.get("S"),dyn.G1.oneMinCumul.get("G0"));
//        
    }
    
//     public static FunctionStructure AlphaFunction(Phase phase,String phase1,String phase2){
////        System.out.println("\n +++++++ phases :"+phase1+" "+phase2+"\n");
//        FunctionStructure alpha,MF1,MF2,fF;
//        alpha=Operators.MultiplyFunctionByCumulative(phase.density.get(phase1),phase.cumul.get(phase2));
//        // ne pas calculer si au départ le produit fF est nul. Crado, à reprendre.
//        if (Numbers.IsZero(Operators.GetFunctionMaxValue(alpha))){
//        return alpha;
//        }
//        else {
////        fF=MultiplyFunctions(Operators.FunctionSupport(f1),Operators.FunctionSupport(F2));
//        MF1=CropFunction(phase.oneMinCumul.get(phase1));
//        MF1=PowerOfFunction(MF1,-1.0);
//        MF2=CropFunction(phase.oneMinCumul.get(phase2));
//        MF2=Operators.PowerOfFunction(MF2,-1.0);
//        alpha=Operators.MultiplyFunctions(alpha, MF1);
//        alpha=Operators.MultiplyFunctions(alpha, MF2);
////        Operators.plotFunction(temp);
//        alpha=Operators.Primitive(alpha);
//        return alpha;
//        }
//    } 
}
