/*******************************************************************************
* File:       Export.java
* Programmer: Uriel Salazar
* Date:       May 28, 2017
* Course:     CSC-18B
* Purpose:    Export Class for binary files
*******************************************************************************/

//package class
package guicalculator;

//import packages
import java.io.Serializable;

//declare class
public class Export implements Serializable
{
    String calculation;

    public Export(String calculation)
    {
        this.calculation = calculation;
    }

    @Override
    public String toString()
    {
        return "Calculation on File [" + calculation + "]";
    }  
}