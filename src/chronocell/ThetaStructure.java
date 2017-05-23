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
public class ThetaStructure {
//    int phaseNb=5;
    double startingTime=0.0;
    FunctionStructure G0 = new FunctionStructure(), G1 = new FunctionStructure(),S = new FunctionStructure(),G2 = new FunctionStructure(),M = new FunctionStructure();
     
    public FunctionStructure getPhase(int i){
        switch(i){
            case  0: return G0;
            case  1: return G1;
            case  2: return S;
            case  3: return G2;
            case  4: return M;
        }
        return null;
    }
    
    public void setPhase(int i,FunctionStructure fct){
        switch(i){
            case  0: G0=fct; return;
            case  1: G1=fct; return;
            case  2: S=fct; return;
            case  3: G2=fct; return;
            case  4: M=fct; return;
        }
    }
}
