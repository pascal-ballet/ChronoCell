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
        // Tests des operateurs
        FunctionStructure initialDensityG1=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(1.0),Numbers.CGN(0.001));
        Operators.MapFunctionValues(initialDensityG1,Operators.sinPeriodOne);
        FunctionStructure transitionProbability=Operators.createFunction(Numbers.CGN(0.5),Numbers.CGN(1.0),Numbers.CGN(0.01)); 
        Operators.MapFunctionValues(transitionProbability,Operators.constant);
        transitionProbability=Operators.AffineFunctionTransformation(1/Operators.IntegrateFunction(transitionProbability, transitionProbability.min, transitionProbability.max),0, transitionProbability);
        FunctionStructure solution=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(1.0),Numbers.CGN(0.01));
        Operators.MapFunctionValues(solution,Operators.sinPeriodOne);
//        Operators.PrintFunction(solution);
        for (int i=0;i<500;i++){            
//            System.err.println("+++++++++++++++++++++++++++++++++\n");
//        Operators.PrintFunction(solution);
            Operators.ComputeSolutionNextValue(solution, transitionProbability);
        }
        // Display Function
        GUI win1 =new GUI();
        win1.SetFunction(solution);
        win1.setVisible(true);
        Operators.PrintFunction(solution);
    }
}
