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
          //p=[C,B,m]
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
          //p=[shift,pO2,C,B,m]
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
      public double op(double dose,double ... p){
          //p[0]=pO2,p[1]=alpha,p[2]=beta,p[3]=m,p[4]=k,p[5]=phase
          double z=(1+p[2]/p[3]*dose*OMF.op(p[0],p[3],p[4]));
          double proba=Math.exp(-p[1]*dose*OMF.op(p[0],p[3],p[4])*z);
            if (p[5]==3){
                return 2*proba;
            }
          else{
              return proba;
          }
        }  
    };
    
    public static FunctionInterface OMF = new FunctionInterface(){
      public double op(double x,double ... p){
          //p[0]=m,p[1]=k
          return (p[0]*x+p[1])/(p[0]*(x+p[1]));
      }
    };  
      
    public static void MapFunctionValues(FunctionStructure fct,double min, double max,FunctionInterface g,double ... p){
        for (int i=(int) Math.round(min/fct.step);i<=(int) Math.round(max/fct.step);i++){
            
            // vérifier i-fct.minIndex
            fct.values[i]=g.op((i-fct.minIndex)*fct.step+fct.min,p);
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
            y=fct.values[fct.minIndex+ (int) Numbers.CGN(Math.round((x-fct.min)/fct.step))];
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
        System.out.println("");
        System.out.println("Function "+name);
        System.out.println(fct);
        System.out.println("step="+fct.step);
        System.out.println("min="+fct.min+", max="+fct.max);
        System.out.println("minIndex="+fct.minIndex+", maxIndex="+fct.maxIndex);
//       for (int i=0;i<fct.values.length;i++){
//            System.err.format("**** f(%f)=%f\n",fct.min+(i-fct.minIndex)*fct.step,fct.values[i]);
//        }
        System.out.println("minVal="+GetFunctionMinValue(fct)+", maxVal="+GetFunctionMaxValue(fct));
        System.out.println("**** Integral ="+IntegrateFunction(fct, fct.min, fct.max));
        if (displayValues==true){
            double x;
            for (int i=fct.minIndex;i<=fct.maxIndex;i++){
                x=Numbers.CGN(fct.min+(i-fct.minIndex)*fct.step);
                System.out.println("**** f("+x+")="+fct.values[i]);
            }
 
        }
    } 
    public static void plotFunction(FunctionStructure fct){
        GUI win =new GUI(fct);
        //win.SetFunction(fct);
        win.setVisible(true);
    }
    
    public static FunctionStructure FunctionSupport(FunctionStructure fct){
        double min=fct.min;
        for (int i=fct.minIndex;i<fct.maxIndex;i++){
            /// arrondi à paramétrer
            if (Numbers.IsZero(fct.values[i])){
                min+=fct.step;
            }
            else {
                break;
            }
        }
        double max=fct.max;
        for (int i=fct.maxIndex;i>=fct.minIndex;i--){
            if (Numbers.IsZero(fct.values[i])){
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
    
    public static FunctionStructure CropFunction(FunctionStructure fct){
        FunctionStructure ind=FunctionSupport(fct);
        fct=MultiplyFunctions(fct, ind);
        return fct;
    }
    
    public static FunctionStructure AlphaFunction(FunctionStructure f1,FunctionStructure F1,FunctionStructure F2){
        FunctionStructure alpha,MF1,MF2,fF,temp;
        temp=Operators.MultiplyFunctions(f1,F2);
        // ne pas calculer si au départ le produit fF est nul. Crado, à reprendre.
        if (Numbers.IsZero(Operators.GetFunctionMaxValue(temp))){
            alpha=temp;
        }
        else {
        fF=MultiplyFunctions(Operators.FunctionSupport(f1),Operators.FunctionSupport(F2));
        MF1=CropFunction(Operators.AffineFunctionTransformation(-1.0, 1.0, F1));
        MF1=PowerOfFunction(MF1,-1.0);
        MF2=CropFunction(Operators.AffineFunctionTransformation(-1.0, 1.0, F2));
        MF2=Operators.PowerOfFunction(MF2,-1.0);
        temp=Operators.MultiplyFunctions(fF, MF1);
        temp=Operators.MultiplyFunctions(temp, MF2);
        alpha=Operators.CumulativeFunction(temp);
        }
        return alpha;
    } 
    
      
    
     public static double IntegrateFunction(FunctionStructure fct,double inf, double sup){
        double sum=0.0;
        int start=(int) Numbers.CGN(Math.round(fct.minIndex+(Math.max(inf, fct.min)-fct.min)/fct.step));
        int end=(int)  Numbers.CGN(Math.round(fct.minIndex+(Math.min(sup, fct.max)-fct.min)/fct.step));
        
//         System.err.format("start=%d, end=%d \n",start,end);
        for (int i=start;i<end;i++){
            // rectangles
            sum+=fct.values[i]*fct.step;
        }
        return sum;
    } 
     
    public static FunctionStructure Primitive(FunctionStructure fct){
        // increase fct's support
        FunctionStructure prim=createFunction(0, fct.max, fct.step);
        for (int i =prim.minIndex+1;i<=prim.maxIndex;i++){
            prim.values[i]=prim.values[i-1]+GetFunctionValue(fct, (i-1)*fct.step)*prim.step;
        }
        /// normalization (crado ? rependre la boucle précédente pour être sûr de ne jamais dépasser 1 par le calcul ?)
//        cum=AffineFunctionTransformation(1.0/GetFunctionMaxValue(cum),0, cum);
        return prim;
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
//                if (fct1.values[i]<0.00000001){
//                    System.out.println(" fct="+fct1.values[i]+", pow="+pow+", fpow="+power.values[i]);
//                }
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
    
        public static FunctionStructure MultiplyFunctionByCumulative(FunctionStructure fct,FunctionStructure cumul){
        FunctionStructure prod=copyFunction(fct);
        double min=Math.max(fct.min, cumul.min);
        for (int i=prod.minIndex;i<=prod.maxIndex;i++){
            if (prod.min+i*prod.step <= cumul.max){
                prod.values[i]=fct.values[i]*GetFunctionValue(cumul,prod.min+i*prod.step);
                    }
        }
        return prod;
    }
        
            public static FunctionStructure MultiplyFunctionByOneMinusCumulative(FunctionStructure fct,FunctionStructure cumul){
        FunctionStructure prod=copyFunction(fct);
        double max=Math.min(fct.max, cumul.max);
        for (int i=prod.minIndex;i<=prod.maxIndex;i++){
            if (prod.min+i*prod.step >= cumul.min){
                prod.values[i]=fct.values[i]*GetFunctionValue(cumul,prod.min+i*prod.step);
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
        double x;
            for (int i=sum.minIndex;i<=sum.maxIndex;i++){
                x=sum.min+(i-sum.minIndex)*sum.step;
                sum.values[i]=GetFunctionValue(fct1,x)+GetFunctionValue(fct2,x);

            }
        return sum;
    } 
    
    public static FunctionStructure AffineFunctionTransformation(double a, double b, FunctionStructure fct){
        FunctionStructure transf=Operators.copyFunction(fct);
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
