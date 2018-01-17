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
    int currentTheta=0;
    double time=0.0;
    double timeStep=0.1;
    // paramètres environementaux
//    double pO2=0.0,alpha=0.044,beta=0.089,m=3.0;
//    int k=3;
}
