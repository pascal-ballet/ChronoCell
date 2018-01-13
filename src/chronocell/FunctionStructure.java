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
}


