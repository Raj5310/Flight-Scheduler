/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flightschedulerrajpokhreldzp5310;
import com.sun.xml.internal.ws.commons.xmlutil.Converter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author Raj
 */
public class Waitlist 
{
     private static ArrayList<WaitlistEntry> waitlist = new ArrayList();
    
     /**
      * Method to add the customers to waitlist
      * @param day
      * @return 
      */
    public static ArrayList<WaitlistEntry> getWaitlistStatus(String day) 
    {
        try 
        {
            String SQLStmt = "SELECT DISTINCT * FROM WAITLIST WHERE DATE = ?";
            PreparedStatement stmt = DataBase.getConnection().prepareStatement(SQLStmt);
            stmt.setString(1, day);
            ResultSet results = stmt.executeQuery();
            while (results.next())
            {
                waitlist.add
                (new WaitlistEntry(results.getString("FLIGHT"),results.getString("NAME"),
                        results.getString("DATE"),(Date)results.getObject("TIME")));       
            }
        }
        catch (SQLException ex) 
        {
            ex.printStackTrace();
        }
        return waitlist;
    }
    
   /**
    * Method to remove the customers from the waitlist
    * @param flightname
    * @return 
    */
     public static ArrayList<String> dropFlight(String flightname)
     {
        ArrayList<String> drop = new ArrayList();
        try
        {
        ArrayList<String> dates = Day.getdates();
        Flight.dropFlight(flightname);
        drop.add("**********  Removing Customers from the Waitlist  ************\n");
         for(int i=0; i< dates.size();i++)
         {
             ArrayList<String> Customer = new ArrayList();
             PreparedStatement getnames= DataBase.getConnection().prepareStatement("SELECT NAME FROM WAITLIST WHERE DATE=? and FLIGHT=?");
             getnames.setString(1,dates.get(i));
             getnames.setString(2,flightname);
             ResultSet resultSet = getnames.executeQuery(); 
             while (resultSet.next())
                Customer.add(resultSet.getObject(1).toString());
             for(int j=0; j < Customer.size(); j++)
                 drop.add(" * "+ Customer.get(j)+" was removed from the waitlist on date"+ dates.get(i) + "\n");
         } 
            PreparedStatement Dropflight= DataBase.getConnection().prepareStatement("DELETE FROM WAITLIST WHERE FLIGHT=?");
            Dropflight.setString(1,flightname);
            Dropflight.executeUpdate();
        }
        catch(SQLException e)
        {
             e.printStackTrace();
        }
        return drop;
    }
    
}
