/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;
//import static chronocell.Operators.exp;
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
        dyn.G0.oneMinCumul.put("Death", Operators.AffineFunctionTransformation(-1.0, 1.0,dyn.G0.cumul.get("Death")));
        dyn.G0.oneMinCumul.put("G1", Operators.AffineFunctionTransformation(-1.0, 1.0,dyn.G0.cumul.get("G1")));
        
       
 
        
        temp=Operators.MultiplyFunctions(dyn.G0.density.get("Death"),dyn.G0.oneMinCumul.get("G1"));
        x=Operators.IntegrateFunction(temp,dyn.G0.density.get("Death").min,dyn.G0.density.get("Death").max);
        dyn.G0.weight.put("Death",x);
        temp=Operators.MultiplyFunctions(dyn.G0.density.get("G1"),dyn.G0.oneMinCumul.get("Death"));
        x=Operators.IntegrateFunction(temp,dyn.G0.density.get("G1").min,dyn.G0.density.get("G1").max);
        dyn.G0.weight.put("G1",x);
        // normalisation
        x=dyn.G0.weight.get("Death")+dyn.G0.weight.get("G1");
        dyn.G0.weight.put("Death",dyn.G0.weight.get("Death")/x);
        dyn.G0.weight.put("G1",dyn.G0.weight.get("G1")/x);
        
//        System.out.println("p1 , p2="+dyn.G0.weight.get("Death")+dyn.G0.weight.get("G1"));
        FunctionStructure f,F,MF,MF2,ind;
        f=dyn.G0.density.get("Death");
        F=dyn.G0.cumul.get("G1");
        temp=Operators.MultiplyFunctions(f,F);
        ind=Operators.MultiplyFunctions(Operators.FunctionSupport(f),Operators.FunctionSupport(F));
        MF=Operators.MultiplyFunctions(ind,dyn.G0.oneMinCumul.get("Death"));
        MF=Operators.PowerOfFunction(MF,-1.0);
        MF2=Operators.MultiplyFunctions(ind,dyn.G0.oneMinCumul.get("G1"));
        MF2=Operators.PowerOfFunction(MF2,-1.0);
        temp=Operators.MultiplyFunctions(temp, MF);
        temp=Operators.MultiplyFunctions(temp, MF2);
        dyn.G0.alpha.put("Death", Operators.CumulativeFunction(temp));
        
//        
//        temp=Operators.MultiplyFunctions(dyn.G0.density.get("G1"), dyn.G0.cumul.get("Death"));
//        temp=Operators.MultiplyFunctions(temp, Operators.PowerOfFunction(dyn.G0.oneMinCumul.get("Death"), -1.0));
//        temp=Operators.MultiplyFunctions(temp, Operators.PowerOfFunction(dyn.G0.oneMinCumul.get("G1"), -1.0));
        
        f=dyn.G0.density.get("G1");
        F=dyn.G0.cumul.get("Death");
        temp=Operators.MultiplyFunctions(f,F);
        ind=Operators.MultiplyFunctions(Operators.FunctionSupport(f),Operators.FunctionSupport(F));
        MF=Operators.MultiplyFunctions(ind,dyn.G0.oneMinCumul.get("Death"));
        MF=Operators.PowerOfFunction(MF,-1.0);
        MF2=Operators.MultiplyFunctions(ind,dyn.G0.oneMinCumul.get("G1"));
        MF2=Operators.PowerOfFunction(MF2,-1.0);
        temp=Operators.MultiplyFunctions(temp, MF);
        temp=Operators.MultiplyFunctions(temp, MF2);       
        dyn.G0.alpha.put("G1", Operators.CumulativeFunction(temp));
        
        temp=Operators.ComposeFunctionInterfaceFunctionStructure(dyn.G0.alpha.get("Death"),Operators.exp,-1.0);
        temp=Operators.MultiplyFunctions(dyn.G0.oneMinCumul.get("Death"), temp);
        dyn.G0.SolutionFilter=Operators.PowerOfFunction(temp, dyn.G0.weight.get("Death"));
        
        temp=Operators.ComposeFunctionInterfaceFunctionStructure(dyn.G0.alpha.get("G1"),Operators.exp,-1.0);
        temp=Operators.MultiplyFunctions(dyn.G0.oneMinCumul.get("G1"), temp);
        temp=Operators.PowerOfFunction(temp, dyn.G0.weight.get("G1"));
        dyn.G0.SolutionFilter=Operators.MultiplyFunctions(dyn.G0.SolutionFilter, temp);
        
                 

        
        // G1
        dyn.G1.cumul.put("G0",Operators.CumulativeFunction(dyn.G1.density.get("G0")));
        dyn.G1.cumul.put("S",Operators.CumulativeFunction(dyn.G1.density.get("S")) );
        
        dyn.G1.oneMinCumul.put("G0", Operators.AffineFunctionTransformation(-1.0, 1.0,dyn.G1.cumul.get("G0")));
        dyn.G1.oneMinCumul.put("S", Operators.AffineFunctionTransformation(-1.0, 1.0,dyn.G1.cumul.get("S")));
        
     
        
        temp=Operators.MultiplyFunctions(dyn.G1.density.get("G0"),dyn.G1.oneMinCumul.get("S"));
        x=Operators.IntegrateFunction(temp,dyn.G1.density.get("G0").min,dyn.G1.density.get("G0").max);
        dyn.G1.weight.put("G0",x);
        temp=Operators.MultiplyFunctions(dyn.G1.density.get("S"),dyn.G1.oneMinCumul.get("G0"));
        x=Operators.IntegrateFunction(temp,dyn.G1.density.get("S").min,dyn.G1.density.get("S").max);
        dyn.G1.weight.put("S",x);
        // normalisation
        x=dyn.G1.weight.get("G0")+dyn.G1.weight.get("S");
        dyn.G1.weight.put("G0",dyn.G1.weight.get("G0")/x);
        dyn.G1.weight.put("S",dyn.G1.weight.get("S")/x);
        
//        System.out.printf("p1 + p2="+x+"\n");
        
//        temp=Operators.MultiplyFunctions(dyn.G1.density.get("G0"), dyn.G1.cumul.get("S"));
//        temp=Operators.MultiplyFunctions(temp, Operators.PowerOfFunction(dyn.G1.oneMinCumul.get("G0"), -1.0));
//        temp=Operators.MultiplyFunctions(temp, Operators.PowerOfFunction(dyn.G1.oneMinCumul.get("S"), -1.0));
        
        f=dyn.G1.density.get("G0");
        F=dyn.G1.cumul.get("S");
        temp=Operators.MultiplyFunctions(f,F);
//        Operators.plotFunction(F);
//        Operators.plotFunction(Operators.FunctionSupport(F));
        ind=Operators.MultiplyFunctions(Operators.FunctionSupport(f),Operators.FunctionSupport(F));
        MF=Operators.MultiplyFunctions(ind,dyn.G1.oneMinCumul.get("G0"));
        MF=Operators.PowerOfFunction(MF,-1.0);
        MF2=Operators.MultiplyFunctions(ind,dyn.G1.oneMinCumul.get("S"));
        MF2=Operators.PowerOfFunction(MF2,-1.0);
        temp=Operators.MultiplyFunctions(temp, MF);
        temp=Operators.MultiplyFunctions(temp, MF2);
        dyn.G1.alpha.put("G0", Operators.CumulativeFunction(temp));
        
                        
        temp=Operators.MultiplyFunctions(dyn.G1.density.get("S"), dyn.G1.cumul.get("G0"));
        temp=Operators.MultiplyFunctions(temp, Operators.PowerOfFunction(dyn.G1.oneMinCumul.get("G0"), -1.0));
        temp=Operators.MultiplyFunctions(temp, Operators.PowerOfFunction(dyn.G1.oneMinCumul.get("S"), -1.0));
        dyn.G1.alpha.put("S", Operators.CumulativeFunction(temp));
        
        temp=Operators.ComposeFunctionInterfaceFunctionStructure(dyn.G1.alpha.get("G0"),Operators.exp,-1.0);
        temp=Operators.MultiplyFunctions(dyn.G1.oneMinCumul.get("G0"), temp);
        dyn.G1.SolutionFilter=Operators.PowerOfFunction(temp, dyn.G1.weight.get("G0"));
        temp=Operators.ComposeFunctionInterfaceFunctionStructure(dyn.G1.alpha.get("S"),Operators.exp,-1.0);
        temp=Operators.MultiplyFunctions(dyn.G1.oneMinCumul.get("S"), temp);
        temp=Operators.PowerOfFunction(temp, dyn.G1.weight.get("S"));
        dyn.G1.SolutionFilter=Operators.MultiplyFunctions(dyn.G1.SolutionFilter, temp);
 


        // S
        dyn.S.cumul.put("G2",Operators.CumulativeFunction(dyn.S.density.get("G2")) );
        dyn.S.oneMinCumul.put("G2", Operators.AffineFunctionTransformation(-1.0, 1.0,dyn.S.cumul.get("G2")));
        dyn.S.weight.put("G2",1.0);
        dyn.S.SolutionFilter=dyn.S.oneMinCumul.get("G2");
        
       
        
        
        // G2
        dyn.G2.cumul.put("M",Operators.CumulativeFunction(dyn.G2.density.get("M")) );
        dyn.G2.oneMinCumul.put("M", Operators.AffineFunctionTransformation(-1.0, 1.0,dyn.G2.cumul.get("M")));
        dyn.G2.weight.put("M",1.0);
        dyn.G2.SolutionFilter=dyn.G2.oneMinCumul.get("M");
//        dyn.G2.ThetaConvolution=Operators.copyFunction(dyn.S.density.get("G2"));

        // M
        dyn.M.cumul.put("G1",Operators.CumulativeFunction(dyn.M.density.get("G1")) );
        dyn.M.oneMinCumul.put("G1", Operators.AffineFunctionTransformation(-1.0, 1.0,dyn.M.cumul.get("G1")));
        dyn.M.weight.put("G1",1.0);
        dyn.M.SolutionFilter=dyn.M.oneMinCumul.get("G1");
//        dyn.M.ThetaConvolution=Operators.copyFunction(dyn.G2.density.get("M"));
        

//      Intermediate function thetaConvolution
//      G0
        dyn.G0.ThetaConvolution=Operators.copyFunction(dyn.G1.density.get("G0"));
        dyn.G0.ThetaConvolution=Operators.MultiplyFunctions(dyn.G0.ThetaConvolution, Operators.PowerOfFunction(dyn.G1.oneMinCumul.get("G0"),1.0-dyn.G1.weight.get("G0")));
        dyn.G0.ThetaConvolution=Operators.MultiplyFunctions(dyn.G0.ThetaConvolution, Operators.PowerOfFunction(dyn.G1.oneMinCumul.get("S"),1.0-dyn.G1.weight.get("S")));
        temp=Operators.copyFunction(dyn.G1.alpha.get("G0"));
        Operators.MapFunctionValues(temp, temp.min, temp.max, Operators.exp,-dyn.G1.weight.get("G0") );
        dyn.G0.ThetaConvolution=Operators.MultiplyFunctions(dyn.G0.ThetaConvolution, temp);
        temp=Operators.copyFunction(dyn.G1.alpha.get("S"));
        Operators.MapFunctionValues(temp, temp.min, temp.max, Operators.exp,-dyn.G1.weight.get("S") );
        dyn.G0.ThetaConvolution=Operators.MultiplyFunctions(dyn.G0.ThetaConvolution, temp);
        
        
//        G1
        // thetaConvolution only to be used with theta_0
        dyn.G1.ThetaConvolution=Operators.copyFunction(dyn.G0.density.get("G1"));
        dyn.G1.ThetaConvolution=Operators.MultiplyFunctions(dyn.G1.ThetaConvolution, Operators.PowerOfFunction(dyn.G0.oneMinCumul.get("G1"),1.0-dyn.G0.weight.get("G1")));
        dyn.G1.ThetaConvolution=Operators.MultiplyFunctions(dyn.G1.ThetaConvolution, Operators.PowerOfFunction(dyn.G0.oneMinCumul.get("Death"),1.0-dyn.G0.weight.get("Death")));
        
        temp=Operators.copyFunction(dyn.G0.alpha.get("G1"));
        Operators.MapFunctionValues(temp, temp.min, temp.max, Operators.exp,-dyn.G0.weight.get("G1") );
        dyn.G1.ThetaConvolution=Operators.MultiplyFunctions(dyn.G1.ThetaConvolution, temp);
        temp=Operators.copyFunction(dyn.G0.alpha.get("Death"));
        Operators.MapFunctionValues(temp, temp.min, temp.max, Operators.exp,-dyn.G0.weight.get("Death") );
        dyn.G1.ThetaConvolution=Operators.MultiplyFunctions(dyn.G1.ThetaConvolution, temp);
//        S
        
        dyn.S.ThetaConvolution=Operators.copyFunction(dyn.G1.density.get("S"));
        dyn.S.ThetaConvolution=Operators.MultiplyFunctions(dyn.S.ThetaConvolution, Operators.PowerOfFunction(dyn.G1.oneMinCumul.get("S"),1.0-dyn.G1.weight.get("S")));
        dyn.S.ThetaConvolution=Operators.MultiplyFunctions(dyn.S.ThetaConvolution, Operators.PowerOfFunction(dyn.G1.oneMinCumul.get("G0"),1.0-dyn.G1.weight.get("G0")));
        temp=Operators.copyFunction(dyn.G1.alpha.get("S"));
        Operators.MapFunctionValues(temp, temp.min, temp.max, Operators.exp,-dyn.G1.weight.get("S") );
        dyn.S.ThetaConvolution=Operators.MultiplyFunctions(dyn.S.ThetaConvolution, temp);
        temp=Operators.copyFunction(dyn.G1.alpha.get("G0"));
        Operators.MapFunctionValues(temp, temp.min, temp.max, Operators.exp,-dyn.G1.weight.get("G0") );
        dyn.S.ThetaConvolution=Operators.MultiplyFunctions(dyn.S.ThetaConvolution, temp);
        
        
    }
}
