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
    
    public static double LeastCommonStep(double step1,double step2){
        double lStep=Math.min(step1,step2);
        double gStep=Math.max(step1, step2);
        double lcs=lStep;
        for (int i=1;true;i++){
            if (Math.abs(i*lStep % gStep) <= 0.0000000001){
                lcs=gStep/i;
                break;
            }
        }
        return CGN(lcs);
    }
    public static boolean IsZero(double x){
        if (Math.abs(x)<0.00000001){
            return true;
        }
        else{
            return false;
        }
    }
}
