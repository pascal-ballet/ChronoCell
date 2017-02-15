/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


// Todo :   * solve shift of time between bifurcations
//          * improve to transition probabilities that can evolve along time and depend on pO2
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
            SimulationStructure simulation=new SimulationStructure();
            simulation.timeStep=0.01;
            simulation.treat= new TreatmentStructure();
            simulation.treat.times= new double[]{100.0,200.0,Double.NaN};
            simulation.treat.doses= new double[]{0.5,0.5,0.0};
            simulation.solution= new SolutionStructure[simulation.treat.times.length];
            simulation.solution[0]=Operators.createSolutionStructure(5);
            simulation.solution[0].phaseName[0]="G1";
            simulation.solution[0].phaseName[1]="S";
            simulation.solution[0].phaseName[2]="G2";
            simulation.solution[0].phaseName[3]="M";
            simulation.solution[0].phaseName[4]="G0";
            
            double support0=20.0,support2=18.0;
            // uitliser le timeStep de simulationStructure
            double step=0.01;
            double pO2=20.0,C=1.0,B=0.075,M=26.3;
            
        /// Phase G1
            ///// Initial conditions
                simulation.solution[0].theta[0]= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(support0),Numbers.CGN(step));
                Operators.MapFunctionValues(simulation.solution[0].theta[0],0.0,1.0,Operators.sinPeriodOne);
                ///// Transition to S
                simulation.solution[0].transitionProbabilities[0]=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(support0),Numbers.CGN(0.01)); 
                Operators.MapFunctionValues(simulation.solution[0].transitionProbabilities[0],15.0,support0,Operators.continuousGeometricDistribution,15.0,pO2,C,B,M);
                simulation.solution[0].transitionProbabilities[0]=Operators.AffineFunctionTransformation(1/Operators.IntegrateFunction(simulation.solution[0].transitionProbabilities[0], simulation.solution[0].transitionProbabilities[0].min, simulation.solution[0].transitionProbabilities[0].max),0, simulation.solution[0].transitionProbabilities[0]);
                ///// Cumulative to S
                simulation.solution[0].oneMinusCumulativeFunctions[0]=Operators.AffineFunctionTransformation(-1.0, 1.0,Operators.CumulativeFunction(simulation.solution[0].transitionProbabilities[0]));
                ///// Transition to G0
                simulation.solution[0].transitionProbabilities[6]=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(support0),Numbers.CGN(0.01)); 
                Operators.MapFunctionValues(simulation.solution[0].transitionProbabilities[6],0.0,support0,Operators.constant,1.0);
                simulation.solution[0].transitionProbabilities[6]=Operators.AffineFunctionTransformation(0.05/Operators.IntegrateFunction(simulation.solution[0].transitionProbabilities[6], simulation.solution[0].transitionProbabilities[6].min, simulation.solution[0].transitionProbabilities[6].max),0, simulation.solution[0].transitionProbabilities[6]);
            ///// Cumulative to G0
                simulation.solution[0].oneMinusCumulativeFunctions[6]=Operators.AffineFunctionTransformation(-1.0, 1.0,Operators.CumulativeFunction(simulation.solution[0].transitionProbabilities[6]));
        /// Phase S
            ///// Initial conditions
                simulation.solution[0].theta[1]= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(8.0),Numbers.CGN(step));
                Operators.MapFunctionValues(simulation.solution[0].theta[1],0.0,8.0,Operators.constant,0.0);
                ///// Transition
                simulation.solution[0].transitionProbabilities[1]=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(8.0),Numbers.CGN(0.01)); 
                Operators.MapFunctionValues(simulation.solution[0].transitionProbabilities[1],7.99,8.0,Operators.constant,1.0);
                simulation.solution[0].transitionProbabilities[1]=Operators.AffineFunctionTransformation(1/Operators.IntegrateFunction(simulation.solution[0].transitionProbabilities[1], simulation.solution[0].transitionProbabilities[1].min, simulation.solution[0].transitionProbabilities[1].max),0, simulation.solution[0].transitionProbabilities[1]);
            ///// Cumulative function
                simulation.solution[0].oneMinusCumulativeFunctions[1]=Operators.AffineFunctionTransformation(-1.0, 1.0,Operators.CumulativeFunction(simulation.solution[0].transitionProbabilities[1]));
        /// Phase G2
            ///// Initial conditions
                simulation.solution[0].theta[2]= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(support2),Numbers.CGN(step));
                Operators.MapFunctionValues(simulation.solution[0].theta[2],0.0,support2,Operators.constant,0.0);
                ///// Transition
                simulation.solution[0].transitionProbabilities[2]=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(support2),Numbers.CGN(0.01)); 
                Operators.MapFunctionValues(simulation.solution[0].transitionProbabilities[2],3.0,support2,Operators.continuousGeometricDistribution,3.0,pO2,C,B,M);
                simulation.solution[0].transitionProbabilities[2]=Operators.AffineFunctionTransformation(1/Operators.IntegrateFunction(simulation.solution[0].transitionProbabilities[2], simulation.solution[0].transitionProbabilities[2].min, simulation.solution[0].transitionProbabilities[2].max),0, simulation.solution[0].transitionProbabilities[2]);
            ///// Cumulative function
                simulation.solution[0].oneMinusCumulativeFunctions[2]=Operators.AffineFunctionTransformation(-1.0, 1.0,Operators.CumulativeFunction(simulation.solution[0].transitionProbabilities[2]));
        /// Phase M
            ///// Initial conditions
                simulation.solution[0].theta[3]= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(2.0),Numbers.CGN(step));
                Operators.MapFunctionValues(simulation.solution[0].theta[3],0.0,2.0,Operators.constant,0.0);
                ///// Transition
                simulation.solution[0].transitionProbabilities[3]=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(2.0),Numbers.CGN(0.01)); 
                Operators.MapFunctionValues(simulation.solution[0].transitionProbabilities[3],1.99,2.0,Operators.constant,1.0);
                simulation.solution[0].transitionProbabilities[3]=Operators.AffineFunctionTransformation(1/Operators.IntegrateFunction(simulation.solution[0].transitionProbabilities[3], simulation.solution[0].transitionProbabilities[3].min, simulation.solution[0].transitionProbabilities[3].max),0, simulation.solution[0].transitionProbabilities[3]);
            ///// Cumulative function
                simulation.solution[0].oneMinusCumulativeFunctions[3]=Operators.AffineFunctionTransformation(-1.0, 1.0,Operators.CumulativeFunction(simulation.solution[0].transitionProbabilities[3]));
        /// Phase G0
            ///// Initial conditions
                simulation.solution[0].theta[4]= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(8.0),Numbers.CGN(step));
                Operators.MapFunctionValues(simulation.solution[0].theta[4],0.0,8.0,Operators.constant,0.0);
                ///// Transition
                simulation.solution[0].transitionProbabilities[4]=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(8.0),Numbers.CGN(0.01)); 
                Operators.MapFunctionValues(simulation.solution[0].transitionProbabilities[4],0.0,8.0,Operators.constant,1.0);
                simulation.solution[0].transitionProbabilities[4]=Operators.AffineFunctionTransformation(0.1/Operators.IntegrateFunction(simulation.solution[0].transitionProbabilities[4], simulation.solution[0].transitionProbabilities[4].min, simulation.solution[0].transitionProbabilities[4].max),0, simulation.solution[0].transitionProbabilities[4]);
            ///// Cumulative function
                simulation.solution[0].oneMinusCumulativeFunctions[4]=Operators.AffineFunctionTransformation(-1.0, 1.0,Operators.CumulativeFunction(simulation.solution[0].transitionProbabilities[4]));
                 
//                for (int i=0;i<=0;i++){
//                    simulation.solution[0].theta[i]=Operators.MultiplyFunctions(simulation.solution[0].theta[1], Operators.PowerOfFunction(simulation.solution[0].oneMinusCumulativeFunctions[1], -1));
//                }
         // Simulation
        

        for (int i=0;i<30000;i++){            
            Operators.ComputeSimulationNextValue(simulation);
        }
//         Display Function
//        GUI win1 =new GUI();    
//        win1.SetFunction(simulation.solution[1].theta[0]);
//        win1.setVisible(true);
//        GUI win2 =new GUI();
//        win2.SetFunction(simulation.solution[1].theta[0]);
//        win2.setVisible(true);
//        GUISolution win3 =new GUISolution();
//        win3.SetFunction(simulation.solution[0]);
//        win3.setVisible(true);
        GUISimulation win4 =new GUISimulation();
        win4.SetFunction(simulation);
        win4.setVisible(true);
    }
}
