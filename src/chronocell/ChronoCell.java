/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chronocell;

/**
 *
 * @author pascal
 */
public class ChronoCell {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Numbers.minStep=0.00001;
        // Parameters
            SolutionStructure sol=Operators.createSolutionStructure(2);
            sol.phaseName[0]="G1/S";
            sol.phaseName[1]="G2/M";
        ///// Initial conditions phase G1/S
            sol.theta[0]= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(1.0),Numbers.CGN(0.01));
            Operators.MapFunctionValues(sol.theta[0],0.5,1.0,Operators.constant);
            ///// Transition function first checkpoint
            sol.transitionProbabilities[0]=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(1.0),Numbers.CGN(0.01)); 
            Operators.MapFunctionValues(sol.transitionProbabilities[0],0.9,1.0,Operators.constant);
            sol.transitionProbabilities[0]=Operators.AffineFunctionTransformation(1/Operators.IntegrateFunction(sol.transitionProbabilities[0], sol.transitionProbabilities[0].min, sol.transitionProbabilities[0].max),0, sol.transitionProbabilities[0]);
        ///// Cumulative function first checkpoint
            sol.oneMinusCumulativeFunctions[0]=Operators.AffineFunctionTransformation(-1.0, 1.0,Operators.CumulativeFunction(sol.transitionProbabilities[0]));
        ///// Initial conditions phase G2/M 
            sol.theta[1]= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(1.0),Numbers.CGN(0.01));
            Operators.MapFunctionValues(sol.theta[1],0.0,0.0,Operators.constant);
            ///// Transition function first checkpoint
            sol.transitionProbabilities[1]=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(1.0),Numbers.CGN(0.01)); 
            Operators.MapFunctionValues(sol.transitionProbabilities[1],0.6,1.0,Operators.sinPeriodOne);
            sol.transitionProbabilities[1]=Operators.AffineFunctionTransformation(1/Operators.IntegrateFunction(sol.transitionProbabilities[1], sol.transitionProbabilities[1].min, sol.transitionProbabilities[1].max),0, sol.transitionProbabilities[1]);
        ///// Cumulative function first checkpoint
            sol.oneMinusCumulativeFunctions[1]=Operators.AffineFunctionTransformation(-1.0, 1.0,Operators.CumulativeFunction(sol.transitionProbabilities[1]));
         // Simulation
        for (int i=0;i<5000;i++){            
            Operators.ComputeSolutionNextValue(sol);
//            Operators.PrintFunction(dst.ageDistribution[0]);
//            System.err.format("intégrale = %f \n",Operators.IntegrateFunction(dst.ageDistribution[0], dst.ageDistribution[0].min, dst.ageDistribution[0].min+1.0));
        }
//         Display Function
//        GUI win1 =new GUI();    
//        win1.SetFunction(sol.theta[0]);
//        win1.setVisible(true);
//        GUI win2 =new GUI();
//        win2.SetFunction(sol.theta[0]);
//        win2.setVisible(true);
        GUISolution win3 =new GUISolution();
        win3.SetFunction(sol);
        win3.setVisible(true);
    }
}
