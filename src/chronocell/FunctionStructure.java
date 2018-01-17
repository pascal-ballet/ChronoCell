/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;
import chronocell.Operators.FunctionInterface;


/**
 *
 * @author goby
 */
public class FunctionStructure {
    String name=null;
    // Description du support de la fonction
    double min=0.0,max=0.0;
    // pas de la discretisation
    double step=1.0;
    // Tableau des valeurs
    double[] values=null;
    // Le tableau peut être plus grand que nécessaire, les minIndex et maxIndex servent à indiquer les indices en dehors desquels ont est sûr d'avoir des valeurs nulles
    // à des fins d'optimisation
    // Attention cependant, les index doivent toujours correspondre aux extrémitées du support
    int minIndex=0,maxIndex=0;    
    // Valeurs par défaut à gauche et à droite du support
    double left=0.0;
    double right=0.0;
    
    public double closestGridPoint(double x){
        return Numbers.CGN((Math.round(x/step))*step);
    }
    
        public int indexOfPoint(double x){
        return minIndex+ (int) Numbers.CGN(Math.round((x-min)/step));
    }
        
        public double pointWithIndex(int i){
        return Numbers.CGN(min+ i*step);
    }
        
          public double GetFunctionValue(double x){
        // On utilise les valeurs par défaut à gauche et à droite
        if (x<min) return left;
        if (x>max) return right;
        return values[indexOfPoint(x)];
    }
          
    public  double getMaxValue(){
        double maxVal=Math.max(left, right);
        for (int i=minIndex+1;i<=maxIndex;i++){
            if (values[i]>maxVal){
                maxVal=values[i];
            }
        }
        return maxVal;
    }
    public  double getMinValue(){
        double minVal=Math.min(left, right);
        for (int i=minIndex+1;i<=maxIndex;i++){
            if (values[i]<minVal){
                minVal=values[i];
            }
        }
        return minVal;
    }
      
     public void SetFunctionValue(double x, double y){
         y=Numbers.CGN(y);
//         System.out.println("setFunction value : x="+x+", y= "+y+", min= "+min+", max= "+max+", left= "+left+", right= "+right);
        double xGrid=closestGridPoint(x);
        // Si xGrid est dans le support, rien de spécial
        if ((xGrid>=min) && (xGrid<=max)){
            values[indexOfPoint(x)]=y;
//            System.out.println("valeur");
            return;
            }
        // si x est en dehors du support mais que y est égal à left (ou right), on ne fait rien
        if (((xGrid<min)&&(y==left))||((xGrid>max)&&(y==right))){
//            System.out.println("rien à faire");
            return;
        }
        // sinon, il faut agrandir le support à gauche ou à droite
        if(xGrid<min) {
//            System.out.println("gauche");
            ResizeFunctionSupportLeft(xGrid);
            values[0]=y;
            return;
        }
        if(xGrid>max){
//            System.out.println("droite");
            ResizeFunctionSupportRight(xGrid);
            values[values.length-1]=y;
            return;
        }
    } 
     
     public void SetFunctionValuesFromInterface(double start, double end, FunctionInterface g,double ... p){
       // laisser l'appelant ajuster à la grille ?
        start=closestGridPoint(start);
        end=closestGridPoint(end);
        if (start<min) ResizeFunctionSupportLeft(start);
        if (end>max) ResizeFunctionSupportRight(end);
//         System.out.println("start="+start+", end="+end);
        // la boucle sur les valeurs de x semble plus propre que de travailler avec le tableau des valeurs.
        double x=start;
        for (;;){
//            System.out.println("x="+x);
            SetFunctionValue(x, g.op( x, p));
            x=closestGridPoint(x+step);
            if (x>end) return;
        }
    }
      public void ResizeFunctionSupportLeft(double newMin){
        // typiquement le type de fonction dont on pourra évaluer l'intérêt de la coder directement avec les tableau de valeurs
        newMin=closestGridPoint(newMin);
        if (newMin>=min){
            System.out.println("Erreur, resize left but newMin >=min");
            return;
        }
        int size=(int) Math.round(1+(max-newMin)/step);
         System.out.println("newSize="+size);
        double[] newValues=new double[size];
        for (int i=0;i<size;i++){
            newValues[i]=GetFunctionValue(newMin+ (i)*step);
            System.out.println("newval= "+ newValues[i]);
        }
        values=newValues;
        min=newMin;
        minIndex=0;
        maxIndex=size-1;
//        for (double x=fct2.min;x<=fct2.max;x+=fct2.step) SetFunctionValue(fct2, x,GetFunctionValue(x) );
//        copyFunction(fct,fct2);
    } 
      
         public void ResizeFunctionSupportRight(double newMax){
        // typiquement le type de fonction dont on pourra évaluer l'intérêt de la coder directement avec les tableau de valeurs
        newMax=closestGridPoint(newMax);
        if (newMax<=max){
            System.out.println("Error, resize right but newMax <= max");
            return;
        }
        int size=(int) Math.round(1+(newMax-min)/step);
         System.out.println("newSize="+size);
        double[] newValues=new double[size];
        for (int i=0;i<size;i++){
            newValues[i]=GetFunctionValue(min+ (i)*step);
            System.out.println("newval= "+ newValues[i]);
        }
        values=newValues;
        minIndex=0;
        max=newMax;
        maxIndex=size-1;
//        for (double x=fct2.min;x<=fct2.max;x+=fct2.step) SetFunctionValue(fct2, x,GetFunctionValue(x) );
//        copyFunction(fct,fct2);
    }   
     
//     public void ResizeFunctionSupport(double newMin, double newMax){
//        // typiquement le type de fonction dont on pourra évaluer l'intérêt de la coder directement avec les tableau de valeurs
//        newMin=closestGridPoint(newMin);
//        newMax=closestGridPoint(newMax);
//        int size=(int) Math.round(1+(newMax-newMin)/step);
//         System.out.println("newSize="+size);
//        double[] newValues=new double[size];
//        for (int i=0;i<size;i++){
//            newValues[i]=GetFunctionValue(newMin+ (i)*step);
//            System.out.println("newval= "+ newValues[i]);
//        }
//        values=newValues;
//        min=newMin;
//        max=newMax;
//        minIndex=0;
//        maxIndex=size-1;
////        for (double x=fct2.min;x<=fct2.max;x+=fct2.step) SetFunctionValue(fct2, x,GetFunctionValue(x) );
////        copyFunction(fct,fct2);
//    }
     
    public int checkIndex(){
        //check vaut 1 en cas de problème
        int check=0;
        // On recherche l'indice le plus grand tel que toutes les valeurs avant cet indice sont nulles
        int minIdx=0;
        while (values[minIdx]==0) minIdx++;
        // On recherche l'indice le plus petit tel que toutes les valeurs après cet indice sont nulles
        int maxIdx=values.length-1;
        while (values[maxIdx]==0) maxIdx--;
        // En cas de différence, on modifie check et on affiche une alerte (optionnel)
        if ((minIdx!=minIndex)||(minIdx!=minIndex)){
            System.out.println("index erronées pour la fonction : "+name);
            check=1;
        }
        return check;
    }
    
    
    // Fonction qui redéfinit le support en fonction des valeurs qui seraient redondantes avec les left et right
    // à reprendre proprement 
    public  void checkAndAdjustSupport(){
        // On recherche l'indice le plus petit tel que toutes les valeurs après cet indice sont égales à right
        int maxIdx=values.length-1;
        int minIdx=0;
        while (values[maxIdx]==right){
//            System.out.println("maxIdx=right "+maxIdx);
            if (maxIdx>0) maxIdx--;
            else break;
        }
//            System.out.println("maxIdx= "+maxIdx);
        // On recherche l'indice le plus grand tel que toutes les valeurs avant cet indice sont égales à left
        while ((values[minIdx]==left)&&(minIdx<maxIdx)) minIdx++;
//            System.out.println("minIdx= "+minIdx);
        
        // En cas de différence, on le signale
        if ((minIdx!=minIndex)||(maxIdx!=maxIndex)) {
//            System.out.println("modification des index et du support de la fonction : "+name);
            double newMin=Numbers.CGN(min+minIdx*step);
            double newMax=Numbers.CGN(min+maxIdx*step);
            double[] newVal= new double[maxIdx-minIdx+1];
            for (int i=0;i<newVal.length;i++) newVal[i]=values[minIdx+i];
            values=newVal;
            minIndex=0;
            maxIndex=maxIdx-minIdx;
            min=newMin;
            max=newMax;
            }
     }
    
}


