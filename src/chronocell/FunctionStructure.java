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
    
    public double roundPoint(double x){
        return Numbers.CGN(min+(Math.round((x-min)/step))*step);
    }
    
    public double floorPoint(double x){
        return Numbers.CGN(min+(Math.floor((x-min)/step))*step);
    }
    
    public double ceilingPoint(double x){
        return Numbers.CGN(min+(Math.floor((x-min)/step)+1)*step);
    }
    
        public int pointIndexRound(double x){
        return minIndex+ (int) Numbers.CGN(Math.round((x-min)/step));
    }
        
        public int pointIndexFloor(double x){
//            if (Math.floor((x-min)/step)>maxIndex)      {
//                System.out.println("index="+((x-min))+", min="+min+", max="+max+", x="+x+", name"+this.name+", minindex="+minIndex);
//                Operators.PrintFunction(this, false);
//            }
        return minIndex+ (int) (Math.floor((x-min)/step));
    }
        
        public double indexPoint(int i){
        return roundPoint(min+ (i-minIndex)*step);
    }
        
        public double getFunctionValue(double x){
        // On utilise les valeurs par défaut à gauche et à droite
        double xGrid=floorPoint(x);
        if (xGrid<min) return left;
        if (xGrid>max) return right;
        int i=pointIndexFloor(x);
        if (i==maxIndex)     return values[i];
//        if (maxIndex>=values.length) System.out.println("ERROR");
//        if (i==maxIndex){
//            
//            Operators.PrintFunction(this, true);
//        }
//    if (i>=values.length-1) Operators.PrintFunction(this, false);
        return Numbers.CGN(values[i]+(values[i+1]-values[i])*(x-indexPoint(i))/(indexPoint(i+1)-indexPoint(i)));
    }
//        
//        public double getFunctionValue(double x) {
//        // On utilise les valeurs par défaut à gauche et à droite
//
//        if (x < min) {
//            return left;
//        }
//        if (x > max) {
//            return right;
//        }
//        
//            return values[pointIndexRound(x)];
//
////        
//    }
          

          
    public  double getMaxValue(){
        double maxVal=Math.max(left, right);
        for (int i=minIndex;i<=maxIndex;i++){
            if (values[i]>maxVal){
                maxVal=values[i];
            }
        }
        return maxVal;
    }
    public  double getMinValue(){
        double minVal=Math.min(left, right);
        for (int i=minIndex;i<=maxIndex;i++){
            if (values[i]<minVal){
                minVal=values[i];
            }
        }
        return minVal;
    }
    
    public  void setSideValues(){
//        System.out.println("length="+values.length);
        left=values[0];
        if (values.length==1) right =values[0];
        else right=values[values.length-1];
    }
      
    public void checkBounds(){
//        if(min!=indexPoint(minIndex)) {
//            System.out.println("pb min");
//            Operators.PrintFunction(this, false);
//        }
        if(pointIndexRound(max)!=maxIndex) {
            System.out.println("pb max");
            Operators.PrintFunction(this, false);
        }
        if(pointIndexRound(min)!=minIndex) {
            System.out.println("pb min");
            Operators.PrintFunction(this, false);
        }
    }
    
     public void SetFunctionValue(double x, double y){
//         y=Numbers.CGN(y);
//        double xGrid=roundPoint(x);
        int xIdx=pointIndexRound(x);
//         System.out.println("setFunction value : x="+x+", xidx= "+xIdx+", min= "+min+", max= "+max+", left= "+left+", right= "+right);
        // Si xGrid est dans le support, rien de spécial
        if ((xIdx>=minIndex) && (xIdx<=maxIndex)){
            values[xIdx]=Numbers.CGN(y);
//            System.out.println("valeur");
            return;
            }
        // si x est en dehors du support mais que y est égal à left (ou right), on ne fait rien
        if (((xIdx<minIndex)&&(y==left))||((xIdx>maxIndex)&&(y==right))){
//            System.out.println("rien à faire");
            return;
        }
        // sinon, il faut peut-être agrandir le support à gauche ou à droite
        if((xIdx<minIndex)&&(xIdx>=0)){
            System.out.println("point="+indexPoint(xIdx)+","+minIndex);
            min=indexPoint(xIdx);
            minIndex=xIdx;
            values[minIndex]=Numbers.CGN(y);
//            System.out.println("point="+indexPoint(xIdx)+","+minIndex+", min="+min);
        }
        if((xIdx<minIndex)&&(xIdx<0))
            {
//            System.out.println("gauche");
            increaseValuesSizeLeft(minIndex-xIdx);
//            minIndex=0;
//            min=indexPoint(xIdx);
//            maxIndex=maxIndex+minIndex-xIdx;
            values[minIndex]=Numbers.CGN(y);
            this.checkBounds();
            return;
        }
        if((xIdx>maxIndex)&&(xIdx<=values.length)){
            maxIndex=xIdx;
            values[maxIndex]=Numbers.CGN(y);
            max=indexPoint(xIdx);
        }
        if((xIdx>maxIndex)&&(xIdx>values.length))
            {
//            System.out.println("gauche");
            increaseValuesSizeRight(xIdx-maxIndex);
//            maxIndex=xIdx;
            values[maxIndex]=Numbers.CGN(y);
            this.checkBounds();
            return;
        }
//        if(xGrid>max){
////            System.out.println("droite");
//            ResizeFunctionSupportRight(xGrid);
//            values[maxIndex]=y;
//            this.checkBounds();
//            return;
//        }
    } 
     
     
     public void SetFunctionValueRegardlessOfSidesValues(double x, double y){
//         y=Numbers.CGN(y);
//        double xGrid=roundPoint(x);
        int xIdx=pointIndexRound(x);
//         System.out.println("setFunction value : x="+x+", xidx= "+xIdx+", min= "+min+", max= "+max+", left= "+left+", right= "+right);
        // Si xGrid est dans le support, rien de spécial
        if ((xIdx>=minIndex) && (xIdx<=maxIndex)){
            values[xIdx]=Numbers.CGN(y);
//            System.out.println("valeur");
            return;
            }
//        // si x est en dehors du support mais que y est égal à left (ou right), on ne fait rien
//        if (((xIdx<minIndex)&&(y==left))||((xIdx>maxIndex)&&(y==right))){
////            System.out.println("rien à faire");
//            return;
//        }
        // sinon, il faut peut-être agrandir le support à gauche ou à droite
        if((xIdx<minIndex)&&(xIdx>=0)){
//            System.out.println("point="+indexPoint(xIdx)+","+minIndex);
            min=indexPoint(xIdx);
            minIndex=xIdx;
            values[minIndex]=Numbers.CGN(y);
//            System.out.println("point="+indexPoint(xIdx)+","+minIndex+", min="+min);
        }
        if((xIdx<minIndex)&&(xIdx<0))
            {
//            System.out.println("gauche");
            increaseValuesSizeLeft(minIndex-xIdx);
//            minIndex=0;
//            min=indexPoint(xIdx);
//            maxIndex=maxIndex+minIndex-xIdx;
            values[minIndex]=Numbers.CGN(y);
            this.checkBounds();
            return;
        }
        if((xIdx>maxIndex)&&(xIdx<=values.length)){
            maxIndex=xIdx;
            values[maxIndex]=Numbers.CGN(y);
            max=indexPoint(xIdx);
        }
        if((xIdx>maxIndex)&&(xIdx>values.length))
            {
//            System.out.println("gauche");
            increaseValuesSizeRight(xIdx-maxIndex);
//            maxIndex=xIdx;
            values[maxIndex]=Numbers.CGN(y);
            this.checkBounds();
            return;
        }
//        if(xGrid>max){
////            System.out.println("droite");
//            ResizeFunctionSupportRight(xGrid);
//            values[maxIndex]=y;
//            this.checkBounds();
//            return;
//        }
    } 
     
     public void SetFunctionValuesFromInterface(FunctionInterface g,double ... p){
       // laisser l'appelant ajuster à la grille ?
        
//         System.out.println("start="+start+", end="+end);
        // la boucle sur les valeurs de x semble plus propre que de travailler avec le tableau des valeurs.
//        for (int i=0;i<values.length;i++){
////            System.out.println("x="+x);
//            values[i]=g.op(indexPoint(i), p);
//        }
         SetFunctionValuesFromInterface(min, max, g, p);
    }
     
     public void SetFunctionValuesFromInterface(double start, double end, FunctionInterface g,double ... p){
       // laisser l'appelant ajuster à la grille ?
        double gstart=roundPoint(start);
        double gend=roundPoint(end);
        if (gstart<min){
//            System.out.println("start="+start+", gstart="+gstart);
            SetFunctionValue(min, Double.NaN);
//            ResizeFunctionSupportLeft(gstart);
        }
        if (gend>max){
//            System.out.println("end="+end+", gend="+gend);
            SetFunctionValue(max, Double.NaN);  
//            ResizeFunctionSupportRight(gend);
        }
//         System.out.println("start="+start+", end="+end);
        // la boucle sur les valeurs de x semble plus propre que de travailler avec le tableau des valeurs.
        double x=gstart;
        for (;;){
//            System.out.println("x="+x);
            SetFunctionValue(x, g.op( x, p));
            x=roundPoint(x+step);
            if (x>gend) return;
        }
    }
     
     public void regularize(){
//         n=Math.max(n,1);
         for (int i=1;i<values.length-1;i++){
             values[i]=(values[i-1]+values[i]+values[i+1])/3;
         }
     }
     
     public void increaseValuesSizeLeft(int n){        
        double[] newArray=new double[n+values.length];
        for (int i=0;i<n;i++){
            newArray[i]=0;
//            System.out.println("newval= "+ newValues[i]);
        }
        for (int i=n;i<newArray.length;i++){
            newArray[i]=values[i-n];
//            System.out.println("newval= "+ newValues[i]);
        }
        values=newArray;
        minIndex=minIndex+n;
        maxIndex=maxIndex+n;
    } 
     
      public void increaseValuesSizeRight(int n){
        // typiquement le type de fonction dont on pourra évaluer l'intérêt de la coder directement avec les tableau de valeurs
        
        
        double[] newArray=new double[n+values.length];
        for (int i=0;i<values.length;i++){
            newArray[i]=values[i];
//            System.out.println("newval= "+ newValues[i]);
        }
        for (int i=values.length;i<newArray.length-1;i++){
            newArray[i]=0;
//            System.out.println("newval= "+ newValues[i]);
        }
        values=newArray;
        
    } 
      
      
    public void reduceValuesSizeLeft(int n){ 
        // attention, fonction sans test concernant min, minindex
        double[] newArray=new double[values.length-n];
        for (int i=0;i<newArray.length;i++){
            newArray[i]=values[i+n];
     //            System.out.println("newval= "+ newValues[i]);
        }
        values=newArray;
    } 
    
        public void reduceValuesSizeRightt(int n){ 
        // attention, fonction sans test concernant max, maxindex
        double[] newArray=new double[values.length-n];
        for (int i=0;i<newArray.length;i++){
            newArray[i]=values[i];
     //            System.out.println("newval= "+ newValues[i]);
        }
        values=newArray;
    } 
     
      public void ResizeFunctionSupportLeft(double newMin){
        // typiquement le type de fonction dont on pourra évaluer l'intérêt de la coder directement avec les tableau de valeurs
        Double newMinGrid=roundPoint(newMin);
        if (newMinGrid>=min){
            System.out.println("Erreur, resize left but newMin >=min");
            return;
        }
        int size=(int) Math.round(1+(max-newMin)/step);
//         System.out.println("newSize of function "+name+" ="+size);
        double[] newValues=new double[size];
        for (int i=0;i<size;i++){
            newValues[i]=getFunctionValue(newMin+ (i)*step);
//            System.out.println("newval= "+ newValues[i]);
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
        newMax=roundPoint(newMax);
        if (newMax<=max){
            System.out.println("Error, resize right but newMax <= max");
            return;
        }
        int size=(int) Math.round(1+(newMax-min)/step);
//         System.out.println("newSize of function "+name+" ="+size);
        double[] newValues=new double[size];
        for (int i=0;i<size;i++){
            newValues[i]=getFunctionValue(min+ (i)*step);
//            System.out.println("newval= "+ newValues[i]);
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
        int maxIdx=maxIndex;
        int minIdx=minIndex;
        while ((values[maxIdx]==right)&&(maxIdx>0)){
//            System.out.println("maxIdx=right "+maxIdx);
            maxIdx--;
        }
//            System.out.println("maxIdx= "+maxIdx);
        // On recherche l'indice le plus grand tel que toutes les valeurs avant cet indice sont égales à left
        while ((values[minIdx]==left)&&(minIdx<maxIdx)) minIdx++;
//            System.out.println("minIdx= "+minIdx);
        
        // En cas de différence, on le signale
        if ((minIdx!=minIndex)||(maxIdx!=maxIndex)) {
//            System.out.println("modification des index et du support de la fonction : "+name);
            double newMin=indexPoint(minIdx);
            double newMax=indexPoint(maxIdx);
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


