/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightschedulerrajpokhreldzp5310;
import java.util.Objects;
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
/**
 *
 * @author Raj
 */

public class Flight 
{
    private static final ArrayList<FlightEntry> flights = new ArrayList();
    private static int seatNumber = 0;

    /**
     * all the flight to the DB
     * @param flightEntry 
     */
    public static void addFlightsandSeats(FlightEntry flightEntry)
    {
        PreparedStatement SQLStmt;

        try
            {
                SQLStmt = DataBase.getConnection().prepareStatement("INSERT INTO FLIGHT VALUES(?,?)");
                SQLStmt.setString(2, flightEntry.getName().toString());
                SQLStmt.setInt(1, flightEntry.getNumberSeats());
                SQLStmt.executeUpdate();
            }
            catch(SQLException ex)
            {
                ex.printStackTrace();
            }        
    }

    /**
     * Get all the flights from the DB
     * @return 
     */
    public static ArrayList<String> getFlights() 
    {
        ArrayList<String> flights = new ArrayList();
          try
        {        
            String sql ="SELECT * FROM FLIGHT";
            PreparedStatement stat = DataBase.getConnection().prepareStatement(sql);
            ResultSet set = stat.executeQuery();
           
           while(set.next())
           {
              flights.add(set.getString("FLIGHT"));
         
           }          
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return flights;
    }

    /**
     * Get the numbers of seats for each each flight
     * @param flightName
     * @return 
     */
    public static int getFlightSeats(String flightName)
    {
            try 
            {     
                String SQLStmt = "SELECT SEATS FROM FLIGHT WHERE FLIGHT =?";
                PreparedStatement prepStmt = DataBase.getConnection().prepareStatement(SQLStmt);
                prepStmt.setString(1, flightName);
                ResultSet results = prepStmt.executeQuery();
                while (results.next())
                {
                       seatNumber = (results.getInt("SEATS"));
                }
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace();
            }
        
        return seatNumber;
    }
    
    /**
     * Method to update the seats available
     * @param flightName 
     */
    public static void updateSeats(String flightName)
    {
        int numofSeats = getFlightSeats(flightName);
        System.out.print(numofSeats);
        try 
        {
            PreparedStatement SQLStmt;
            SQLStmt = DataBase.getConnection().prepareStatement("UPDATE FLIGHT SET SEATS =? WHERE FLIGHT =?");
            SQLStmt.setInt(1,numofSeats + 1);
            SQLStmt.setString(2,flightName); 
            SQLStmt.executeUpdate();
        } 
        catch (SQLException ex) 
        {
                ex.printStackTrace();
        }

    }
    
    /**
     * Remove Flight from the DB
     * @param Flight 
     */
    public static void drop(String Flight)
    { 
         try 
            {
                String SQLStmt = "DELETE FROM FLIGHT WHERE FLIGHT = ?";
                PreparedStatement results = DataBase.getConnection().prepareStatement(SQLStmt);
                results.setString(1,Flight);
                results.execute();
            }
            catch (SQLException ex) 
            {
                ex.printStackTrace();
            }   
    }
    
    /**
     * Remove flight and customer from the waitling list
     * Method that vaildates to see if there are any seats available to rebook the customer for the flight that has just been removed
     * @param flightname
     * @return 
     */
   public static ArrayList<String> dropFlight(String flightname)
   {
       ArrayList<String>drop= new ArrayList();       
       try 
       {   
           ArrayList<String> dates = Day.getdates();
           Flight.drop(flightname);
           
           drop.add("*************** Rebooking Customers to another Flight Available ***************\n");
           drop.add("***************      Flight "+flightname+" has been canced "+ " ********************************\n");

           for(int i=0; i< dates.size(); i++)
           {
                 ArrayList<String> Customer = new ArrayList();
                 PreparedStatement getnames = DataBase.getConnection().prepareStatement("SELECT NAME FROM BOOKING WHERE FLIGHT=? and DATE=?");
                 getnames.setString(1,flightname);
                 getnames.setString(2,dates.get(i));
                 ResultSet resultSet = getnames.executeQuery(); 
                 
                 while (resultSet.next())
                     Customer.add(resultSet.getObject(1).toString());
                 
                 for(int j=0; j< Customer.size(); j++)
                 {   //Rebooking customers to another flight if possible
                    drop.add(rebookingCustomers(Customer.get(j), dates.get(i)).toString());
                 }                
            } 
                PreparedStatement Dropflight = DataBase.getConnection().prepareStatement("DELETE FROM BOOKING WHERE FLIGHT=?");
                Dropflight.setString(1,flightname);
                Dropflight.executeUpdate();
         }
       
        catch(SQLException ex)
        {
             ex.printStackTrace();
        }

       
        return drop;
    }
   
   /**
    * method to rebook customer on the other flight available
    * @param customer
    * @param date
    * @return 
    */
   public static String rebookingCustomers(String customer, String date)
   {
     String message = "";
    {
        ArrayList<String> getallFlights= Flight.getFlights();
        int status = 0;
        for(int i=0; i<getallFlights.size(); i++)
        {
            status = Booking.addBooking(new BookingEntry(customer, date, getallFlights.get(i)));
            if(status == 1)
                return message = "----> "+customer+" has successfully rebooked on flight "+ getallFlights.get(i)+"\n";
            else
                return message = "There are no flights available";
        }
    }
        return "----->"+ customer+" failed to book another flight. There are no flights available \n\n";
   }
}
