/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightschedulerrajpokhreldzp5310;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Raj
 */
public class DataBase 
{
    private static Connection connection;
    private final static String DB_URL = "jdbc:derby://localhost:1527/FlightSchedule";
    
    public static Connection getConnection() 
    {
        if (connection == null) 
        {
            try
            {
                connection = DriverManager.getConnection(DB_URL, "java", "pennstate");
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace();
            }
        }
    
        return connection;
    }
    
}
