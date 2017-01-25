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
        FunctionStructure fct1=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(Math.PI/2),Numbers.CGN(0.05));
        FunctionStructure fct2=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(Math.PI/4),Numbers.CGN(0.03));
        Operators.FillFunctionValues(fct1);
        Operators.FillFunctionValues(fct2);
//        System.err.format("minStep=%f\n", Numbers.LeastCommonStep(fct1.step, fct2.step));
        fct2=Operators.TranslateFunction(2.5, Operators.AffineFunctionTransformation(-1, 0, fct2));
        FunctionStructure fct3=Operators.AddFunctions(fct1, fct2);
        // Display Function
        GUI win1 =new GUI();
        win1.SetFunction(fct3);
        win1.setVisible(true);
        System.err.format("Intégrale = %f\n",Operators.IntegrateFunction(fct3, fct3.min, fct3.max));
//        Operators.PrintFunction(fct3);
    }
}
