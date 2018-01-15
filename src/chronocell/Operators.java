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
    
    // creation d'une fonction nulle sur un support défini
    public static FunctionStructure createFunction(double min,double max,double step){
        FunctionStructure fct=new FunctionStructure();
        fct.min=Numbers.CGN(min);
        fct.max=Numbers.CGN(max);
        fct.step=Numbers.CGN(step);
        // On détermine la taille du tableau des valeurs (le cast en int vient du fait que Math.round renvoie un long
        int size=(int) Math.round(1+(max-min)/step);
        fct.values=new double[size];
        // On initialise toutes les valeurs à 0
        for (int i=0;i<size;i++){
            fct.values[i]=0.0;
        }
        // On initialise les index min et max (ici, c'est un choix, mais il est logique de déclarer que toutes les valeurs sont nulles 
        fct.minIndex=0;
        fct.maxIndex=size-1;
        return fct;
    }
    
/// Creéation d'une copie exacte d'une fonction
    
    public static FunctionStructure createFunctionCopy(FunctionStructure fct){
        FunctionStructure cpy=new FunctionStructure();
        cpy.name=fct.name;
        cpy.min=fct.min;
        cpy.max=fct.max;
        cpy.left=fct.left;
        cpy.right=fct.right;
        cpy.step=fct.step;
        cpy.minIndex=fct.minIndex;
        cpy.maxIndex=fct.maxIndex;
        cpy.values=new double[fct.values.length];
        for (int i=0;i<cpy.values.length;i++){
            cpy.values[i]=fct.values[i];
        }
        return cpy;
    }
    
    // copie d'une fonction sur une autre (écrase)
    
    public static void copyFunction(FunctionStructure fct,FunctionStructure fct2){
        fct.name=fct2.name;
        fct.min=fct2.min;
        fct.max=fct2.max;
        fct.left=fct2.left;
        fct.right=fct2.right;
        fct.step=fct2.step;
        fct.minIndex=fct2.minIndex;
        fct.maxIndex=fct2.maxIndex;
        fct.values=new double[fct2.values.length];
        for (int i=0;i<fct.values.length;i++){
            fct.values[i]=fct2.values[i];
        }
    }
    
           
     

    public static FunctionStructure ComposeFunctionInterfaceWithFunction(FunctionInterface g,FunctionStructure fct,double ... p){
        FunctionStructure comp = createFunction(fct.min, fct.max, fct.step);
        comp.left=g.op(fct.left,p);
        comp.right=g.op(fct.right,p);
        double x=fct.min;
        for (;;){
            comp.SetFunctionValue(x, g.op(fct.GetFunctionValue(x), p));
            x=fct.closestGridPoint(x+fct.step);
            if (x>fct.max) break;
        }
        return comp;
    }
    

    
    
    
    public static void PrintFunction(FunctionStructure fct,boolean displayValues){
        System.out.println("*** Print Function***");
        System.out.println("Name= "+fct.name+", Adress= "+fct);
        System.out.println("support=["+fct.min+","+fct.max+"]"+", step="+fct.step);
//        System.out.println("minIndex="+fct.minIndex+", maxIndex="+fct.maxIndex);
//       for (int i=0;i<fct.values.length;i++){
//            System.err.format("**** f(%f)=%f\n",fct.min+(i-fct.minIndex)*fct.step,fct.values[i]);
//        }
        System.out.println("minVal="+fct.getMinValue()+", maxVal="+fct.getMaxValue());
        System.out.println("**** Integral ="+IntegrateFunction(fct, fct.min, fct.max));
        if (displayValues==true){
            System.out.println("left= "+fct.left+", right= "+fct.right);
            double x;
            for (int i=fct.minIndex;i<=fct.maxIndex;i++){
                x=Numbers.CGN(fct.min+(i-fct.minIndex)*fct.step);
                System.out.println("**** f("+x+")="+fct.values[i]+", Index= "+i);
            }
 
        }
    } 
    public static void plotFunction(FunctionStructure fct){
        GUI win =new GUI(fct);//,fct.name);
//        win.SetFunction(fct);
        win.setVisible(true);
    }
    

    
      
    
     public static double IntegrateFunction(FunctionStructure fct,double inf, double sup){
        double sum=0.0;
        //        int start=(int) Numbers.CGN(Math.round(fct.minIndex+(Math.max(inf, fct.min)-fct.min)/fct.step));
        //        int end=(int)  Numbers.CGN(Math.round(fct.minIndex+(Math.min(sup, fct.max)-fct.min)/fct.step));

        //         System.err.format("start=%d, end=%d \n",start,end);
        //System.out.println("inxinf="+fct.indexOfPoint(inf)+", inxsup="+fct.indexOfPoint(sup));
        for (int i=fct.indexOfPoint(inf);i<fct.indexOfPoint(sup);i++){
            // trapèzes
            sum+=Numbers.CGN((fct.values[i]+fct.values[i+1])/2*fct.step);
        }
        return sum;
    } 
     
     
    public static FunctionStructure createDistributionFromFunction(FunctionStructure fct){
        fct.left=0.0;
        fct.right=0.0;
        double norm=Numbers.CGN(1/IntegrateFunction(fct, fct.min, fct.max));
        return createAffineFunctionTransformation(norm, 0, fct);
    }  
//    public static FunctionStructure Primitive(FunctionStructure fct){
//        // increase fct's support
//        FunctionStructure prim=createFunction(0, fct.max, fct.step);
//        for (int i =prim.minIndex+1;i<=prim.maxIndex;i++){
//            prim.values[i]=prim.values[i-1]+fct.GetFunctionValue((i-1)*fct.step)*prim.step;
//        }
//        /// normalization (crado ? rependre la boucle précédente pour être sûr de ne jamais dépasser 1 par le calcul ?)
////        cum=AffineFunctionTransformation(1.0/GetFunctionMaxValue(cum),0, cum);
//        return prim;
//    } 
     
    public static FunctionStructure CumulativeFunction(FunctionStructure fct){
        if ((!Numbers.IsZero(fct.left))||(!Numbers.IsZero(fct.right))){
            System.out.println("Erreur, calcul de fonction de répartition d'une fonction qui n'est pas une distribution (support non borné)");
        } 
        if ((!Numbers.IsZero(Numbers.CGN(Operators.IntegrateFunction(fct, fct.min, fct.max)-1.0)))){
            System.out.println("Attention, calcul de fonction de répartition d'une fonction qui n'est pas une distribution : intégrale ="+Numbers.CGN(Operators.IntegrateFunction(fct, fct.min, fct.max)));
        }
        FunctionStructure cum=createFunction(fct.min, fct.max, fct.step);
        cum.left=0.0;
        cum.right=1.0;
        
        for (int i =cum.minIndex+1;i<=cum.maxIndex;i++){
            cum.values[i]=cum.values[i-1]+(fct.GetFunctionValue( (i-1)*fct.step)+fct.GetFunctionValue((i)*fct.step))/2*cum.step;
        }
        /// normalization (crado ? rependre la boucle précédente pour être sûr de ne jamais dépasser 1 par le calcul ?)
//        cum=AffineFunctionTransformation(1.0/cum.getMaxValue(),0, cum);
        return cum;
    } 
     
    
    public static FunctionStructure PowerOfFunction(FunctionStructure fct1,double pow){
        FunctionStructure power=createFunctionCopy(fct1);
        fct1.name=null;
        fct1.left=Math.pow(fct1.left,pow);
        fct1.right=Math.pow(fct1.right,pow);
        for (int i=power.minIndex;i<=power.maxIndex;i++){
                power.values[i]=Math.pow(fct1.values[i],pow);
               
//                System.out.println("fct"+fct1.values[i]+"pow"+power.values[i]);
//                if (fct1.values[i]<0.00000001){
//                    System.out.println(" fct="+fct1.values[i]+", pow="+pow+", fpow="+power.values[i]);
//                }
        }
        
    return power;
    }  
//******************** Reprendre ici, modif cruciale avec le left et right
    public static FunctionStructure createProductFunction(FunctionStructure fct1,FunctionStructure fct2){
        FunctionStructure prod=new FunctionStructure();
//        if ((fct1.min-fct2.max)*(fct2.min-fct1.max)<0) {
        if (((fct1.max<fct2.min)||(fct2.max<fct1.min))) {
            prod=Operators.createFunction(0,0,1);
        }
        else{
            prod=Operators.createFunction(Math.max(fct1.min, fct2.min), Math.min(fct1.max, fct2.max), Numbers.LeastCommonStep(fct1.step,fct2.step));
//            System.out.println("step1 ="+fct1.step+", step2 ="+fct2.step+", lqs ="+Numbers.LeastCommonStep(fct1.step,fct2.step));
            for (int i=prod.minIndex;i<=prod.maxIndex;i++){
                prod.values[i]=fct1.GetFunctionValue( prod.min+i*prod.step)*fct2.GetFunctionValue( prod.min+i*prod.step);
            }
        }
        
    return prod;
    } 
    
        public static FunctionStructure MultiplyFunctionByCumulative(FunctionStructure fct,FunctionStructure cumul){
        FunctionStructure prod=createFunctionCopy(fct);
        double min=Math.max(fct.min, cumul.min);
        for (int i=prod.minIndex;i<=prod.maxIndex;i++){
            if (prod.min+i*prod.step <= cumul.max){
                prod.values[i]=fct.values[i]*cumul.GetFunctionValue(prod.min+i*prod.step);
                    }
        }
        return prod;
    }
        
            public static FunctionStructure MultiplyFunctionByOneMinusCumulative(FunctionStructure fct,FunctionStructure cumul){
        FunctionStructure prod=createFunctionCopy(fct);
        double max=Math.min(fct.max, cumul.max);
        for (int i=prod.minIndex;i<=prod.maxIndex;i++){
            if (prod.min+i*prod.step >= cumul.min){
                prod.values[i]=fct.values[i]*cumul.GetFunctionValue(prod.min+i*prod.step);
                    }
        }
        return prod;
    }
     
    public static FunctionStructure MultiplyFunctionRaw(FunctionStructure fct1,FunctionStructure fct2){
        FunctionStructure prod=new FunctionStructure();
//        if ((fct1.min-fct2.max)*(fct2.min-fct1.max)<0) {
        if (((fct1.max<fct2.min)||(fct2.max<fct1.min))) {
            prod=Operators.createFunction(0,0,1);
        }
        else{
            prod=Operators.createFunction(fct1.min, fct1.max,Numbers.LeastCommonStep(fct1.step,fct2.step));
            for (int i=prod.minIndex;i<=prod.maxIndex;i++){
                prod.values[i]=fct1.GetFunctionValue( prod.min+i*prod.step)*fct2.GetFunctionValue( prod.min+i*prod.step);
            }
        }
    return prod;
    }  
    

    
    public static FunctionStructure AddFunctions(FunctionStructure fct1,FunctionStructure fct2){
        FunctionStructure sum=Operators.createFunction(Math.min(fct1.min, fct2.min), Math.max(fct1.max, fct2.max), Numbers.LeastCommonStep(fct1.step,fct2.step));
        double x;
            for (int i=sum.minIndex;i<=sum.maxIndex;i++){
                x=sum.min+(i-sum.minIndex)*sum.step;
                sum.values[i]=fct1.GetFunctionValue(x)+fct2.GetFunctionValue(x);

            }
        return sum;
    } 
    
    public static FunctionStructure createAffineFunctionTransformation(double a, double b, FunctionStructure fct){
        FunctionStructure transf=Operators.createFunctionCopy(fct);
//        PrintFunction("affineTranform",transf);
        for (int i=transf.minIndex;i<=transf.maxIndex;i++){
            transf.values[i]=Numbers.CGN(fct.values[i]*a+b);
        }
//        PrintFunction("affineTranformAfter",transf);
    return transf;
    } 
    
    public static FunctionStructure TranslateFunction(double t, FunctionStructure fct){
//        PrintFunction(fct);
        FunctionStructure transl=createFunctionCopy(fct);
        transl.min+=t;
        transl.max+=t;
    return transl;
    } 
    
    public static double LaplaceTransform(double lambda, FunctionStructure fct){
        FunctionStructure expo=Operators.createFunction(fct.min,fct.max,fct.step);
        expo.SetFunctionValuesFromInterface(expo.min,expo.max,Operators.exp, -lambda);
//        Operators.plotFunction(MultiplyFunctions(fct, expo));
        double l=IntegrateFunction(createProductFunction(fct, expo),fct.min,fct.max);
          return l;
    } 
    
    public static double InverseLaplaceTransform(double x, FunctionStructure fct){
        double step=0.001;
        double epsilon=0.005;
        double lambda=0.0;
        double laplace=LaplaceTransform(lambda, fct);
//        puisque l'on ne manipule que des fonctions positives, la transformée de laplace est décroissante
        if (laplace<x){
            System.out.println("Inversion laplace impossible");
            return -1.0;
        }
        while (Math.abs(x-laplace)>epsilon){
            System.out.println("laplace="+laplace+", lambda="+lambda+", x="+x+", step="+step);
            while (laplace>x){
                lambda+=step;
                laplace=LaplaceTransform(lambda, fct);
            }
            lambda-=step;
            step/=2;
        }
          return lambda;
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
        fct=createProductFunction(fct, ind);
        return fct;
    }
    
    public static FunctionStructure AlphaFunction(FunctionStructure f1,FunctionStructure F1,FunctionStructure F2){
        FunctionStructure alpha,MF1,MF2,fF,temp;
        temp=Operators.createProductFunction(f1,F2);
        // ne pas calculer si au départ le produit fF est nul. Crado, à reprendre.
        if (Numbers.IsZero(temp.getMaxValue())){
            alpha=temp;
        }
        else {
        fF=createProductFunction(Operators.FunctionSupport(f1),Operators.FunctionSupport(F2));
        MF1=CropFunction(Operators.createAffineFunctionTransformation(-1.0, 1.0, F1));
        MF1=PowerOfFunction(MF1,-1.0);
        MF2=CropFunction(Operators.createAffineFunctionTransformation(-1.0, 1.0, F2));
        MF2=Operators.PowerOfFunction(MF2,-1.0);
        temp=Operators.createProductFunction(fF, MF1);
        temp=Operators.createProductFunction(temp, MF2);
        alpha=Operators.CumulativeFunction(temp);
        }
        return alpha;
    } 
    
       ///                                   Lambda part, create a new package ?
    
    
    
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
    
        
    public static FunctionInterface exponentialDistribution = new FunctionInterface(){
      public double op(double x,double ... p){
          // p[0] coefficient, p[1] décalage
          if (x >= p[1]){
            return Math.exp(-p[0]*(x-p[1]));
          }  
          else {
              return 0;
          }
      }
    };   
    
    public static FunctionInterface exp = new FunctionInterface(){
      public double op(double x,double ... p){
          // p[0] coefficient
          return Math.exp(p[0]*x);
          }  
    };
      
    public static FunctionInterface homographie = new FunctionInterface(){
      public double op(double x,double ... p){
          // p[0]=a,p[1]=b,p[2]=c,p[3]=d
          return (p[0]*x+p[1])/(p[2]*x+p[3]);
          }  
    };
     
    public static FunctionInterface boundedExponentialDistribution = new FunctionInterface(){
      public double op(double x,double ... p){
          // p[0] coefficient, p[1] décalage, p[2] borne sup
          if ((x >= p[1]) && (x<= p[2])) {
            return Math.exp(-p[0]*(x-p[1]))/(1.0-Math.exp((p[1]-p[2])/p[0]));
          }  
          else {
              return 0;
          }
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

    
        public static FunctionInterface piecewiseLinear = new FunctionInterface(){
      public double op(double x,double ... p){
          //p [0], puis f(p[2*i])=p[2*i+1] , p[p.length]
          int n=p.length/2;
          if (x<p[0]){
              return p[1];
          }
          if (x>=p[2*n-2]){
              return p[2*n-1];
          }
          for (int i=0;i<n;i++){
              if ((x>=p[2*i])&&(x<p[2*i+2])){
                  return p[2*i+1]+(x-p[2*i])*(p[2*i+3]-p[2*i+1])/(p[2*i+2]-p[2*i]);
              }
          }
          return 0;
        }  
    };
    
    public static FunctionInterface survivalProbability = new FunctionInterface(){
      public double op(double dose,double ... p){
          //p[0]=pO2,p[1]=alpha,p[2]=beta,p[3]=m,p[4]=k,p[5]=phase
          double z=(1+p[2]/p[3]*dose*OMF.op(p[0],p[3],p[4]));
          double proba=Math.exp(-p[1]*dose*OMF.op(p[0],p[3],p[4])*z);
//                System.out.println("proba="+proba);
            if ((p[5]==3)||(p[5]==4)){
                return 0.0*proba;
            }
          else{
              return 1.0;//proba;
          }
        }  
    };
    
//    public static FunctionInterface survivalFunction = new FunctionInterface(){
//      public double op(double x,double ... p){
//          //p[0]=dose,p[1]=phase,p[2]=pO2 ?
//          switch ((int) p[1]){
//              case 1: ;
//          }
//        }  
//    };
    
    public static FunctionInterface OMF = new FunctionInterface(){
      public double op(double x,double ... p){
          //p[0]=m,p[1]=k
          return (p[0]*x+p[1])/(p[0]*(x+p[1]));
      }
    };  

}
