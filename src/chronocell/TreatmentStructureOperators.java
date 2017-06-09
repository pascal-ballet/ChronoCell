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
public class TreatmentStructureOperators {
    public static void displayTreatment(TreatmentStructure treat){
        System.out.println(" ----- Traitement----- :");
        for (int i=0;i<treat.times.length-1;i++){
            System.out.println("Temps "+(int) (i+1)+" = "+treat.times[i]+", Dose = "+treat.doses[i]);
        }
    }
    
}
