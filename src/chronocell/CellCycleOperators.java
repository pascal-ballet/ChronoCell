/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;

/**
 *
 * @author goby
 */
public class CellCycleOperators {
    
//    public static Network createNetwork(){
//        Network net = new Network();
//        net.G0 = new Hashtable<String,FunctionStructure>();
//        return net;
//    }
    
    public static void PhaseFilling(CellCycle net){
        FunctionStructure temp=null;
        double x=0.0;
        // G0
        net.G0.cumul.put("Death",Operators.CumulativeFunction(net.G0.density.get("Death")));
        net.G0.cumul.put("G1",Operators.CumulativeFunction(net.G0.density.get("G1")) );
        
        net.G0.oneMinCumul.put("Death", Operators.AffineFunctionTransformation(-1.0, 1.0,net.G0.cumul.get("Death")));
        net.G0.oneMinCumul.put("G1", Operators.AffineFunctionTransformation(-1.0, 1.0,net.G0.cumul.get("G1")));
        
        temp=Operators.MultiplyFunctions(net.G0.density.get("Death"),net.G0.oneMinCumul.get("G1"));
        x=Operators.IntegrateFunction(temp,net.G0.density.get("Death").min,net.G0.density.get("Death").max);
        net.G0.weight.put("Death",x);
        temp=Operators.MultiplyFunctions(net.G0.density.get("G1"),net.G0.oneMinCumul.get("Death"));
        x=Operators.IntegrateFunction(temp,net.G0.density.get("G1").min,net.G0.density.get("G1").max);
        net.G0.weight.put("G1",x);
        // normalisation
        x=net.G0.weight.get("Death")+net.G0.weight.get("G1");
        net.G0.weight.put("Death",net.G0.weight.get("Death")/x);
        net.G0.weight.put("G1",net.G0.weight.get("G1")/x);
        
//        System.out.printf("p1 + p2="+x+"\n");
        
        temp=Operators.MultiplyFunctions(net.G0.density.get("Death"), net.G0.cumul.get("G1"));
        temp=Operators.MultiplyFunctions(temp, Operators.PowerOfFunction(net.G0.oneMinCumul.get("Death"), -1.0));
        temp=Operators.MultiplyFunctions(temp, Operators.PowerOfFunction(net.G0.oneMinCumul.get("G1"), -1.0));
        net.G0.alpha.put("Death", Operators.CumulativeFunction(temp));
                        
        temp=Operators.MultiplyFunctions(net.G0.density.get("G1"), net.G0.cumul.get("Death"));
        temp=Operators.MultiplyFunctions(temp, Operators.PowerOfFunction(net.G0.oneMinCumul.get("Death"), -1.0));
        temp=Operators.MultiplyFunctions(temp, Operators.PowerOfFunction(net.G0.oneMinCumul.get("G1"), -1.0));
        net.G0.alpha.put("G1", Operators.CumulativeFunction(temp));
        
        temp=Operators.ComposeFunctionInterfaceFunctionStructure(net.G0.alpha.get("Death"),Operators.exp,-1.0);
        temp=Operators.MultiplyFunctions(net.G0.oneMinCumul.get("Death"), temp);
        net.G0.thetaFilter=Operators.PowerOfFunction(temp, net.G0.weight.get("Death"));
        temp=Operators.ComposeFunctionInterfaceFunctionStructure(net.G0.alpha.get("G1"),Operators.exp,-1.0);
        temp=Operators.MultiplyFunctions(net.G0.oneMinCumul.get("G1"), temp);
        temp=Operators.PowerOfFunction(temp, net.G0.weight.get("G1"));
        net.G0.thetaFilter=Operators.MultiplyFunctions(net.G0.thetaFilter, temp);
        
        // G1
        net.G1.cumul.put("G0",Operators.CumulativeFunction(net.G1.density.get("G0")));
        net.G1.cumul.put("S",Operators.CumulativeFunction(net.G1.density.get("S")) );
        
        net.G1.oneMinCumul.put("G0", Operators.AffineFunctionTransformation(-1.0, 1.0,net.G1.cumul.get("G0")));
        net.G1.oneMinCumul.put("S", Operators.AffineFunctionTransformation(-1.0, 1.0,net.G1.cumul.get("S")));
        
        temp=Operators.MultiplyFunctions(net.G1.density.get("G0"),net.G1.oneMinCumul.get("S"));
        x=Operators.IntegrateFunction(temp,net.G1.density.get("G0").min,net.G1.density.get("G0").max);
        net.G1.weight.put("G0",x);
        temp=Operators.MultiplyFunctions(net.G1.density.get("S"),net.G1.oneMinCumul.get("G0"));
        x=Operators.IntegrateFunction(temp,net.G1.density.get("S").min,net.G1.density.get("S").max);
        net.G1.weight.put("S",x);
        // normalisation
        x=net.G1.weight.get("G0")+net.G1.weight.get("S");
        net.G1.weight.put("G0",net.G1.weight.get("G0")/x);
        net.G1.weight.put("S",net.G1.weight.get("S")/x);
        
//        System.out.printf("p1 + p2="+x+"\n");
        
        temp=Operators.MultiplyFunctions(net.G1.density.get("G0"), net.G1.cumul.get("S"));
        temp=Operators.MultiplyFunctions(temp, Operators.PowerOfFunction(net.G1.oneMinCumul.get("G0"), -1.0));
        temp=Operators.MultiplyFunctions(temp, Operators.PowerOfFunction(net.G1.oneMinCumul.get("S"), -1.0));
        net.G1.alpha.put("G0", Operators.CumulativeFunction(temp));
                        
        temp=Operators.MultiplyFunctions(net.G1.density.get("S"), net.G1.cumul.get("G0"));
        temp=Operators.MultiplyFunctions(temp, Operators.PowerOfFunction(net.G1.oneMinCumul.get("G0"), -1.0));
        temp=Operators.MultiplyFunctions(temp, Operators.PowerOfFunction(net.G1.oneMinCumul.get("S"), -1.0));
        net.G1.alpha.put("S", Operators.CumulativeFunction(temp));
        
        temp=Operators.ComposeFunctionInterfaceFunctionStructure(net.G1.alpha.get("G0"),Operators.exp,-1.0);
        temp=Operators.MultiplyFunctions(net.G1.oneMinCumul.get("G0"), temp);
        net.G1.thetaFilter=Operators.PowerOfFunction(temp, net.G1.weight.get("G0"));
        temp=Operators.ComposeFunctionInterfaceFunctionStructure(net.G1.alpha.get("S"),Operators.exp,-1.0);
        temp=Operators.MultiplyFunctions(net.G1.oneMinCumul.get("S"), temp);
        temp=Operators.PowerOfFunction(temp, net.G1.weight.get("S"));
        net.G1.thetaFilter=Operators.MultiplyFunctions(net.G1.thetaFilter, temp);
        
        
        
        
//        net.G1.cumul.put("G0",Operators.CumulativeFunction(net.G1.density.get("G0")) );
//        net.G1.oneMinCumul.put("G0", Operators.AffineFunctionTransformation(-1.0, 1.0,net.G1.cumul.get("G0")));
//        net.G1.weight.put("G0",Operators.IntegrateFunction(Operators.MultiplyFunctions(net.G1.density.get("G0"), net.G0.oneMinCumul.get("S")),net.G0.density.get("G0").min,net.G0.density.get("G0").max));
//        FunctionStructure oneMinCumulG1ToS=Operators.AffineFunctionTransformation(-1.0, 1.0,Operators.CumulativeFunction(net.G1.density.get("S")));
//        net.G1.cumul.put("S",Operators.CumulativeFunction(net.G1.density.get("S")) );
//        net.G1.oneMinCumul.put("S", Operators.AffineFunctionTransformation(-1.0, 1.0,net.G1.cumul.get("S")));
//        net.G1.weight.put("S",Operators.IntegrateFunction(Operators.MultiplyFunctions(net.G1.density.get("S"), net.G0.oneMinCumul.get("G0")),net.G0.density.get("S").min,net.G0.density.get("S").max));
//        
//        temp=Operators.ComposeFunctionInterfaceFunctionStructure(net.G1.alpha.get("G0"),Operators.exp,-1.0);
//        temp=Operators.MultiplyFunctions(net.G1.oneMinCumul.get("G0"), temp);
//        net.G1.thetaFilter=Operators.PowerOfFunction(temp, net.G1.weight.get("G0"));
//        temp=Operators.ComposeFunctionInterfaceFunctionStructure(net.G1.alpha.get("S"),Operators.exp,-1.0);
//        temp=Operators.MultiplyFunctions(net.G1.oneMinCumul.get("S"), temp);
//        temp=Operators.PowerOfFunction(temp, net.G1.weight.get("S"));
//        net.G1.thetaFilter=Operators.MultiplyFunctions(net.G1.thetaFilter, temp);

        // S
        net.S.cumul.put("G2",Operators.CumulativeFunction(net.S.density.get("G2")) );
        net.S.oneMinCumul.put("G2", Operators.AffineFunctionTransformation(-1.0, 1.0,net.S.cumul.get("G2")));
        net.S.weight.put("G2",1.0);
        net.S.thetaFilter=net.S.oneMinCumul.get("G2");
        // G2
        net.G2.cumul.put("M",Operators.CumulativeFunction(net.G2.density.get("M")) );
        net.G2.oneMinCumul.put("M", Operators.AffineFunctionTransformation(-1.0, 1.0,net.G2.cumul.get("M")));
        net.G2.weight.put("M",1.0);
        net.G2.thetaFilter=net.G2.oneMinCumul.get("M");
        // M
        net.M.cumul.put("G1",Operators.CumulativeFunction(net.M.density.get("G1")) );
        net.M.oneMinCumul.put("G1", Operators.AffineFunctionTransformation(-1.0, 1.0,net.M.cumul.get("G1")));
        net.M.weight.put("G1",1.0);
        net.M.thetaFilter=net.M.oneMinCumul.get("G1");
    }
}
