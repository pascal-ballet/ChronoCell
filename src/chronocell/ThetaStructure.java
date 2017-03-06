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
    CellDynamics dyn=null;
    FunctionStructure G0,G1,S,G2,M = new FunctionStructure();
    FunctionStructure[] tab={G0,G1,S,G2,M};
}
