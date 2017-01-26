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
        fct.min=Numbers.CGN(min);
        fct.max=Numbers.CGN(max);
        fct.step=Numbers.CGN(step);
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
    
    /// Lambda part
    
    public interface FunctionInterface {
        public double op(double x);
    }
    
    public static FunctionInterface transitionProbability = new FunctionInterface(){
      public double op(double x){
          return 1;
      }  
    };
    
    public static FunctionInterface initialDensityG1 = new FunctionInterface(){
      public double op(double x){
          return Math.sin(x*Math.PI);
      }  
    };
    
    public static void MapFunctionValues(FunctionStructure fct,FunctionInterface g){
        for (int i=0;i<fct.values.length;i++){
            fct.values[i]=g.op(i*fct.step+fct.min);
        }
    }
    
     
    public static double GetFunctionValue(FunctionStructure fct,double x){
        double y=0.0;
        if ((x>=fct.min) && (x<=fct.max)){
            y=fct.values[(int) ((x-fct.min)/fct.step)];
            }
        return y;
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
            if (fct.values[i]<minVal){
                minVal=fct.values[i];
            }
        }
        return minVal;
    }
    
    public static void PrintFunction(FunctionStructure fct){
        System.err.format("min=%f, max=%f\n",fct.min,fct.max);
        for (int i=0;i<fct.values.length;i++){
            System.err.format("**** f(%f)=%f\n",fct.min+i*fct.step,fct.values[i]);
        }
        System.err.println("");
    } 
     public static double IntegrateFunction(FunctionStructure fct,double inf, double sup){
        double sum=0.0;
        int start=(int) ((Math.max(inf, fct.min)-fct.min)/fct.step);
        int end=(int) ((Math.min(sup, fct.max)-fct.min)/fct.step);
//        System.err.println(start);
//        System.err.println(end);
        for (int i=start;i<=end-1;i++){
            // rectangles
//            sum+=fct.values[i]*fct.step;
            // trapèzes
            sum+=(fct.values[i]+fct.values[i+1])/2*fct.step;
        }
        return sum;
    }          
     
    public static FunctionStructure MultiplyFunctions(FunctionStructure fct1,FunctionStructure fct2){
        FunctionStructure prod=Operators.createFunction(Math.max(fct1.min, fct2.min), Math.min(fct1.max, fct2.max), Numbers.LeastCommonStep(fct1.step,fct2.step));
        for (int i=0;i<prod.values.length;i++){
            prod.values[i]=GetFunctionValue(fct1, prod.min+i*prod.step)*GetFunctionValue(fct2, prod.min+i*prod.step);
        }
    return prod;
    }  
    
    public static FunctionStructure AddFunctions(FunctionStructure fct1,FunctionStructure fct2){
        FunctionStructure sum=Operators.createFunction(Math.min(fct1.min, fct2.min), Math.max(fct1.max, fct2.max), Numbers.LeastCommonStep(fct1.step,fct2.step));
        for (int i=0;i<sum.values.length;i++){
            sum.values[i]=GetFunctionValue(fct1, sum.min+i*sum.step)+GetFunctionValue(fct2, sum.min+i*sum.step);
        }
    return sum;
    } 
    
    public static FunctionStructure AffineFunctionTransformation(double a, double b, FunctionStructure fct){
        FunctionStructure trans=Operators.createFunction(fct.min, fct.max, fct.step);
        for (int i=0;i<trans.values.length;i++){
            trans.values[i]=fct.values[i]*a+b;
        }
    return trans;
    } 
    
    public static FunctionStructure TranslateFunction(double t, FunctionStructure fct){
        FunctionStructure transl=Operators.createFunction(fct.min+t, fct.max+t, fct.step);
        for (int i=0;i<transl.values.length;i++){
            transl.values[i]=fct.values[i];
        }
    return transl;
    } 
    
}
