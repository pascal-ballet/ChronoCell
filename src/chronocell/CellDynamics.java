/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;

import java.util.Hashtable;

/**
 *
 * @author goby
 */
public class CellDynamics {
    Phase G0= PhaseOperators.createPhase("G0");
    Phase G1= PhaseOperators.createPhase("G1");
    Phase S= PhaseOperators.createPhase("S");
    Phase G2= PhaseOperators.createPhase("G2");
    Phase M= PhaseOperators.createPhase("M");
//    Phase phases[]={G0,G1,S,G2,M};
    
    public Phase getPhase(int i){
        switch(i){
            case  0: return G0;
            case  1: return G1;
            case  2: return S;
            case  3: return G2;
            case  4: return M;
        }
        return null;
    }
}
