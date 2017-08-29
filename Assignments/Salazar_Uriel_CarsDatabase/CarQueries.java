/*******************************************************************************
* File:       CarQueries.java
* Programmer: Uriel Salazar
* Date:       May 24, 2017
* Course:     CSC-18B
* Purpose:    Contain the prepared statements to execute queries and interact
              with the database 
*******************************************************************************/

//package class
package carsdatabase;

//import packages
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;

//declare class
public class CarQueries
{
    //declare variables
    private static final String URL = "jdbc:derby://localhost:1527/Cars";
    private static final String USERNAME = "cars";
    private static final String PASSWORD = "vroom";
    
    //instantiate connection object
    private Connection conn;
    
    //declare prepared statements
    private PreparedStatement selectAllCars;
    private PreparedStatement selectCarsByModel;
    private PreparedStatement insertNewCar;
    private PreparedStatement deleteCurrentCar;
    
    //constructor
    public CarQueries()
    {
        //try-catch block
        try
        {
            //connect to database
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            
            //create query that selects all entries in the database
            selectAllCars = conn.prepareStatement("SELECT * FROM Cars");
            
            //create query that selects entries with a specific model
            selectCarsByModel = conn.prepareStatement(
                    "SELECT * FROM Cars WHERE CarModel = ?");
            
            //create insert that adds a new entry into the database
            insertNewCar = conn.prepareStatement(
                    "INSERT INTO Cars " +
                    "(CarMake, CarModel, CarYear, CarMileage)" +
                    "VALUES (?, ?, ?, ?)");
            
            //create delete that removes an entry from the database
            deleteCurrentCar = conn.prepareStatement(
                    "DELETE FROM Cars WHERE CarID = ?");
            
            
        }
        catch(SQLException sqlE)
        {
            //print error message
            System.err.println(sqlE.getMessage());
            System.exit(1);
        }
    }
    
    /**
     * declare method "getAllCars"
     * selects all cars in the database
     */
    public List< Car > getAllCars()
    {
        List< Car > results = null;
        ResultSet resultSet = null;
        
        //try-catch block
        try
        {
            //executeQuery returns ResultSet containing matching queries
            resultSet = selectAllCars.executeQuery();
            
            //instantiate array list object
            results = new ArrayList< Car >();
            
            //loop through resultSet to add member variables
            while(resultSet.next())
            {
                results.add(new Car(
                        resultSet.getInt("CarID"),
                        resultSet.getString("CarMake"),
                        resultSet.getString("CarModel"),
                        resultSet.getString("CarYear"),
                        resultSet.getInt("CarMileage")));
            }
        }
        catch(SQLException sqlE)
        {
            //print error message
            System.err.println(sqlE.getMessage());
        }
        finally
        {
            //try-catch block for closing ResultSet object
            try
            {
                resultSet.close();
            }
            catch(SQLException sqlE)
            {
                //print error message
                System.err.println(sqlE.getMessage());
                close();
            }
        }
        return results;
    }
    
    /**
     * declare method "getCarsByModel"
     * selects cars by model name
     */
    public List< Car > getCarsByModel(String model)
    {
        List< Car > results = null;
        ResultSet resultSet = null;
        
        //try-catch block
        try
        {
            //specify car model
            selectCarsByModel.setString(1, model);
            
            //executeQuery returns ResultSet containing matching queries
            resultSet = selectCarsByModel.executeQuery();
            
            //instantiate array list object
            results = new ArrayList< Car >();
            
            //loop through resultSet to add member variables
            while(resultSet.next())
            {
                results.add(new Car(
                        resultSet.getInt("CarID"),
                        resultSet.getString("CarMake"),
                        resultSet.getString("CarModel"),
                        resultSet.getString("CarYear"),
                        resultSet.getInt("CarMileage")));
            }  
        }
        catch(SQLException sqlE)
        {
            //print error message
            System.err.println(sqlE.getMessage());
        }
        finally
        {
            //try-catch block for closing ResultSet object
            try
            {
                resultSet.close();
            }
            catch(SQLException sqlE)
            {
                //print error message
                System.err.println(sqlE.getMessage());
                close();
            }
        }
        return results;
    }
    
    /**
     * declare method "addCar"
     * add a car
     */
    public int addCar(String make, String model, String year, int mileage)
    {
        //declare variables
        int result = 0;
        
        //set parameters, then execute insertNewCar
        try
        {
            insertNewCar.setString(1, make);
            insertNewCar.setString(2, model);
            insertNewCar.setString(3, year);
            insertNewCar.setInt(4, mileage);
            
            //insert the new entry; returns # of rows updated
            result = insertNewCar.executeUpdate();
        }
        catch(SQLException sqlE)
        {
            //print error message
            System.err.println(sqlE.getMessage());
            close();
        }
        return result;
    }
    
    /**
     * declare method "removeCar"
     * remove a car
     */
    public int removeCar(int id)
    {
        //declare variables
        int result = 0;
        
        //set parameters, then execute remove
        try
        {
            //specifiy car ID
            deleteCurrentCar.setInt(1, id);
            
            //delete the current entry
            result = deleteCurrentCar.executeUpdate();
        }
        catch(SQLException sqlE)
        {
            //print error message
            System.err.println(sqlE.getMessage());
            close();
        }
        return result;
    }

    /**
     * declare method "close"
     * closes the database connection
     */
    public void close()
    {
        //try-catch block for closing connection
        try
        {
            conn.close();
        }
        catch(SQLException sqlE)
        {
            //print error message
            System.err.println(sqlE.getMessage());
        }
    }
//end class CarQueries
}

