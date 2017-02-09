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
            SolutionStructure sol=Operators.createSolutionStructure(4);
            sol.phaseName[0]="G1";
            sol.phaseName[1]="S";
            sol.phaseName[2]="G2";
            sol.phaseName[3]="M";
            
            
        /// Phase G1
            ///// Initial conditions
                sol.theta[0]= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(30.0),Numbers.CGN(0.01));
                Operators.MapFunctionValues(sol.theta[0],0.0,30.0,Operators.constant);
                ///// Transition
                sol.transitionProbabilities[0]=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(30.0),Numbers.CGN(0.01)); 
                Operators.MapFunctionValues(sol.transitionProbabilities[0],15.0,30.0,Operators.continuousGeometricDistribution1);
                sol.transitionProbabilities[0]=Operators.AffineFunctionTransformation(1/Operators.IntegrateFunction(sol.transitionProbabilities[0], sol.transitionProbabilities[0].min, sol.transitionProbabilities[0].max),0, sol.transitionProbabilities[0]);
            ///// Cumulative
                sol.oneMinusCumulativeFunctions[0]=Operators.AffineFunctionTransformation(-1.0, 1.0,Operators.CumulativeFunction(sol.transitionProbabilities[0]));
        /// Phase S
            ///// Initial conditions
                sol.theta[1]= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(8.0),Numbers.CGN(0.01));
                Operators.MapFunctionValues(sol.theta[1],0.0,8.0,Operators.constant);
                ///// Transition
                sol.transitionProbabilities[1]=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(8.0),Numbers.CGN(0.01)); 
                Operators.MapFunctionValues(sol.transitionProbabilities[1],7.99,8.0,Operators.constant);
                sol.transitionProbabilities[1]=Operators.AffineFunctionTransformation(1/Operators.IntegrateFunction(sol.transitionProbabilities[1], sol.transitionProbabilities[1].min, sol.transitionProbabilities[1].max),0, sol.transitionProbabilities[1]);
            ///// Cumulative function
                sol.oneMinusCumulativeFunctions[1]=Operators.AffineFunctionTransformation(-1.0, 1.0,Operators.CumulativeFunction(sol.transitionProbabilities[1]));
        /// Phase G2
            ///// Initial conditions
                sol.theta[2]= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(18.0),Numbers.CGN(0.01));
                Operators.MapFunctionValues(sol.theta[2],0.0,18.0,Operators.constant);
                ///// Transition
                sol.transitionProbabilities[2]=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(18.0),Numbers.CGN(0.01)); 
                Operators.MapFunctionValues(sol.transitionProbabilities[2],3.0,18.0,Operators.continuousGeometricDistribution2);
                sol.transitionProbabilities[2]=Operators.AffineFunctionTransformation(1/Operators.IntegrateFunction(sol.transitionProbabilities[2], sol.transitionProbabilities[2].min, sol.transitionProbabilities[2].max),0, sol.transitionProbabilities[2]);
            ///// Cumulative function
                sol.oneMinusCumulativeFunctions[2]=Operators.AffineFunctionTransformation(-1.0, 1.0,Operators.CumulativeFunction(sol.transitionProbabilities[2]));
        /// Phase M
            ///// Initial conditions
                sol.theta[3]= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(2.0),Numbers.CGN(0.01));
                Operators.MapFunctionValues(sol.theta[3],0.0,2.0,Operators.constant);
                ///// Transition
                sol.transitionProbabilities[3]=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(2.0),Numbers.CGN(0.01)); 
                Operators.MapFunctionValues(sol.transitionProbabilities[3],1.99,2.0,Operators.constant);
                sol.transitionProbabilities[3]=Operators.AffineFunctionTransformation(1/Operators.IntegrateFunction(sol.transitionProbabilities[3], sol.transitionProbabilities[3].min, sol.transitionProbabilities[3].max),0, sol.transitionProbabilities[3]);
            ///// Cumulative function
                sol.oneMinusCumulativeFunctions[3]=Operators.AffineFunctionTransformation(-1.0, 1.0,Operators.CumulativeFunction(sol.transitionProbabilities[3]));
         // Simulation
        for (int i=0;i<20000;i++){            
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
