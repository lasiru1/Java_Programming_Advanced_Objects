/*******************************************************************************
* File:       Car.java
* Programmer: Uriel Salazar
* Date:       May 24, 2017
* Course:     CSC-18B
* Purpose:    Contain member variables: carID, carMake, carModel, carYear,
              carMileage
*******************************************************************************/

//package class
package carsdatabase;

//declare classes
public class Car
{
    //declare variables
    private int carID;
    private String carMake;
    private String carModel;
    private String carYear;
    private int carMileage;
    
    //default constructor
    public Car()
    {
        //empty
    }
    
    //constructor
    public Car(int carID, String carMake, String carModel, String carYear,
            int carMileage)
    {
        setCarID(carID);
        setCarMake(carMake);
        setCarModel(carModel);
        setCarYear(carYear);
        setCarMileage(carMileage);
    }
    
    //carID setter/getter
    public void setCarID(int carID)
    {
        this.carID = carID;
    }
    public int getCarID()
    {
        return carID;
    }
    
    //carMake setter/getter
    public void setCarMake(String carMake)
    {
        this.carMake = carMake;
    }
    public String getCarMake()
    {
        return carMake;
    }
    
    //carModel setter/getter
    public void setCarModel(String carModel)
    {
        this.carModel = carModel;
    }
    public String getCarModel()
    {
        return carModel;
    }
    
    //carYear setter/getter
    public void setCarYear(String carYear)
    {
        this.carYear = carYear;
    }
    public String getCarYear()
    {
        return carYear;
    }
    
    //carMileage setter/getter
    public void setCarMileage(int carMileage)
    {
        this.carMileage = carMileage;
    }
    public int getCarMileage()
    {
        return carMileage;
    }
//end class
}   
