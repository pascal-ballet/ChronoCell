/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;

import java.util.ArrayList;

/**
 *
 * @author goby
 */
public class CellPopulation {
    double size=0.0;
    CellDynamics dynamics=new CellDynamics();
    ArrayList<ThetaStructure> theta=new ArrayList();
//    ThetaStructure theta=new ThetaStructure();
}
