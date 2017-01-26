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

// special class for the solution which has unbounded support frow below
public class SolutionStructure {
    double min=0.0,max=0.0,step=1.0;
    long index=0;
    int blocSize=0;
    double[] values=null;   
}
