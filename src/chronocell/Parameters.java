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
public class Parameters {    
    FunctionStructure transitionDensity= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(2*Math.PI),Numbers.CGN(0.001));
}
