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
public class Numbers {
    public static double minStep=0.001;
    private static double ClosestGridNumber(double number){
        long gridIndex=(long) Math.round(number/minStep);
        double gridNumber=gridIndex*minStep;
        return gridNumber;
    }
    public static double CGN(double number){
        return ClosestGridNumber(number);
    }
}
