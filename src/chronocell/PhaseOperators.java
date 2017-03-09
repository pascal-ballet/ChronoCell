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
public class PhaseOperators {
    public static Phase createPhase(String name){
        Phase phase=new Phase();
        phase.name=name;
        return phase;
    }
}
