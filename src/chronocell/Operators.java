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
    
    public static FunctionStructure copyFunction(FunctionStructure fct){
        FunctionStructure cpy=new FunctionStructure();
        cpy.min=Numbers.CGN(fct.min);
        cpy.max=Numbers.CGN(fct.max);
        cpy.step=Numbers.CGN(fct.step);
        cpy.minIndex=fct.minIndex;
        cpy.maxIndex=fct.maxIndex;
        cpy.values=new double[fct.values.length];
        for (int i=0;i<cpy.values.length;i++){
            cpy.values[i]=fct.values[i];
        }
        return cpy;
    }
 
    public static SolutionStructure createSolutionStructure(int phaseNb){
        SolutionStructure sol= new SolutionStructure();
        sol.phaseName= new String[phaseNb];
        sol.theta= new FunctionStructure[phaseNb];
        sol.transitionProbabilities= new FunctionStructure[phaseNb];
        sol.oneMinusCumulativeFunctions= new FunctionStructure[phaseNb];
        return sol;
    }
    
    /// Lambda part, create a new package ?
    public interface FunctionInterface {
        public double op(double x,double ... p);
    }
    
    public static FunctionInterface constant = new FunctionInterface(){
      public double op(double x,double ... p){
//          System.err.format("p=%f",p[0]);
          return p[0];
      }  
    };
    
    public static FunctionInterface sinPeriodOne = new FunctionInterface(){
      public double op(double x,double ... p){
          return Math.sin(x*Math.PI);
      }  
    };
    
    public static FunctionInterface gompertz = new FunctionInterface(){
      public double op(double x,double ... p){
          //p=[C,B,M]
          //x=pO2
          // double C=1.0, B=0.075, M=26.3, pO2=60.0;
          return p[0]*Math.exp(-Math.exp(-p[1]*(x-p[2])));
          }  
    };
    
    public static FunctionInterface continuousGeometricDistribution = new FunctionInterface(){
      public double op(double x,double ... p){
          //p=[shift,pO2,C,B,M]
          // double C=1.0, B=0.075, M=26.3, pO2=60.0;
          double[] parameterGompertz=new double[]{p[2],p[3],p[4]};
          double prob=gompertz.op(p[1],parameterGompertz);
          
          
          if (x>p[0]){
          return -Math.log(1-prob)*Math.pow(1-prob,x-p[0]);
          }
          else {
              return 0;
          }
      }  
    };

    public static FunctionInterface survivalProbability = new FunctionInterface(){
      public double op(double x,double ... p){
          return 0.5;
          }  
    };
    
  
      
    public static void MapFunctionValues(FunctionStructure fct,double min, double max,FunctionInterface g,double ... p){
        for (int i=(int) Math.round(min/fct.step);i<=(int) Math.round(max/fct.step);i++){
            fct.values[i]=g.op(i*fct.step+fct.min,p);
        }
    }
    
     
    public static double GetFunctionValue(FunctionStructure fct,double x){
        double y=0.0;
        if ((x>=fct.min) && (x<=fct.max)){
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
    
    public static void PrintFunction(String name, FunctionStructure fct,boolean displayValues){
        System.err.format("\n Function :%s \n",name);
        System.err.format("step=%f\n",fct.step);
        System.err.format("min=%f, max=%f\n",fct.min,fct.max);
//        System.err.format("values length=%d\n",fct.values.length);
        System.err.format("minIndex=%d, maxIndex=%d\n",fct.minIndex,fct.maxIndex);
//       for (int i=0;i<fct.values.length;i++){
//            System.err.format("**** f(%f)=%f\n",fct.min+(i-fct.minIndex)*fct.step,fct.values[i]);
//        }
        System.err.format("**** Integral =%f\n",IntegrateFunction(fct, fct.min, fct.max));
        if (displayValues==true){
            for (int i=fct.minIndex;i<=fct.maxIndex;i++){
                System.err.format("**** f(%f)=%f\n",fct.min+(i-fct.minIndex)*fct.step,fct.values[i]);
            }
 
        }
    } 
    
     public static double IntegrateFunction(FunctionStructure fct,double inf, double sup){
        double sum=0.0;
        int start=(int) (Math.round(fct.minIndex+(Math.max(inf, fct.min)-fct.min)/fct.step));
        int end=(int)  (Math.round(fct.minIndex+(Math.min(sup, fct.max)-fct.min)/fct.step));
        
//         System.err.format("start=%d, end=%d \n",start,end);
        for (int i=start;i<=end-1;i++){
            // rectangles
            sum+=fct.values[i]*fct.step;
        }
        return sum;
    } 
     
    public static FunctionStructure CumulativeFunction(FunctionStructure fct){
        // increase fct's support
        FunctionStructure temp=createFunction(0, fct.max, fct.step);
        int indexDiff=(int) (Math.round(fct.min/fct.step));
//        temp.minIndex= fct.minIndex+ indexDiff;
        for (int i=temp.minIndex+ indexDiff;i<=temp.maxIndex;i++){
            temp.values[i]=fct.values[i-indexDiff]*temp.step;
        }
//        PrintFunction(fct);
//        PrintFunction(temp);
        FunctionStructure cum=createFunction(0, temp.max, temp.step);
        for (int i=cum.minIndex+1;i<=cum.maxIndex;i++){
            cum.values[i]=cum.values[i-1]+temp.values[i];
        }
//        PrintFunction(cum);
        return cum;
    } 
     
    
    public static FunctionStructure PowerOfFunction(FunctionStructure fct1,double pow){
        FunctionStructure power=copyFunction(fct1);
        for (int i=power.minIndex;i<=power.maxIndex;i++){
                power.values[i]=Math.pow(fct1.values[i],pow);
        }
        
    return power;
    }  
    
    public static FunctionStructure MultiplyFunctions(FunctionStructure fct1,FunctionStructure fct2){
        FunctionStructure prod=new FunctionStructure();
        if ((fct1.min-fct2.max)*(fct2.min-fct1.max)<0) {
            prod=Operators.createFunction(0,0,1);
        }
        else{
            prod=Operators.createFunction(Math.max(fct1.min, fct2.min), Math.min(fct1.max, fct2.max), Numbers.LeastCommonStep(fct1.step,fct2.step));
            for (int i=prod.minIndex;i<=prod.maxIndex;i++){
                prod.values[i]=GetFunctionValue(fct1, prod.min+i*prod.step)*GetFunctionValue(fct2, prod.min+i*prod.step);
            }
        }
        
    return prod;
    }  
     
    public static FunctionStructure MultiplyFunctionRaw(FunctionStructure fct1,FunctionStructure fct2){
        FunctionStructure prod=new FunctionStructure();
        if ((fct1.min-fct2.max)*(fct2.min-fct1.max)<0) {
            prod=Operators.createFunction(0,0,1);
        }
        else{
            prod=Operators.createFunction(fct1.min, fct1.max,Numbers.LeastCommonStep(fct1.step,fct2.step));
            for (int i=prod.minIndex;i<=prod.maxIndex;i++){
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
//        PrintFunction("affineTranform",transf);
        for (int i=transf.minIndex;i<=transf.maxIndex;i++){
            transf.values[i]=fct.values[i]*a+b;
        }
//        PrintFunction("affineTranformAfter",transf);
    return transf;
    } 
    
    public static FunctionStructure TranslateFunction(double t, FunctionStructure fct){
//        PrintFunction(fct);
        FunctionStructure transl=copyFunction(fct);
        transl.min+=t;
        transl.max+=t;
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
    
    
    public static void ComputeSolutionNextValue(SolutionStructure sol){
        // If solutions' support is filled, increase the size of array sol.values
        for (int i=0;i<sol.theta.length;i++){
            if (sol.theta[i].minIndex==0){
                DoubleArraySizeToLeft(sol.theta[i]);
            }
        } 
        double[] nextVal= new double[sol.theta.length];
        for (int i=0;i<sol.theta.length;i++){
            FunctionStructure tempProb=TranslateFunction(sol.theta[i].min, sol.transitionProbabilities[i]);
            nextVal[i]=IntegrateFunction(MultiplyFunctions(sol.theta[i],tempProb),tempProb.min,tempProb.max);
        }
        for (int i=0;i<sol.theta.length;i++){
            int j=(i+1) % sol.theta.length;
            sol.theta[j].min=sol.theta[j].min-sol.theta[j].step;
            sol.theta[j].minIndex-=1;
//            System.err.format("i = %d et i+1 mod %d = %d \n",i,dst.ageDistribution.length,(i+1) % dst.ageDistribution.length);
            if (j==0){
               sol.theta[j].values[sol.theta[j].minIndex]=2*nextVal[i]; 
            }
            else{
                sol.theta[j].values[sol.theta[j].minIndex]=nextVal[i];
            }
            
        }
    }
    
    public static void ApplyTreatment(int treatNb,SimulationStructure simulation){
        System.err.format("traitement %d\n", treatNb);
        simulation.solution[simulation.currentSolution+1]=createSolutionStructure(simulation.solution[simulation.currentSolution].phaseName.length);
        simulation.solution[simulation.currentSolution+1].phaseName=simulation.solution[simulation.currentSolution].phaseName;
        simulation.solution[simulation.currentSolution+1].transitionProbabilities=simulation.solution[simulation.currentSolution].transitionProbabilities;
        simulation.solution[simulation.currentSolution+1].oneMinusCumulativeFunctions=simulation.solution[simulation.currentSolution].oneMinusCumulativeFunctions;
        simulation.currentSolution+=1;
        for (int i=0;i<simulation.solution[simulation.currentSolution].phaseName.length;i++){
            simulation.solution[simulation.currentSolution].theta[i]=createFunction(simulation.solution[simulation.currentSolution].transitionProbabilities[i].min,simulation.solution[treatNb].transitionProbabilities[i].max, simulation.solution[treatNb].transitionProbabilities[i].step);
            simulation.solution[simulation.currentSolution].theta[i].min=simulation.solution[simulation.currentSolution-1].theta[i].min;
            simulation.solution[simulation.currentSolution].theta[i].max=simulation.solution[treatNb].theta[i].min+simulation.solution[simulation.currentSolution].theta[i].max;                    
            for (int j=0;j<simulation.solution[simulation.currentSolution].theta[i].values.length;j++){
               simulation.solution[simulation.currentSolution].theta[i].values[j]=simulation.solution[simulation.currentSolution-1].theta[i].values[simulation.solution[simulation.currentSolution-1].theta[i].minIndex+j]*survivalProbability.op(simulation.treat.doses[treatNb]);
            }
        }
    }
    
    public static void ComputeSimulationNextValue(SimulationStructure simulation){
//            System.err.format("current.time= %f, treatTime= %f \n",simulation.currentTime, simulation.treat.times[simulation.currentTreatment]);
            if (simulation.currentTime>simulation.treat.times[simulation.currentTreatment]){
                ApplyTreatment(simulation.currentTreatment,simulation);
                simulation.currentTreatment+=1;
            }
        ComputeSolutionNextValue(simulation.solution[simulation.currentTreatment]);
        // unifier les step entre toutes les phases
        simulation.currentTime+=simulation.solution[simulation.currentTreatment].theta[0].step;
    }
}
