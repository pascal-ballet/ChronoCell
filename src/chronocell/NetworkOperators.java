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
public class NetworkOperators {
    
//    public static Network createNetwork(){
//        Network net = new Network();
//        net.G0 = new Hashtable<String,FunctionStructure>();
//        return net;
//    }
    
    public static void PhaseFilling(Network net){
        FunctionStructure temp=null;
        // G0
        net.G0.Cumul.put("Death",Operators.CumulativeFunction(net.G0.density.get("Death")) );
        net.G0.oneMinCumul.put("Death", Operators.AffineFunctionTransformation(-1.0, 1.0,net.G0.Cumul.get("Death")));
        net.G0.weight.put("Death",Operators.IntegrateFunction(Operators.MultiplyFunctions(net.G0.density.get("Death"), net.G0.oneMinCumul.get("G1")),net.G0.density.get("Death").min,net.G0.density.get("Death").max));
        temp=Operators.MultiplyFunctions(net.G0.density.get("Death"), net.G0.Cumul.get("G1"));
        temp=Operators.MultiplyFunctions(temp, Operators.PowerOfFunction(net.G0.oneMinCumul.get("Death"), -1.0));
        temp=Operators.MultiplyFunctions(temp, Operators.PowerOfFunction(net.G0.oneMinCumul.get("G1"), -1.0));
        net.G0.alpha.put("Death", Operators.CumulativeFunction(temp));
                
        FunctionStructure oneMinCumulG0ToG1=Operators.AffineFunctionTransformation(-1.0, 1.0,Operators.CumulativeFunction(net.G0.density.get("G1")));
        net.G0.Cumul.put("G1",Operators.CumulativeFunction(net.G0.density.get("G1")) );
        net.G0.oneMinCumul.put("G1", Operators.AffineFunctionTransformation(-1.0, 1.0,net.G0.Cumul.get("G1")));
        temp=Operators.MultiplyFunctions(net.G0.density.get("G1"), net.G0.Cumul.get("Death"));
        temp=Operators.MultiplyFunctions(temp, Operators.PowerOfFunction(net.G0.oneMinCumul.get("Death"), -1.0));
        temp=Operators.MultiplyFunctions(temp, Operators.PowerOfFunction(net.G0.oneMinCumul.get("G1"), -1.0));
        net.G0.alpha.put("Death", Operators.CumulativeFunction(temp));
        // G1
        net.G1.Cumul.put("G0",Operators.CumulativeFunction(net.G1.density.get("G0")) );
        net.G1.oneMinCumul.put("G0", Operators.AffineFunctionTransformation(-1.0, 1.0,net.G1.Cumul.get("G0")));
        net.G1.weight.put("G0",Operators.IntegrateFunction(Operators.MultiplyFunctions(net.G1.density.get("G0"), net.G0.oneMinCumul.get("S")),net.G0.density.get("G0").min,net.G0.density.get("G0").max));
        FunctionStructure oneMinCumulG1ToS=Operators.AffineFunctionTransformation(-1.0, 1.0,Operators.CumulativeFunction(net.G1.density.get("S")));
        net.G1.Cumul.put("S",Operators.CumulativeFunction(net.G1.density.get("S")) );
        net.G1.oneMinCumul.put("S", Operators.AffineFunctionTransformation(-1.0, 1.0,net.G1.Cumul.get("S")));
        net.G1.weight.put("S",Operators.IntegrateFunction(Operators.MultiplyFunctions(net.G1.density.get("S"), net.G0.oneMinCumul.get("G0")),net.G0.density.get("S").min,net.G0.density.get("S").max));
        // S
        net.S.Cumul.put("G2",Operators.CumulativeFunction(net.G1.density.get("S")) );
        net.S.oneMinCumul.put("G2", Operators.AffineFunctionTransformation(-1.0, 1.0,net.G1.Cumul.get("S")));
        net.S.weight.put("G2",1.0);
        // G2
        net.G2.Cumul.put("M",Operators.CumulativeFunction(net.G1.density.get("G2")) );
        net.G2.oneMinCumul.put("M", Operators.AffineFunctionTransformation(-1.0, 1.0,net.G1.Cumul.get("G2")));
        net.G2.weight.put("M",1.0);
        // M
        net.M.Cumul.put("G1",Operators.CumulativeFunction(net.G1.density.get("M")) );
        net.M.oneMinCumul.put("G1", Operators.AffineFunctionTransformation(-1.0, 1.0,net.G1.Cumul.get("M")));
        net.M.weight.put("G1",1.0);
    }
}
