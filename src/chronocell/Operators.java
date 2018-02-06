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
//    public static FunctionStructure createFunction(double min,double max,double step,String name){
//        FunctionStructure fct=new FunctionStructure();
//        fct.name=name;
//        fct.min=Numbers.CGN(min);
//        fct.step=Math.max(Numbers.CGN(step),Numbers.minStep);
//        fct.max=Numbers.CGN( min+Math.floor((max-min)/step)*step );
////        System.out.println("step"+fct.step+", minstep"+Numbers.minStep);
//        // On détermine la taille du tableau des valeurs (le cast en int vient du fait que Math.round renvoie un long
//        int size=(int) Math.floor(1+(max-min)/fct.step);
//        fct.values=new double[size];
//        // On initialise toutes les valeurs à 0
//        for (int i=0;i<size;i++){
//            fct.values[i]=0.0;
//        }
//        // On initialise les index min et max (ici, c'est un choix, mais il est logique de déclarer que toutes les valeurs sont nulles 
//        fct.minIndex=0;
//        fct.maxIndex=size-1;
//        fct.left=0.0;
//        fct.right=0.0;
//        return fct;
//    }
    
    public static FunctionStructure createFunction(double min, double max, int n, String name) {
        FunctionStructure fct = new FunctionStructure();
        fct.name = name;
        fct.min = Numbers.CGN(min);
        fct.max = Numbers.CGN(max);
        fct.step = Numbers.CGN((max-min)/(n-1));
//        fct.max = Numbers.CGN(min + Math.floor((max - min) / fct.step) * fct.step);
//        System.out.println("step"+fct.step+", minstep"+Numbers.minStep);
        // On détermine la taille du tableau des valeurs (le cast en int vient du fait que Math.round renvoie un long
//        int size = (int) Math.round(1 + (max - min) / fct.step);
        fct.values = new double[n];
        // On initialise toutes les valeurs à 0
        for (int i = 0; i < n; i++) {
            fct.values[i] = 0.0;
        }
        // On initialise les index min et max (ici, c'est un choix, mais il est logique de déclarer que toutes les valeurs sont nulles 
        fct.minIndex = 0;
        fct.maxIndex = n-1 ;
        fct.left = 0.0;
        fct.right = 0.0;
        return fct;
    }
    
     public static FunctionStructure createDirac(double x, String name) {
        FunctionStructure fct = new FunctionStructure();
        fct.name = name;
        fct.min = Numbers.CGN(x);
        fct.max = Numbers.CGN(x);
        fct.step=Double.NaN;
        fct.values = new double[1];
       fct.values[0]=Double.NaN;
        fct.minIndex = 0;
        fct.maxIndex = 0 ;
        fct.left = 0.0;
        fct.right = 0.0;
        return fct;
    }   
    
    public static FunctionStructure createFunction(double min,double max,double step, String name){
        int n=(int) Math.floor((max-min)/step);
        FunctionStructure fct=createFunction(min, max, n, name);
        return fct;
    }
    
       public static FunctionStructure createFunction(double min,double max,double step){
        FunctionStructure fct=createFunction(min, max, step, null);
        return fct;
    }
       
        public static FunctionStructure createFunction(double min,double max,int n){
        FunctionStructure fct=createFunction(min, max, n, null);
        return fct;
    }
    
/// Création d'une copie exacte d'une fonction
    
    public static FunctionStructure createFunctionCopy(FunctionStructure fct){
        FunctionStructure cpy=Operators.createFunctionCopy(fct, fct.name);
        return cpy;
    }
    
        public static FunctionStructure createFunctionCopy(FunctionStructure fct,String name){
        FunctionStructure cpy=new FunctionStructure();
        cpy.name=name;
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
        double x;
        for (int i=comp.minIndex;i<=comp.maxIndex;i++){
//            comp.values[i]=g.op(fct.getFunctionValue(comp.indexPoint(i)), p);
            x=comp.indexPoint(i);
            comp.SetFunctionValue(x,g.op(fct.getFunctionValue(x), p));
        }
//        double x=fct.min;
//        for (;;){
//            comp.SetFunctionValue(x, g.op(fct.GetFunctionValueInterpolate(x,1), p));
//            x=fct.closestGridPoint(x+fct.step);
//            if (x>fct.max) break;
//        }
        return comp;
    }
    /// f ° g
    public static FunctionStructure ComposeFunctions(FunctionStructure f,FunctionStructure g){
        FunctionStructure comp = createFunction(g.min, g.max, g.step);
        comp.left=f.getFunctionValue(g.left);
        comp.right=f.getFunctionValue(g.right);
        double x;
        for (int i=comp.minIndex;i<=comp.maxIndex;i++){
//            comp.values[i]=f.getFunctionValue(g.values[i]);
            x=comp.indexPoint(i);
            comp.SetFunctionValue(x, f.getFunctionValue(g.getFunctionValue(x)));
        }
//        double x=fct.min;
//        for (;;){
//            comp.SetFunctionValue(x, g.op(fct.GetFunctionValue(x), p));
//            x=fct.closestGridPoint(x+fct.step);
//            if (x>fct.max) break;
//        }
        return comp;
    }
    
    
    
    public static void PrintFunction(FunctionStructure fct,boolean displayValues){
        System.out.println("************************************************************************");
        System.out.println("*** Print Function***");
        System.out.println("Name = "+fct.name+", Adress= "+fct);
        System.out.println("support = ["+fct.min+","+fct.max+"]"+", step = "+fct.step);
        System.out.println("effective support = ["+fct.indexPoint(fct.minIndex)+","+fct.indexPoint(fct.maxIndex)+"]");
        System.out.println("minIndex="+fct.minIndex+", maxIndex="+fct.maxIndex);
//       for (int i=0;i<fct.values.length;i++){
//            System.err.format("**** f(%f)=%f\n",fct.min+(i-fct.minIndex)*fct.step,fct.values[i]);
//        }
        System.out.println("minVal = "+fct.getMinValue()+", maxVal = "+fct.getMaxValue());
        System.out.println("**** Integral = "+IntegrateFunction(fct, fct.min, fct.max));
        System.out.println("left = "+fct.left+", right = "+fct.right+", values array length = "+fct.values.length);
        if (displayValues==true){
            double x;
            for (int i=fct.minIndex;i<=fct.maxIndex;i++){
                
                System.out.println("**** f("+fct.indexPoint(i)+") = "+fct.getFunctionValue(fct.indexPoint(i))+", Index = "+i);
            }
        }
  System.out.println("************************************************************************");
    } 
    public static void plotFunction(FunctionStructure fct){
        GUI win =new GUI(fct);//,fct.name);
//        win.SetFunction(fct);
        win.setVisible(true);
    }
    
        public static void plotFunction(FunctionStructure fct,String label){
            FunctionStructure cpy=Operators.createFunctionCopy(fct);
            cpy.name=label;
        GUI win =new GUI(cpy);//,fct.name);
//        win.SetFunction(fct);
        win.setVisible(true);
    }
        
        
    

// public static double IntegrateFunction(FunctionStructure fct,double inf, double sup,double step){
//        double sum=0.0;
//        //        int start=(int) Numbers.CGN(Math.round(fct.minIndex+(Math.max(inf, fct.min)-fct.min)/fct.step));
//        //        int end=(int)  Numbers.CGN(Math.round(fct.minIndex+(Math.min(sup, fct.max)-fct.min)/fct.step));
//
//        //         System.err.format("start=%d, end=%d \n",start,end);
////        System.out.println("inxinf="+fct.indexOfPoint(inf)+", inxsup="+fct.indexOfPoint(sup));
//        for (int i=fct.indexOfPoint(inf);i<fct.indexOfPoint(sup);i++){
//            // trapèzes
//            sum+=((fct.values[i]+fct.values[i+1])/2*fct.step);
//        }
//        return Numbers.CGN(sum);
////        return sum;
//    }     
      
    
public static double IntegrateFunction(FunctionStructure fct,double inf, double sup){
    
//        double step=1.0;
//        double int0=IntegrateFunction(fct, inf, sup,step);
//        step=0.1;
//        double int1=IntegrateFunction(fct, inf, sup,step);
//        while (Math.abs(int1-int0)>0.0001){
//            step/=10;
//            int0=int1;
//            int1=IntegrateFunction(fct, inf, sup,step);
//        }
//        
//        return int1;
        return IntegrateFunction(fct,inf,sup,ChronoCell.integrationStep);
    } 

public static double IntegrateFunctionRectangles(FunctionStructure fct,double inf, double sup){
    
//        double step=1.0;
//        double int0=IntegrateFunction(fct, inf, sup,step);
//        step=0.1;
//        double int1=IntegrateFunction(fct, inf, sup,step);
//        while (Math.abs(int1-int0)>0.0001){
//            step/=10;
//            int0=int1;
//            int1=IntegrateFunction(fct, inf, sup,step);
//        }
//        
//        return int1;
        return IntegrateFunctionRectangles(fct,inf,sup,ChronoCell.integrationStep);
    } 
     
     public static double IntegrateFunction(FunctionStructure fct,double inf, double sup, double step){
        
        step=(sup-inf)/Math.floor((sup-inf)/step);
//         System.out.println("In Function Integrate : step="+step);
        //        int start=(int) Numbers.CGN(Math.round(fct.minIndex+(Math.max(inf, fct.min)-fct.min)/fct.step));
        //        int end=(int)  Numbers.CGN(Math.round(fct.minIndex+(Math.min(sup, fct.max)-fct.min)/fct.step));

        //         System.err.format("start=%d, end=%d \n",start,end);
//        System.out.println("inxinf="+fct.indexOfPoint(inf)+", inxsup="+fct.indexOfPoint(sup));
        double infGrid=fct.ceilingPoint(inf);
        double supGrid=fct.floorPoint(sup);
        double sum=(fct.getFunctionValue(infGrid)+fct.getFunctionValue(inf))/2*(infGrid-inf)+
                    (fct.getFunctionValue(supGrid)+fct.getFunctionValue(sup))/2*(sup-supGrid);
        
        double t=infGrid;
        while (t+step<supGrid){
//         System.out.println("sum="+sum);
//            
//            System.out.println("inf= "+inf+", t="+t+", aire= "+(fct.getFunctionValue(t)+fct.getFunctionValue(t+step))/2*step);
            // trapèzes
            sum=sum+(fct.getFunctionValue(t)+fct.getFunctionValue(t+step))/2*step;
//            sum=sum+fct.getFunctionValue(t)*step;
            t=t+step;
        }
//        sum=sum+fct.getFunctionValue(fct.max)*(fct.max-t+step);
//        return Numbers.CGN(sum);
        return sum;
//        return IntegrateFunction(fct,inf,sup);
    } 
     
     public static double IntegrateFunctionRectangles(FunctionStructure fct,double inf, double sup, double step){
        
        step=(sup-inf)/Math.floor((sup-inf)/step);
//         System.out.println("In Function Integrate : step="+step);
        //        int start=(int) Numbers.CGN(Math.round(fct.minIndex+(Math.max(inf, fct.min)-fct.min)/fct.step));
        //        int end=(int)  Numbers.CGN(Math.round(fct.minIndex+(Math.min(sup, fct.max)-fct.min)/fct.step));

        //         System.err.format("start=%d, end=%d \n",start,end);
//        System.out.println("inxinf="+fct.indexOfPoint(inf)+", inxsup="+fct.indexOfPoint(sup));
        double infGrid=fct.ceilingPoint(inf);
        double supGrid=fct.floorPoint(sup);
        double sum=fct.getFunctionValue(infGrid)*(infGrid-inf)+fct.getFunctionValue(supGrid)*(sup-supGrid);
        
        double t=infGrid;
        while (t+step<supGrid){
//         System.out.println("sum="+sum);
//            
//            System.out.println("inf= "+inf+", t="+t+", aire= "+(fct.getFunctionValue(t)+fct.getFunctionValue(t+step))/2*step);
            // trapèzes
            sum=sum+fct.getFunctionValue(t)*step;
//            sum=sum+fct.getFunctionValue(t)*step;
            t=t+step;
        }
//        sum=sum+fct.getFunctionValue(fct.max)*(fct.max-t+step);
//        return Numbers.CGN(sum);
        return sum;
//        return IntegrateFunction(fct,inf,sup);
    } 
     
//          public static double IntegrateFunction(FunctionStructure fct,double inf, double sup){
//        double sum=0.0;
////        //        int start=(int) Numbers.CGN(Math.round(fct.minIndex+(Math.max(inf, fct.min)-fct.min)/fct.step));
////        //        int end=(int)  Numbers.CGN(Math.round(fct.minIndex+(Math.min(sup, fct.max)-fct.min)/fct.step));
////
////        //         System.err.format("start=%d, end=%d \n",start,end);
//////        System.out.println("inxinf="+fct.indexOfPoint(inf)+", inxsup="+fct.indexOfPoint(sup));
//        for (int i=fct.pointIndexFloor(inf);i<fct.pointIndexFloor(sup);i++){
//            // trapèzes
//            sum+=((fct.values[i]+fct.values[i+1])/2*fct.step);
//        }
//        return Numbers.CGN(sum);
////        double sum=IntegrateFunction(fct, inf, sup,fct.step);
////        return sum;
//    } 
     
    public static void makeDistributionFromFunction(FunctionStructure fct){
        fct.left=0.0;
        fct.right=0.0;
        fct.checkAndAdjustSupport();
        double norm=Numbers.CGN(1/IntegrateFunction(fct, fct.min, fct.max));
        affineFunctionTransformation(fct, norm, 0.0);
    }       
     
    public static FunctionStructure createDistributionFromFunction(FunctionStructure fct){
        FunctionStructure aff=createFunctionCopy(fct);
        aff.left=0.0;
        aff.right=0.0;
        double norm=Numbers.CGN(1/IntegrateFunction(aff, aff.min, aff.max));
        affineFunctionTransformation(aff, norm, 0.0);
        return aff;
    }  

     
    public static FunctionStructure createCumulativeFunction(FunctionStructure fct){
        if ((!Numbers.IsZero(fct.left))||(!Numbers.IsZero(fct.right))){
            System.out.println("Erreur, calcul de fonction de répartition d'une fonction qui n'est pas une distribution (support non borné)");
        } 
        if ((!Numbers.IsZero(Numbers.CGN(Operators.IntegrateFunction(fct, fct.min, fct.max,fct.step)-1.0)))){
            System.out.println("Attention, fonction "+fct.name+", calcul de fonction de répartition d'une fonction qui n'est pas une distribution : intégrale ="+Numbers.CGN(Operators.IntegrateFunction(fct, fct.min, fct.max)));
        }
        FunctionStructure cum=createFunction(fct.min, fct.max, fct.step);
        cum.left=0.0;
        cum.right=1.0;
        cum.name=fct.name+".cumulative";
        double x=cum.indexPoint(cum.minIndex);
//        cum.values[cum.minIndex]=0.0;
        cum.SetFunctionValue(x, 0.0);
//        Operators.plotFunction(fct);
        for (int i =cum.minIndex+1;i<=cum.maxIndex;i++){
//            cum.values[i]=cum.values[i-1]+(fct.getFunctionValue(cum.indexPoint(i-1))+fct.getFunctionValue(cum.indexPoint(i)))/2*cum.step;
//            cum.values[i]=cum.values[i-1]+(fct.getFunctionValue(cum.indexPoint(i-1)))*cum.step;
            x=cum.indexPoint(i);
            cum.SetFunctionValue(x, cum.getFunctionValue(x-cum.step)+(fct.getFunctionValue(x-cum.step)+fct.getFunctionValue(x))/2*cum.step);
        }
        
//        double t=fct.min;
//        for (int i =cum.minIndex+1;i<=cum.maxIndex;i++){
//            cum.values[i]=Operators.IntegrateFunction(fct, 0, t,0.001);
//            t+=cum.step;
//        }
//        Operators.affineFunctionTransformation(cum, 1/cum.getMaxValue(), 0);
//        / normalization (crado ? rependre la boucle précédente pour être sûr de ne jamais dépasser 1 par le calcul ?)
//        cum=AffineFunctionTransformation(1.0/cum.getMaxValue(),0, cum);
        return cum;
    } 
     
    
    public static FunctionStructure createPowerOfFunction(FunctionStructure fct,double pow){
        FunctionStructure power=createFunctionCopy(fct);
        power.name=fct.name+".power"+pow;
        power.left=Math.pow(fct.left,pow);
        power.right=Math.pow(fct.right,pow);
        double x;
        for (int i=power.minIndex;i<=power.maxIndex;i++){
            x=power.indexPoint(i);
//                power.values[i]=Math.pow(fct.values[i],pow);
                power.SetFunctionValue(x, Math.pow(fct.getFunctionValue(x),pow));
               
//                System.out.println("fct"+fct1.values[i]+"pow"+power.values[i]);
//                if (fct1.values[i]<0.00000001){
//                    System.out.println(" fct="+fct1.values[i]+", pow="+pow+", fpow="+power.values[i]);
//                }
        }
        
    return power;
    }  
    
    public static FunctionStructure createProductFunction(FunctionStructure f1,FunctionStructure f2){
        // initialement le step est Numbers.LeastCommonStep, mais c'est trop gourmand, on prend donc le min 
        FunctionStructure prod=createFunction(Math.min(f1.min,f2.min ), Math.max(f1.max,f2.max ), Math.min(f1.step, f2.step));
        prod.left=Numbers.CGN(f1.left*f2.left);
        prod.right=Numbers.CGN(f1.right*f2.right);
        double x;
//        f1.checkBounds();
//        f2.checkBounds();
//        for (int i=prod.minIndex;i<=prod.maxIndex;i++){
//            x=prod.indexPoint(i);
//            prod.values[i]=f1.getFunctionValue(x)*f2.getFunctionValue(x);
//        }
        
//        for (double x = prod.min; x <= prod.max; x+=prod.step) {
            for (int i=prod.minIndex;i<=prod.maxIndex;i++){
            x = prod.indexPoint(i);
            prod.SetFunctionValue(x, f1.getFunctionValue(x) * f2.getFunctionValue(x));
        }
//        prod.checkAndAdjustSupport();
    return prod;
    } 
    
//        public static FunctionStructure MultiplyFunctionByCumulative(FunctionStructure fct,FunctionStructure cumul){
//        FunctionStructure prod=createFunctionCopy(fct);
//        double min=Math.max(fct.min, cumul.min);
//        for (int i=prod.minIndex;i<=prod.maxIndex;i++){
//            if (prod.min+i*prod.step <= cumul.max){
//                prod.values[i]=fct.values[i]*cumul.GetFunctionValue(prod.min+i*prod.step);
//                    }
//        }
//        return prod;
//    }
        
//            public static FunctionStructure MultiplyFunctionByOneMinusCumulative(FunctionStructure fct,FunctionStructure cumul){
//        FunctionStructure prod=createFunctionCopy(fct);
//        double max=Math.min(fct.max, cumul.max);
//        for (int i=prod.minIndex;i<=prod.maxIndex;i++){
//            if (prod.min+i*prod.step >= cumul.min){
//                prod.values[i]=fct.values[i]*cumul.GetFunctionValue(prod.min+i*prod.step);
//                    }
//        }
//        return prod;
//    }
     
//    public static FunctionStructure MultiplyFunctionRaw(FunctionStructure fct1,FunctionStructure fct2){
//        FunctionStructure prod=new FunctionStructure();
////        if ((fct1.min-fct2.max)*(fct2.min-fct1.max)<0) {
//        if (((fct1.max<fct2.min)||(fct2.max<fct1.min))) {
//            prod=Operators.createFunction(0,0,1);
//        }
//        else{
//            prod=Operators.createFunction(fct1.min, fct1.max,Numbers.LeastCommonStep(fct1.step,fct2.step));
//            for (int i=prod.minIndex;i<=prod.maxIndex;i++){
//                prod.values[i]=fct1.GetFunctionValue( prod.min+i*prod.step)*fct2.GetFunctionValue( prod.min+i*prod.step);
//            }
//        }
//    return prod;
//    }  
    

    
    public static FunctionStructure createSumFunction(FunctionStructure f1,FunctionStructure f2){
        FunctionStructure sum=Operators.createFunction(Math.min(f1.min, f2.min), Math.max(f1.max, f2.max), Math.min(f1.step,f2.step));
        double x;
        sum.left=Numbers.CGN(f1.left+f2.left);
        sum.right=Numbers.CGN(f1.right+f2.right);
        for (int i=sum.minIndex;i<=sum.maxIndex;i++){
            x=sum.indexPoint(i);
            sum.SetFunctionValue(x,Numbers.CGN(f1.getFunctionValue(x)+f2.getFunctionValue(x)));
        }
        return sum;
    } 
    
    public static void affineFunctionTransformation(FunctionStructure fct,double a, double b ){
    //  x -> af(x)+b
//    FunctionStructure transf=Operators.createFunctionCopy(fct);
//        PrintFunction("affineTranform",transf);
    fct.left=Numbers.CGN(fct.left*a+b);
    fct.right=Numbers.CGN(fct.right*a+b);
    double x;
    for (int i=fct.minIndex;i<=fct.maxIndex;i++){
        x=fct.indexPoint(i);
//        fct.values[i]=Numbers.CGN(fct.values[i]*a+b);
        fct.SetFunctionValue(x, Numbers.CGN(fct.getFunctionValue(x)*a+b));
    }
//        PrintFunction("affineTranformAfter",transf);
//    return transf;
    } 
    
    public static FunctionStructure createAffineFunctionTransformation(double a, double b, FunctionStructure fct){
        // crée x -> af(x)+b
        FunctionStructure transf=Operators.createFunctionCopy(fct);
//        PrintFunction("affineTranform",transf);
        transf.left=Numbers.CGN(fct.left*a+b);
        transf.right=Numbers.CGN(fct.right*a+b);
        double x;
        for (int i=transf.minIndex;i<=transf.maxIndex;i++){
            x=transf.indexPoint(i);
            transf.SetFunctionValue(x, Numbers.CGN(fct.getFunctionValue(x)*a+b));
        }
//        PrintFunction("affineTranformAfter",transf);
    return transf;
    } 
    
    public static FunctionStructure createTranslatedFunction(double t, FunctionStructure fct){
//        PrintFunction(fct);
        FunctionStructure transl=createFunctionCopy(fct);
        transl.min=transl.min+t;
        transl.max=transl.max+t;
    return transl;
    } 
    
        
    public static void translateFunction(double t, FunctionStructure fct){
//        PrintFunction(fct);
        fct.min=fct.roundPoint(fct.min+t);
        fct.max=fct.roundPoint(fct.max+t);
    } 
    
    public static double LaplaceTransform(double lambda, FunctionStructure fct){
        if ((lambda==0.0)&&(fct.right!=0)){
            System.out.println("Erreur : transformée de Laplace non définie");
            return -1.0;                
    }
        // Régler la précision ici (le pas d'intégration)
        FunctionStructure expo=Operators.createFunction(fct.min,fct.max,0.01);
        expo.SetFunctionValuesFromInterface(expo.min,expo.max,Operators.exp,-lambda);
//        System.out.println("int="+Operators.IntegrateFunction(expo, expo.min, expo.max));
//        Operators.plotFunction(createProductFunction(fct, expo));
        double l=IntegrateFunction(createProductFunction(expo,fct),fct.min,fct.max,fct.step);
//          return Numbers.CGN(l+(fct.left*(1-Math.exp(-lambda*fct.min))+fct.right*Math.exp(-lambda*fct.max))/lambda);
            return l;
    } 
    
    //*************
    
    public static double InverseLaplaceTransform(double x, FunctionStructure fct){
        double epsilon=0.001;
        double step=1.0;
        double laplace=LaplaceTransform(step, fct);
        while (laplace<x) {
            step/=2;
            laplace=LaplaceTransform(step, fct);
            if (Numbers.IsZero(step)) 
            {
                System.out.println("Inversion laplace impossible");
                return -1.0;
            }
        }
//        puisque l'on ne manipule que des fonctions positives, la transformée de laplace est décroissante
        double lambda=step;
        while (Math.abs(x-laplace)>epsilon){
            while (laplace>=x){
//                System.out.println("Laplace="+laplace+", lambda="+lambda+", x="+x+", step="+step+", erreur="+Math.abs(x-laplace));
                lambda+=step;
                laplace=LaplaceTransform(lambda, fct);
            }
            lambda-=step;
//            System.out.println("step= "+step);
            while (laplace<x){
                step/=2;
                laplace=LaplaceTransform(lambda+step, fct);
                if(step==0.0) {
                    System.out.println("Inversion laplace impossible");
                    return -1.0;
                 }
            }
        }
          return Numbers.CGN(lambda);
    } 
    
//    public static void DoubleValuesArraySizeToLeft(FunctionStructure fct){
//    // Pour éviter les recopies incessantes lorsque l'on simule les populations
//        double[] newVal= new double[2*fct.values.length];
//        for (int i=0;i<fct.values.length;i++){
//            newVal[i]=0.0;
//        }
//        for (int i=fct.values.length;i<newVal.length;i++){
//            newVal[i]=fct.values[i-fct.values.length];
//        }
//        fct.minIndex=fct.values.length;
////        fct.minIndex=0;
//        fct.maxIndex=2*fct.values.length-1;
//        fct.values=newVal;
////        PrintFunction(fct, false);
//    }
    
//        public static FunctionStructure FunctionSupport(FunctionStructure fct){
//        double min=fct.min;
//        for (int i=fct.minIndex;i<fct.maxIndex;i++){
//            /// arrondi à paramétrer
//            if (Numbers.IsZero(fct.values[i])){
//                min+=fct.step;
//            }
//            else {
//                break;
//            }
//        }
//        double max=fct.max;
//        for (int i=fct.maxIndex;i>=fct.minIndex;i--){
//            if (Numbers.IsZero(fct.values[i])){
//                max-=fct.step;
//            }
//            else {
//                break;
//            }
//        }
//        FunctionStructure ind=createFunction(min, max, fct.step);
//        for (int i=ind.minIndex;i<=ind.maxIndex;i++){
//            ind.values[i]=1.0;
//        }
//        return ind;
//    }
//    
//    public static FunctionStructure CropFunction(FunctionStructure fct){
//        FunctionStructure ind=FunctionSupport(fct);
//        fct=createProductFunction(fct, ind);
//        return fct;
//    }
//    
//    public static FunctionStructure AlphaFunction(FunctionStructure f1,FunctionStructure F1,FunctionStructure F2){
//        FunctionStructure alpha,MF1,MF2,fF,temp;
//        temp=Operators.createProductFunction(f1,F2);
//        // ne pas calculer si au départ le produit fF est nul. Crado, à reprendre.
//        if (Numbers.IsZero(temp.getMaxValue())){
//            alpha=temp;
//        }
//        else {
//        fF=createProductFunction(Operators.FunctionSupport(f1),Operators.FunctionSupport(F2));
//        MF1=CropFunction(Operators.createAffineFunctionTransformation(-1.0, 1.0, F1));
//        MF1=PowerOfFunction(MF1,-1.0);
//        MF2=CropFunction(Operators.createAffineFunctionTransformation(-1.0, 1.0, F2));
//        MF2=Operators.PowerOfFunction(MF2,-1.0);
//        temp=Operators.createProductFunction(fF, MF1);
//        temp=Operators.createProductFunction(temp, MF2);
//        alpha=Operators.CumulativeFunction(temp);
//        }
//        return alpha;
//    } 
    
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

    
    public static FunctionInterface pow = new FunctionInterface(){
      public double op(double x,double ... p){
          // p[0] exposant
          return Math.pow(x,p[0]);
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
