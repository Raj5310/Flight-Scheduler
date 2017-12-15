/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightschedulerrajpokhreldzp5310;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
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
public class Booking 
{    
    /**
     * Method that determine if the customer should be booked or waitlisted to the flight
     * @param newBooking
     * @return 
     */
     public static int addBooking(BookingEntry newBooking)
    {
        PreparedStatement SQLStmt;
        int seats = 0, status = 0;
        seats = Flight.getFlightSeats(newBooking.getFlight().toString());
        boolean availableSeats = (seats !=0); //convert int to bool        
        
        if(availableSeats)
        {
            try
            {
                SQLStmt = DataBase.getConnection().prepareStatement("INSERT INTO BOOKING VALUES(?,?,?)");
                SQLStmt.setString(1, newBooking.getCustomer().toString());
                SQLStmt.setString(2, newBooking.getFlight().toString());
                SQLStmt.setString(3, newBooking.getDay().toString());
                status = SQLStmt.executeUpdate();              
                if(status == 1)
                {
                    SQLStmt = DataBase.getConnection().prepareStatement("UPDATE FLIGHT SET SEATS =? WHERE FLIGHT =?");
                    SQLStmt.setInt(1,seats-1);
                    SQLStmt.setString(2, newBooking.getFlight()); 
                    status = SQLStmt.executeUpdate();
                }
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
            }
        }
        else
        {
             try 
           {
                SQLStmt = DataBase.getConnection().prepareStatement("INSERT INTO WAITLIST VALUES(?,?,?,?)");  
                SQLStmt.setString(1, newBooking.getDay().toString());
                SQLStmt.setString(2, newBooking.getFlight().toString());
                SQLStmt.setString(3, new Timestamp(System.currentTimeMillis()).toString());
                SQLStmt.setString(4, newBooking.getCustomer().toString());
                status = SQLStmt.executeUpdate();
            } 
             catch (SQLException ex) 
            {
                ex.printStackTrace();
            }
        }
        
        return status;
    }
    
     
     /**
      * Methods that get the customer by name and return the Booking Entity
      * @param userName
      * @return 
      */
    
    public static ArrayList<BookingEntry> getCustomerByName(String userName) 
    {
            ArrayList<BookingEntry> newbook= new ArrayList();
            PreparedStatement SQLStmt;
            try 
            {
                SQLStmt = DataBase.getConnection().prepareStatement("SELECT FLIGHT,DATE FROM BOOKING WHERE NAME=?");
                SQLStmt.setString(1,userName);
                ResultSet resultSet = SQLStmt.executeQuery(); 
                while(resultSet.next()) 
                {
                    newbook.add(new BookingEntry(userName,resultSet.getObject(1).toString(),resultSet.getObject(2).toString()));
                }
          
            } catch (SQLException ex) 
            {
                ex.printStackTrace();
            }
            
        return newbook;
    }
    
    
    /**
     * Get the Booking by flight and day
     * @param date
     * @param flight
     * @return 
     */
    
    public static ArrayList<Customer> getBookingsByFlightAndDay(String date, String flight)
    {
        ArrayList<Customer> customers = new ArrayList();
        try 
        {
            String SQLStmt = "SELECT NAME FROM BOOKING WHERE FLIGHT =? AND DATE=?";
            PreparedStatement prepStmt = DataBase.getConnection().prepareStatement(SQLStmt);
            prepStmt.setString(1, flight);
            prepStmt.setString(2, date);
            ResultSet results = prepStmt.executeQuery();
            while (results.next())
            {
                customers.add(new Customer(results.getString("NAME")));
            }
        } 
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }        
        return customers;
    }
   
    /**
     * gets all the customers from the DB
     * @return 
     */
   public static ArrayList<String> getCustomers() 
    {
        ArrayList<String> Customers = new ArrayList();
         try
        {        
            String sql ="SELECT * FROM BOOKING";
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
   
   /**
    * method to delete customer from Booking and vaildates to refill the remaining seats from the waitlist
    * @param name
    * @param date
    * @return 
    */
   
   public static String deleteBooking(String name,String date)
    {
        String message= "";
        String flightname ="";
        String passenger ="";
        
        try
        {
            String SQLStmt = "SELECT FLIGHT FROM BOOKING WHERE NAME =? AND DATE=?";
            PreparedStatement prepStmt = DataBase.getConnection().prepareStatement(SQLStmt);
            prepStmt.setString(1, name);
            prepStmt.setString(2, date);
            ResultSet results = prepStmt.executeQuery();
            while (results.next())
            {
               flightname = (results.getString("FLIGHT"));
            }
            //add seats 
            Flight.updateSeats(flightname);
            PreparedStatement deleteCustomer = DataBase.getConnection().prepareStatement("DELETE FROM BOOKING WHERE NAME=? AND DATE=?");
            deleteCustomer.setString(1,name);
            deleteCustomer.setString(2,date);
            deleteCustomer.executeUpdate();
            
            PreparedStatement getnames = DataBase.getConnection().prepareStatement("SELECT NAME FROM WAITLIST WHERE DATE=? AND FLIGHT=? ORDER BY TIME");
            getnames.setString(1,date);
            getnames.setString(2,flightname);
            ResultSet resultSet2 = getnames.executeQuery(); 
            resultSet2.next();
            passenger = resultSet2.getObject(1).toString();
           
            if(!passenger.isEmpty())
            {
                Booking.addBooking(new BookingEntry(passenger, date, flightname));
                PreparedStatement deleteWaitlist = DataBase.getConnection().prepareStatement("DELETE FROM WAITLIST WHERE NAME=? and DATE=?");
                deleteWaitlist.setString(1,passenger);
                deleteWaitlist.setString(2,date);
                deleteWaitlist.executeUpdate();
                message = name+" was removed from Booking from flight "+flightname+ " on date "+date+ "\n"+passenger+ " was added to Booking from the waitlist on flight "+ flightname+" on date "+date +"\n";
            }
            else if(passenger.isEmpty())
            {
                message = "The waitlist is empty, a seat is added to the flight "+flightname +"\n";
            }
            else
                message = "The available seats are added to the flight "+flightname +"\n";
         }
        catch(SQLException e)
        {
             e.printStackTrace();
        }
        
        return message;
    }
}
