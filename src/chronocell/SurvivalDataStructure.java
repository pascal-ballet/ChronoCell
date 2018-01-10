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
public class SurvivalDataStructure {
    //    int phaseNb=5;
    double[][] G0, G1, S, G2, M;
     
    public double[][] getPhase(int i){
        switch(i){
            case  0: return G0;
            case  1: return G1;
            case  2: return S;
            case  3: return G2;
            case  4: return M;
        }
        return null;
    }
    
    public void setPhase(int i,double[][] tab){
        switch(i){
            case  0: G0=tab; return;
            case  1: G1=tab; return;
            case  2: S=tab; return;
            case  3: G2=tab; return;
            case  4: M=tab; return;
        }
    }
}
