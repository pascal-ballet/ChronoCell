/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


// Todo :   *  write properly solutions for initial condition to solve the shift of time between bifurcations
//          * solve the problem of cell death
//          * improve to transition probabilities that can evolve along time and depend on pO2
//          * implement population size calculus
//          * check oprators that copy functions or not
package chronocell;

import static chronocell.Operators.IntegrateFunction;
import static chronocell.Operators.MultiplyFunctions;
import static chronocell.Operators.PowerOfFunction;
import static chronocell.Operators.TranslateFunction;

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
            simulation.treat.times= new double[]{Double.NaN};
            simulation.treat.doses= new double[]{0.0};
            simulation.solution= new SolutionStructure[simulation.treat.times.length];
            simulation.theta= new ThetaStructure[simulation.treat.times.length];
            for (int i=0;i<simulation.treat.times.length;i++){
                simulation.theta[i] = new ThetaStructure();
            }
            
            simulation.solution[0]=Operators.createSolutionStructure(5);
            simulation.solution[0].phaseName[0]="G0";
            simulation.solution[0].phaseName[1]="G1";
            simulation.solution[0].phaseName[2]="S";
            simulation.solution[0].phaseName[3]="G2";
            simulation.solution[0].phaseName[4]="M";
            
            double support0=20.0,support2=18.0;
            // uitliser le timeStep de simulationStructure
            double step=0.01;
            double pO2=20.0,C=1.0,B=0.075,M=26.3;
            int indice=0;
    //Network creation
        // G0->Death
        FunctionStructure G0ToDeath=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(8.0),Numbers.CGN(0.001)); 
        Operators.MapFunctionValues(G0ToDeath,0.0,8.0,Operators.gaussian,3.0,1.0);
        G0ToDeath=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(G0ToDeath, G0ToDeath.min, G0ToDeath.max),0, G0ToDeath);
        simulation.theta[0].dyn.G0.density.put("Death", G0ToDeath);
        // G0->G1
        FunctionStructure G0ToG1=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(8.0),Numbers.CGN(0.001));  
        Operators.MapFunctionValues(G0ToG1,0.0,8.0,Operators.gaussian,2.0,1.0);;
        G0ToDeath=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(G0ToG1, G0ToG1.min, G0ToG1.max),0, G0ToG1);
        simulation.theta[0].dyn.G0.density.put("G1", G0ToG1);
        // G1->G0
        FunctionStructure G1ToG0=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(support0),Numbers.CGN(0.01)); 
        Operators.MapFunctionValues(G1ToG0,0.0,support0,Operators.gaussian,15.0,5.0);
        G0ToDeath=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(G1ToG0, G1ToG0.min, G1ToG0.max),0, G1ToG0);
        simulation.theta[0].dyn.G1.density.put("G0", G1ToG0);
        // G1->S
        FunctionStructure G1ToS=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(support0),Numbers.CGN(0.01));
        Operators.MapFunctionValues(G1ToS,14.0,support0,Operators.gaussian,16.0,1.0);
        G0ToDeath=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(G1ToS, G1ToS.min, G1ToS.max),0, G1ToS);
        simulation.theta[0].dyn.G1.density.put("S", G1ToS);
        // S->G2
        FunctionStructure SToG2=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(8.0),Numbers.CGN(0.01)); 
        Operators.MapFunctionValues(SToG2,7.99,8.0,Operators.constant,1.0);
        G0ToDeath=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(SToG2, SToG2.min, SToG2.max),0, SToG2);
        simulation.theta[0].dyn.S.density.put("G2", SToG2);
        // G2->M
        FunctionStructure G2ToM=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(support2),Numbers.CGN(0.01)); 
        Operators.MapFunctionValues(G2ToM,3.0,support2,Operators.continuousGeometricDistribution,3.0,pO2,C,B,M);
        G0ToDeath=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(G2ToM, G2ToM.min, G2ToM.max),0, G2ToM);
        simulation.theta[0].dyn.G2.density.put("M", G2ToM);
        // M->G1
        FunctionStructure MToG1=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(2.0),Numbers.CGN(0.01)); 
        Operators.MapFunctionValues(MToG1,1.99,2.0,Operators.constant,1.0);
        G0ToDeath=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(MToG1, MToG1.min, MToG1.max),0, MToG1);
        simulation.theta[0].dyn.M.density.put("G1", MToG1);    
            
        CellDynamicsOperators.PhaseFilling(simulation.theta[0].dyn);
            ///// Initial conditions
        /// Phase G0
        simulation.theta[0].G0= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(8.0),Numbers.CGN(step));
        Operators.MapFunctionValues(simulation.theta[0].G0,0.0,8.0,Operators.constant,0.0);
        /// Phase G1
        simulation.theta[0].G1= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(support0),Numbers.CGN(step));
        Operators.MapFunctionValues(simulation.theta[0].G1,0.0,10.0,Operators.constant,1.0);
        /// Phase S
        simulation.theta[0].S= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(8.0),Numbers.CGN(step));
        Operators.MapFunctionValues(simulation.theta[0].S,0.0,8.0,Operators.constant,0.0);
        /// Phase G2
        simulation.theta[0].G2= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(support2),Numbers.CGN(step));
        Operators.MapFunctionValues(simulation.theta[0].G2,0.0,support2,Operators.constant,0.0);
        /// Phase M
        simulation.theta[0].M= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(2.0),Numbers.CGN(step));
        Operators.MapFunctionValues(simulation.theta[0].M,0.0,2.0,Operators.constant,0.0);        
                
                
            ///// Transition to death
                indice=0;
                simulation.solution[0].transitionProbabilities[indice]=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(8.0),Numbers.CGN(0.01)); 
                Operators.MapFunctionValues(simulation.solution[0].transitionProbabilities[indice],0.0,8.0,Operators.gaussian,2.0,1.0);
                simulation.solution[0].transitionProbabilities[indice]=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(simulation.solution[0].transitionProbabilities[indice], simulation.solution[0].transitionProbabilities[indice].min, simulation.solution[0].transitionProbabilities[indice].max),0, simulation.solution[0].transitionProbabilities[indice]);
                ///// Cumulative function
                simulation.solution[0].oneMinusCumulativeFunctions[0]=Operators.AffineFunctionTransformation(-1.0, 1.0,Operators.CumulativeFunction(simulation.solution[0].transitionProbabilities[indice]));
            ///// Transition to G1
                indice=1;
                simulation.solution[0].transitionProbabilities[indice]=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(8.0),Numbers.CGN(0.01)); 
                Operators.MapFunctionValues(simulation.solution[0].transitionProbabilities[indice],0.0,8.0,Operators.gaussian,2.0,1.0);
                simulation.solution[0].transitionProbabilities[indice]=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(simulation.solution[0].transitionProbabilities[indice], simulation.solution[0].transitionProbabilities[indice].min, simulation.solution[0].transitionProbabilities[indice].max),0, simulation.solution[0].transitionProbabilities[indice]);
                ///// Cumulative function
                simulation.solution[0].oneMinusCumulativeFunctions[indice]=Operators.AffineFunctionTransformation(-1.0, 1.0,Operators.CumulativeFunction(simulation.solution[0].transitionProbabilities[indice]));
            //// comparison
                simulation.solution[0].probaDeathBeforeG1=Operators.IntegrateFunction(Operators.MultiplyFunctions(simulation.solution[0].transitionProbabilities[0], simulation.solution[0].oneMinusCumulativeFunctions[1]), simulation.solution[0].transitionProbabilities[0].min,simulation.solution[0].transitionProbabilities[0].max);
        /// Phase G1
            ///// Initial conditions
                simulation.solution[0].theta[1]= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(support0),Numbers.CGN(step));
                Operators.MapFunctionValues(simulation.solution[0].theta[1],0.0,10.0,Operators.constant,1.0);
            ///// Transition to G0
                indice=2;
                simulation.solution[0].transitionProbabilities[indice]=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(support0),Numbers.CGN(0.01)); 
                Operators.MapFunctionValues(simulation.solution[0].transitionProbabilities[indice],0.0,support0,Operators.gaussian,15.0,5.0);
                simulation.solution[0].transitionProbabilities[indice]=Operators.AffineFunctionTransformation(0.2/Operators.IntegrateFunction(simulation.solution[0].transitionProbabilities[indice], simulation.solution[0].transitionProbabilities[indice].min, simulation.solution[0].transitionProbabilities[indice].max),0, simulation.solution[0].transitionProbabilities[indice]);
                ///// Cumulative to G0
                simulation.solution[0].oneMinusCumulativeFunctions[indice]=Operators.AffineFunctionTransformation(-1.0, 1.0,Operators.CumulativeFunction(simulation.solution[0].transitionProbabilities[indice]));
            ///// Transition to S
                indice=3;
                simulation.solution[0].transitionProbabilities[indice]=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(support0),Numbers.CGN(0.01)); 
                Operators.MapFunctionValues(simulation.solution[0].transitionProbabilities[indice],14.0,support0,Operators.gaussian,16.0,1.0);
                simulation.solution[0].transitionProbabilities[indice]=Operators.AffineFunctionTransformation(0.8/Operators.IntegrateFunction(simulation.solution[0].transitionProbabilities[indice], simulation.solution[0].transitionProbabilities[indice].min, simulation.solution[0].transitionProbabilities[indice].max),0, simulation.solution[0].transitionProbabilities[indice]);
                ///// Cumulative to S
                simulation.solution[0].oneMinusCumulativeFunctions[indice]=Operators.AffineFunctionTransformation(-1.0, 1.0,Operators.CumulativeFunction(simulation.solution[0].transitionProbabilities[indice]));
                
            //// comparison
                simulation.solution[0].probaSBeforeG0=Operators.IntegrateFunction(Operators.MultiplyFunctions(simulation.solution[0].transitionProbabilities[3], simulation.solution[0].oneMinusCumulativeFunctions[2]), simulation.solution[0].transitionProbabilities[3].min,simulation.solution[0].transitionProbabilities[3].max);
        /// Phase S
            ///// Initial conditions
                simulation.solution[0].theta[2]= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(8.0),Numbers.CGN(step));
                Operators.MapFunctionValues(simulation.solution[0].theta[2],0.0,8.0,Operators.constant,0.0);
                ///// Transition
                indice=4;
                simulation.solution[0].transitionProbabilities[indice]=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(8.0),Numbers.CGN(0.01)); 
                Operators.MapFunctionValues(simulation.solution[0].transitionProbabilities[indice],7.99,8.0,Operators.constant,1.0);
                simulation.solution[0].transitionProbabilities[indice]=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(simulation.solution[0].transitionProbabilities[indice], simulation.solution[0].transitionProbabilities[indice].min, simulation.solution[0].transitionProbabilities[indice].max),0, simulation.solution[0].transitionProbabilities[indice]);
            ///// Cumulative function
                simulation.solution[0].oneMinusCumulativeFunctions[indice]=Operators.AffineFunctionTransformation(-1.0, 1.0,Operators.CumulativeFunction(simulation.solution[0].transitionProbabilities[indice]));
        /// Phase G2
            ///// Initial conditions
                simulation.solution[0].theta[3]= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(support2),Numbers.CGN(step));
                Operators.MapFunctionValues(simulation.solution[0].theta[3],0.0,support2,Operators.constant,0.0);
                ///// Transition
                indice=5;
                simulation.solution[0].transitionProbabilities[indice]=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(support2),Numbers.CGN(0.01)); 
                Operators.MapFunctionValues(simulation.solution[0].transitionProbabilities[indice],3.0,support2,Operators.continuousGeometricDistribution,3.0,pO2,C,B,M);
                simulation.solution[0].transitionProbabilities[indice]=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(simulation.solution[0].transitionProbabilities[indice], simulation.solution[0].transitionProbabilities[indice].min, simulation.solution[0].transitionProbabilities[indice].max),0, simulation.solution[0].transitionProbabilities[indice]);
            ///// Cumulative function
                simulation.solution[0].oneMinusCumulativeFunctions[indice]=Operators.AffineFunctionTransformation(-1.0, 1.0,Operators.CumulativeFunction(simulation.solution[0].transitionProbabilities[indice]));
                
        /// Phase M
            ///// Initial conditions
                simulation.solution[0].theta[4]= Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(2.0),Numbers.CGN(step));
                Operators.MapFunctionValues(simulation.solution[0].theta[4],0.0,2.0,Operators.constant,0.0);
                ///// Transition
                indice=6;
                simulation.solution[0].transitionProbabilities[indice]=Operators.createFunction(Numbers.CGN(0.0),Numbers.CGN(2.0),Numbers.CGN(0.01)); 
                Operators.MapFunctionValues(simulation.solution[0].transitionProbabilities[indice],1.99,2.0,Operators.constant,1.0);
                simulation.solution[0].transitionProbabilities[indice]=Operators.AffineFunctionTransformation(1.0/Operators.IntegrateFunction(simulation.solution[0].transitionProbabilities[indice], simulation.solution[0].transitionProbabilities[indice].min, simulation.solution[0].transitionProbabilities[indice].max),0, simulation.solution[0].transitionProbabilities[indice]);
            ///// Cumulative function
                simulation.solution[0].oneMinusCumulativeFunctions[indice]=Operators.AffineFunctionTransformation(-1.0, 1.0,Operators.CumulativeFunction(simulation.solution[0].transitionProbabilities[indice]));

//        for (int i=0;i<2000;i++){            
//            Operators.ComputeSimulationNextValue(simulation);
//        }
//        System.err.format("DeathBG1 = %f \n", simulation.solution[0].probaDeathBeforeG1);
//        System.err.format("SBG2 = %f \n", simulation.solution[0].probaSBeforeG0);
//        FunctionStructure tempProb=TranslateFunction(simulation.solution[0].theta[1].min, simulation.solution[0].transitionProbabilities[3]);
//        FunctionStructure     tempCumul=TranslateFunction(simulation.solution[0].theta[1].min, simulation.solution[0].oneMinusCumulativeFunctions[3]);
//        FunctionStructure    tempCumulComp=TranslateFunction(simulation.solution[0].theta[1].min, simulation.solution[0].oneMinusCumulativeFunctions[2]);
//        FunctionStructure temp=MultiplyFunctions( MultiplyFunctions(PowerOfFunction(tempCumulComp,simulation.solution[0].probaSBeforeG0),tempProb),PowerOfFunction(tempCumul, -simulation.solution[0].probaDeathBeforeG1));
//        double nextVal=IntegrateFunction( MultiplyFunctions( simulation.solution[0].theta[1], MultiplyFunctions( MultiplyFunctions(PowerOfFunction(tempCumulComp,simulation.solution[0].probaSBeforeG0),tempProb),PowerOfFunction(tempCumul, -simulation.solution[0].probaDeathBeforeG1))),tempProb.min,tempProb.max);
//           
//        System.err.format("nextVal = %f \n", nextVal);
////         Display Function
        GUI win1 =new GUI();    
        win1.SetFunction(simulation.theta[0].dyn.S.ThetaConvolution);
        win1.setVisible(true);
//        GUI win2 =new GUI();
//        win2.SetFunction(simulation.solution[0].theta[1]);
//        win2.setVisible(true);
//        GUISolution win3 =new GUISolution();
//        win3.SetFunction(simulation.solution[0]);
//        win3.setVisible(true);
//        GUISimulation win4 =new GUISimulation();
//        win4.SetFunction(simulation);
//        win4.setVisible(true);
    }
}
