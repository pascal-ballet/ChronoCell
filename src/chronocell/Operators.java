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
        int size=(int) Math.round(1+(max-min)/step);
//        System.err.format("\n dans creation max=%f, min=%f,step=%f \n",max,min,step);
//        System.err.format("\n dans creation max-min=%f, \n",max-min);
//        System.err.format("\n dans creation 1+(max-min)/step=%d, \n", (int) (1+(max-min)/step));
//        System.err.format("\n dans creation 1+(max-min)/step=%.50g, \n", 1+(max-min)/step);
//        System.err.format("\n dans creation size =%d \n",size);
        fct.values=new double[size];
        for (int i=0;i<size;i++){
            fct.values[i]=0.0;
        }
        fct.minIndex=0;
        fct.maxIndex=size-1;
        return fct;
    }
 
    public static AgeDistributionsStructure createAgeDistributionStructure(int phaseNb){
        AgeDistributionsStructure dst= new AgeDistributionsStructure();
        dst.ageDistribution= new FunctionStructure[phaseNb];
        dst.transitionProbabilities= new FunctionStructure[phaseNb];
        return dst;
    }
    
       
    public static void FillFunctionValues(FunctionStructure fct){
        for (int i=fct.minIndex; i<=fct.maxIndex;i++){
            fct.values[i]=Math.sin(i*fct.step+fct.min);
        }
    }
    
    /// Lambda part, create a new package ?
    
    public interface FunctionInterface {
        public double op(double x);
    }
    
    public static FunctionInterface constant = new FunctionInterface(){
      public double op(double x){
          return 1;
      }  
    };
    
    public static FunctionInterface sinPeriodOne = new FunctionInterface(){
      public double op(double x){
          return Math.sin(x*Math.PI);
      }  
    };
    
      
    public static void MapFunctionValues(FunctionStructure fct,FunctionInterface g){
        for (int i=fct.minIndex;i<=fct.maxIndex;i++){
            fct.values[i]=g.op(i*fct.step+fct.min);
        }
    }
    
     
    public static double GetFunctionValue(FunctionStructure fct,double x){
        double y=0.0;
        if ((x>=fct.min) && (x<=fct.max)){
//            PrintFunction(fct);
//            System.err.format("x=%f\n",x);
//            System.err.format("index=%d\n",fct.minIndex+ (int) (((x-fct.min)/fct.step)));

//////// switch cast (int) to Math.round
//            y=fct.values[fct.minIndex+ (int) (((x-fct.min)/fct.step))];
            y=fct.values[fct.minIndex+ (int) (Math.round((x-fct.min)/fct.step))];
            }
        return y;
    }
    
         
    public static double GetFunctionMaxValue(FunctionStructure fct){
        double maxVal=fct.values[fct.minIndex];
        for (int i=fct.minIndex+1;i<=fct.maxIndex;i++){
            if (fct.values[i]>maxVal){
                maxVal=fct.values[i];
            }
        }
        return maxVal;
    }
    public static double GetFunctionMinValue(FunctionStructure fct){
        double minVal=fct.values[fct.minIndex];
        for (int i=fct.minIndex+1;i<=fct.maxIndex;i++){
            if (fct.values[i]<minVal){
                minVal=fct.values[i];
            }
        }
        return minVal;
    }
    
    public static void PrintFunction(FunctionStructure fct){
        System.err.println("");
        System.err.format("min=%f, max=%f\n",fct.min,fct.max);
        System.err.format("values length=%d\n",fct.values.length);
        System.err.format("minIndex=%d, maxIndex=%d\n",fct.minIndex,fct.maxIndex);
        System.err.format("step=%f\n",fct.step);
//        for (int i=fct.minIndex;i<=fct.maxIndex;i++){
//            System.err.format("**** f(%f)=%f\n",fct.min+(i-fct.minIndex)*fct.step,fct.values[i]);
//        }
    } 
     public static double IntegrateFunction(FunctionStructure fct,double inf, double sup){
        double sum=0.0;
//        int start=(int) (fct.minIndex+(Math.max(inf, fct.min)-fct.min)/fct.step);
//        int end=(int) (fct.minIndex+(Math.min(sup, fct.max)-fct.min)/fct.step);
        
//////// switch cast (int) to Math.round
        int start=(int) (Math.round(fct.minIndex+(Math.max(inf, fct.min)-fct.min)/fct.step));
        int end=(int)  (Math.round(fct.minIndex+(Math.min(sup, fct.max)-fct.min)/fct.step));
        
//        System.err.println(start);
//        System.err.println(end);
        for (int i=start;i<=end-1;i++){
            // rectangles
            sum+=fct.values[i]*fct.step;
            // trapèzes
//            sum+=(fct.values[i]+fct.values[i+1])/2*fct.step;
        }
        return sum;
    }          
     
    public static FunctionStructure MultiplyFunctions(FunctionStructure fct1,FunctionStructure fct2){
        FunctionStructure prod=new FunctionStructure();
        if ((fct1.min-fct2.max)*(fct2.min-fct1.max)<0) {
            prod=Operators.createFunction(0,0,1);
        }
        else{
            prod=Operators.createFunction(Math.max(fct1.min, fct2.min), Math.min(fct1.max, fct2.max), Numbers.LeastCommonStep(fct1.step,fct2.step));
            for (int i=prod.minIndex;i<=prod.maxIndex;i++){
//            System.err.format("fct1 et 2 appelées en %f \n",prod.min+i*prod.step);
                prod.values[i]=GetFunctionValue(fct1, prod.min+i*prod.step)*GetFunctionValue(fct2, prod.min+i*prod.step);
            }
        }
        
    return prod;
    }  
    
    
    public static FunctionStructure AddFunctions(FunctionStructure fct1,FunctionStructure fct2){
        FunctionStructure sum=Operators.createFunction(Math.min(fct1.min, fct2.min), Math.max(fct1.max, fct2.max), Numbers.LeastCommonStep(fct1.step,fct2.step));
        for (int i=sum.minIndex;i<=sum.maxIndex;i++){
            sum.values[i]=GetFunctionValue(fct1, sum.min+i*sum.step)+GetFunctionValue(fct2, sum.min+i*sum.step);
        }
    return sum;
    } 
    
    public static FunctionStructure AffineFunctionTransformation(double a, double b, FunctionStructure fct){
        FunctionStructure transf=Operators.createFunction(fct.min, fct.max, fct.step);
        for (int i=transf.minIndex;i<=transf.maxIndex;i++){
            transf.values[i]=fct.values[i]*a+b;
        }
    return transf;
    } 
    
    public static FunctionStructure TranslateFunction(double t, FunctionStructure fct){
//        PrintFunction(fct);
        FunctionStructure transl=Operators.createFunction(fct.min+t, fct.max+t, fct.step);
//        System.err.format("translation de %f\n",t);
//        PrintFunction(transl);
        for (int i=transl.minIndex;i<=transl.maxIndex;i++){
            transl.values[i]=fct.values[i];
        }
    return transl;
    } 
    
    public static void DoubleArraySizeToLeft(FunctionStructure fct){
        double[] newVal= new double[2*fct.values.length];
        for (int i=0;i<fct.values.length;i++){
            newVal[i]=0.0;
        }
        for (int i=fct.values.length;i<newVal.length;i++){
            newVal[i]=fct.values[i-fct.values.length];
        }
        fct.minIndex=fct.values.length;
        fct.maxIndex=2*fct.values.length-1;
        fct.values=newVal;
    }
    
    
    public static void ComputeSolutionNextValue(AgeDistributionsStructure dst){
        // If solutions' support is filled, increase the size of array sol.values
        for (int i=0;i<dst.ageDistribution.length;i++){
            if (dst.ageDistribution[i].minIndex==0){
                DoubleArraySizeToLeft(dst.ageDistribution[i]);
            }
        } 
        double[] nextVal= new double[dst.ageDistribution.length];
        for (int i=0;i<dst.ageDistribution.length;i++){
            FunctionStructure tempProb=TranslateFunction(dst.ageDistribution[i].min, dst.transitionProbabilities[i]);
            nextVal[i]=IntegrateFunction(MultiplyFunctions(dst.ageDistribution[i],tempProb),tempProb.min,tempProb.max);
        }
        for (int i=0;i<dst.ageDistribution.length;i++){
            int j=(i+1) % dst.ageDistribution.length;
            dst.ageDistribution[j].min=dst.ageDistribution[j].min-dst.ageDistribution[j].step;
            dst.ageDistribution[j].minIndex-=1;
//            System.err.format("i = %d et i+1 mod %d = %d \n",i,dst.ageDistribution.length,(i+1) % dst.ageDistribution.length);
            dst.ageDistribution[j].values[dst.ageDistribution[j].minIndex]=nextVal[i];
        }
//        if (sol.minIndex==0){
//            double[] newVal= new double[2*sol.values.length];
//            for (int i=0;i<sol.values.length;i++){
//                newVal[i]=0.0;
//            }
//            for (int i=sol.values.length;i<newVal.length;i++){
//                newVal[i]=sol.values[i-sol.values.length];
//            }
//            sol.minIndex=sol.values.length;
//            sol.maxIndex=2*sol.values.length-1;
//            sol.values=newVal;
//        }
//        FunctionStructure tempProb=TranslateFunction(sol.min, prob);
//        PrintFunction(tempProb);
//        PrintFunction(sol);
//        sol.values[sol.minIndex-1]=IntegrateFunction(MultiplyFunctions(sol,tempProb),tempProb.min,tempProb.max);
//        sol.min=sol.min-sol.step;
//        sol.minIndex-=1;
    } 
}
