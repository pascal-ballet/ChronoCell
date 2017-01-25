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
        Numbers.minStep=0.001;
        FunctionStructure fct=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(1.0),Numbers.CGN(0.1));
        Operators.FillFunctionValues(fct);
        Operators.PrintFunction(fct);
        // Display Function
        GUI win1 =new GUI();
        win1.setVisible(true);
        win1.SetFunction(fct);
    }
    
}
