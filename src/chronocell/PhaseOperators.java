/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;

import java.awt.RenderingHints;
import java.util.Hashtable;
import java.util.Enumeration;
/**
 *
 * @author goby
 */
public class PhaseOperators {
    public static Phase createPhase(String name){
        Phase phase=new Phase();
        phase.name=name;
        return phase;
    }
    public static Phase copyPhase(Phase phase){
        Phase copy=new Phase();
        copy.name=phase.name;
        copy.density=copyHashtable(phase.density);
        copy.cumul=copyHashtable(phase.cumul);
        copy.oneMinCumul=copyHashtable(phase.oneMinCumul);
        copy.alpha=copyHashtable(phase.alpha);
        
        copy.solutionFilter=Operators.copyFunction(phase.solutionFilter);
        copy.thetaConvolution=Operators.copyFunction(phase.thetaConvolution);
        return phase;
    }
    
    public static Hashtable<String,FunctionStructure> copyHashtable(Hashtable<String,FunctionStructure> table){
        Hashtable<String,FunctionStructure> copy=new Hashtable<String,FunctionStructure>();
        Enumeration<String> e=table.keys();
        String key=null;
        while(e.hasMoreElements()){
            key=e.nextElement();
            copy.put(key, table.get(key));
        }
        return copy;
    }
}

