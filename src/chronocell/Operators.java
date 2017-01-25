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


public class Operators {
    public static FunctionStructure createFunction(double min,double max,double step){
        FunctionStructure fct=new FunctionStructure();
        fct.min=min;
        fct.max=max;
        fct.step=step;
        int size=(int) (1+(max-min)/step);
        fct.values=new double[size];
        for (int i=0;i<size;i++){
            fct.values[i]=0.0;
        }
        return fct;
    }
    
    public static void FillFunctionValues(FunctionStructure fct){
        for (int i=0;i<fct.values.length;i++){
            fct.values[i]=Math.sin(i*fct.step+fct.min);
        }
    }
    
    public static double GetFunctionMaxValue(FunctionStructure fct){
        double maxVal=fct.values[0];
        for (int i=1;i<fct.values.length;i++){
            if (fct.values[i]>maxVal){
                maxVal=fct.values[i];
            }
        }
        return maxVal;
    }
    public static double GetFunctionMinValue(FunctionStructure fct){
        double minVal=fct.values[0];
        for (int i=1;i<fct.values.length;i++){
            if (fct.values[i]>minVal){
                minVal=fct.values[i];
            }
        }
        return minVal;
    }
    
    public static void PrintFunction(FunctionStructure fct){
            for (int i=0;i<fct.values.length;i++){
            System.err.print(fct.values[i]);
            System.err.print(" ** ");
        }
        System.err.println("");
    } 
             
}
