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
        // TODO code application logic here
        Numbers.minStep=0.00001;
        // Parameters
        AgeDistributionsStructure dst=Operators.createAgeDistributionStructure(2);
            ///// Initial conditions phase G1/S
            dst.ageDistribution[0]= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(1.0),Numbers.CGN(0.02));
            Operators.MapFunctionValues(dst.ageDistribution[0],Operators.sinPeriodOne);
            ///// Initial conditions phase G2/M 
            dst.ageDistribution[1]= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(1.0),Numbers.CGN(0.02));
            Operators.MapFunctionValues(dst.ageDistribution[1],Operators.sinPeriodOne);
            ///// Transition function first checkpoint
            dst.transitionProbabilities[0]=Operators.createFunction(Numbers.CGN(0.5),Numbers.CGN(1.0),Numbers.CGN(0.01)); 
            Operators.MapFunctionValues(dst.transitionProbabilities[0],Operators.constant);
            dst.transitionProbabilities[0]=Operators.AffineFunctionTransformation(1/Operators.IntegrateFunction(dst.transitionProbabilities[0], dst.transitionProbabilities[0].min, dst.transitionProbabilities[0].max),0, dst.transitionProbabilities[0]);
            ///// Transition function first checkpoint
            dst.transitionProbabilities[1]=Operators.createFunction(Numbers.CGN(0.5),Numbers.CGN(1.0),Numbers.CGN(0.01)); 
            Operators.MapFunctionValues(dst.transitionProbabilities[1],Operators.constant);
            dst.transitionProbabilities[1]=Operators.AffineFunctionTransformation(1/Operators.IntegrateFunction(dst.transitionProbabilities[1], dst.transitionProbabilities[1].min, dst.transitionProbabilities[1].max),0, dst.transitionProbabilities[1]);
            // Simulation
        for (int i=0;i<100;i++){            
            Operators.ComputeSolutionNextValue(dst);
//            Operators.PrintFunction(dst.ageDistribution[0]);
        }
//         Display Function
        GUI win1 =new GUI();
        win1.SetFunction(dst.ageDistribution[0]);
        win1.setVisible(true);
        Operators.PrintFunction(dst.ageDistribution[0]);
        GUI win2 =new GUI();
        win2.SetFunction(dst.ageDistribution[1]);
        win2.setVisible(true);
        Operators.PrintFunction(dst.ageDistribution[1]);
    }
}
