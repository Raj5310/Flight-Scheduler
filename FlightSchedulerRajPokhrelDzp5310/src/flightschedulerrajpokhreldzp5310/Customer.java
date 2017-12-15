/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightschedulerrajpokhreldzp5310;
import static com.sun.corba.se.impl.util.Utility.printStackTrace;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;

/**
 *
 * @author Raj
 */
public class Customer
{
    private String name;

    /**
     * 
     * @param name 
     */
    public Customer(String name) 
    {
        this.name = name;
    }
    
    /**
     * 
     * @return 
     */
    public String getName() 
    {
        return name;
    }

    /**
     * 
     * @param name 
     */
    public void setName(String name)
    {
        this.name = name;
    }
     
    /**
     * add customer to the DB
     * @param name 
     */
    public static void addCustomer(String name)
    {
           PreparedStatement SQLStmt;
            try
            {
                SQLStmt = DataBase.getConnection().prepareStatement("INSERT INTO CUSTOMER VALUES(?)");
                SQLStmt.setString(1, name);
                SQLStmt.executeUpdate();     
            }
            catch(Exception ex)
            {
                printStackTrace();
            }
    }
    
    /**
     * get all the customers from the DB
     * @return 
     */
    public static ArrayList<String> getCustomers()
    {
       ArrayList<String> Customers = new ArrayList();
         try
        {        
            String sql ="SELECT * FROM CUSTOMER";
            PreparedStatement stat = DataBase.getConnection().prepareStatement(sql);
            ResultSet set = stat.executeQuery();
           
           while(set.next())
           {
               Customers.add(set.getString("NAME"));
           }          
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
         
        return Customers;
    }
}
