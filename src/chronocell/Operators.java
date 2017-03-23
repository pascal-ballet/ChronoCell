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
    
    public static FunctionInterface gaussian = new FunctionInterface(){
      public double op(double x,double ... p){
          //p=[mu,sigma^2]
          return Math.pow(p[1]*Math.PI*2.0,-0.5)*Math.exp(-0.5*Math.pow(x-p[0],2)/p[1]);
          }  
    };
    
        
    public static FunctionInterface exp = new FunctionInterface(){
      public double op(double x,double ... p){
          // p[0] coefficient
          return Math.exp(p[0]*x);
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
          //p[0]=phase
          if (p[0]==2){
              return 0.2;
          }
          else{
              return 0.8;
          }
        }  
    };
    
  
      
    public static void MapFunctionValues(FunctionStructure fct,double min, double max,FunctionInterface g,double ... p){
        for (int i=(int) Math.round(min/fct.step);i<=(int) Math.round(max/fct.step);i++){
            fct.values[i]=g.op(i*fct.step+fct.min,p);
        }
    }
    
    public static FunctionStructure ComposeFunctionInterfaceFunctionStructure(FunctionStructure fct,FunctionInterface g,double ... p){
        FunctionStructure comp = copyFunction(fct);
        for (int i=comp.minIndex;i<=comp.maxIndex;i++){
            comp.values[i]=g.op(fct.values[i],p);
        }
        return comp;
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
                System.err.format("**** f(%f)=%.20f\n",fct.min+(i-fct.minIndex)*fct.step,fct.values[i]);
            }
 
        }
    } 
    public static void plotFunction(FunctionStructure fct){
        GUI win =new GUI();
        win.SetFunction(fct);
        win.setVisible(true);
    }
    
    public static FunctionStructure FunctionSupport(FunctionStructure fct){
        double min=fct.min;
        for (int i=fct.minIndex;i<fct.maxIndex;i++){
            /// arrondi à paramétrer
            if (Math.abs(fct.values[i])<0.0000001){
                min+=fct.step;
            }
            else {
                break;
            }
        }
        double max=fct.max;
        for (int i=fct.maxIndex;i>=fct.minIndex;i--){
            if (Math.abs(fct.values[i])<0.0000001){
                max-=fct.step;
            }
            else {
                break;
            }
        }
        FunctionStructure ind=createFunction(min, max, fct.step);
        for (int i=ind.minIndex;i<=ind.maxIndex;i++){
            ind.values[i]=1.0;
        }
        return ind;
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
        FunctionStructure cum=createFunction(0, fct.max, fct.step);
        for (int i =cum.minIndex+1;i<=cum.maxIndex;i++){
            cum.values[i]=cum.values[i-1]+GetFunctionValue(fct, (i-1)*fct.step)*cum.step;
        }
        /// normalization (crado ? rependre la boucle précédente pour être sûr de ne jamais dépasser 1 par le calcul ?)
        cum=AffineFunctionTransformation(1.0/GetFunctionMaxValue(cum),0, cum);
        return cum;
    } 
     
    
    public static FunctionStructure PowerOfFunction(FunctionStructure fct1,double pow){
        FunctionStructure power=copyFunction(fct1);
        for (int i=power.minIndex;i<=power.maxIndex;i++){
                power.values[i]=Math.pow(fct1.values[i],pow);
               
//                System.out.println("fct"+fct1.values[i]+"pow"+power.values[i]);
                if (fct1.values[i]<0.00000001){
                    System.out.println(" fct="+fct1.values[i]+", pow="+pow+", fpow="+power.values[i]);
                }
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
    
    

}
