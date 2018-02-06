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
public class StableSolution {
    public static double LaplaceEquation(double lambda,CellDynamics dyn){
        double x=0.0;
        double y=0.0;
        x=Operators.LaplaceTransform(lambda, Operators.createProductFunction(dyn.G0.density.get("G1"), dyn.G0.oneMinCumul.get("Death")));
        x*=Operators.LaplaceTransform(lambda, Operators.createProductFunction(dyn.G1.density.get("G0"), dyn.G1.oneMinCumul.get("S")));
        y=Operators.LaplaceTransform(lambda, dyn.S.density.get("G2"));
        y*=Operators.LaplaceTransform(lambda, dyn.G2.density.get("M"));
//        System.out.println("y==== "+Operators.LaplaceTransform(lambda, dyn.M.density.get("G1")));
        y*=Operators.LaplaceTransform(lambda, dyn.M.density.get("G1"));
        
//        Operators.PrintFunction(dyn.M.density.get("G1"),true);
        y*=Operators.LaplaceTransform(lambda, Operators.createProductFunction(dyn.G1.density.get("S"), dyn.G1.oneMinCumul.get("G0")));
        return x+2*y;
    }
    
    public static double SolveEquation(CellDynamics dyn){
        double step=0.001;
        double epsilon=0.00001;
        double lambda=0.0;
        double s=LaplaceEquation(lambda,dyn);
//        puisque l'on ne manipule que des fonctions positives, la transformée de laplace est décroissante
        if (s<1.0){
//            System.out.println("s="+s+", Pas de solution stable (la population s'éteint)");
            return -1.0;
        }
        boolean test=false;
        while (Math.abs(1.0-s)>epsilon){
            
            while (s>1.0){
                test=true;
                lambda+=step;
                s=LaplaceEquation(lambda,dyn);
//            System.out.println("while 2 : s="+s+", lambda="+lambda+", step="+step);
            }
            if (test==true){
                lambda-=step;
                s=LaplaceEquation(lambda,dyn);
                test=false;
            }               
            step/=2.0;        
//            System.out.println("while 1 : s="+s+", lambda="+lambda+", step="+step);
        }
        return lambda;
    }
    public static void StableInitialCondition(ThetaStructure theta,CellPopulation pop){
        FunctionStructure expo=null;
        double lambda=SolveEquation(pop.dynamics);
        System.out.println("sol="+LaplaceEquation(lambda, pop.dynamics)+", lambda="+lambda);
        double[] LaplaceCoef = new double[pop.dynamics.phaseNb];
        LaplaceCoef[0]=Operators.LaplaceTransform(lambda, Operators.createProductFunction(pop.dynamics.G1.density.get("G0"), pop.dynamics.G1.oneMinCumul.get("S")));
        LaplaceCoef[1]=1.0;
        LaplaceCoef[2]=Operators.LaplaceTransform(lambda, Operators.createProductFunction(pop.dynamics.G1.density.get("S"), pop.dynamics.G1.oneMinCumul.get("G0")));
        LaplaceCoef[3]=LaplaceCoef[2]*Operators.LaplaceTransform(lambda, pop.dynamics.S.density.get("G2"));
        LaplaceCoef[4]=LaplaceCoef[3]*Operators.LaplaceTransform(lambda, pop.dynamics.G2.density.get("M"));
        double supportMax=0.0;
        supportMax=pop.dynamics.G0.oneMinCumul.get("G1").max;
         if (supportMax<pop.dynamics.G1.oneMinCumul.get("G0").max){
            supportMax=pop.dynamics.G1.oneMinCumul.get("G0").max;
        }
        if (supportMax<pop.dynamics.S.oneMinCumul.get("G2").max){
            supportMax=pop.dynamics.S.oneMinCumul.get("G2").max;
        }
        if (supportMax<pop.dynamics.G2.oneMinCumul.get("M").max){
            supportMax=pop.dynamics.G2.oneMinCumul.get("M").max;
        }
        if (supportMax<pop.dynamics.M.oneMinCumul.get("G1").max){
            supportMax=pop.dynamics.M.oneMinCumul.get("G1").max;
        }
//        expo=Operators.createFunction(0.0, supportMax, pop.dynamics.G0.density.get("G1").step);
        expo=Operators.createFunction(0.0, supportMax, pop.timeStep);
        expo.SetFunctionValuesFromInterface(expo.min,expo.max,Operators.exp, -lambda);
        
        for (int i=0;i<pop.dynamics.phaseNb;i++){
//             Operators.plotFunction(simulation.theta[0].getPhase(i));
            theta.setPhase(i,Operators.createAffineFunctionTransformation(LaplaceCoef[i],0.0,expo));
//            Operators.plotFunction(theta.getPhase(i));
        } 
//        theta.setPhase(1,Operators.AffineFunctionTransformation(10.0*LaplaceCoef[1],0.0,expo));
    }
}
